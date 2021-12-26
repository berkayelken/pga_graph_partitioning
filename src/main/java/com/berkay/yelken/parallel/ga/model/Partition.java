package com.berkay.yelken.parallel.ga.model;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class Partition {
	private int size;
	private transient Set<Node> nodes;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
