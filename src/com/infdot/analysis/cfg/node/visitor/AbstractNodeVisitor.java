package com.infdot.analysis.cfg.node.visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.infdot.analysis.cfg.node.AbstractNode;
import com.infdot.analysis.cfg.node.AssignmentNode;
import com.infdot.analysis.cfg.node.ConditionNode;
import com.infdot.analysis.cfg.node.DeclarationNode;
import com.infdot.analysis.cfg.node.EntryNode;
import com.infdot.analysis.cfg.node.ExitNode;
import com.infdot.analysis.cfg.node.OutputNode;
import com.infdot.analysis.solver.DataflowConstraintSet;
import com.infdot.analysis.solver.DataflowExpression;

/**
 * Base class for CFG visitors.
 * 
 * @author Raivo Laanemets
 * 
 * @param <V> type of dataflow variable values (lattice type).
 */
public abstract class AbstractNodeVisitor<V> {
	
	private Map<AbstractNode, DataflowExpression<V>> visited =
		new HashMap<AbstractNode, DataflowExpression<V>>();
	
	private int currentVarId = 0;
	private Map<AbstractNode, Integer> variables =
		new HashMap<AbstractNode, Integer>();
	
	public abstract void visitAssignment(AssignmentNode node);
	
	public abstract void visitDeclaration(DeclarationNode node);
	
	public abstract void visitExit(ExitNode node);
	
	public abstract void visitOutput(OutputNode node);
	
	public abstract void visitCondition(ConditionNode node);
	
	public abstract void visitEntry(EntryNode node);
	
	public boolean hasVisited(AbstractNode node) {
		return visited.containsKey(node);
	}
	
	public void markVisited(AbstractNode node) {
		visited.put(node, null);
	}
	
	protected void setConstraint(AbstractNode node, DataflowExpression<V> expression) {
		visited.put(node, expression);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (Entry<AbstractNode, DataflowExpression<V>> e : visited.entrySet()) {
			builder.append("[[").append(e.getKey()).append("]]")
				.append(" = ").append(e.getValue()).append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * Returns collected dataflow expressions.
	 */
	public Map<AbstractNode, DataflowExpression<V>> getExpressions() {
		return visited;
	}
	
	/**
	 * Helper method to generate variable id from given node.
	 * For this analysis each node is a variable.
	 */
	protected int getVariableId(AbstractNode node) {
		Integer id = variables.get(node);
		if (id == null) {
			id = currentVarId++;
			variables.put(node, id);
		}
		
		return id;
	}
	
	/**
	 * Creates constraint set from found expressions.
	 */
	public DataflowConstraintSet<V, AbstractNode> getConstraints() {
		
		DataflowConstraintSet<V, AbstractNode> set =
			new DataflowConstraintSet<V, AbstractNode>();
		
		for (Entry<AbstractNode, DataflowExpression<V>> e : visited.entrySet()) {
			
			// Ignore if there is no constraint
			// for the node.
			if (e.getValue() == null) {
				continue;
			}
			
			set.addConstraint(getVariableId(e.getKey()), e.getValue(), e.getKey());
		}
		
		return set;
	}

}
