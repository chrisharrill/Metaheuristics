package com.chrisharrill.metaheuristics.searches.pso;

import java.util.Random;

import com.chrisharrill.metaheuristics.accept.Acceptor;
import com.chrisharrill.metaheuristics.fitness.Fitness;
import com.chrisharrill.metaheuristics.searches.Search;


public class ParticleSwarmOptimization extends Search {
	private final double c1 = 2.0;
	private final double c2 = 2.0;
	private int solutionSize;
	private Particle[] population;
	private int populationSize;
	private double[] gBestSolution;
	private double gBest;
	Random r = new Random();

	public ParticleSwarmOptimization(Fitness fitness, Acceptor acceptor, int populationSize) {
		super(fitness, acceptor);
		this.solutionSize = fitness.getSolutionSize();
		this.populationSize = populationSize;
		population = new Particle[this.populationSize];
		for (int i = 0; i < this.populationSize; i++)
			population[i] = new Particle(fitness, c1, c2);
		gBest = fitness.getWorstFitness();
		gBestSolution = new double[this.solutionSize];
	}

	private void findBestParticle() {
		for (int i = 0; i < populationSize; i++) {
			if (fitness.fitnessCompare(population[i].best, gBest) > 0) {
				// Verify
				double[] temp = population[i].position.clone();
				double tempFit = fitness.evaluateSolution(temp);
				if (tempFit == population[i].best
						&& fitness.fitnessCompare(tempFit, gBest) > 0) {
					gBest = population[i].best;
					gBestSolution = population[i].position.clone();
				}
			}
		}
	}
	
	public void resetFitnesses(){
		for (int i = 0; i < populationSize; i++)
			population[i].best = fitness.getWorstFitness();
		gBest = fitness.getWorstFitness();
	}

	@Override
	protected double[] doStep(int currentIteration, double previousFitness) {
		for (Particle p : population)
			p.getFitness();
		findBestParticle();
		for (Particle p : population)
			p.update(gBestSolution);
		return gBestSolution.clone();
	}

}
