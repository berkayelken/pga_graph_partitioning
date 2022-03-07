package com.berkay.yelken.parallel.ga.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.Node;
import com.berkay.yelken.parallel.ga.model.fitness.FitnessModel;
import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import com.berkay.yelken.parallel.ga.model.genetic.Generation;

public final class FitnessHandler {

	public static List<Chromosome> calculateFitness(Generation g, Graph graph) {
		Stream<Chromosome> generation = g.getChromosomes().parallelStream().map(c -> calculateFitness(c, graph));

		return generation.sorted().collect(Collectors.toList());
	}

	private static Chromosome calculateFitness(Chromosome c, Graph graph) {

		ConcurrentMap<Integer, ConcurrentMap<Integer, Node>> partitionMap = getChromosomePartition(c, graph);
		calculatePartitionsCosts(c, partitionMap);
		return c;
	}

	private static ConcurrentMap<Integer, ConcurrentMap<Integer, Node>> getChromosomePartition(Chromosome c, Graph graph) {
		ConcurrentMap<Integer, ConcurrentMap<Integer, Node>> partitionMap = new ConcurrentHashMap<>();
		LinkedList<Node> nodes = new LinkedList<>(graph.getNodeList());

		c.getGenes().stream().forEach(gene -> {
			ConcurrentMap<Integer, Node> nodeList = partitionMap.get(gene.getValue());
			if(nodeList == null)
				nodeList = new ConcurrentHashMap<>();
			Node node = nodes.pop();
			nodeList.put(node.getId(), node);
			partitionMap.put(gene.getValue(), nodeList);
		});

		return partitionMap;
	}

	private static void calculatePartitionsCosts(Chromosome c, ConcurrentMap<Integer, ConcurrentMap<Integer, Node>> partitionMap) {

		partitionMap.entrySet().parallelStream().forEach(entry -> {
			Set<Integer> allYVals = new ConcurrentSkipListSet<>();
			AtomicInteger nonZeroCount = new AtomicInteger();
			 entry.getValue().values().parallelStream().forEach(node -> {
				 allYVals.addAll(node.getYVals());
				 nonZeroCount.accumulateAndGet(node.getYVals().size(), (a, b) -> a+ b);
			 });
			long cost = entry.getValue().values().stream().filter(node -> !allYVals.contains(node.getId())).count();
			c.getCostMap().put(entry.getKey(), Double.valueOf(cost));
			c.getNonZeroMap().put(entry.getKey(), nonZeroCount.get());
		});

		AtomicReference<Double> costOfParts = new AtomicReference<>(0.0);
		c.getCostMap().values().stream().forEach(cost -> costOfParts.updateAndGet(cp -> cp + cost));
		c.setTotalCost(costOfParts.get());
	}
}
