package com.berkay.yelken.parallel.ga.util;

import static com.berkay.yelken.parallel.ga.util.CrossoverUtil.doCrossover;
import static com.berkay.yelken.parallel.ga.util.CrossoverUtil.doGenerationCrossover;
import static com.berkay.yelken.parallel.ga.util.FitnessHandler.calculateFitness;
import static com.berkay.yelken.parallel.ga.util.MutationUtil.doGenerationMutation;

import java.util.List;
import java.util.stream.Collectors;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.InitType;
import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import com.berkay.yelken.parallel.ga.model.genetic.Generation;

public final class PopulationInitializer {
	public static Generation getInitial(Graph graph, InitType initType, int partitionSize, int genesNum, int populationSize, int mutationRatio, List<Chromosome> seedList) {
		if(seedList == null || seedList.size() == 0)
			return  new Generation(partitionSize, genesNum, populationSize);
		Generation tempGeneration = null;
		switch (initType) {
		case TRIBE:
			tempGeneration = getTribeGeneration(partitionSize, genesNum, populationSize, mutationRatio, seedList);
			break;
		case PSUEDO_RANDOM:
			tempGeneration = getPsuedoRandomGeneration(partitionSize, genesNum, populationSize, seedList);
			break;
		case FULLY_RANDOM:
		default:
			tempGeneration =  new Generation(partitionSize, genesNum, populationSize);
			break;
		}

		return new Generation(calculateFitness(tempGeneration, graph));
	}

	private static Generation getPsuedoRandomGeneration(int partitionSize, int genesNum, int populationSize, List<Chromosome> seedList) {
		List<Chromosome> chromosomeList = seedList.size() > populationSize ?
				seedList.stream().sorted().limit(genesNum).collect(Collectors.toList()):
				seedList.stream().collect(Collectors.toList());

		int neededPopulationSize = populationSize - chromosomeList.size();
		addRandomChromosomes(chromosomeList, partitionSize, genesNum, neededPopulationSize);
		return new Generation(chromosomeList);
	}

	private static void addRandomChromosomes(List<Chromosome> chromosomeList, int partitionSize, int genesNum, int populationSize) {
		if(populationSize == 0)
			return;
		Generation tempGeneration = new Generation(partitionSize, genesNum, populationSize);
		chromosomeList.addAll(tempGeneration.getChromosomes());
	}

	private static Generation getTribeGeneration(int partitionSize, int genesNum, int populationSize, int mutationRatio, List<Chromosome> seedList) {
		List<Chromosome> chromosomeList = seedList.size() > populationSize ?
				seedList.stream().sorted().limit(genesNum).collect(Collectors.toList()):
				seedList.stream().collect(Collectors.toList());
		int requiredSize = populationSize - chromosomeList.size();

		if(populationSize == 0)
			return new Generation(chromosomeList);

		while(addChildToPopulation(chromosomeList, requiredSize, partitionSize, mutationRatio));

		return new Generation(chromosomeList);
	}

	private static boolean addChildToPopulation(List<Chromosome> chromosomeList, int requiredSize, int partitionSize, int mutationRatio) {
		int childrenCount = chromosomeList.size() * 2;

		Generation newGeneration = new Generation(chromosomeList);

		doGenerationCrossover(newGeneration, childrenCount);
		doGenerationMutation(newGeneration, partitionSize, mutationRatio);

		for(int i = 0; i < chromosomeList.size() && i < requiredSize; i++){
			if(chromosomeList.size() == requiredSize){
				return false;
			}
			chromosomeList.add(newGeneration.getChromosomes().get(i));
		}

		return true;
	}
}
