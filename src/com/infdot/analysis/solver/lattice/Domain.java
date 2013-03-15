package com.infdot.analysis.solver.lattice;

public interface Domain<V> {
	public V bottom();
	
	/**
	 * Factory method to create analysis state of given size.
	 */
	public V[] createStartState(int size);
}
