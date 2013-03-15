package com.infdot.analysis.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtil {
	
	public static <T> Set<T> union(Set<T> s1, Set<T> s2) {
		Set<T> s = new HashSet<T>();
		s.addAll(s1);
		s.addAll(s2);
		
		return s;
	}
	
	public static <T1, T2> Collection<T2> transform(Collection<T1> c1, Collection<T2> c2, Transform<T1, T2> t) {
		for (T1 o : c1) {
			c2.add(t.apply(o));
		}
		
		return c2;
	}

}
