package com.infdot.analysis.cfg.node;

import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;
import com.infdot.analysis.examples.VariableNameTransform;
import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.statement.Assignment;
import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.lattice.powerset.PowersetDomain;
import com.infdot.analysis.util.CollectionUtil;

/**
 * CFG node for assignments.
 * 
 * @author Raivo Laanemets
 */
public class AssignmentNode extends AbstractNode {
	private Assignment assignment;
	
	public AssignmentNode(Assignment assignment) {
		this.assignment = assignment;
	}

	public Identifier getIdentifier() {
		return assignment.getIdentifier();
	}
	
	public Expression getExpression() {
		return assignment.getExpression();
	}

	@Override
	protected <V> void visit(AbstractNodeVisitor<V> visitor) {
		visitor.visitAssignment(this);
	}

	@Override
	public String toString() {
		return assignment.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AssignmentNode
			&& ((AssignmentNode) obj).assignment.equals(assignment);
	}

	@Override
	public int hashCode() {
		return assignment.hashCode();
	}
	
	/**
	 * Common method to retrieve variables as strings from this node.
	 */
	public DataflowExpression<Set<String>> vars() {
		Set<Identifier> vars = new HashSet<Identifier>();
		assignment.getExpression().collectVariables(vars);
		
		return PowersetDomain.set(CollectionUtil.transform(
				vars, new HashSet<String>(), new VariableNameTransform()));
	}
	
}
