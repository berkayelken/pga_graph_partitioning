package com.berkay.yelken.parallel.ga.model.genetic;

import com.berkay.yelken.parallel.ga.model.Node;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chromosome implements Comparable<Chromosome> {
	private List<Gene> genes;
	private ConcurrentMap<Integer, Double> costMap = new ConcurrentHashMap<>();
	private ConcurrentMap<Integer, Integer> nonZeroMap = new ConcurrentHashMap<>();
	private double totalCost;

	public Chromosome(List<Gene> genes) {
		this.genes = genes;
	}

	public Chromosome(int geneInterval, long genesNum) {
		List<Gene> genes = Stream.generate(() -> new Gene(geneInterval)).limit(genesNum).collect(Collectors.toList());
		this.genes = Collections.synchronizedList(genes);
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public ConcurrentMap<Integer, Double> getCostMap() {
		return costMap;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public ConcurrentMap<Integer, Integer> getNonZeroMap() {
		return nonZeroMap;
	}

	public double getImbalanceValue() {
		int max = getNonZeroMap().values().stream().max(Integer::compareTo).get();
		AtomicInteger total = new AtomicInteger();
		getNonZeroMap().values().stream().forEach(nonZero -> total.accumulateAndGet(nonZero, (a, b) -> a + b));

		double avg = Double.valueOf(total.get()) / Double.valueOf(getNonZeroMap().size());

		return Double.valueOf(max)/avg;
	}

	public void handleAddGene(int partition, Node node) {
		double totalWeight = getCostMap().containsKey(partition) ? getCostMap().get(partition) : 0.0;
		totalWeight += node.getWeight();
		getCostMap().put(partition, totalWeight);
		getNonZeroMap().put(node.getId(), node.getYVals().size());
		Gene gene = new Gene();
		gene.setValue(node.getId());
		getGenes().add(gene);
	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(getTotalCost(), o.getTotalCost());
	}

}
