package com.infdot.analysis.cfg.node;

import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;
import com.infdot.analysis.examples.VariableNameTransform;
import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.lattice.powerset.PowersetDomain;
import com.infdot.analysis.util.CollectionUtil;

/**
 * CFG node for condition expressions.
 * 
 * @author Raivo Laanemets
 */
public class ConditionNode extends AbstractNode {
	private Expression condition;

	public ConditionNode(Expression condition) {
		this.condition = condition;
	}

	@Override
	protected <V> void visit(AbstractNodeVisitor<V> visitor) {
		visitor.visitCondition(this);
	}

	public Expression getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		return condition.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ConditionNode
			&& ((ConditionNode) obj).condition.equals(condition);
	}

	@Override
	public int hashCode() {
		return condition.hashCode();
	}
	
	/**
	 * Common method to retrieve variables as strings from this node.
	 */
	public DataflowExpression<Set<String>> vars() {
		Set<Identifier> vars = new HashSet<Identifier>();
		condition.collectVariables(vars);
		
		return PowersetDomain.set(CollectionUtil.transform(
				vars, new HashSet<String>(), new VariableNameTransform()));
	}

}
