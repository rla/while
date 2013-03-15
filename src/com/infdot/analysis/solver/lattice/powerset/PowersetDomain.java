package com.infdot.analysis.solver.lattice.powerset;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.lattice.Domain;

/**
 * Standard powerset domain with empty set as bottom
 * and set union as join operator.
 * 
 * @author Raivo Laanemets
 *
 * @param <T> type of set values.
 */
public class PowersetDomain<T> implements Domain<Set<T>> {

	@Override
	public Set<T> bottom() {
		return Collections.<T>emptySet();
	}

	@Override
	public Set<T>[] createStartState(int size) {
		@SuppressWarnings("unchecked")
		Set<T>[] s = new Set[size];
		
		for (int i = 0; i < size; i++) {
			s[i] = bottom();
		}
		
		return s;
	}
	
	/**
	 * Helper method to construct set expression from concrete set.
	 */
	public static <V> DataflowExpression<Set<V>> set(Collection<V> set) {
		Set<V> strings = new HashSet<V>();
		
		for (V i : set) {
			strings.add(i);
		}
		
		return new SetExpression<V>(strings);
	}
	
	/**
	 * Helper method to construct expression for removing single
	 * element from the powerset value.
	 */
	public static <V> DataflowExpression<Set<V>> remove(
		DataflowExpression<Set<V>> set, V e) {
		
		return new RemoveOperation<V>(set, e);
	}
	
	/**
	 * Helper method to construct union operation between two
	 * powerset dataflow expressions.
	 */
	public static <V> DataflowExpression<Set<V>> union(
		DataflowExpression<Set<V>> e1,
		DataflowExpression<Set<V>> e2) {
		
		return new UnionOperation<V>(e1, e2);
	}
	
	/**
	 * Helper method to construct set substract operation.
	 */
	public static <V> DataflowExpression<Set<V>> sub(
		DataflowExpression<Set<V>> e1,
		DataflowExpression<Set<V>> e2) {
		
		return new SubstractOperation<V>(e1, e2);
	}
	
	/**
	 * Helper method to construct singleton set.
	 */
	public static <V> DataflowExpression<Set<V>> one(V e) {
		return new SetExpression<V>(e);
	}

	public static <V> DataflowExpression<Set<V>> empty() {
		return new EmptySet<V>();
	}

}
