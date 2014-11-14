package com.chrisharrill.metaheuristics.fitness;

public class MichaelwiczFitness implements Fitness {

	@Override
	public double evaluateSolution(double[] solution) {
		if (solution == null || solution.length != 2)
			return getWorstFitness();
		double m = 10.0;
		double pi = Math.PI;
		double x = solution[0];
		double y = solution[1];
		double f = -Math.sin(x) * Math.pow(Math.sin(x * x / pi), 2.0 * m)
				- Math.sin(y) * Math.pow(Math.sin(2.0 * y * y / pi), 2.0 * m);
		return f;
	}

	@Override
	public double getWorstFitness() {
		return Double.MAX_VALUE;
	}

	@Override
	public int fitnessCompare(double fit1, double fit2) {
		if (fit1 < fit2)
			return 1;
		if (fit2 < fit1)
			return -1;
		return 0;
	}

	@Override
	public double getBestFitness() {
		return -1.8013;
	}

	@Override
	public double[][] getBounds() {
		double[][] bounds = new double[2][2];
		bounds[0] = new double[] {0.0, 0.0};
		bounds[1] = new double[] {5.0, 5.0};
		return bounds;
	}

	@Override
	public int getSolutionSize() {
		return 2;
	}

}
