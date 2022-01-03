package com.berkay.yelken.parallel.ga.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.GraphSize;
import com.berkay.yelken.parallel.ga.model.Node;

@Service
public class GraphService {

	public Graph createGraph(GraphSize size) {
		Graph graph = new Graph();
		AtomicInteger counter = new AtomicInteger(0);
		try (Stream<String> lines = Files.lines(size.getFilePath())) {
			lines.parallel().filter(line -> StringUtils.hasText(line) && !line.startsWith("%"))
					.forEach(line -> addNode(graph, line, counter));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return graph;
	}

	private void addNode(Graph graph, String line, AtomicInteger counter) {
		String[] data = line.split("\\s");
		if (data.length != 3)
			return;

		int x = Integer.valueOf(data[0]);
		int y = Integer.valueOf(data[1]);
		int id = counter.incrementAndGet();
		double weight = Double.parseDouble(data[2]);
		
		Node newNode = new Node(id, x, y, weight);

		graph.addNode(newNode);
	}

}
