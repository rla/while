package com.infdot.analysis.solver.lattice.powerset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.DataflowVariable;
import com.infdot.analysis.util.StringUtil;

/**
 * Union operator for powerset operator for powerset domain.
 * 
 * @author Raivo Laanemets
 *
 * @param <V> type of set element of set whose powerset is used.
 */
public class AllUnionOperation<V> implements DataflowExpression<Set<V>> {
	private List<DataflowVariable<Set<V>>> variables;

	public AllUnionOperation(List<DataflowVariable<Set<V>>> variables) {
		this.variables = variables;
	}

	@Override
	public Set<V> eval(Set<V>[] values) {
		Set<V> set = new HashSet<V>();
		
		for (DataflowVariable<Set<V>> v : variables) {
			set.addAll(v.eval(values));
		}
		
		return set;
	}

	@Override
	public String toString() {
		return "U(" + StringUtil.join(variables, ",") + ")";
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		for (DataflowVariable<Set<V>> v : this.variables) {
			v.collectVariables(variables);
		}
	}

}
