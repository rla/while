package com.infdot.analysis.language.expression.visitor;

import com.infdot.analysis.language.expression.Constant;
import com.infdot.analysis.language.expression.Div;
import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.expression.Gt;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.expression.Input;
import com.infdot.analysis.language.expression.Sub;

/**
 * Expression visitor that gives each expression an unique identifier.
 * Identifiers start from 0.
 * 
 * @author Raivo Laanemets
 */
public class ExpressionNumberingVisitor extends AbstractExpressionVisitor<Void> {
	private int expressionId = 0;
	
	@Override
	public Void visitConstant(Constant constant) {
		setId(constant);
		return null;
	}

	@Override
	public Void visitDiv(Div div, Void e1, Void e2) {
		setId(div);
		return null;
	}

	@Override
	public Void visitGt(Gt gt, Void e1, Void e2) {
		setId(gt);
		return null;
	}

	@Override
	public Void visitIdentifier(Identifier identifier) {
		setId(identifier);
		return null;
	}

	@Override
	public Void visitInput(Input input) {
		setId(input);
		return null;
	}

	@Override
	public Void visitSub(Sub sub, Void e1, Void e2) {
		setId(sub);
		return null;
	}
	
	private void setId(Expression e) {
		e.setId(expressionId++);
	}

}
