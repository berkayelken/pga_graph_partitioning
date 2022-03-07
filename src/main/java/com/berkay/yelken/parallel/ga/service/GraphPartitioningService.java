package com.berkay.yelken.parallel.ga.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.berkay.yelken.parallel.ga.model.Node;
import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.GraphPartitioning;
import com.berkay.yelken.parallel.ga.model.GraphSize;
import com.berkay.yelken.parallel.ga.model.Partition;
import com.berkay.yelken.parallel.ga.model.request.RequestModel;
import com.berkay.yelken.parallel.ga.model.response.PartitionResponse;
import com.berkay.yelken.parallel.ga.model.response.ResponseModel;

import static com.berkay.yelken.parallel.ga.util.SeedUtil.getSeedList;

@Service
public class GraphPartitioningService {
	@Autowired
	private GraphService graphService;

	@Autowired
	private GeneticService geneticService;

	public ResponseModel doPartitioning(RequestModel request) {
		ResponseModel res = new ResponseModel();
		GraphSize size = request.getSize();

		Graph graph = graphService.createGraph(size, request.isUnweighted());
		List<Chromosome> seedList = getSeedList(size, graph, request.getPartitionCount(), request.getSeedCount());

		LocalTime start = LocalTime.now();

		GraphPartitioning model = geneticService.applyAlgorithm(graph, res, request.getInitialPopulationType(), request.getPopulationSize(),
				request.getGenerationCount(), request.getPartitionCount(), seedList);

		doPartitioning(model, res);
		LocalTime end = LocalTime.now();
		long time = Duration.between(start, end).toMillis();
		double avg = time / request.getGenerationCount();
		res.setAvgTime(avg + " ms");
		res.setTotalTime(time + " ms");

		return res;
	}

	private void doPartitioning(GraphPartitioning model, ResponseModel res) {
		List<Node> sorted =  model.getGraph().getNodesMap().values().stream()
				.sorted((n1, n2) -> Double.compare(n2.getWeight(), n1.getWeight())).collect(Collectors.toList());
		LinkedList<Node> nodes = new LinkedList<>(sorted);
		model.getBest().getGenes().stream().forEach(g -> model.addToPartition(g.getValue(), nodes.pop()));

		model.getPartitions().entrySet().parallelStream().forEach(entry -> {
			Partition partition = entry.getValue();
			int key = entry.getKey();

			partition.setCost(model.getBest().getCostMap().get(key));
			partition.setNonZeroCount(model.getBest().getNonZeroMap().get(key));
		});
		model.getPartitions().entrySet().parallelStream().forEach(entry -> handlePartition(entry, res, model));

	}

	private void handlePartition(Entry<Integer, Partition> entry, ResponseModel res, GraphPartitioning model) {
		Partition part = entry.getValue();
		int size = part.getNodes().size();
		int key = entry.getKey();

		int partitionSize = model.getPartitions().size();
		part.setSize(size);
		PartitionResponse partRes = new PartitionResponse();
		partRes.setSize(size);
		partRes.setCost(part.getCost() / partitionSize);
		partRes.setNonZeroCount(part.getNonZeroCount());
		res.incrementCost(partRes.getCost());
		res.getPartitions().put(key + 1, partRes);
	}

}
