package com.berkay.yelken.parallel.ga.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.GraphSize;
import com.berkay.yelken.parallel.ga.model.InitType;
import com.berkay.yelken.parallel.ga.model.Node;
import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;

import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import static com.berkay.yelken.parallel.ga.model.InitType.FULLY_RANDOM;

public final class SeedUtil {
	public static List<Chromosome> getSeedList(GraphSize graphSize, Graph graph, InitType initType, int partitionSize, int seedCount) {
		List<Chromosome> seedList = new LinkedList<>();
		if(initType == FULLY_RANDOM)
			return seedList;
		String seedName = graphSize.getSeedUri() + partitionSize + "-seed";
		for(int i = 1; i <= seedCount; i++) {
			seedList.add(getChromosome(graph, seedName + i));
		}
		return seedList;
	}

	private static Chromosome getChromosome(Graph graph, String name) {
		Chromosome chromosome = new Chromosome(new LinkedList<>());

		try (Stream<String> lines = Files.lines(getResourcePath(name))) {
			lines.skip(1).filter(line -> StringUtils.hasText(line))
					.forEach(line -> addGene(graph, chromosome, line));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return chromosome;
	}

	private static void addGene(Graph graph, Chromosome chromosome, String line) {
		String[] data = line.split("\\s");
		if (data.length != 2)
			return;

		int id = Integer.valueOf(data[0]);
		int part = Integer.valueOf(data[1]) - 1;

		chromosome.handleAddGene(part, graph.getNodeByID(id));
	}

	private static Path getResourcePath(String fileName) {
		return Paths.get(GraphSize.class.getClassLoader().getResource(fileName).getFile().substring(1));
	}
}
