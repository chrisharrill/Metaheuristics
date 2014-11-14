package com.chrisharrill.metaheuristics.searches.cuckoo;

public class Nest {

	private double[] location;
	private double fitness;

	public Nest(double[] location) {
		this.location = location;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
}
