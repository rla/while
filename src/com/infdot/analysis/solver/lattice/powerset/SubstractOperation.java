package com.infdot.analysis.solver.lattice.powerset;

import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;

/**
 * Operation to remove set of elements from the given set.
 * 
 * @author Raivo Laanemets
 * 
 * @param <V> type of set element.
 */
public class SubstractOperation<V> implements DataflowExpression<Set<V>> {
	private DataflowExpression<Set<V>> e1;
	private DataflowExpression<Set<V>> e2;

	public SubstractOperation(DataflowExpression<Set<V>> e1,
			DataflowExpression<Set<V>> e2) {
		
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	public Set<V> eval(Set<V>[] values) {
		Set<V> set = new HashSet<V>();
		set.addAll(e1.eval(values));
		set.removeAll(e2.eval(values));
		
		return set;
	}

	@Override
	public String toString() {
		return e1 + "\\" + e2;
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		e1.collectVariables(variables);
		e2.collectVariables(variables);
	}

}
