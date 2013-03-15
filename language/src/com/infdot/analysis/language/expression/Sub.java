package com.infdot.analysis.language.expression;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;


public class Sub extends AbstractBinaryOperator {
	
	public Sub(String name, int value) {
		super("-", name, value);
	}
	
	public Sub(String name1, String name2) {
		super("-", name1, name2);
	}
	
	public Sub(Expression e1, Expression e2) {
		super("-", e1, e2);
	}

	@Override
	protected <V> V visitOperator(AbstractExpressionVisitor<V> visitor, V e1, V e2) {
		return visitor.visitSub(this, e1, e2);
	}
	
}
