package com.infdot.analysis.language.statement;

import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;

public class Output implements Statement {
	private Identifier identifier;
	
	public Output(String name) {
		this.identifier = new Identifier(name);
	}

	public Output(Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public void toCodeString(StringBuilder builder, String ident) {
		builder.append(ident).append(this).append(';');
	}

	@Override
	public String toString() {
		return "output " + identifier;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Output
			&& ((Output) obj).identifier.equals(identifier);
	}

	@Override
	public int hashCode() {
		return identifier.hashCode();
	}

	@Override
	public <T> T visit(AbstractStatementVisitor<T> visitor) {
		return visitor.visitOutput(this);
	}
	
}
