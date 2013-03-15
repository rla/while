package com.infdot.analysis.solver;

import java.util.Set;

import com.infdot.analysis.cfg.node.AbstractNode;

/**
 * Dataflow variable. Assumes that each variable can have
 * unique integer identificator defined by specific analysis. 
 * 
 * @author Raivo Laanemets
 */
public class DataflowVariable<V>
	implements DataflowExpression<V> {
	
	private int id;
	private AbstractNode node;
	
	public DataflowVariable(int id, AbstractNode node) {
		this.id = id;
		this.node = node;
	}

	@Override
	public V eval(V[] values) {
		return values[id];
	}

	@Override
	public String toString() {
		return "[[" + node + "]]";
	}

	@Override
	public void collectVariables(Set<Integer> variables) {
		variables.add(id);
	}

}
