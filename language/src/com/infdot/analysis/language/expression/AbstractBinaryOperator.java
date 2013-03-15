package com.infdot.analysis.language.expression;

import java.util.Set;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;

/**
 * Abstract base class for binary operators.
 * 
 * @author Raivo Laanemets
 */
public abstract class AbstractBinaryOperator extends Expression {
	private String op;
	private Expression e1;
	private Expression e2;
	
	public AbstractBinaryOperator(String op, String name, int value) {
		this.op = op;
		this.e1 = new Identifier(name);
		this.e2 = new Constant(value);
	}
	
	public AbstractBinaryOperator(String op, Expression e1, Expression e2) {
		this.op = op;
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public AbstractBinaryOperator(String op, String name1, String name2) {
		this.op = op;
		this.e1 = new Identifier(name1);
		this.e2 = new Identifier(name2);
	}

	public final Expression getE1() {
		return e1;
	}

	public final Expression getE2() {
		return e2;
	}

	@Override
	public final void collectVariables(Set<Identifier> variables) {
		e1.collectVariables(variables);
		e2.collectVariables(variables);
	}
	
	@Override
	public final String toString() {
		return e1 + op + e2;
	}

	@Override
	public final <V> V visit(AbstractExpressionVisitor<V> visitor) {
		return visitOperator(visitor, e1.visit(visitor), e2.visit(visitor));
	}
	
	/**
	 * Base classes must override this for doing work at the node.
	 */
	protected abstract <V> V visitOperator(AbstractExpressionVisitor<V> visitor, V e1, V e2);

}
