package com.infdot.analysis.solver.lattice.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.lattice.ElementJoinOperation;
import com.infdot.analysis.util.StringUtil;

/**
 * Join (least upper bound) for map lattices.
 * 
 * @author Raivo Laanemets
 *
 * @param <K> type of map key
 * @param <V> type of map value
 */
public class AllJoinOperation<K, V> implements DataflowExpression<Map<K, V>> {
	private List<DataflowExpression<Map<K, V>>> expressions;
	private ElementJoinOperation<V> elementJoin;

	public AllJoinOperation(List<DataflowExpression<Map<K, V>>> expressions,
			ElementJoinOperation<V> elementJoin) {
		
		this.expressions = expressions;
		this.elementJoin = elementJoin;
	}

	@Override
	public Map<K, V> eval(Map<K, V>[] values) {
		Map<K, V> result = new HashMap<K, V>(expressions.get(0).eval(values));
		
		for (DataflowExpression<Map<K, V>> e : expressions.subList(1, expressions.size())) {
			joinInto(result, e.eval(values));
		}
		
		return result;
	}
	
	private void joinInto(Map<K, V> result, Map<K, V> joinable) {
		for (K k : result.keySet()) {
			result.put(k, elementJoin.join(result.get(k), joinable.get(k)));
		}
		
		for (K k : joinable.keySet()) {
			if (!result.containsKey(k)) {
				result.put(k, elementJoin.join(result.get(k), joinable.get(k)));
			}
		}
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		for (DataflowExpression<Map<K, V>> e : expressions) {
			e.collectVariables(variables);
		}
	}

	@Override
	public String toString() {
		return "ALLJOIN(" + StringUtil.join(expressions, ",") + ")";
	}

}
