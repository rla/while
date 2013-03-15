package com.infdot.analysis.solver.lattice.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;

public class EmptyMapOperation<K, V> implements DataflowExpression<Map<K, V>> {

	@Override
	public Map<K, V> eval(Map<K, V>[] values) {
		return new HashMap<K, V>();
	}

	@Override
	public void collectVariables(Set<Integer> variables) {}

	@Override
	public String toString() {
		return "[]";
	}

}
