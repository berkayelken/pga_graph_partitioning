package com.berkay.yelken.parallel.ga.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.GraphPartitioning;
import com.berkay.yelken.parallel.ga.model.GraphSize;
import com.berkay.yelken.parallel.ga.model.Partition;
import com.berkay.yelken.parallel.ga.model.request.RequestModel;
import com.berkay.yelken.parallel.ga.model.response.PartitionResponse;
import com.berkay.yelken.parallel.ga.model.response.ResponseModel;

@Service
public class GraphPartitioningService {
	@Autowired
	private GraphService graphService;

	@Autowired
	private GeneticService geneticService;

	public ResponseModel doPartitioning(RequestModel request) {
		ResponseModel res = new ResponseModel();
		GraphSize size = GraphSize.valueOf(request.getSize());
		if (size == null)
			size = GraphSize.SMALL;

		Graph graph = graphService.createGraph(size);
		LocalTime start = LocalTime.now();
		GraphPartitioning model = geneticService.applyAlgorithm(graph, res, request.getPopulationSize(),
				request.getGenerationCount());
		doPartitioning(model, res);
		LocalTime end = LocalTime.now();
		long time = Duration.between(start, end).toMillis();
		double avg = time / request.getGenerationCount();
		res.setAvgTime(avg + " ms");
		res.setTotalTime(time + " ms");

		return res;
	}

	private void doPartitioning(GraphPartitioning model, ResponseModel res) {
		AtomicInteger counter = new AtomicInteger();
		model.getBest().getGenes().stream().forEach(
				g -> model.addToPartition(g.getValue(), model.getGraph().getNodeByID(counter.incrementAndGet())));

		model.getPartitions().entrySet().stream().forEach(entry -> handlePartition(entry, res));

	}

	private void handlePartition(Entry<Integer, Partition> entry, ResponseModel res) {
		Partition part = entry.getValue();
		int size = part.getNodes().size();
		int key = entry.getKey();

		part.setSize(size);
		PartitionResponse partRes = new PartitionResponse();
		partRes.setSize(size);
		res.getPartitions().put(key + 1, partRes);
	}

}
