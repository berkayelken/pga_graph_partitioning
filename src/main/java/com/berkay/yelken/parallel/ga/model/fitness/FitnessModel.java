package com.berkay.yelken.parallel.ga.model.fitness;

import java.util.Collections;
import java.util.List;

import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;

public class FitnessModel {
	private List<Chromosome> chromosomes;
	private double costFitExpValue;
	private double balanceFitExpValue;

	public FitnessModel(List<Chromosome> chromosomes, double costFitExpValue, double balanceFitExpValue) {
		this.chromosomes = Collections.synchronizedList(chromosomes);
		this.costFitExpValue = costFitExpValue;
		this.balanceFitExpValue = balanceFitExpValue;
	}

	public List<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(List<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

	public double getCostFitExpValue() {
		return costFitExpValue;
	}

	public void setCostFitExpValue(double costFitExpValue) {
		this.costFitExpValue = costFitExpValue;
	}

	public double getBalanceFitExpValue() {
		return balanceFitExpValue;
	}

	public void setBalanceFitExpValue(double balanceFitExpValue) {
		this.balanceFitExpValue = balanceFitExpValue;
	}

}
