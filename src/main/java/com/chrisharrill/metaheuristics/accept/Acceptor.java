package com.chrisharrill.metaheuristics.accept;

public interface Acceptor {

	public boolean accept(double fitness, int iteration);
}
