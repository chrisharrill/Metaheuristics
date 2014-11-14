package com.chrisharrill.metaheuristics.fitness;

public class AdditionFitness implements Fitness {

	@Override
	public double evaluateSolution(double[] solution) {
		double sum = 0.0;
		for (double d : solution)
			sum += d;
		return sum;
	}

	@Override
	public double getWorstFitness() {
		return -Double.MAX_VALUE;
	}

	@Override
	public int fitnessCompare(double fit1, double fit2) {
		if (fit1 > fit2)
			return 1;
		if (fit1 < fit2)
			return -1;
		return 0;
	}

	@Override
	public double getBestFitness() {
		return Double.MAX_VALUE;
	}

	@Override
	public double[][] getBounds() {
		return null;
	}

	@Override
	public int getSolutionSize() {
		return 0;
	}

}
