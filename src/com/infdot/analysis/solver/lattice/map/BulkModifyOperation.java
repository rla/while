package com.infdot.analysis.solver.lattice.map;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;

/**
 * Expression to modify map lattice by overwriting values with
 * another map.
 * 
 * @author Raivo Laanemets
 *
 * @param <K> type of map key.
 * @param <V> type of map value.
 */
public class BulkModifyOperation<K, V> implements DataflowExpression<Map<K, V>> {
	private DataflowExpression<Map<K, V>> map;
	private Map<K, V> mods;

	public BulkModifyOperation(DataflowExpression<Map<K, V>> map, Map<K, V> mods) {
		this.map = map;
		this.mods = mods;
	}

	@Override
	public Map<K, V> eval(Map<K, V>[] values) {
		Map<K, V> m = map.eval(values);
		
		for (Entry<K, V> e : mods.entrySet()) {
			m.put(e.getKey(), e.getValue());
		}
		
		return m;
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		map.collectVariables(variables);
	}

	@Override
	public String toString() {
		return map.toString() + mods;
	}

}
