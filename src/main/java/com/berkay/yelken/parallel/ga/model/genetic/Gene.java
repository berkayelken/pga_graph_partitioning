package com.berkay.yelken.parallel.ga.model.genetic;

import java.security.SecureRandom;
import java.util.Random;

public class Gene {
	private int value;

	public Gene() {

	}

	public Gene(int geneInterval) {
		Random sr = new SecureRandom();
		value = sr.nextInt(geneInterval);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
