package com.berkay.yelken.parallel.ga.model;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Graph {

	private ConcurrentMap<Integer, Node> nodesMap;
	private ConcurrentMap<Integer, Node> xNodes;
	private ConcurrentMap<Integer, Node> yNodes;

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
	
	public ConcurrentMap<Integer, Node> getXNodes() {
		if (xNodes == null) {
			synchronized (this) {
				if (xNodes == null)
					xNodes = new ConcurrentHashMap<>();
			}
		}
		
		return xNodes;
	}

	public void setxNodes(ConcurrentMap<Integer, Node> xNodes) {
		this.xNodes = xNodes;
	}

	public ConcurrentMap<Integer, Node> getYNodes() {
		if (yNodes == null) {
			synchronized (this) {
				if (yNodes == null)
					yNodes = new ConcurrentHashMap<>();
			}
		}
		
		return yNodes;
	}

	public void setyNodes(ConcurrentMap<Integer, Node> yNodes) {
		this.yNodes = yNodes;
	}

	public Node getNodeByID(int id) {
		return getNodesMap().get(id);
	}

	public Set<Integer> getNodesIds() {
		return getNodesMap().keySet();
	}

	public void addNode(Node node) {
		getNodesMap().put(node.getId(), node);
		addNeighbourMap(node);
	}
	
	private void addNeighbourMap(Node node) {
		getXNodes().put(node.getX(), node);
		getYNodes().put(node.getY(), node);
	}

	public int size() {
		return nodesMap.keySet().size();
	}

}
