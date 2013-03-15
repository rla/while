package com.infdot.analysis.cfg.node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;
import com.infdot.analysis.examples.VariableNameTransform;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.statement.Declaration;
import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.lattice.powerset.PowersetDomain;
import com.infdot.analysis.util.CollectionUtil;

public class DeclarationNode extends AbstractNode {
	private Declaration variables;

	public DeclarationNode(Declaration variables) {
		this.variables = variables;
	}

	public List<Identifier> getIdentifiers() {
		return variables.getIdentifiers();
	}

	@Override
	protected <V> void visit(AbstractNodeVisitor<V> visitor) {
		visitor.visitDeclaration(this);
	}

	@Override
	public String toString() {
		return variables.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof DeclarationNode
			&& ((DeclarationNode) obj).variables.equals(variables);
	}

	@Override
	public int hashCode() {
		return variables.hashCode();
	}
	
	/**
	 * Common method to retrieve variables as strings from this node.
	 */
	public DataflowExpression<Set<String>> vars() {
		return PowersetDomain.set(CollectionUtil.transform(
				variables.getIdentifiers(), new HashSet<String>(), new VariableNameTransform()));
	}
	
}
