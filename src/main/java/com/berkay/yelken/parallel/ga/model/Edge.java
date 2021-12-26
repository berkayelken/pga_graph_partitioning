package com.berkay.yelken.parallel.ga.model;

import java.util.Objects;


public class Edge implements Comparable<Edge> {	
	private int id;
	private double weight;

	private Node node1;
	private Node node2;

	public Edge(int id, double weight, Node node1, Node node2) {
		this.id = id;
		this.weight = weight;
		this.node1 = node1;
		this.node2 = node2;
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

	public Node getNode1() {
		return node1;
	}

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public Node getNode2() {
		return node2;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	public Node getPairedNode(Node node) {
		if (node.equals(node1))
			return node2;
		if (node.equals(node2))
			return node1;
		return null;
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

		Edge other = (Edge) obj;

		return id == other.id;
	}

	@Override
	public int compareTo(Edge o) {
		return Integer.compare(getId(), o.getId());
	}

}
