package com.berkay.yelken.parallel.ga.service;

import static com.berkay.yelken.parallel.ga.util.CrossoverUtil.doGenerationCrossover;
import static com.berkay.yelken.parallel.ga.util.FitnessHandler.calculateFitness;
import static com.berkay.yelken.parallel.ga.util.MutationUtil.doGenerationMutation;
import static com.berkay.yelken.parallel.ga.util.SelectionUtil.doNaturalSelection;
import static java.lang.Double.NaN;
import static java.lang.Math.floor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berkay.yelken.parallel.ga.configuration.GeneticProperties;
import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.GraphPartitioning;
import com.berkay.yelken.parallel.ga.model.fitness.FitnessModel;
import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import com.berkay.yelken.parallel.ga.model.genetic.Generation;
import com.berkay.yelken.parallel.ga.model.response.ResponseModel;

@Service
public class GeneticService {
	@Autowired
	private GeneticProperties prop;

	private static final int nanoToMilli = 1000000;

	public GraphPartitioning applyAlgorithm(Graph g, ResponseModel res, int populationSize, int generationSize) {
		GraphPartitioning gp = new GraphPartitioning();
		gp.setGraph(g);

		res.setPartitionNum(prop.getPartition());
		res.setGenerationCount(generationSize);

		AtomicLong totalSelectionTimeCounter = new AtomicLong();
		AtomicLong totalCrossoverTimeCounter = new AtomicLong();
		AtomicLong totalMutationTimeCounter = new AtomicLong();

		Generation initial = new Generation(prop.getPartition(), g.size(), populationSize);

		FitnessModel fitnessModel = calculateFitness(initial, g, NaN, NaN);
		double costFitExpValue = fitnessModel.getCostFitExpValue();
		double balanceFitExpValue = fitnessModel.getBalanceFitExpValue();
		for (int i = 0; i < generationSize; i++) {
			int chromosomeCount = fitnessModel.getChromosomes().size();
			int selection = Double.valueOf(floor(prop.getSelection() * chromosomeCount)).intValue();
			int newBornCount = chromosomeCount - selection;

			LocalTime start = LocalTime.now();
			Generation newGeneration = new Generation(doNaturalSelection(fitnessModel.getChromosomes(), selection));
			LocalTime end = LocalTime.now();
			totalSelectionTimeCounter.accumulateAndGet(Duration.between(start, end).toNanos(), (a, b) -> a + b);

			start = LocalTime.now();
			doGenerationCrossover(newGeneration, newBornCount);
			end = LocalTime.now();
			totalCrossoverTimeCounter.accumulateAndGet(Duration.between(start, end).toNanos(), (a, b) -> a + b);

			start = LocalTime.now();
			doGenerationMutation(newGeneration, prop.getPartition(), prop.getMutation());
			end = LocalTime.now();
			totalMutationTimeCounter.accumulateAndGet(Duration.between(start, end).toNanos(), (a, b) -> a + b);

			fitnessModel = calculateFitness(initial, g, costFitExpValue, balanceFitExpValue);

			newGeneration.setChromosomes(
					fitnessModel.getChromosomes().parallelStream().sorted().collect(Collectors.toList()));
			gp.getGenerations().add(newGeneration);
		}

		Chromosome best = getBestChromosome(gp.getGenerations());
		gp.setBest(best);

		long time = totalSelectionTimeCounter.get();
		double avg = time / generationSize;
		time /= nanoToMilli;
		res.setAvgSelectionTime(avg + " nanosecond");
		res.setTotalSelectionTime(time + " ms");

		time = totalCrossoverTimeCounter.get();
		avg = time / generationSize;
		time /= nanoToMilli;
		res.setAvgCrossoverTime(avg + " nanosecond");
		res.setTotalCrossoverTime(time + " ms");

		time = totalMutationTimeCounter.get();
		avg = time / generationSize;
		time /= nanoToMilli;
		res.setAvgMutationTime(avg + " nanosecond");
		res.setTotalMutationTime(time + " ms");

		return gp;
	}

	private Chromosome getBestChromosome(List<Generation> generations) {

		List<Chromosome> bests = new ArrayList<>();
		generations.parallelStream().forEach(g -> {
			Chromosome c = g.getChromosomes().parallelStream().filter(GeneticService::isValidChromosome).sorted()
					.findFirst().get();
			bests.add(c);
		});

		return bests.parallelStream().filter(GeneticService::isValidChromosome).sorted().findFirst().get();
	}

	private static boolean isValidChromosome(Chromosome c) {
		return c != null && !Double.isNaN(c.getFitness());
	}
}
