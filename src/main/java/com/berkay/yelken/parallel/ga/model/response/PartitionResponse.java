package com.berkay.yelken.parallel.ga.model.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PartitionResponse implements Serializable {
	private static final long serialVersionUID = 1490424919634603373L;
	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
