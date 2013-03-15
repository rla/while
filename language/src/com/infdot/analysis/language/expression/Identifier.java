package com.infdot.analysis.language.expression;

import java.util.Set;

import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;

public class Identifier extends Expression {
	private String name;

	public Identifier(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void collectVariables(Set<Identifier> variables) {
		variables.add(this);
	}

	@Override
	public <V> V visit(AbstractExpressionVisitor<V> visitor) {
		return visitor.visitIdentifier(this);
	}

	public String getName() {
		return name;
	}
	
}
