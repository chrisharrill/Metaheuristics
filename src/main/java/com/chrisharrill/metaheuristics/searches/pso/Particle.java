package com.chrisharrill.metaheuristics.searches.pso;

import com.chrisharrill.metaheuristics.fitness.Fitness;

public class Particle {
	private Fitness fitness;
	private double[] lowerBounds;
	private double[] upperBounds;
	private double c1;
	private double c2;
	
	public double[] position;
	public double[] velocity;
	public double best;
	public double[] bestPosition;

	public Particle(Fitness fitness, double c1, double c2) {
		this.fitness = fitness;
		this.lowerBounds = fitness.getBounds()[0];
		this.upperBounds = fitness.getBounds()[1];
		this.c1 = c1;
		this.c2 = c2;
		
		position = new double[fitness.getSolutionSize()];
		velocity = new double[fitness.getSolutionSize()];
		best = fitness.getWorstFitness();
		bestPosition = new double[fitness.getSolutionSize()];

		
		for (int i = 0; i < fitness.getSolutionSize(); i++) {
			position[i] = lowerBounds[i] + (upperBounds[i] - lowerBounds[i]) * Math.random();
			velocity[i] = lowerBounds[i] + (upperBounds[i] - lowerBounds[i]) * Math.random();
		}
	}

	public void getFitness() {
		double newFit = fitness.evaluateSolution(position);
		if (fitness.fitnessCompare(newFit, best) > 0) {
			best = newFit;
			bestPosition = position.clone();
		}
	}

	public void update(double[] globalBestSolution) {
		for (int i = 0; i < fitness.getSolutionSize(); i++) {
			velocity[i] = velocity[i] + c1 * Math.random()
					* (bestPosition[i] - position[i]) + c2 * Math.random()
					* (globalBestSolution[i] - position[i]);
			position[i] = position[i] + velocity[i];
			if (position[i] < lowerBounds[i])
				position[i] = lowerBounds[i];
			if (position[i] > upperBounds[i])
				position[i] = upperBounds[i];
		}
	}
}
