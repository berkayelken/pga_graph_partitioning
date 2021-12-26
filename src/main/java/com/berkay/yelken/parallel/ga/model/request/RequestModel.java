package com.berkay.yelken.parallel.ga.model.request;

public class RequestModel {
	private String size;
	private int populationSize;
	private int generationCount;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
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

}
