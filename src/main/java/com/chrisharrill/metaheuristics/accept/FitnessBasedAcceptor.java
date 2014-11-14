package com.chrisharrill.metaheuristics.accept;

import com.chrisharrill.metaheuristics.fitness.Fitness;

public class FitnessBasedAcceptor implements Acceptor {
	
	private Fitness fitnessEvaluator;

	public FitnessBasedAcceptor(Fitness fitnessEvaluator) {
		this.fitnessEvaluator = fitnessEvaluator;
	}

	@Override
	public boolean accept(double fitness, int iteration) {
		return fitnessEvaluator.fitnessCompare(fitness, fitnessEvaluator.getBestFitness()) > 0;
	}

}
