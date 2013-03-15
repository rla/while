package com.infdot.analysis.solver;

import java.util.LinkedList;
import java.util.Queue;

import com.infdot.analysis.solver.lattice.Domain;

/**
 * Practical worklist solver.
 * "Lecture Notes on Static Analysis" p.19
 * 
 * @author Raivo Laanemets
 * 
 * @param <V> type of dataflow variable values.
 */
public class Solver<V> {
	private Domain<V> domain;
	private DataflowConstraintSet<V, ?> constraints;
	
	/**
	 * Constructs new solver instance.
	 */
	public Solver(Domain<V> domain, DataflowConstraintSet<V, ?> constraints) {
		this.domain = domain;
		this.constraints = constraints;
	}
	
	/**
	 * Solves the analysis. Before solving recalculates constraint
	 * dependencies.
	 */
	public V[] solve() {
		constraints.recalculateDependencies();
		V[] analysis = constraints.createStartState(domain);
		
		// Queue is our worklist.
		Queue<DataflowContraint<V>> queue = new LinkedList<DataflowContraint<V>>();
		queue.addAll(constraints.getConstraints());
		
		while (!queue.isEmpty()) {
			DataflowContraint<V> constraint = queue.poll();
			V x = constraint.getExpression().eval(analysis);
			int v = constraint.getVariable();
			if (!analysis[v].equals(x)) {
				analysis[v] = x;
				// Put all constraints that has to be re-evaluated
				// into the worklist.
				queue.addAll(constraint.getDependent());
			}
		}
		
		return analysis;
	}
}
