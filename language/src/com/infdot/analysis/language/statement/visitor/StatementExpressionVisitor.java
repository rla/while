package com.infdot.analysis.language.statement.visitor;

import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;
import com.infdot.analysis.language.statement.Assignment;
import com.infdot.analysis.language.statement.Compound;
import com.infdot.analysis.language.statement.Declaration;
import com.infdot.analysis.language.statement.If;
import com.infdot.analysis.language.statement.Output;
import com.infdot.analysis.language.statement.While;

/**
 * Statement visitor that visits each statement
 * expressions with expressions visitors.
 * 
 * @author Raivo Laanemets
 */
public class StatementExpressionVisitor extends AbstractStatementVisitor<Void> {
	private AbstractExpressionVisitor visitor;

	public StatementExpressionVisitor(AbstractExpressionVisitor visitor) {
		this.visitor = visitor;
	}

	@Override
	public Void visitAssignment(Assignment assignment) {
		assignment.getIdentifier().visit(visitor);
		assignment.getExpression().visit(visitor);
		
		return null;
	}

	@Override
	public Void visitCompound(Compound compound, Void s1, Void s2) {
		return null;
	}

	@Override
	public Void visitDeclaration(Declaration declaration) {
		for (Identifier id : declaration.getIdentifiers()) {
			id.visit(visitor);
		}
		
		return null;
	}

	@Override
	public Void visitIf(If ifStatement, Void body) {
		ifStatement.getCondition().visit(visitor);
		return null;
	}

	@Override
	public Void visitOutput(Output output) {
		output.getIdentifier().visit(visitor);
		return null;
	}

	@Override
	public Void visitWhile(While whileStatement, Void body) {
		whileStatement.getCondition().visit(visitor);
		return null;
	}

}
