package com.infdot.analysis.language.statement;

import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;


public interface Statement {
	/**
	 * Method to build string representation of code.
	 */
	void toCodeString(StringBuilder builder, String ident);
	
	/**
	 * Method for accepting statement visitor.
	 */
	<T> T visit(AbstractStatementVisitor<T> visitor);
}
