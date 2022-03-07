package com.berkay.yelken.parallel.ga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class Graph {

	private List<Node> nodeList = new ArrayList<>();

	private ConcurrentMap<Integer, Node> nodesMap = new ConcurrentHashMap<>();

	public ConcurrentMap<Integer, Node> getNodesMap() {
		return nodesMap;
	}

	public void setNodesMap(ConcurrentMap<Integer, Node> nodesMap) {
		this.nodesMap = nodesMap;
	}

	public Node getNodeByID(int id) {
		return getNodesMap().get(id);
	}

	public Set<Integer> getNodesIds() {
		return getNodesMap().keySet();
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public void addNode(int x, int y, double weight) {
		Node node = getNodesMap().containsKey(x) ? getNodesMap().get(x): new Node(x);

		node.addYVals(y);
		node.incrementWeight(weight);

		getNodesMap().put(node.getId(), node);
	}

	public void handleNodeList() {
		setNodeList(getNodesMap().values().stream().collect(Collectors.toList()));
	}

	public int size() {
		return nodesMap.keySet().size();
	}

}
