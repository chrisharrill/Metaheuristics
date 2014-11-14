package com.chrisharrill.metaheuristics.watch;

public interface Watcher {

	public void update(double fitness, int iteration, double[] solution);
}
