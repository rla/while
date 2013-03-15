package com.infdot.analysis.language.statement;

import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;

public class Compound implements Statement {
	private Statement statement1;
	private Statement statement2;
	
	public Compound(Statement statement1, Statement statement2) {
		this.statement1 = statement1;
		this.statement2 = statement2;
	}

	@Override
	public void toCodeString(StringBuilder builder, String ident) {
		statement1.toCodeString(builder, ident);
		builder.append('\n');
		statement2.toCodeString(builder, ident);
	}
	
	public static Statement makeCompound(Statement... statements) {
		Statement ret = statements[0];
		for (int i = 1; i < statements.length; i++) {
			ret = new Compound(ret, statements[i]);
		}
		
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Compound
			&& ((Compound) obj).statement1.equals(statement1)
			&& ((Compound) obj).statement2.equals(statement2);
	}

	@Override
	public int hashCode() {
		return statement1.hashCode() ^ statement2.hashCode();
	}

	public Statement getStatement1() {
		return statement1;
	}

	public Statement getStatement2() {
		return statement2;
	}

	@Override
	public <T> T visit(AbstractStatementVisitor<T> visitor) {
		T c1 = statement1.visit(visitor);
		T c2 = statement2.visit(visitor);
		
		return visitor.visitCompound(this, c1, c2);
	}

}
