package com.infdot.analysis.language.expression;

import java.util.Set;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;
import com.infdot.analysis.language.expression.visitor.ExpressionNumberingVisitor;

/**
 * Base class for language expressions.
 * 
 * @author Raivo Laanemets
 */
public abstract class Expression {
	/**
	 * Unique identifier for each expression.
	 * @see {@link ExpressionNumberingVisitor}
	 */
	private int id = -1;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Helper method to collect variables.
	 */
	public abstract void collectVariables(Set<Identifier> variables);
	
	/**
	 * Method to accept visitor.
	 */
	public abstract <V> V visit(AbstractExpressionVisitor<V> visitor);

	@Override
	public final boolean equals(Object obj) {
		checkId();
		return obj instanceof Expression
			&& ((Expression) obj).id == id;
	}

	@Override
	public final int hashCode() {
		checkId();
		return id;
	}
	
	private void checkId() {
		if (id < 0) {
			throw new IllegalStateException("Expression " + this + " has no id set");
		}
	}

}
