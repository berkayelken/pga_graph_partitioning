package com.berkay.yelken.parallel.ga.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum GraphSize {
	SMALL {
		public Path getFilePath() {
			return getResourcePath("dermatology_5NN.mtx");
		}
	},
	MEDIUM {
		public Path getFilePath() {
			return getResourcePath("dataset22mfeatzernike_10NN.mtx");
		}
	},
	LARGE {
		public Path getFilePath() {
			return getResourcePath("MISKnowledgeMap.mtx");
		}
	};

	public abstract Path getFilePath();

	private static Path getResourcePath(String fileName) {

		return Paths.get(GraphSize.class.getClassLoader().getResource(fileName).getFile().substring(1));
	}
}
