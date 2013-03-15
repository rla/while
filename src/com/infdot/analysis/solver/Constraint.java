package com.infdot.analysis.solver;

public interface Constraint<V> {
	/**
	 * Evaluates constraint on the given values.
	 */
	V eval(V[] values);
}
