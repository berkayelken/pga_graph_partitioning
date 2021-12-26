package com.berkay.yelken.parallel.ga.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import com.berkay.yelken.parallel.ga.model.genetic.Gene;
import com.berkay.yelken.parallel.ga.model.genetic.Generation;

public final class CrossoverUtil {
	public static void doGenerationCrossover(Generation g, int newbornSize) {
		List<Chromosome> newBorns = new ArrayList<>();
		Stream.generate(() -> 0).limit(newbornSize).parallel().forEach(x -> {
			Chromosome parent1 = g.getChromosomes().parallelStream().findAny().get();
			Chromosome parent2 = g.getChromosomes().parallelStream().findAny().get();
			if (parent1.equals(parent2))
				parent2 = g.getChromosomes().parallelStream().findAny().get();

			doCrossover(newBorns, parent1, parent2);
		});
	}

	private static void doCrossover(List<Chromosome> cs, Chromosome parent1, Chromosome parent2) {
		Random rand = new SecureRandom();
		int randCut = rand.nextInt(parent1.getGenes().size() - 1);

		Stream<Gene> part1 = parent1.getGenes().parallelStream().limit(randCut);
		Stream<Gene> part2 = parent2.getGenes().parallelStream().skip(randCut);

		Chromosome child = new Chromosome(Stream.concat(part1, part2).collect(Collectors.toList()));
		cs.add(child);
	}
}
