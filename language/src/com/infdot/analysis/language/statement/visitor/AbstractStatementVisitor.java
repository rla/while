package com.infdot.analysis.language.statement.visitor;

import com.infdot.analysis.language.statement.Assignment;
import com.infdot.analysis.language.statement.Compound;
import com.infdot.analysis.language.statement.Declaration;
import com.infdot.analysis.language.statement.If;
import com.infdot.analysis.language.statement.Output;
import com.infdot.analysis.language.statement.While;

/**
 * Base class for statement visitors.
 * 
 * @author Raivo Laanemets
 */
public abstract class AbstractStatementVisitor<T> {

	public abstract T visitAssignment(Assignment assignment);
	
	public abstract T visitCompound(Compound compound, T s1, T s2);
	
	public abstract T visitDeclaration(Declaration declaration);
	
	public abstract T visitIf(If ifStatement, T body);
	
	public abstract T visitOutput(Output output);
	
	public abstract T visitWhile(While whileStatement, T body);
}
