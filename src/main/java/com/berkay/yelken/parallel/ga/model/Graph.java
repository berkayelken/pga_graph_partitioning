package com.berkay.yelken.parallel.ga.model;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class Graph {

	private ConcurrentMap<Integer, Node> nodesMap;
	private Set<Edge> edges;

	public ConcurrentMap<Integer, Node> getNodesMap() {
		if (nodesMap == null) {
			synchronized (this) {
				if (nodesMap == null)
					nodesMap = new ConcurrentHashMap<>();
			}
		}

		return nodesMap;
	}

	public void setNodesMap(ConcurrentMap<Integer, Node> nodesMap) {
		this.nodesMap = nodesMap;
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

	public Node getNodeByID(int id) {
		return getNodesMap().get(id);
	}

	public Set<Integer> getNodesIds() {
		return getNodesMap().keySet();
	}

	public Set<Node> getNodeNeighbors(int nodeId) {
		Node node = getNodesMap().get(nodeId);

		Set<Node> neigbors = node.getEdges().parallelStream()
				.filter(edge -> node.equals(edge.getNode1()) || node.equals(edge.getNode2())).map(edge -> {
					if (node.equals(edge.getNode1()))
						return edge.getNode2();
					return edge.getNode1();
				}).collect(Collectors.toSet());

		return neigbors;
	}

	public ConcurrentMap<Node, Edge> getNodeNeighborsWithCorrespondingEdge(int nodeId) {
		ConcurrentMap<Node, Edge> neighbors = new ConcurrentHashMap<>();
		Node node = getNodesMap().get(nodeId);
		node.getEdges().parallelStream().filter(edge -> node.equals(edge.getNode1()) || node.equals(edge.getNode2()))
				.forEach(edge -> {
					if (node.equals(edge.getNode1())) {
						neighbors.put(edge.getNode2(), edge);
						return;
					}
					neighbors.put(edge.getNode1(), edge);
				});

		return neighbors;
	}

	public void addNode(Node node) {
		getNodesMap().put(node.getId(), node);
	}

	public int size() {
		return nodesMap.keySet().size();
	}

}
