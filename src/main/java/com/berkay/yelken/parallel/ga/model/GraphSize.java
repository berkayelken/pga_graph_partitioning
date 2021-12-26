package com.berkay.yelken.parallel.ga.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum GraphSize {
	SMALL {
		public Path getFilePath() {
			return getResourcePath("200.txt");
		}
	},
	MEDIUM {
		public Path getFilePath() {
			return getResourcePath("1k.txt");
		}
	},
	LARGE {
		public Path getFilePath() {
			return getResourcePath("10k.txt");
		}
	};

	public abstract Path getFilePath();

	private static Path getResourcePath(String fileName) {

		return Paths.get(GraphSize.class.getClassLoader().getResource(fileName).getFile().substring(1));
	}
}
