package com.berkay.yelken.parallel.ga.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.GraphSize;

@Service
public class GraphService {

	public Graph createGraph(GraphSize size, boolean unweighted) {
		Graph graph = new Graph();
		try (Stream<String> lines = Files.lines(size.getFilePath())) {
			lines.parallel().filter(line -> StringUtils.hasText(line) && !line.startsWith("%"))
					.forEach(line -> addNode(graph, line, unweighted));

			graph.handleNodeList();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return graph;
	}

	private void addNode(Graph graph, String line, boolean unweighted) {
		String[] data = line.split("\\s");
		if (data.length != 3)
			return;

		int x = Integer.valueOf(data[0]);
		int y = Integer.valueOf(data[1]);

		double weight = unweighted ? 1.0 : Double.parseDouble(data[2]);

		graph.addNode(x, y, weight);
		graph.addNode(y, x, weight);
	}

}
