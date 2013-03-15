package com.infdot.analysis.language.expression;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;


public class Gt extends AbstractBinaryOperator {
	
	public Gt(String name, int value) {
		super(">", name, value);
	}
	
	public Gt(Expression e1, Expression e2) {
		super(">", e1, e2);
	}

	@Override
	protected <V> V visitOperator(AbstractExpressionVisitor<V> visitor, V e1, V e2) {
		return visitor.visitGt(this, e1, e2);
	}
	
}
