package com.infdot.analysis.solver.lattice;

/**
 * Join (least upper bound) operation for single elements of some lattice.
 * 
 * @author Raivo Laanemets
 */
public interface ElementJoinOperation<V> {
	V join(V e1, V e2);
}
