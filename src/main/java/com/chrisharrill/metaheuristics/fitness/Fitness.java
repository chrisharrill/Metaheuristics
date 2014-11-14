package com.chrisharrill.metaheuristics.fitness;

public interface Fitness {
	
	public double evaluateSolution(double[] solution);
	public double getBestFitness();
	public double getWorstFitness();
	public int fitnessCompare(double fit1, double fit2);
	public double[][] getBounds();
	public int getSolutionSize();
}
