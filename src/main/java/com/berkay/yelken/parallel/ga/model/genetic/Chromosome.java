package com.berkay.yelken.parallel.ga.model.genetic;

import static java.lang.Double.NaN;
import static java.lang.Math.pow;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chromosome implements Comparable<Chromosome> {
	private List<Gene> genes;
	private double costFitness = NaN;
	private double balanceFitness = NaN;
	private double fitness = NaN;

	public Chromosome(List<Gene> genes) {
		this.genes = genes;
	}

	public Chromosome(int geneInterval, long genesNum) {
		genes = Stream.generate(() -> new Gene(geneInterval)).limit(genesNum).collect(Collectors.toList());
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	public double getCostFitness() {
		return costFitness;
	}

	public void setCostFitness(double costFitness) {
		this.costFitness = costFitness;
		updateFitness();
	}

	public double getBalanceFitness() {
		return balanceFitness;
	}

	public void setBalanceFitness(double balanceFitness) {
		this.balanceFitness = balanceFitness;
		updateFitness();
	}

	public double getFitness() {
		return fitness;
	}

	private void updateFitness() {
		fitness = pow(getCostFitness(), 2) + pow(getBalanceFitness(), 2);
	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(o.getFitness(), getFitness());
	}

}
