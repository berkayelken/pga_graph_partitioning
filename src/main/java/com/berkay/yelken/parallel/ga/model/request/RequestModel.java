package com.berkay.yelken.parallel.ga.model.request;

import com.berkay.yelken.parallel.ga.model.GraphSize;
import com.berkay.yelken.parallel.ga.model.InitType;

public class RequestModel {
	private GraphSize size;
	private int populationSize;
	private int generationCount;
	private int partitionCount;
	private boolean unweighted;
	private InitType initialPopulationType;
	private int seedCount;

	public GraphSize getSize() {
		return size;
	}

	public void setSize(GraphSize size) {
		this.size = size;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public void setGenerationCount(int generationCount) {
		this.generationCount = generationCount;
	}

	public int getPartitionCount() {
		return partitionCount;
	}

	public void setPartitionCount(int partitionCount) {
		this.partitionCount = partitionCount;
	}

	public boolean isUnweighted() {
		return unweighted;
	}

	public void setUnweighted(boolean unweighted) {
		this.unweighted = unweighted;
	}

	public InitType getInitialPopulationType() {
		return initialPopulationType;
	}

	public void setInitialPopulationType(InitType initialPopulationType) {
		this.initialPopulationType = initialPopulationType;
	}

	public int getSeedCount() {
		return seedCount;
	}

	public void setSeedCount(int seedCount) {
		this.seedCount = seedCount;
	}
}
