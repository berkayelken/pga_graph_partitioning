package com.berkay.yelken.parallel.ga.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import com.berkay.yelken.parallel.ga.model.genetic.Generation;

public class GraphPartitioning {
	private Chromosome best;
	private List<Generation> generations;
	private Graph graph;
	private ConcurrentMap<Integer, Partition> partitions;

	public Chromosome getBest() {
		return best;
	}

	public void setBest(Chromosome best) {
		this.best = best;
	}

	public List<Generation> getGenerations() {
		if (generations == null) {
			synchronized (this) {
				if (generations == null)
					generations = Collections.synchronizedList(new ArrayList<>());
			}
		}

		return generations;
	}

	public void setGenerations(List<Generation> generations) {
		this.generations = generations;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public ConcurrentMap<Integer, Partition> getPartitions() {
		if (partitions == null) {
			synchronized (this) {
				if (partitions == null)
					partitions = new ConcurrentHashMap<>();
			}
		}

		return partitions;
	}

	public void addToPartition(int part, Node node) {
		if (!getPartitions().containsKey(part)) {
			synchronized (this) {
				if (!getPartitions().containsKey(part))
					getPartitions().put(part, new Partition());
			}
		}

		getPartitions().get(part).getNodes().add(node);
	}

}
