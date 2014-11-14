package com.chrisharrill.metaheuristics.searches;

import java.util.HashSet;
import java.util.Set;

import com.chrisharrill.metaheuristics.accept.Acceptor;
import com.chrisharrill.metaheuristics.fitness.Fitness;
import com.chrisharrill.metaheuristics.watch.Watcher;


public abstract class Search {
	private Set<Watcher> watchers;
	protected Fitness fitness;
	private Acceptor acceptor;
	
	private int currentIteration;
	private double currentFitness;
	private double[] currentSolution;
	
	public Search(Fitness fitness, Acceptor acceptor) {
		this.fitness = fitness;
		this.acceptor = acceptor;
		
		watchers = new HashSet<Watcher>();
		currentIteration = 0;
		currentFitness = fitness.getWorstFitness();
		currentSolution = new double[fitness.getSolutionSize()];
	}
	
	public final void addWatcher(Watcher watcher) {
		watchers.add(watcher);
	}
	
	public int getIteration(){
		return currentIteration;
	}
	
	public double[] getSolution(){
		return currentSolution;
	}
	
	public final void search() {
		currentIteration = 0;
		while (!acceptor.accept(currentFitness, 0))
			this.step();
	}
	
	public final void search(int numIterations) {
		currentIteration = 0;
		while (!acceptor.accept(currentFitness, currentIteration))
			this.step();
	}
	
	public final void step() {
		currentSolution = this.doStep(currentIteration, currentFitness);
		currentFitness = fitness.evaluateSolution(currentSolution);
		for (Watcher watcher : watchers)
			watcher.update(currentFitness, currentIteration, currentSolution);
		currentIteration++;
	}
	
	protected abstract double[] doStep(int currentIteration, double previousFitness);
}
