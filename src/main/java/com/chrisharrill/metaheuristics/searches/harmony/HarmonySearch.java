package com.chrisharrill.metaheuristics.searches.harmony;

import com.chrisharrill.metaheuristics.accept.Acceptor;
import com.chrisharrill.metaheuristics.fitness.Fitness;
import com.chrisharrill.metaheuristics.searches.Search;

public class HarmonySearch extends Search {
	private int solutionSize;
	private int harmonyMemorySize;
	private double[][] harmonyMemory;
	private double[] fitnesses;
	private double consideringRate;
	private double pitchAdjustingRate;
	private double[] low;
	private double[] up;

	public HarmonySearch(Fitness fitness, Acceptor acceptor, int memSize, double cRate, double pRate) {
		super(fitness, acceptor);
		solutionSize = fitness.getSolutionSize();
		harmonyMemorySize = memSize;
		harmonyMemory = new double[harmonyMemorySize][solutionSize];
		fitnesses = new double[harmonyMemorySize];
		this.fitness = fitness;
		for (int i = 0; i < harmonyMemorySize; i++)
			fitnesses[i] = fitness.getWorstFitness();
		consideringRate = cRate;
		pitchAdjustingRate = pRate;
		this.low = fitness.getBounds()[0];
		this.up = fitness.getBounds()[1];
	}

	private int getWorstIndex() {
		double worst = fitness.getBestFitness();
		int worstIndex = -1;
		for (int i = 0; i < harmonyMemorySize; i++)
			if (fitness.fitnessCompare(fitnesses[i], worst) < 0) {
				worst = fitnesses[i];
				worstIndex = i;
			}
		return worstIndex;
	}

	private int getBestIndex() {
		double best = fitness.getWorstFitness();
		int bestIndex = -1;
		for (int i = 0; i < harmonyMemorySize; i++)
			if (fitness.fitnessCompare(fitnesses[i], best) > 0) {
				best = fitnesses[i];
				bestIndex = i;
			}
		return bestIndex;
	}

	@Override
	protected double[] doStep(int currentIteration, double previousFitness) {
		double[] temp = new double[solutionSize];
		for (int j = 0; j < solutionSize; j++) {
			if (Math.random() < consideringRate) {
				int index = (int) Math.floor(Math.random()
						* harmonyMemorySize);
				temp[j] = harmonyMemory[index][j];
				if (Math.random() < pitchAdjustingRate)
					temp[j] += 0.01 * up[j] - 0.01 * low[j]
							* (Math.random() * 2.0 - 1.0);
				if (temp[j] < low[j])
					temp[j] = low[j];
				if (temp[j] > up[j])
					temp[j] = up[j];
			} else {
				double rand = Math.random();
				temp[j] = low[j] + up[j] * rand - low[j] * rand;
			}
		}
		double f = fitness.evaluateSolution(temp);
		int worstIndex = getWorstIndex();
		if (fitness.fitnessCompare(f, fitnesses[worstIndex]) > 0) {
			fitnesses[worstIndex] = f;
			harmonyMemory[worstIndex] = temp.clone();
		}
		int bestIndex = getBestIndex();
		if (bestIndex < 0)
			bestIndex = 0;
		return harmonyMemory[bestIndex].clone();
		
	}
}
