package com.infdot.analysis.solver.lattice.powerset;

import java.util.Collections;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;

/**
 * Dataflow expression to construct empty set.
 * 
 * @author Raivo Laanemets
 */
public class EmptySet<V> implements DataflowExpression<Set<V>> {

	@Override
	public Set<V> eval(Set<V>[] values) {
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		return "{}";
	}

	@Override
	public void collectVariables(Set<Integer> variables) {}

}
