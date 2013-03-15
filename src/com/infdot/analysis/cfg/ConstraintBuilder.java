package com.infdot.analysis.cfg;

import com.infdot.analysis.cfg.node.AssignmentNode;
import com.infdot.analysis.cfg.node.ConditionNode;
import com.infdot.analysis.cfg.node.DeclarationNode;
import com.infdot.analysis.cfg.node.EntryNode;
import com.infdot.analysis.cfg.node.ExitNode;
import com.infdot.analysis.cfg.node.OutputNode;
import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;
import com.infdot.analysis.solver.DataflowExpression;

public abstract class ConstraintBuilder<V> extends AbstractNodeVisitor<V> {

	@Override
	public void visitAssignment(AssignmentNode node) {
		setConstraint(node, assignment(node));
	}

	@Override
	public void visitDeclaration(DeclarationNode node) {
		setConstraint(node, declaration(node));
	}

	@Override
	public void visitExit(ExitNode node) {
		setConstraint(node, exit(node));
	}

	@Override
	public void visitOutput(OutputNode node) {
		setConstraint(node, output(node));
	}

	@Override
	public void visitCondition(ConditionNode node) {
		setConstraint(node, condition(node));
	}
	
	@Override
	public void visitEntry(EntryNode node) {
		setConstraint(node, entry(node));
	}

	public abstract DataflowExpression<V> assignment(AssignmentNode assignment);
	
	public abstract DataflowExpression<V> declaration(DeclarationNode declaration);
	
	public abstract DataflowExpression<V> exit(ExitNode exit);
	
	public abstract DataflowExpression<V> output(OutputNode output);
	
	public abstract DataflowExpression<V> condition(ConditionNode condition);
	
	public abstract DataflowExpression<V> entry(EntryNode exit);
}
