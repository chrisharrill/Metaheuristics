package com.chrisharrill.metaheuristics.searches.cuckoo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.chrisharrill.metaheuristics.accept.Acceptor;
import com.chrisharrill.metaheuristics.fitness.Fitness;
import com.chrisharrill.metaheuristics.searches.Search;


public class CuckooSearch extends Search {
	private int numNests;
	private double prob;
	private double[] low;
	private double[] up;
	private Nest[] nests;
	private double best;
	private double[] bestLoc;
	private double currentBest;
	private double[] currentBestLoc;
	private int locSize;
	private Random r = new Random();

	public CuckooSearch(Fitness fitness, Acceptor acceptor, int numberOfNests, double probabilityOfDiscovery) {
		super(fitness, acceptor);
		numNests = numberOfNests;
		prob = probabilityOfDiscovery;
		low = fitness.getBounds()[0];
		up = fitness.getBounds()[1];
		nests = new Nest[numNests];
		locSize = fitness.getSolutionSize();
		best = fitness.getWorstFitness();
		bestLoc = new double[locSize];
		currentBest = fitness.getWorstFitness();
		currentBestLoc = new double[locSize];
		for (int i = 0; i < numNests; i++) {
			double[] location = new double[locSize];
			for (int j = 0; j < low.length; j++) {
				// double rand = Math.random();
				// location[j] = low[j] + up[j] * rand - low[j] * rand;
				// Strange syntax guarantess it won't overflow
				location[j] = low[j] + (up[j] - low[j]) * Math.random();
			}
			nests[i] = new Nest(location);
			nests[i].setFitness(fitness.evaluateSolution(nests[i].getLocation()));
		}
	}

	public void setInitialSoln(double[] loc, int i) {
		nests[i].setLocation(loc.clone());
		nests[i].setFitness(fitness.evaluateSolution(loc));
	}

	private void getBestNest() {
		double best = fitness.getWorstFitness();
		double[] location = new double[locSize];
		for (int i = 0; i < numNests; i++) {
			double f = fitness.evaluateSolution(nests[i].getLocation());
			if (fitness.fitnessCompare(f, nests[i].getFitness()) > 0)
				nests[i].setFitness(f);
			if (fitness.fitnessCompare(nests[i].getFitness(), best) > 0) {
				best = nests[i].getFitness();
				location = nests[i].getLocation();
			}
		}
		currentBest = best;
		currentBestLoc = location.clone();
	}

	private double[] LevyFlight(double[] loc) {
		double beta = 3.0 / 2.0;
		double gamma1 = 1.32934038818; // = gamma(1 + beta)
		double gamma2 = 0.919062526849; // = gamma(1 + beta/2)
		double sigma = Math.pow((gamma1 * Math.sin(Math.PI / 2.0) / (gamma2
				* beta * Math.pow(2.0, (beta - 1.0) / 2.0))), 1.0 / beta);
		for (int i = 0; i < locSize; i++) {
			double u = r.nextGaussian() * sigma;
			double v = r.nextGaussian();
			double step = u / Math.pow(Math.abs(v), 1.0 / beta);
			double stepsize = 0.01 * step * (loc[i] - currentBestLoc[i]);
			loc[i] += stepsize * r.nextGaussian();
		}
		double[] loc_temp = loc.clone();
		for (int i = 0; i < loc.length; i++) {
			if (loc[i] < low[i])
				loc_temp[i] = low[i];
			if (loc[i] > up[i])
				loc_temp[i] = up[i];
		}
		return loc_temp;
	}

	private void getCuckoos() {
		for (int i = 0; i < numNests; i++) {
			double[] newLoc = LevyFlight(nests[i].getLocation());
			double newFit = fitness.evaluateSolution(newLoc);
			if (fitness.fitnessCompare(newFit, nests[i].getFitness()) > 0) {
				nests[i] = new Nest(newLoc);
				nests[i].setFitness(newFit);
			}
		}
	}

	private void emptyNests() {
		int[] discoveredBooleans = new int[numNests];
		for (int i = 0; i < numNests; i++)
			discoveredBooleans[i] = (r.nextDouble() < prob) ? 1 : 0;
		ArrayList<Integer> range1 = new ArrayList<Integer>();
		ArrayList<Integer> range2 = new ArrayList<Integer>();
		for (int i = 0; i < numNests; i++) {
			range1.add(Integer.valueOf(i));
			range2.add(Integer.valueOf(i));
		}
		double[][] stepsize = new double[numNests][];
		for (int i = 0; i < numNests; i++) {
			double[] temp = new double[locSize];
			Collections.shuffle(range1);
			Collections.shuffle(range2);
			for (int j = 0; j < locSize; j++)
				temp[j] = r.nextDouble()
						* (nests[range1.get(i)].getLocation()[j] - nests[range2.get(i)].getLocation()[j]);
			stepsize[i] = temp;
		}
		for (int i = 0; i < numNests; i++) {
			double[] nestLocation = nests[i].getLocation();
			for (int j = 0; j < locSize; j++)
				nestLocation[j] = nestLocation[j] + stepsize[i][j]
						* discoveredBooleans[i];
			for (int j = 0; j < locSize; j++) {
				if (nestLocation[j] < low[j])
					nestLocation[j] = low[j];
				if (nestLocation[j] > up[j])
					nestLocation[j] = up[j];
			}
		}
	}

	@Override
	protected double[] doStep(int currentIteration, double previousFitness) {
		getCuckoos();
		getBestNest();
		emptyNests();
		getBestNest();
		if (fitness.fitnessCompare(currentBest, best) > 0) {
			best = currentBest;
			bestLoc = currentBestLoc.clone();
		}
		return bestLoc.clone();
	}

}