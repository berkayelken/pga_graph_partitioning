package com.berkay.yelken.parallel.ga.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum GraphSize {
	SMALL {
		public Path getFilePath() {
			return getResourcePath("small/dermatology_5NN.mtx");
		}

		@Override
		public String getSeedUri() {
			return "small/seed/dermatology_5NN.mtx-u";
		}

	},
	MEDIUM {
		public Path getFilePath() {
			return getResourcePath("medium/dataset22mfeatzernike_10NN.mtx");
		}

		@Override
		public String getSeedUri() {
			return "medium/seed/dataset22mfeatzernike_10NN.mtx-u";
		}

	},
	LARGE {
		public Path getFilePath() {
			return getResourcePath("large/MISKnowledgeMap.mtx");
		}

		@Override
		public String getSeedUri() {
			return "large/seed/MISKnowledgeMap.mtx-u";
		}

	};

	public abstract Path getFilePath();

	public abstract String getSeedUri();

	private static Path getResourcePath(String fileName) {

		return Paths.get(GraphSize.class.getClassLoader().getResource(fileName).getFile().substring(1));
	}

	public static GraphSize getSize(String name) {
		try{
			GraphSize size = GraphSize.valueOf(name);
			if (size == null)
				size = GraphSize.SMALL;
			return size;
		} catch (Throwable t) {
			return SMALL;
		}
	}
}
