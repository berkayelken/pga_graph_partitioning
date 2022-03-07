package com.berkay.yelken.parallel.ga.model;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicReference;

public class Node implements Comparable<Node> {

	private int id, x;
	private AtomicReference<Double> weight = new AtomicReference<>(0.0);
	private Set<Integer> yVals = new ConcurrentSkipListSet<>();

	public Node(int x) {
		this.id = x;
		this.x = x;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public double getWeight() {
		return weight.get();
	}

	public void incrementWeight(double weight) {
		this.weight.updateAndGet(w -> w + weight);
	}

	public Set<Integer> getYVals() {
		return yVals;
	}

	public void addYVals(int y) {
		getYVals().add(y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Node other = (Node) obj;

		return id == other.id;
	}

	@Override
	public int compareTo(Node o) {
		return Integer.compare(getId(), o.getId());
	}

}
