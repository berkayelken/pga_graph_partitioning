package com.berkay.yelken.parallel.ga.util;

import java.security.SecureRandom;
import java.util.Random;

import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import com.berkay.yelken.parallel.ga.model.genetic.Gene;
import com.berkay.yelken.parallel.ga.model.genetic.Generation;

public final class MutationUtil {
	public static void doGenerationMutation(Generation g, int geneInterval, int mutation) {
		g.getChromosomes().parallelStream().forEach(c -> doMutation(c, geneInterval, mutation));
	}

	private static void doMutation(Chromosome c, int geneInterval, int mutation) {

		Random rand = new SecureRandom();
		if (rand.nextInt(101) < mutation)
			return;
		int index = rand.nextInt(c.getGenes().size());
		int mutator = rand.nextInt(geneInterval);
		Gene newGene = new Gene();
		newGene.setValue(mutator);
		c.getGenes().set(index, newGene);

	}
}
