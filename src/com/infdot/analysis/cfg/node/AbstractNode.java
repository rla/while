package com.infdot.analysis.cfg.node;

import java.util.HashSet;
import java.util.Set;

import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;

public abstract class AbstractNode {
	private Set<AbstractNode> successors = new HashSet<AbstractNode>();
	private Set<AbstractNode> predecessors = new HashSet<AbstractNode>();
	
	/**
	 * Adds given successor to this node. This node will be
	 * automatically added to the given node's predecessors.
	 */
	public void addSuccessor(AbstractNode n) {
		successors.add(n);
		n.predecessors.add(this);
	}
	
	/**
	 * Returns all successors of this node.
	 */
	public Set<AbstractNode> getSuccessors() {
		return successors;
	}
	
	public Set<AbstractNode> getPredecessors() {
		return predecessors;
	}

	public <V> void visitNodes(AbstractNodeVisitor<V> visitor) {
		if (!visitor.hasVisited(this)) {
			visit(visitor);
			
			for (AbstractNode node : predecessors) {
				node.visitNodes(visitor);
			}
			
			for (AbstractNode node : successors) {
				node.visitNodes(visitor);
			}
		}
	}
	
	protected abstract <V> void visit(AbstractNodeVisitor<V> visitor);

	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException("Subclasses of AbstractNode must implement equals method");
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("Subclasses of AbstractNode must implement hashCode method");
	}
	
}
