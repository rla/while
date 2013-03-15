package com.infdot.analysis.solver.lattice.powerset;

import java.util.List;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.DataflowVariable;
import com.infdot.analysis.util.StringUtil;

/**
 * Intersect operation that calculates intersect of all
 * given variables. 
 * 
 * @author Raivo Laanemets
 */
public class AllIntersectOperation<V> implements DataflowExpression<Set<V>> {
	private List<DataflowVariable<Set<V>>> variables;
	
	public AllIntersectOperation(List<DataflowVariable<Set<V>>> variables) {
		this.variables = variables;
	}

	@Override
	public Set<V> eval(Set<V>[] values) {
		Set<V> set = variables.get(0).eval(values);
		
		for (DataflowVariable<Set<V>> v : variables.subList(1, variables.size())) {
			set.retainAll(v.eval(values));
		}
		
		return set;
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		for (DataflowVariable<Set<V>> v : this.variables) {
			v.collectVariables(variables);
		}
	}
	
	@Override
	public String toString() {
		return "I(" + StringUtil.join(variables, ",") + ")";
	}

}
