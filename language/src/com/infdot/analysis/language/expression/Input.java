package com.infdot.analysis.language.expression;

import java.util.Set;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;

public class Input extends Expression {

	@Override
	public String toString() {
		return "input";
	}

	@Override
	public void collectVariables(Set<Identifier> variables) {}

	@Override
	public <V> V visit(AbstractExpressionVisitor<V> visitor) {
		return visitor.visitInput(this);
	}
	
}
