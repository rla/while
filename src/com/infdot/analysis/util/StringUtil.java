package com.infdot.analysis.util;

import java.util.Collection;

public class StringUtil {
	
	public static String join(Collection<?> col, String separator) {
		StringBuilder builder = new StringBuilder();
		
		boolean first = true;
		for (Object o : col) {
			if (first) {
				first = false;
			} else {
				builder.append(separator);
			}
			builder.append(o);
		}
		
		return builder.toString();
	}
}
