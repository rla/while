package com.infdot.analysis.language.statement;

import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;

public class If implements Statement {
	private Expression condition;
	private Statement statement;
	
	public If(Expression condition, Statement statement) {
		this.condition = condition;
		this.statement = statement;
	}

	@Override
	public void toCodeString(StringBuilder builder, String ident) {
		builder.append(ident).append("if (").append(condition).append(") {\n");
		statement.toCodeString(builder, ident + "  ");
		builder.append('\n').append(ident).append("}");
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof If
			&& ((If) obj).condition.equals(condition)
			&& ((If) obj).statement.equals(statement);
	}

	@Override
	public int hashCode() {
		return condition.hashCode() ^ statement.hashCode();
	}

	public Statement getStatement() {
		return statement;
	}

	public Expression getCondition() {
		return condition;
	}

	@Override
	public <T> T visit(AbstractStatementVisitor<T> visitor) {
		T s = statement.visit(visitor);
		return visitor.visitIf(this, s);
	}
	
}
