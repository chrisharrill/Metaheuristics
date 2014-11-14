package com.chrisharrill.metaheuristics.watch;

public class SimpleWatcher implements Watcher {

	@Override
	public void update(double fitness, int iteration, double[] solution) {
		System.out.printf("At iteration %d, the best fitness is %f\n", iteration, fitness);
	}

}
