package com.infdot.analysis.examples.liveness;

import static com.infdot.analysis.solver.lattice.powerset.PowersetDomain.one;
import static com.infdot.analysis.solver.lattice.powerset.PowersetDomain.remove;
import static com.infdot.analysis.solver.lattice.powerset.PowersetDomain.sub;
import static com.infdot.analysis.solver.lattice.powerset.PowersetDomain.union;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.infdot.analysis.cfg.node.AbstractNode;
import com.infdot.analysis.cfg.node.AssignmentNode;
import com.infdot.analysis.cfg.node.ConditionNode;
import com.infdot.analysis.cfg.node.DeclarationNode;
import com.infdot.analysis.cfg.node.EntryNode;
import com.infdot.analysis.cfg.node.ExitNode;
import com.infdot.analysis.cfg.node.OutputNode;
import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;
import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.DataflowVariable;
import com.infdot.analysis.solver.lattice.powerset.AllUnionOperation;
import com.infdot.analysis.solver.lattice.powerset.EmptySet;

public class LivenessVisitor extends AbstractNodeVisitor<Set<String>> {
	
	/**
	 * For assignments we have [[n]]=JOIN(n) \ {id} U vars(E).
	 */
	@Override
	public void visitAssignment(AssignmentNode node) {
		setConstraint(node, union(remove(join(node), node.getIdentifier().getName()), node.vars()));
	}

	/**
	 * For declarations we have [[n]]=JOIN(n) \ {id1, ..., idk}
	 */
	@Override
	public void visitDeclaration(DeclarationNode node) {
		setConstraint(node, sub(join(node), node.vars()));
	}

	/**
	 * For exit node we have the empty set.
	 */
	@Override
	public void visitExit(ExitNode node) {
		setConstraint(node, new EmptySet<String>());
	}

	/**
	 * For output statements we have [[n]] = JOIN(n) U vars(E)
	 */
	@Override
	public void visitOutput(OutputNode node) {
		setConstraint(node, union(join(node), one(node.getIdentifier().getName())));
	}
	
	/**
	 * For conditions we have [[n]] = JOIN(n) U vars(E)
	 */
	@Override
	public void visitCondition(ConditionNode node) {
		setConstraint(node, union(join(node), node.vars()));
	}
	
	@Override
	public void visitEntry(EntryNode node) {
		setConstraint(node, new EmptySet<String>());
	}
	
	/**
	 * Helper method for join operation.
	 */
	private DataflowExpression<Set<String>> join(AbstractNode node) {
		List<DataflowVariable<Set<String>>> vars =
			new ArrayList<DataflowVariable<Set<String>>>();
		
		for (AbstractNode suc : node.getSuccessors()) {
			vars.add(new DataflowVariable<Set<String>>(getVariableId(suc), suc));
		}
		
		return new AllUnionOperation<String>(vars);
	}

}
