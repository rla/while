package com.infdot.analysis.solver.lattice.powerset;

import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;

public class RemoveOperation<V> implements DataflowExpression<Set<V>> {
	private DataflowExpression<Set<V>> setExpression;
	private V element;

	public RemoveOperation(DataflowExpression<Set<V>> setExpression,
			V element) {

		this.setExpression = setExpression;
		this.element = element;
	}

	@Override
	public Set<V> eval(Set<V>[] values) {
		Set<V> set = new HashSet<V>();
		set.addAll(setExpression.eval(values));
		set.remove(element);
		
		return set;
	}

	@Override
	public String toString() {
		return "(" + setExpression + "\\{" + element + "})";
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		setExpression.collectVariables(variables);
	}

}
