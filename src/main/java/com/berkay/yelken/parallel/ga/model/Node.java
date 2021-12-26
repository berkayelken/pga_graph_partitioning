package com.berkay.yelken.parallel.ga.model;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class Node implements Comparable<Node> {

	private int id;
	private double weight;

	private Set<Edge> edges;

	public Node(int id, double weight) {
		this.id = id;
		this.weight = weight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Set<Edge> getEdges() {
		if (edges == null) {
			synchronized (this) {
				if (edges == null)
					edges = new ConcurrentSkipListSet<>();
			}
		}

		return edges;
	}

	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}

	public Node followEdge(Edge e) {
		return e.getPairedNode(this);
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
