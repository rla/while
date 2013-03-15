package com.infdot.analysis.util;

public interface Transform<T1, T2> {
	T2 apply(T1 o);
}