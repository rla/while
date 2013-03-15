package com.infdot.analysis.examples.liveness;

import java.util.Set;

import com.infdot.analysis.cfg.Cfg;
import com.infdot.analysis.cfg.node.AbstractNode;
import com.infdot.analysis.language.expression.visitor.ExpressionNumberingVisitor;
import com.infdot.analysis.language.statement.Statement;
import com.infdot.analysis.language.statement.visitor.StatementExpressionVisitor;
import com.infdot.analysis.parser.ParseException;
import com.infdot.analysis.parser.WhileLang;
import com.infdot.analysis.solver.DataflowConstraintSet;
import com.infdot.analysis.solver.Solver;
import com.infdot.analysis.solver.lattice.powerset.PowersetDomain;

/**
 * Lecture Notes on Static Analysis, p. 19
 * 
 * @author Raivo Laanemets
 */
public class Liveness {
	
	public static void main(String[] args) throws ParseException {
		
		Statement program = WhileLang.parse(Liveness.class.getResourceAsStream("liveness.while"));
		StringBuilder builder = new StringBuilder();
		program.toCodeString(builder, "");
		
		System.out.println(builder);
		
		StatementExpressionVisitor numberingVisitor =
			new StatementExpressionVisitor(new ExpressionNumberingVisitor());
		
		program.visit(numberingVisitor);
		
		Cfg cfg = new Cfg(program);
		LivenessVisitor visitor = new LivenessVisitor();
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
