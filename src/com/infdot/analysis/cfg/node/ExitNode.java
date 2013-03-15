package com.infdot.analysis.cfg.node;

import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;

public class ExitNode extends AbstractNode {

	@Override
	protected <V> void visit(AbstractNodeVisitor<V> visitor) {
		visitor.visitExit(this);
	}

	@Override
	public String toString() {
		return "exit";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ExitNode;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
