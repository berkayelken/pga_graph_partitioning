package com.berkay.yelken.parallel.ga.model.genetic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generation {
	private List<Chromosome> chromosomes;

	public Generation(List<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

	public Generation(int geneInterval, int genesNum, int populationSize) {
		chromosomes = Stream.generate(() -> new Chromosome(geneInterval, genesNum)).limit(populationSize)
				.collect(Collectors.toList());
	}

	public List<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(List<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

}
