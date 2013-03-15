package com.infdot.analysis.cfg;

import com.infdot.analysis.cfg.node.AbstractNode;
import com.infdot.analysis.cfg.node.EntryNode;
import com.infdot.analysis.cfg.node.ExitNode;
import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;
import com.infdot.analysis.language.statement.Statement;

/**
 * Represents program CFG (Control Flow Graph).
 * 
 * @author Raivo Laanemets
 */
public class Cfg {
	private AbstractNode start;
	
	/**
	 * Constructs CFG from the given program. Adds single exit
	 * node automatically.
	 */
	public Cfg(Statement program) {
		Fragment fragment = program.visit(new FragmentBuilderVisitor());
		fragment.addSuccessor(new Fragment(new ExitNode()));
		start = new EntryNode();
		start.addSuccessor(fragment.getStart());
	}

	public AbstractNode getStart() {
		return start;
	}
	
	public <V> void visit(AbstractNodeVisitor<V> visitor) {
		start.visitNodes(visitor);
	}

}
