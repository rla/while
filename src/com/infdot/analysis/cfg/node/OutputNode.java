package com.infdot.analysis.cfg.node;

import com.infdot.analysis.cfg.node.visitor.AbstractNodeVisitor;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.statement.Output;

/**
 * Node for outout statement.
 * 
 * @author Raivo Laanemets
 */
public class OutputNode extends AbstractNode {
	private Output output;

	public OutputNode(Output output) {
		this.output = output;
	}

	public Identifier getIdentifier() {
		return output.getIdentifier();
	}

	@Override
	protected <V> void visit(AbstractNodeVisitor<V> visitor) {
		visitor.visitOutput(this);
	}

	@Override
	public String toString() {
		return output.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof OutputNode
			&& ((OutputNode) obj).output.equals(output);
	}

	@Override
	public int hashCode() {
		return output.hashCode();
	}

}
