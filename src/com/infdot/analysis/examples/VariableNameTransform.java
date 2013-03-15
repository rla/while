package com.infdot.analysis.examples;

import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.util.Transform;

/**
 * Helper class to access variable name.
 * 
 * @author Raivo Laanemets
 */
public class VariableNameTransform implements Transform<Identifier, String> {

	@Override
	public String apply(Identifier o) {
		return o.getName();
	}

}
