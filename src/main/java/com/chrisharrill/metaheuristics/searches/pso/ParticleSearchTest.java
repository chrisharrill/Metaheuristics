package com.chrisharrill.metaheuristics.searches.pso;

import com.chrisharrill.metaheuristics.accept.Acceptor;
import com.chrisharrill.metaheuristics.accept.FitnessBasedAcceptor;
import com.chrisharrill.metaheuristics.fitness.Fitness;
import com.chrisharrill.metaheuristics.fitness.MichaelwiczFitness;
import com.chrisharrill.metaheuristics.searches.Search;
import com.chrisharrill.metaheuristics.watch.SimpleWatcher;
import com.chrisharrill.metaheuristics.watch.Watcher;

public class ParticleSearchTest {

	public static void main(String[] args) {
		Watcher watcher = new SimpleWatcher();
		Fitness fitness = new MichaelwiczFitness();
		Acceptor acceptor = new FitnessBasedAcceptor(fitness);
		
		Search search = new ParticleSwarmOptimization(fitness, acceptor, 25);
		search.addWatcher(watcher);
		
		search.search();
	}
}
