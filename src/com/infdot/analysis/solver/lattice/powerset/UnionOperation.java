package com.infdot.analysis.solver.lattice.powerset;

import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;

/**
 * Dataflow operation to take union of two powerset values.
 * 
 * @author Raivo Laanemets
 */
public class UnionOperation<V> implements DataflowExpression<Set<V>> {
	private DataflowExpression<Set<V>> e1;
	private DataflowExpression<Set<V>> e2;
	
	public UnionOperation(
			DataflowExpression<Set<V>> e1,
			DataflowExpression<Set<V>> e2) {
		
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	public Set<V> eval(Set<V>[] values) {
		Set<V> set = new HashSet<V>();
		
		set.addAll(e1.eval(values));
		set.addAll(e2.eval(values));
		
		return set;
	}

	@Override
	public String toString() {
		return e1 + "U" + e2;
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		e1.collectVariables(variables);
		e2.collectVariables(variables);
	}

}
