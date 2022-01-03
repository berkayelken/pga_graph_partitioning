package com.berkay.yelken.parallel.ga.util;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.berkay.yelken.parallel.ga.model.Graph;
import com.berkay.yelken.parallel.ga.model.Node;
import com.berkay.yelken.parallel.ga.model.fitness.FitnessModel;
import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;
import com.berkay.yelken.parallel.ga.model.genetic.Generation;

public final class FitnessHandler {

	public static FitnessModel calculateFitness(Generation g, Graph graph, double costFitExpValue,
			double balanceFitExpValue) {
		FitnessModel fitnessModel = new FitnessModel(null, costFitExpValue, balanceFitExpValue);

		Stream<Chromosome> generation = g.getChromosomes().stream().map(c -> calculateFitness(c, graph));

		handleFitnessValue(g, fitnessModel);
		fitnessModel.setChromosomes(generation.sorted().collect(Collectors.toList()));

		return fitnessModel;
	}

	private static Chromosome calculateFitness(Chromosome c, Graph graph) {
		c.setCostFitness(getCostFitness(c, graph.getNodesMap().values()));
		StandardDeviation stdDev = new StandardDeviation();
		AtomicInteger nodeCounter = new AtomicInteger();
		double[] weights = c.getGenes().stream()
				.mapToDouble(cs -> graph.getNodeByID(nodeCounter.incrementAndGet()).getWeight())
				.filter(w -> !Double.isNaN(w)).toArray();
		double balanceFitness = stdDev.evaluate(weights);
		c.setBalanceFitness(balanceFitness);
		return c;
	}

	private static double getCostFitness(Chromosome c, Collection<Node> nodes) {
		AtomicReference<Double> costFitness = new AtomicReference<>(0.0);
		nodes.stream().forEach(node -> {
			costFitness.accumulateAndGet(node.getWeight(), (a, b) -> a + b);
		});
		return costFitness.get();
	}

	private static void handleFitnessValue(Generation g, FitnessModel fitnessModel) {

		if (Double.isNaN(fitnessModel.getCostFitExpValue())) {
			double costFits = g.getChromosomes().stream().mapToDouble(c -> c.getCostFitness()).average().getAsDouble();
			fitnessModel.setCostFitExpValue(costFits);
		}

		if (Double.isNaN(fitnessModel.getBalanceFitExpValue())) {
			double balanceFits = g.getChromosomes().stream().mapToDouble(c -> c.getBalanceFitness()).average()
					.getAsDouble();
			fitnessModel.setBalanceFitExpValue(balanceFits);
		}

		g.getChromosomes().stream().forEach(c -> {
			c.setCostFitness(c.getCostFitness() / fitnessModel.getCostFitExpValue());
			c.setBalanceFitness(c.getBalanceFitness() / fitnessModel.getBalanceFitExpValue());
		});
	}
}
