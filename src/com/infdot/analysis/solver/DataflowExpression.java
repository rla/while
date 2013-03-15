package com.infdot.analysis.solver;

import java.util.Set;


public interface DataflowExpression<V> {
	/**
	 * Evaluates expression using the given variable values.
	 */
	V eval(V[] values);
	
	/**
	 * Helper to collect all variables,
	 */
	void collectVariables(Set<Integer> variables);
}
