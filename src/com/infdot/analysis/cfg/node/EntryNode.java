package com.infdot.analysis.cfg.node;

import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;

public class EntryNode extends AbstractNode {

	@Override
	protected <V> void visit(AbstractNodeVisitor<V> visitor) {
		visitor.visitEntry(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof EntryNode;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "entry";
	}

}
