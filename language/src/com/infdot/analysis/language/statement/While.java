package com.infdot.analysis.language.statement;

import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;

public class While implements Statement {
	private Expression condition;
	private Statement body;
	
	public While(Expression condition, Statement body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public void toCodeString(StringBuilder builder, String ident) {
		builder.append(ident).append("while (").append(condition).append(") {\n");
		body.toCodeString(builder, ident + "  ");
		builder.append("\n").append(ident).append('}');	
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getBody() {
		return body;
	}

	@Override
	public <T> T visit(AbstractStatementVisitor<T> visitor) {
		T b = body.visit(visitor);
		return visitor.visitWhile(this, b);
	}
	
}
