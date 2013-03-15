package com.infdot.analysis.language.expression;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;


public class Div extends AbstractBinaryOperator {

	public Div(Expression e1, Expression e2) {
		super("/", e1, e2);
	}

	public Div(String name, int value) {
		super("/", name, value);
	}

	@Override
	protected <V> V visitOperator(AbstractExpressionVisitor<V> visitor, V e1, V e2) {
		return visitor.visitDiv(this, e1, e2);
	}

}
