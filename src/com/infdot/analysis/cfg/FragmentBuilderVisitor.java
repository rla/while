package com.infdot.analysis.cfg;

import com.infdot.analysis.cfg.node.AssignmentNode;
import com.infdot.analysis.cfg.node.ConditionNode;
import com.infdot.analysis.cfg.node.DeclarationNode;
import com.infdot.analysis.cfg.node.OutputNode;
import com.infdot.analysis.language.statement.Assignment;
import com.infdot.analysis.language.statement.Compound;
import com.infdot.analysis.language.statement.Declaration;
import com.infdot.analysis.language.statement.If;
import com.infdot.analysis.language.statement.Output;
import com.infdot.analysis.language.statement.While;
import com.infdot.analysis.language.statement.visitor.AbstractStatementVisitor;
import com.infdot.analysis.util.CollectionUtil;

public class FragmentBuilderVisitor extends AbstractStatementVisitor<Fragment> {

	@Override
	public Fragment visitAssignment(Assignment assignment) {
		return new Fragment(new AssignmentNode(assignment));
	}

	@Override
	public Fragment visitCompound(Compound compound, Fragment fragment1, Fragment fragment2) {
		fragment1.addSuccessor(fragment2);
		
		return new Fragment(fragment1.getStart(), fragment2.getEnds());
	}

	@Override
	public Fragment visitDeclaration(Declaration declaration) {
		return new Fragment(new DeclarationNode(declaration));
	}

	@Override
	public Fragment visitIf(If ifStatement, Fragment body) {
		Fragment condition = new Fragment(new ConditionNode(ifStatement.getCondition()));
		
		condition.addSuccessor(body);
		
		return new Fragment(condition.getStart(), CollectionUtil.union(condition.getEnds(), body.getEnds()));
	}

	@Override
	public Fragment visitOutput(Output output) {
		return new Fragment(new OutputNode(output));
	}

	@Override
	public Fragment visitWhile(While whileStatement, Fragment body) {
		Fragment condition = new Fragment(new ConditionNode(whileStatement.getCondition()));
		
		condition.addSuccessor(body);
		body.addSuccessor(condition);
		
		return new Fragment(condition.getStart(), condition.getEnds());
	}

}
