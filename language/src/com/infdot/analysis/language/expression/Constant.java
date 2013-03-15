package com.infdot.analysis.language.expression;

import java.util.Set;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;

public class Constant extends Expression {
	private int value;

	public Constant(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void collectVariables(Set<Identifier> variables) {}
	
	@Override
	public <V> V visit(AbstractExpressionVisitor<V> visitor) {
		return visitor.visitConstant(this);
	}

	public int getValue() {
		return value;
	}
	
}
