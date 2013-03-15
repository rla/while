package com.infdot.analysis.language.statement;

import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;

public class Assignment implements Statement {
	private Identifier identifier;
	private Expression expression;
	
	public Assignment(String string, Expression expression) {
		this.identifier = new Identifier(string);
		this.expression = expression;
	}
	
	public Assignment(Identifier identifier, Expression expression) {
		this.identifier = identifier;
		this.expression = expression;
	}

	@Override
	public void toCodeString(StringBuilder builder, String ident) {
		builder.append(ident).append(this).append(';');
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return identifier + "=" + expression;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Assignment
			&& ((Assignment) obj).identifier.equals(identifier)
			&& ((Assignment) obj).expression.equals(expression);
	}

	@Override
	public int hashCode() {
		return identifier.hashCode() ^ expression.hashCode();
	}

	@Override
	public <T> T visit(AbstractStatementVisitor<T> visitor) {
		return visitor.visitAssignment(this);
	}
	
}
