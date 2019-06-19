package com.webapplication.JSONBeans;

import java.util.ArrayList;
import java.util.List;

public class GraphData {

	
	private List<String> labels;
	private List<Double> data;
	
	public GraphData() {
		
		labels = new ArrayList<String>();
		data = new ArrayList<Double>();
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<Double> getDataframes() {
		return data;
	}

	public void setDataframes(List<Double> dataframes) {
		this.data = dataframes;
	}
	
	
}
