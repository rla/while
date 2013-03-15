package com.infdot.analysis.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents constraint xi = F(x1, ..., xn) for
 * a single dataflow variable xi.
 * 
 * @author Raivo Laanemets
 *
 * @param <V> type of dataflow values.
 */
public class DataflowContraint<V> {
	private int variable;
	private DataflowExpression<V> expression;
	private Set<Integer> variables = new HashSet<Integer>();
	private List<DataflowContraint<V>> dependent =
		new ArrayList<DataflowContraint<V>>();
	
	/**
	 * Creates new constraint. Automatically calculates
	 * occurring variables.
	 */
	public DataflowContraint(int variable, DataflowExpression<V> expression) {
		this.variable = variable;
		this.expression = expression;
		
		expression.collectVariables(variables);
	}

	/**
	 * Returns all variables that this constraint depends on.
	 */
	public Set<Integer> getVariables() {
		return variables;
	}

	/**
	 * Clears set of depentent constraints (constraints
	 * that depend on this one).
	 */
	public void clearDependencies() {
		dependent.clear();
	}
	
	/**
	 * Adds constraint that depends on this constraint.
	 * @see {@link DataflowConstraintSet#recalculateDependencies()}
	 */
	public void addDependent(DataflowContraint<V> constraint) {
		dependent.add(constraint);
	}

	/**
	 * Returns dataflow expression bound to this constraint.
	 */
	public DataflowExpression<V> getExpression() {
		return expression;
	}

	/**
	 * Returns left side of this constraint.
	 */
	public int getVariable() {
		return variable;
	}

	/**
	 * Returns list of dependent constraints that have to
	 * be re-evaluated when this constraint has been changed.
	 */
	public List<DataflowContraint<V>> getDependent() {
		return dependent;
	}

}
