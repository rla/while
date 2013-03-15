package com.infdot.analysis.solver.lattice.map;

import java.util.Map;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.util.Transform;

/**
 * Implements map modification where value depends
 * on dataflow variable values.
 * 
 * @author Raivo Laanemets
 */
public class ModifyWithEvalOperation<K, V> implements DataflowExpression<Map<K, V>> {
	private DataflowExpression<Map<K, V>> map;
	private K key;
	private Transform<Map<K, V>, V> exp;
	
	public ModifyWithEvalOperation(DataflowExpression<Map<K, V>> map, K key,
			Transform<Map<K, V>, V> exp) {

		this.map = map;
		this.key = key;
		this.exp = exp;
	}

	@Override
	public Map<K, V> eval(Map<K, V>[] values) {
		Map<K, V> m = map.eval(values);
		V v = exp.apply(m);
		m.put(key, v);
		
		return m;
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		map.collectVariables(variables);
	}

	@Override
	public String toString() {
		return map + "{" + key + "->" + exp + "}";
	}
	
}
