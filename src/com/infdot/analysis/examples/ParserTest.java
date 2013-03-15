package com.infdot.analysis.examples;

import com.infdot.analysis.language.statement.Statement;
import com.infdot.analysis.parser.ParseException;
import com.infdot.analysis.parser.WhileLang;

public class ParserTest {

	public static void main(String[] args) throws ParseException {
		
		Statement program = WhileLang.parse(ParserTest.class.getResourceAsStream("liveness.while"));
		
		StringBuilder builder = new StringBuilder();
		program.toCodeString(builder, "");
		
		System.out.println(builder);
	}

}
