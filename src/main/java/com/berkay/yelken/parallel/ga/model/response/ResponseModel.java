package com.berkay.yelken.parallel.ga.model.response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseModel {
	private int partitionNum;
	private int generationCount;

	private String totalTime;
	private String totalSelectionTime;
	private String totalCrossoverTime;
	private String totalMutationTime;
	private String avgTime;
	private String avgSelectionTime;
	private String avgCrossoverTime;
	private String avgMutationTime;

	private double totalCost;
	private Map<Integer, PartitionResponse> partitions;

	public int getPartitionNum() {
		return partitionNum;
	}

	public void setPartitionNum(int partitionNum) {
		this.partitionNum = partitionNum;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public void setGenerationCount(int generationCount) {
		this.generationCount = generationCount;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getTotalSelectionTime() {
		return totalSelectionTime;
	}

	public void setTotalSelectionTime(String totalSelectionTime) {
		this.totalSelectionTime = totalSelectionTime;
	}

	public String getTotalCrossoverTime() {
		return totalCrossoverTime;
	}

	public void setTotalCrossoverTime(String totalCrossoverTime) {
		this.totalCrossoverTime = totalCrossoverTime;
	}

	public String getTotalMutationTime() {
		return totalMutationTime;
	}

	public void setTotalMutationTime(String totalMutationTime) {
		this.totalMutationTime = totalMutationTime;
	}

	public String getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(String avgTime) {
		this.avgTime = avgTime;
	}

	public String getAvgSelectionTime() {
		return avgSelectionTime;
	}

	public void setAvgSelectionTime(String avgSelectionTime) {
		this.avgSelectionTime = avgSelectionTime;
	}

	public String getAvgCrossoverTime() {
		return avgCrossoverTime;
	}

	public void setAvgCrossoverTime(String avgCrossoverTime) {
		this.avgCrossoverTime = avgCrossoverTime;
	}

	public String getAvgMutationTime() {
		return avgMutationTime;
	}

	public void setAvgMutationTime(String avgMutationTime) {
		this.avgMutationTime = avgMutationTime;
	}

	public double getTotalCost() {
		return totalCost / 2;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public void incrementCost(double cost) {
		totalCost += cost;
	}

	public Map<Integer, PartitionResponse> getPartitions() {
		if (partitions == null) {
			synchronized (this) {
				if (partitions == null)
					partitions = new ConcurrentHashMap<>();
			}
		}

		return partitions;
	}

	public void setPartitions(Map<Integer, PartitionResponse> partitions) {
		this.partitions = partitions;
	}

}
