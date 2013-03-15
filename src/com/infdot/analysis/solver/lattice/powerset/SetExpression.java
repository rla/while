package com.infdot.analysis.solver.lattice.powerset;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.util.StringUtil;

/**
 * Dataflow expression to construct powerset value from
 * the given set of values.
 * 
 * @author Raivo Laanemets
 *
 * @param <V> type of set element.
 */
public class SetExpression<V> implements DataflowExpression<Set<V>> {
	private Set<V> set;

	public SetExpression(Collection<V> set) {
		this.set = new HashSet<V>(set);
	}
	
	public SetExpression(V element) {
		this.set = Collections.singleton(element);
	}

	@Override
	public Set<V> eval(Set<V>[] values) {
		return set;
	}

	@Override
	public String toString() {
		return "{" + StringUtil.join(set, ",") + "}";
	}

	@Override
	public void collectVariables(Set<Integer> variables) {}

}
