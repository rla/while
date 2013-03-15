package com.infdot.analysis.cfg;

import java.util.Collections;
import java.util.Set;

import com.infdot.analysis.cfg.node.AbstractNode;

/**
 * CFG fragment. Has single start and multiple end nodes.
 * 
 * @author Raivo Laanemets
 */
public class Fragment {
	private AbstractNode start;
	private Set<AbstractNode> ends;
	
	public Fragment(AbstractNode start, Set<AbstractNode> ends) {
		this.start = start;
		this.ends = ends;
	}
	
	public Fragment(AbstractNode node) {
		this.start = node;
		this.ends = Collections.singleton(node);
	}

	public AbstractNode getStart() {
		return start;
	}

	public Set<AbstractNode> getEnds() {
		return ends;
	}
	
	/**
	 * This is the main method to connect CFG fragments.
	 */
	public void addSuccessor(Fragment suc) {
		for (AbstractNode end : ends) {
			end.addSuccessor(suc.getStart());
		}
	}
	
}
