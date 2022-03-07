package com.berkay.yelken.parallel.ga.model;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class Partition {
	private int size;
	private double cost = 0.0;
	private int nonZeroCount = 0;
	private transient Set<Node> nodes;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getNonZeroCount() {
		return nonZeroCount;
	}

	public void setNonZeroCount(int nonZeroCount) {
		this.nonZeroCount = nonZeroCount;
	}

	public Set<Node> getNodes() {
		if (nodes == null) {
			synchronized (this) {
				if (nodes == null)
					nodes = new ConcurrentSkipListSet<>();
			}
		}

		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

}
