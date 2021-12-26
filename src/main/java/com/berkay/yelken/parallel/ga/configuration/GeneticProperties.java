package com.berkay.yelken.parallel.ga.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "genetic")
public class GeneticProperties {
	private double selection;
	private int mutation;
	private int partition;

	public double getSelection() {
		return selection;
	}

	public void setSelection(double selection) {
		this.selection = selection;
	}

	public int getMutation() {
		return mutation;
	}

	public void setMutation(int mutation) {
		this.mutation = mutation;
	}

	public int getPartition() {
		return partition;
	}

	public void setPartition(int partition) {
		this.partition = partition;
	}

}
