/* Generated By:JavaCC: Do not edit this line. WhileLang.java */
package com.infdot.analysis.parser;

import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.expression.Constant;
import com.infdot.analysis.language.expression.Identifier;
import com.infdot.analysis.language.expression.Input;
import com.infdot.analysis.language.expression.Div;
import com.infdot.analysis.language.expression.Expression;
import com.infdot.analysis.language.expression.Gt;
import com.infdot.analysis.language.expression.Sub;

import com.infdot.analysis.language.statement.Statement;
import com.infdot.analysis.language.statement.If;
import com.infdot.analysis.language.statement.While;
import com.infdot.analysis.language.statement.Assignment;
import com.infdot.analysis.language.statement.Declaration;
import com.infdot.analysis.language.statement.Compound;
import com.infdot.analysis.language.statement.Output;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.InputStream;
import java.io.StringReader;

@SuppressWarnings("unused")
public class WhileLang implements WhileLangConstants {
        public static Statement parse(String code) throws ParseException {
                return new WhileLang(new StringReader(code)).program();
        }

        public static Statement parse(InputStream input) throws ParseException {
                return new WhileLang(input).program();
        }

        public static Statement makeStatement(List<Statement> statements) {
                Statement statement = statements.get(0);
                for (Statement next : statements.subList(1, statements.size())) {
                        statement = new Compound(statement, next);
                }

                return statement;
        }

        public static Expression makeBinaryOperator(String symbol,
                        List<Expression> expressions, boolean isLeftAssociative) {
                expressions = new ArrayList<Expression>(expressions);
                if (isLeftAssociative) {
                        Collections.reverse(expressions);
                }

                return makeBinaryOperator1(symbol, expressions, isLeftAssociative);
        }

        private static Expression makeBinaryOperator1(String symbol,
                        List<Expression> expressions, boolean isLeftAssociative) {
                if (expressions.size() == 1) {
                        return expressions.get(0);
                }
                Expression left = expressions.get(0);
                expressions.remove(0);
                Expression right = makeBinaryOperator1(symbol, expressions,
                                isLeftAssociative);

                if (isLeftAssociative) {
                        return makeBinaryOperator(symbol, right, left);
                } else {
                        return makeBinaryOperator(symbol, left, right);
                }
        }

        private static Expression makeBinaryOperator(String op, Expression e1, Expression e2) {
                if ("/".equals(op)) {
                        return new Div(e1, e2);
                } else if ("-".equals(op)) {
                        return new Sub(e1, e2);
                } else if (">".equals(op)) {
                        return new Gt(e1, e2);
                } else {
                        throw new IllegalArgumentException("Unknown operator: " + op);
                }
        }

  final public Statement program() throws ParseException {
        Statement t;
    t = statements();
    jj_consume_token(0);
                {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public Statement statements() throws ParseException {
        Statement t;
        List<Statement> list = new ArrayList<Statement>();
    t = statement();
                          list.add(t);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OUTPUT:
      case WHILE:
      case IF:
      case VAR:
      case IDENTIFIER:
      case 25:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      t = statement();
                            list.add(t);
    }
                {if (true) return makeStatement(list);}
    throw new Error("Missing return statement in function");
  }

  final public Statement statement() throws ParseException {
        Statement t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VAR:
      t = declaration();
      break;
    case IDENTIFIER:
      t = assignment();
      break;
    case IF:
      t = ifs();
      break;
    case WHILE:
      t = whiles();
      break;
    case 25:
      t = block();
      break;
    case OUTPUT:
      t = output();
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public Statement declaration() throws ParseException {
        Token t;
        List<Identifier> ids = new ArrayList<Identifier>();
    jj_consume_token(VAR);
    t = jj_consume_token(IDENTIFIER);
                                 ids.add(new Identifier(t.image));
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      t = jj_consume_token(IDENTIFIER);
                                 ids.add(new Identifier(t.image));
    }
    jj_consume_token(SEMICOLON);
                {if (true) return new Declaration(ids);}
    throw new Error("Missing return statement in function");
  }

  final public Statement assignment() throws ParseException {
        Token t;
        Expression e;
    t = jj_consume_token(IDENTIFIER);
    jj_consume_token(ASSIGN);
    e = expression();
    jj_consume_token(SEMICOLON);
                {if (true) return new Assignment(new Identifier(t.image), e);}
    throw new Error("Missing return statement in function");
  }

  final public Expression expression() throws ParseException {
        Expression e;
        List<Expression> list = new ArrayList<Expression>();
    e = additive();
                         list.add(e);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GT:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      jj_consume_token(GT);
      e = additive();
                               list.add(e);
    }
                {if (true) return makeBinaryOperator(">", list, true);}
    throw new Error("Missing return statement in function");
  }

  final public Expression additive() throws ParseException {
        Expression e;
        List<Expression> list = new ArrayList<Expression>();
    e = multiplicative();
                               list.add(e);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_4;
      }
      jj_consume_token(MINUS);
      e = multiplicative();
                                     list.add(e);
    }
                {if (true) return makeBinaryOperator("-", list, true);}
    throw new Error("Missing return statement in function");
  }

  final public Expression multiplicative() throws ParseException {
        Expression e;
        List<Expression> list = new ArrayList<Expression>();
    e = unary();
                      list.add(e);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SLASH:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_5;
      }
      jj_consume_token(SLASH);
      e = unary();
                            list.add(e);
    }
                {if (true) return makeBinaryOperator("/", list, true);}
    throw new Error("Missing return statement in function");
  }

  final public Expression unary() throws ParseException {
        Token t;
        Expression e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
      t = jj_consume_token(INTEGER_LITERAL);
                                {if (true) return new Constant(Integer.parseInt(t.image));}
      break;
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                             {if (true) return new Identifier(t.image);}
      break;
    case INPUT:
      e = input();
                        {if (true) return e;}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Expression input() throws ParseException {
    jj_consume_token(INPUT);
                {if (true) return new Input();}
    throw new Error("Missing return statement in function");
  }

  final public Statement ifs() throws ParseException {
        Expression c;
        Statement s;
    jj_consume_token(IF);
    jj_consume_token(LPAREN);
    c = expression();
    jj_consume_token(RPAREN);
    s = statement();
                {if (true) return new If(c, s);}
    throw new Error("Missing return statement in function");
  }

  final public Statement whiles() throws ParseException {
        Expression c;
        Statement s;
    jj_consume_token(WHILE);
    jj_consume_token(LPAREN);
    c = expression();
    jj_consume_token(RPAREN);
    s = statement();
                {if (true) return new While(c, s);}
    throw new Error("Missing return statement in function");
  }

  final public Statement block() throws ParseException {
        Statement s;
    jj_consume_token(25);
    s = statements();
    jj_consume_token(26);
                {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  final public Statement output() throws ParseException {
        Token t;
    jj_consume_token(OUTPUT);
    t = jj_consume_token(IDENTIFIER);
    jj_consume_token(SEMICOLON);
                {if (true) return new Output(t.image);}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public WhileLangTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x20015c0,0x20015c0,0x40000,0x100000,0x400000,0x1000000,0x1820,};
   }

  /** Constructor with InputStream. */
  public WhileLang(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public WhileLang(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new WhileLangTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public WhileLang(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new WhileLangTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public WhileLang(WhileLangTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(WhileLangTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[27];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 27; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}