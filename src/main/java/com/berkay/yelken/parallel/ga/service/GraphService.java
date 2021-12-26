package com.berkay.yelken.parallel.ga.service;

import static java.lang.Double.NaN;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.berkay.yelken.parallel.ga.model.Edge;
import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.GraphSize;
import com.berkay.yelken.parallel.ga.model.Node;

@Service
public class GraphService {

	public Graph createGraph(GraphSize size) {
		Graph graph = new Graph();
		AtomicInteger counter = new AtomicInteger(0);
		try (Stream<String> lines = Files.lines(size.getFilePath())) {

			lines.parallel().filter(line -> StringUtils.hasText(line)).forEach(line -> {
				String[] data = line.split("\\s");
				double weight = Double.parseDouble(data[1]);
				if (data[0].contains("-"))
					createEdge(graph, data[0], counter, weight);
				else
					createNode(graph, Integer.valueOf(data[0]), weight);
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

		return graph;
	}

	private void createEdge(Graph graph, String data, AtomicInteger counter, double weight) {
		int edgeID = counter.incrementAndGet();

		String[] eData = data.split("-");
		int id1 = Integer.valueOf(eData[0]);
		int id2 = Integer.valueOf(eData[1]);
		if (!graph.getNodesMap().containsKey(id1))
			createNode(graph, id1, NaN);
		if (!graph.getNodesMap().containsKey(id2))
			createNode(graph, id2, NaN);

		Edge e = new Edge(edgeID, weight, graph.getNodesMap().get(id1), graph.getNodesMap().get(id2));
		graph.getEdges().add(e);

		graph.getNodesMap().get(id1).getEdges().add(e);
		graph.getNodesMap().get(id2).getEdges().add(e);
	}

	private void createNode(Graph graph, int id, double weight) {
		if (graph.getNodesMap().containsKey(id) && graph.getNodeByID(id).getWeight() == NaN && weight != NaN) {
			Node node = graph.getNodeByID(id);
			node.setWeight(weight);
			graph.getNodesMap().put(id, node);
			return;
		}

		graph.addNode(new Node(id, weight));
	}

}
