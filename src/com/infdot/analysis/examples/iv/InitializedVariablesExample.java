package com.infdot.analysis.examples.iv;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.infdot.analysis.cfg.Cfg;
import com.infdot.analysis.cfg.ConstraintBuilder;
import com.infdot.analysis.cfg.node.AbstractNode;
import com.infdot.analysis.cfg.node.AssignmentNode;
import com.infdot.analysis.cfg.node.ConditionNode;
import com.infdot.analysis.cfg.node.DeclarationNode;
import com.infdot.analysis.cfg.node.EntryNode;
import com.infdot.analysis.cfg.node.ExitNode;
import com.infdot.analysis.cfg.node.OutputNode;
import com.infdot.analysis.examples.liveness.Liveness;
import com.infdot.analysis.language.expression.visitor.ExpressionNumberingVisitor;
import com.infdot.analysis.language.statement.Statement;
import com.infdot.analysis.language.statement.visitor.StatementExpressionVisitor;
import com.infdot.analysis.parser.ParseException;
import com.infdot.analysis.parser.WhileLang;
import com.infdot.analysis.solver.DataflowConstraintSet;
import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.DataflowVariable;
import com.infdot.analysis.solver.Solver;
import com.infdot.analysis.solver.lattice.powerset.AllIntersectOperation;
import com.infdot.analysis.solver.lattice.powerset.PowersetDomain;

public class InitializedVariablesExample extends ConstraintBuilder<Set<String>> {

	/**
	 * For assignments we have [[n]] = insersect [[w]] U {id}
	 */
	@Override
	public DataflowExpression<Set<String>> assignment(AssignmentNode assignment) {
		return PowersetDomain.union(
			intersect(assignment),
			PowersetDomain.one(assignment.getIdentifier().getName())
		);
	}

	@Override
	public DataflowExpression<Set<String>> declaration(DeclarationNode declaration) {
		return intersect(declaration);
	}

	@Override
	public DataflowExpression<Set<String>> exit(ExitNode exit) {
		return intersect(exit);
	}

	@Override
	public DataflowExpression<Set<String>> output(OutputNode output) {
		return intersect(output);
	}

	@Override
	public DataflowExpression<Set<String>> condition(ConditionNode condition) {
		return intersect(condition);
	}
	
	@Override
	public DataflowExpression<Set<String>> entry(EntryNode exit) {
		return PowersetDomain.empty();
	}
	
	/**
	 * Helper method for intersecting predecessor variables.
	 */
	private DataflowExpression<Set<String>> intersect(AbstractNode node) {
		List<DataflowVariable<Set<String>>> vars =
			new ArrayList<DataflowVariable<Set<String>>>();
		
		for (AbstractNode suc : node.getPredecessors()) {
			vars.add(new DataflowVariable<Set<String>>(getVariableId(suc), suc));
		}
		
		return new AllIntersectOperation<String>(vars);
	}
	
	public static void main(String[] args) throws ParseException {
		Statement program = WhileLang.parse(Liveness.class.getResourceAsStream("liveness.while"));
		StringBuilder builder = new StringBuilder();
		program.toCodeString(builder, "");
		
		System.out.println(builder);
		
		StatementExpressionVisitor numberingVisitor =
			new StatementExpressionVisitor(new ExpressionNumberingVisitor());
		
		program.visit(numberingVisitor);
		
		Cfg cfg = new Cfg(program);
		InitializedVariablesExample visitor = new InitializedVariablesExample();
		cfg.visit(visitor);
		
		System.out.println(visitor);
		
		DataflowConstraintSet<Set<String>, AbstractNode> constraints
			= visitor.getConstraints();
		
		Solver<Set<String>> solver = new Solver<Set<String>>(
				new PowersetDomain<String>(), constraints);
		
		Set<String>[] solution = solver.solve();
		
		constraints.dumpWithMetainfo(solution);
	}

}
