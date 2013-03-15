package com.infdot.analysis.examples.sign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infdot.analysis.cfg.Cfg;
import com.infdot.analysis.cfg.ConstraintBuilder;
import com.infdot.analysis.cfg.node.AbstractNode;
import com.infdot.analysis.cfg.node.AssignmentNode;
import com.infdot.analysis.cfg.node.ConditionNode;
import com.infdot.analysis.cfg.node.DeclarationNode;
import com.infdot.analysis.cfg.node.EntryNode;
import com.infdot.analysis.cfg.node.ExitNode;
import com.infdot.analysis.cfg.node.OutputNode;
import com.infdot.analysis.examples.sign.SignExample.Sign;
import com.infdot.analysis.language.expression.Constant;
import com.infdot.analysis.language.expression.Div;
import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.expression.Gt;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.expression.Input;
import com.infdot.analysis.language.expression.Sub;
import com.infdot.analysis.language.expression.visitor.AbstractExpressionVisitor;
import com.infdot.analysis.language.expression.visitor.ExpressionNumberingVisitor;
import com.infdot.analysis.language.statement.Statement;
import com.infdot.analysis.language.statement.visitor.StatementExpressionVisitor;
import com.infdot.analysis.parser.ParseException;
import com.infdot.analysis.parser.WhileLang;
import com.infdot.analysis.solver.DataflowConstraintSet;
import com.infdot.analysis.solver.DataflowExpression;
import com.infdot.analysis.solver.DataflowVariable;
import com.infdot.analysis.solver.Solver;
import com.infdot.analysis.solver.lattice.ElementJoinOperation;
import com.infdot.analysis.solver.lattice.map.AllJoinOperation;
import com.infdot.analysis.solver.lattice.map.BulkModifyOperation;
import com.infdot.analysis.solver.lattice.map.EmptyMapOperation;
import com.infdot.analysis.solver.lattice.map.MapDomain;
import com.infdot.analysis.solver.lattice.map.ModifyWithEvalOperation;
import com.infdot.analysis.util.Transform;

public class SignExample extends ConstraintBuilder<Map<String, Sign>> {
	
	/**
	 * Lattice values for sign analysis.
	 */
	enum Sign {
		NEG,
		ZERO,
		POS,
		TOP;
		
		static Sign sign(int i) {
			if (i > 0) {
				return POS;
			} else if (i < 0) {
				return NEG;
			} else {
				return ZERO;
			}
		}
	} 

	@Override
	public DataflowExpression<Map<String, Sign>> assignment(
			AssignmentNode assignment) {
		
		return new ModifyWithEvalOperation<String, Sign>(
				join(assignment),
				assignment.getIdentifier().getName(),
				eval(assignment.getExpression()));
	}

	@Override
	public DataflowExpression<Map<String, Sign>> declaration(
			DeclarationNode declaration) {
		
		Map<String, Sign> mods = new HashMap<String, Sign>();
		
		for (Identifier i : declaration.getIdentifiers()) {
			mods.put(i.getName(), null);
		}
		
		return new BulkModifyOperation<String, Sign>(join(declaration), mods);
	}

	@Override
	public DataflowExpression<Map<String, Sign>> exit(ExitNode exit) {
		return join(exit);
	}

	@Override
	public DataflowExpression<Map<String, Sign>> output(OutputNode output) {
		return join(output);
	}

	@Override
	public DataflowExpression<Map<String, Sign>> condition(
			ConditionNode condition) {
		
		return join(condition);
	}

	@Override
	public DataflowExpression<Map<String, Sign>> entry(EntryNode exit) {
		return new EmptyMapOperation<String, Sign>();
	};
	
	private static final SignJoinOperation JOIN_OPERATION = new SignJoinOperation();
	
	/**
	 * Joins dataflow at CFG node.
	 */
	private DataflowExpression<Map<String, Sign>> join(AbstractNode node) {
		List<DataflowExpression<Map<String, Sign>>> vars =
			new ArrayList<DataflowExpression<Map<String,Sign>>>();
		
		for (AbstractNode pred : node.getPredecessors()) {
			vars.add(new DataflowVariable<Map<String,Sign>>(getVariableId(pred), pred));
		}
		
		return new AllJoinOperation<String, Sign>(vars, JOIN_OPERATION);
	}
	
	/**
	 * Constructs transform that can be evaluated during dataflow
	 * calculations.
	 */
	private Transform<Map<String, Sign>, Sign> eval(final Expression e) {
		return new Transform<Map<String, Sign>, Sign>() {

			@Override
			public Sign apply(Map<String, Sign> o) {
				return e.visit(new AbstractEvaluator(o));
			}

			@Override
			public String toString() {
				return "eval(" + e + ")";
			}
			
		};
	}
	
	/**
	 * Joins two sign values of single variable.
	 */
	private static class SignJoinOperation implements ElementJoinOperation<Sign> {

		@Override
		public Sign join(Sign e1, Sign e2) {
			if (e1 == Sign.TOP || e2 == Sign.TOP) {
				return Sign.TOP;
			}
			
			if (e1 == null) {
				return e2;
			}
			
			if (e2 == null) {
				return e1;
			}
			
			if (e1 == e2) {
				return e1;
			}
			
			return Sign.TOP;
		}
	}
	
	/**
	 * Evaluator that calculates the sign of an expression for
	 * the given environment (). 
	 */
	private static class AbstractEvaluator extends AbstractExpressionVisitor<Sign> {
		private Map<String, Sign> env;

		public AbstractEvaluator(Map<String, Sign> env) {
			this.env = env;
		}

		@Override
		public Sign visitConstant(Constant constant) {
			return Sign.sign(constant.getValue());
		}

		@Override
		public Sign visitDiv(Div div, Sign e1, Sign e2) {
			return DIV_TABLE.apply(e1, e2);
		}

		@Override
		public Sign visitGt(Gt gt, Sign e1, Sign e2) {
			return GT_TABLE.apply(e1, e2);
		}

		@Override
		public Sign visitIdentifier(Identifier identifier) {
			return env.get(identifier.getName());
		}

		@Override
		public Sign visitInput(Input input) {
			return Sign.TOP;
		}

		@Override
		public Sign visitSub(Sub sub, Sign e1, Sign e2) {
			return SUB_TABLE.apply(e1, e2);
		}
	}
	
	/**
	 * Base class for calculating abstract
	 * operator results.
	 */
	private static class OperatorTable {
		private Sign[][] table = new Sign[5][];
		private int row = 0;
		
		public void add(Sign nullV, Sign zeroV, Sign negV, Sign posV, Sign topV) {
			table[row++] = new Sign[] {nullV, zeroV, negV, posV, topV};
		}
		
		public Sign apply(Sign e1, Sign e2) {
			return table[signToInt(e1)][signToInt(e2)];
		}
		
		private int signToInt(Sign s) {
			if (s == B) {
				return 0;
			} else if (s == Z) {
				return 1;
			} else if (s == N) {
				return 2;
			} else if (s == P) {
				return 3;
			} else {
				return 4;
			}
		}
	}
	
	private static final Sign B = null;
	private static final Sign Z = Sign.ZERO;
	private static final Sign N = Sign.NEG;
	private static final Sign P = Sign.POS;
	private static final Sign T = Sign.TOP;
	
	private static final OperatorTable ADD_TABLE = new AddTable();
	private static final OperatorTable SUB_TABLE = new SubTable();
	private static final OperatorTable DIV_TABLE = new DivTable();
	private static final OperatorTable GT_TABLE = new GtTable();
	
	/**
	 * Implementation of abstract add (+) operation.
	 */
	private static class AddTable extends OperatorTable {
		public AddTable() {
			add(B, B, B, B, B);
			add(B, Z, N, P, T);
			add(B, N, N, T, T);
			add(B, P, T, P, T);
			add(B, T, T, T, T);
		}
	}
	
	/**
	 * Implementation of abstract substract (-) operation.
	 */
	private static class SubTable extends OperatorTable {
		public SubTable() {
			add(B, B, B, B, B);
			add(B, Z, P, N, T);
			add(B, N, T, N, T);
			add(B, P, P, T, T);
			add(B, T, T, T, T);
		}
	}
	
	/**
	 * Implementation of abstract multiplication (*).
	 */
	private static class MultTable extends OperatorTable {
		public MultTable() {
			add(B, Z, B, B, B);
			add(Z, Z, Z, Z, Z);
			add(B, Z, P, N, T);
			add(B, Z, N, P, T);
			add(B, Z, T, T, T);
		}
	}
	
	/**
	 * Implementation of abstract division (/).
	 */
	private static class DivTable extends OperatorTable {
		public DivTable() {
			add(B, B, B, B, B);
			add(B, T, Z, Z, T);
			add(B, T, T, T, T);
			add(B, T, T, T, T);
			add(B, T, T, T, T);
		}
	}
	
	/**
	 * Implementation of abstract comparision (>).
	 */
	private static class GtTable extends OperatorTable {
		public GtTable() {
			add(B, B, B, B, B);
			add(B, Z, P, Z, T);
			add(B, Z, T, Z, T);
			add(B, P, P, T, T);
			add(B, T, T, T, T);
		}
	}
	
	/**
	 * Implementation of abstract equality comparision (==).
	 */
	private static class EqualTable extends OperatorTable {
		public EqualTable() {
			add(B, B, B, B, B);
			add(B, P, Z, Z, T);
			add(B, Z, T, Z, T);
			add(B, Z, Z, T, T);
			add(B, T, T, T, T);
		}
	}
	
	public static void main(String[] args) throws ParseException {
		Statement program = WhileLang.parse(SignExample.class.getResourceAsStream("sign.while"));
		StringBuilder builder = new StringBuilder();
		program.toCodeString(builder, "");
		
		System.out.println(builder);
		
		StatementExpressionVisitor numberingVisitor =
			new StatementExpressionVisitor(new ExpressionNumberingVisitor());
		
		program.visit(numberingVisitor);
		
		Cfg cfg = new Cfg(program);
		SignExample visitor = new SignExample();
		cfg.visit(visitor);
		
		System.out.println(visitor);
		
		DataflowConstraintSet<Map<String, Sign>, AbstractNode> constraints
			= visitor.getConstraints();
		
		Solver<Map<String, Sign>> solver = new Solver<Map<String, Sign>>(
				new MapDomain<String, Sign>(), constraints);
		
		Map<String, Sign>[] solution = solver.solve();
		
		constraints.dumpWithMetainfo(solution);
	}
}
