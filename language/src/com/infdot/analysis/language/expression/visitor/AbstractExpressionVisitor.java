package com.infdot.analysis.language.expression.visitor;

import com.infdot.analysis.language.expression.Constant;
import com.infdot.analysis.language.expression.Div;
import com.infdot.analysis.language.expression.Gt;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.expression.Input;
import com.infdot.analysis.language.expression.Sub;

/**
 * Base class for expression visitors. Assumes that there are no loops
 * in expression (trees). Has no cycle detection.
 * 
 * @author Raivo Laanemets
 */
public abstract class AbstractExpressionVisitor<V> {
	
	public abstract V visitConstant(Constant constant);
	
	public abstract V visitDiv(Div div, V e1, V e2);
	
	public abstract V visitGt(Gt gt, V e1, V e2);
	
	public abstract V visitIdentifier(Identifier identifier);
	
	public abstract V visitInput(Input input);
	
	public abstract V visitSub(Sub sub, V e1, V e2);
}
