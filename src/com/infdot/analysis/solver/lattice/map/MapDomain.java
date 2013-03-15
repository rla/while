package com.infdot.analysis.solver.lattice.map;

import java.util.HashMap;
import java.util.Map;

import com.infdot.analysis.solver.lattice.Domain;

public class MapDomain<K, V> implements Domain<Map<K, V>> {

	@Override
	public Map<K, V> bottom() {
		return new HashMap<K, V>();
	}

	@Override
	public Map<K, V>[] createStartState(int size) {
		@SuppressWarnings("unchecked")
		Map<K, V>[] s = new Map[size];
		
		for (int i = 0; i < size; i++) {
			s[i] = bottom();
		}
		
		return s;
	}

}
