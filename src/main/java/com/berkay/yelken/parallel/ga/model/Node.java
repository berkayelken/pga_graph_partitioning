package com.berkay.yelken.parallel.ga.model;

import java.util.Objects;

public class Node implements Comparable<Node> {

	private int id, x, y;
	private double weight;

	public Node(int id, int x, int y, double weight) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.weight = weight;
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

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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
