package com.infdot.analysis.language.statement;

import java.util.ArrayList;
import java.util.List;

import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;

public class Declaration implements Statement {
	private List<Identifier> identifiers;
	
	public Declaration(String... strings) {
		this.identifiers = new ArrayList<Identifier>();
		for (String string : strings) {
			identifiers.add(new Identifier(string));
		}
	}

	public Declaration(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	@Override
	public String toString() {
		return "var " + identifiers.toString();
	}

	@Override
	public void toCodeString(StringBuilder builder, String ident) {
		builder.append(ident).append(this).append(';');
	}

	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Declaration
			&& ((Declaration) obj).identifiers.equals(identifiers);
	}

	@Override
	public int hashCode() {
		return identifiers.hashCode();
	}

	@Override
	public <T> T visit(AbstractStatementVisitor<T> visitor) {
		return visitor.visitDeclaration(this);
	}
	
}
