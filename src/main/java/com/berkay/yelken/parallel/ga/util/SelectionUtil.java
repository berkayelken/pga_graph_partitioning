package com.berkay.yelken.parallel.ga.util;

import java.util.List;
import java.util.stream.Collectors;

import com.berkay.yelken.parallel.ga.model.genetic.Chromosome;

public final class SelectionUtil {

	public static List<Chromosome> doNaturalSelection(List<Chromosome> cs, int selection) {
		return cs.parallelStream().limit(selection).collect(Collectors.toList());
	}
}
