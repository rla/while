package com.infdot.analysis.solver;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.infdot.analysis.solver.lattice.Domain;

/**
 * Helper class to keep set of all dataflow constraints
 * of some specific analysis.
 * 
 * @author Raivo Laanemets
 * 
 * @param <V> type of dataflow values.
 * @param <M> type of metainfo associated with dataflow variables.
 */
public class DataflowConstraintSet<V, M> {
	private Map<Integer, DataflowContraint<V>> constraints =
		new HashMap<Integer, DataflowContraint<V>>();
	
	/**
	 * Associates variable to node where it belongs.
	 * Used for interpreting results later.
	 */
	private Map<Integer, M> metainfo = new HashMap<Integer, M>();
	
	/**
	 * Adds constraint xi = F(x1, ..., xn) to the constraint set.
	 */
	public void addConstraint(int variable, DataflowExpression<V> expression, M metainfo) {
		constraints.put(variable, new DataflowContraint<V>(variable, expression));
		this.metainfo.put(variable, metainfo);
	}
	
	/**
	 * Recalculates dependency sets for each constraints. Constraint
	 * Cd depends on constraint xi = ... when xi occurs as variable
	 * in Cd and thus Cd has to be recalculated. 
	 */
	public void recalculateDependencies() {
		for (DataflowContraint<V> constraint : constraints.values()) {
			constraint.clearDependencies();
		}
		
		for (DataflowContraint<V> constraint : constraints.values()) {
			for (int i : constraint.getVariables()) {
				constraints.get(i).addDependent(constraint);
			}
		}
	}
	
	/**
	 * Creates start state for analysis.
	 */
	public V[] createStartState(Domain<V> domain) {
		int size = Collections.max(constraints.keySet()) + 1;
		
		return domain.createStartState(size);
	}
	
	/**
	 * Returns collection of constraints in this set.
	 */
	public Collection<DataflowContraint<V>> getConstraints() {
		return constraints.values();
	}
	
	/**
	 * Helper method for debugging. Outputs variable values with
	 * associated metainfo.
	 */
	public void dumpWithMetainfo(V[] analysis) {
		for (int i = 0; i < analysis.length; i++) {
			System.out.println(metainfo.get(i) + " = " + analysis[i]);
		}
	}
}
