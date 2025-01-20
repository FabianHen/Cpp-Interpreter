// Generated from c://Users//Hannes//Documents//Duales
// Studium//HSBI//3.Semester//Compilerbau//Cpp-Compiler//src//main//antlr//Cpp.g4 by ANTLR 4.13.1
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class CppParser extends Parser {
  static {
    RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int T__0 = 1,
      T__1 = 2,
      T__2 = 3,
      T__3 = 4,
      T__4 = 5,
      T__5 = 6,
      T__6 = 7,
      T__7 = 8,
      T__8 = 9,
      T__9 = 10,
      T__10 = 11,
      T__11 = 12,
      T__12 = 13,
      T__13 = 14,
      T__14 = 15,
      T__15 = 16,
      T__16 = 17,
      T__17 = 18,
      T__18 = 19,
      T__19 = 20,
      T__20 = 21,
      T__21 = 22,
      T__22 = 23,
      T__23 = 24,
      T__24 = 25,
      T__25 = 26,
      T__26 = 27,
      T__27 = 28,
      T__28 = 29,
      T__29 = 30,
      T__30 = 31,
      T__31 = 32,
      T__32 = 33,
      T__33 = 34,
      T__34 = 35,
      T__35 = 36,
      TYPEINT = 37,
      TYPECHAR = 38,
      TYPEBOOL = 39,
      ASSIGN = 40,
      EQUSIGN = 41,
      NOT = 42,
      INT = 43,
      CHAR = 44,
      BOOL = 45,
      LEFTBRACKET = 46,
      RIGHTBRACKET = 47,
      ID = 48,
      WS = 49,
      COMMENT = 50;
  public static final int RULE_program = 0,
      RULE_stmt = 1,
      RULE_expr = 2,
      RULE_classDef = 3,
      RULE_classMember = 4,
      RULE_overrideFndecl = 5,
      RULE_virtual = 6,
      RULE_destructor = 7,
      RULE_binding = 8,
      RULE_objcall = 9,
      RULE_vardecl = 10,
      RULE_constructorCall = 11,
      RULE_fndecl = 12,
      RULE_constructor = 13,
      RULE_return = 14,
      RULE_fndef = 15,
      RULE_overrideFndef = 16,
      RULE_print = 17,
      RULE_while = 18,
      RULE_if = 19,
      RULE_elseif = 20,
      RULE_else = 21,
      RULE_identifier = 22,
      RULE_incDec = 23,
      RULE_args = 24,
      RULE_paramlist = 25,
      RULE_param = 26,
      RULE_block = 27,
      RULE_type = 28,
      RULE_assignop = 29;

  private static String[] makeRuleNames() {
    return new String[] {
      "program",
      "stmt",
      "expr",
      "classDef",
      "classMember",
      "overrideFndecl",
      "virtual",
      "destructor",
      "binding",
      "objcall",
      "vardecl",
      "constructorCall",
      "fndecl",
      "constructor",
      "return",
      "fndef",
      "overrideFndef",
      "print",
      "while",
      "if",
      "elseif",
      "else",
      "identifier",
      "incDec",
      "args",
      "paramlist",
      "param",
      "block",
      "type",
      "assignop"
    };
  }

  public static final String[] ruleNames = makeRuleNames();

  private static String[] makeLiteralNames() {
    return new String[] {
      null,
      "';'",
      "'new'",
      "'('",
      "')'",
      "'*'",
      "'/'",
      "'+'",
      "'-'",
      "'=='",
      "'!='",
      "'>'",
      "'<'",
      "'>='",
      "'<='",
      "'&&'",
      "'||'",
      "'{'",
      "'}'",
      "'.'",
      "'class'",
      "':'",
      "'override'",
      "'virtual'",
      "'~'",
      "'void'",
      "'return'",
      "'print_bool'",
      "'print_int'",
      "'print_char'",
      "'while'",
      "'if'",
      "'else'",
      "'&'",
      "'++'",
      "'--'",
      "','",
      "'int'",
      "'char'",
      "'bool'",
      null,
      "'='",
      "'!'",
      null,
      null,
      null,
      "'['",
      "']'"
    };
  }

  private static final String[] _LITERAL_NAMES = makeLiteralNames();

  private static String[] makeSymbolicNames() {
    return new String[] {
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      "TYPEINT",
      "TYPECHAR",
      "TYPEBOOL",
      "ASSIGN",
      "EQUSIGN",
      "NOT",
      "INT",
      "CHAR",
      "BOOL",
      "LEFTBRACKET",
      "RIGHTBRACKET",
      "ID",
      "WS",
      "COMMENT"
    };
  }

  private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated public static final String[] tokenNames;

  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override
  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  @Override
  public String getGrammarFileName() {
    return "Cpp.g4";
  }

  @Override
  public String[] getRuleNames() {
    return ruleNames;
  }

  @Override
  public String getSerializedATN() {
    return _serializedATN;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public CppParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ProgramContext extends ParserRuleContext {
    public TerminalNode EOF() {
      return getToken(CppParser.EOF, 0);
    }

    public List<StmtContext> stmt() {
      return getRuleContexts(StmtContext.class);
    }

    public StmtContext stmt(int i) {
      return getRuleContext(StmtContext.class, i);
    }

    public ProgramContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_program;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterProgram(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitProgram(this);
    }
  }

  public final ProgramContext program() throws RecognitionException {
    ProgramContext _localctx = new ProgramContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_program);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(63);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 348472139186188L) != 0)) {
          {
            {
              setState(60);
              stmt();
            }
          }
          setState(65);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(66);
        match(EOF);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class StmtContext extends ParserRuleContext {
    public ClassDefContext classDef() {
      return getRuleContext(ClassDefContext.class, 0);
    }

    public BindingContext binding() {
      return getRuleContext(BindingContext.class, 0);
    }

    public VardeclContext vardecl() {
      return getRuleContext(VardeclContext.class, 0);
    }

    public FndeclContext fndecl() {
      return getRuleContext(FndeclContext.class, 0);
    }

    public ReturnContext return_() {
      return getRuleContext(ReturnContext.class, 0);
    }

    public ConstructorCallContext constructorCall() {
      return getRuleContext(ConstructorCallContext.class, 0);
    }

    public FndefContext fndef() {
      return getRuleContext(FndefContext.class, 0);
    }

    public PrintContext print() {
      return getRuleContext(PrintContext.class, 0);
    }

    public WhileContext while_() {
      return getRuleContext(WhileContext.class, 0);
    }

    public IfContext if_() {
      return getRuleContext(IfContext.class, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public StmtContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_stmt;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterStmt(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitStmt(this);
    }
  }

  public final StmtContext stmt() throws RecognitionException {
    StmtContext _localctx = new StmtContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_stmt);
    try {
      setState(95);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(68);
            classDef();
            setState(69);
            match(T__0);
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(71);
            binding();
            setState(72);
            match(T__0);
          }
          break;
        case 3:
          enterOuterAlt(_localctx, 3);
          {
            setState(74);
            vardecl();
            setState(75);
            match(T__0);
          }
          break;
        case 4:
          enterOuterAlt(_localctx, 4);
          {
            setState(77);
            fndecl();
            setState(78);
            match(T__0);
          }
          break;
        case 5:
          enterOuterAlt(_localctx, 5);
          {
            setState(80);
            return_();
            setState(81);
            match(T__0);
          }
          break;
        case 6:
          enterOuterAlt(_localctx, 6);
          {
            setState(83);
            constructorCall();
            setState(84);
            match(T__0);
          }
          break;
        case 7:
          enterOuterAlt(_localctx, 7);
          {
            setState(86);
            fndef();
          }
          break;
        case 8:
          enterOuterAlt(_localctx, 8);
          {
            setState(87);
            print();
            setState(88);
            match(T__0);
          }
          break;
        case 9:
          enterOuterAlt(_localctx, 9);
          {
            setState(90);
            while_();
          }
          break;
        case 10:
          enterOuterAlt(_localctx, 10);
          {
            setState(91);
            if_();
          }
          break;
        case 11:
          enterOuterAlt(_localctx, 11);
          {
            setState(92);
            expr(0);
            setState(93);
            match(T__0);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ExprContext extends ParserRuleContext {
    public ExprContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_expr;
    }

    public ExprContext() {}

    public void copyFrom(ExprContext ctx) {
      super.copyFrom(ctx);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class NEWContext extends ExprContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public ArgsContext args() {
      return getRuleContext(ArgsContext.class, 0);
    }

    public NEWContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterNEW(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitNEW(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ADDContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public ADDContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterADD(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitADD(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class CHARContext extends ExprContext {
    public TerminalNode CHAR() {
      return getToken(CppParser.CHAR, 0);
    }

    public CHARContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterCHAR(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitCHAR(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class NEAQUALSContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public NEAQUALSContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterNEAQUALS(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitNEAQUALS(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class INTContext extends ExprContext {
    public TerminalNode INT() {
      return getToken(CppParser.INT, 0);
    }

    public INTContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterINT(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitINT(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class LEAQUALSContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public LEAQUALSContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterLEAQUALS(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitLEAQUALS(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ARRACCContext extends ExprContext {
    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public List<TerminalNode> LEFTBRACKET() {
      return getTokens(CppParser.LEFTBRACKET);
    }

    public TerminalNode LEFTBRACKET(int i) {
      return getToken(CppParser.LEFTBRACKET, i);
    }

    public List<TerminalNode> RIGHTBRACKET() {
      return getTokens(CppParser.RIGHTBRACKET);
    }

    public TerminalNode RIGHTBRACKET(int i) {
      return getToken(CppParser.RIGHTBRACKET, i);
    }

    public ARRACCContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterARRACC(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitARRACC(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class INCDECWRAPContext extends ExprContext {
    public IncDecContext incDec() {
      return getRuleContext(IncDecContext.class, 0);
    }

    public INCDECWRAPContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterINCDECWRAP(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitINCDECWRAP(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class OBJContext extends ExprContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public OBJContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterOBJ(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitOBJ(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class IDContext extends ExprContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public IDContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterID(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitID(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class COLContext extends ExprContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public COLContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterCOL(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitCOL(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class SUBContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public SUBContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterSUB(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitSUB(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ORContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public ORContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterOR(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitOR(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class EQUALSContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public EQUALSContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterEQUALS(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitEQUALS(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class MULContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public MULContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterMUL(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitMUL(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class GREATERContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public GREATERContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterGREATER(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitGREATER(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class GEAQUALSContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public GEAQUALSContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterGEAQUALS(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitGEAQUALS(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class OBJMEMContext extends ExprContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public List<ObjcallContext> objcall() {
      return getRuleContexts(ObjcallContext.class);
    }

    public ObjcallContext objcall(int i) {
      return getRuleContext(ObjcallContext.class, i);
    }

    public OBJMEMContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterOBJMEM(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitOBJMEM(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class DIVContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public DIVContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterDIV(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitDIV(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class NOTContext extends ExprContext {
    public TerminalNode NOT() {
      return getToken(CppParser.NOT, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public NOTContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterNOT(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitNOT(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class BOOLContext extends ExprContext {
    public TerminalNode BOOL() {
      return getToken(CppParser.BOOL, 0);
    }

    public BOOLContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterBOOL(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitBOOL(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ANDContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public ANDContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterAND(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitAND(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class LESSContext extends ExprContext {
    public ExprContext e1;
    public ExprContext e2;

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public LESSContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterLESS(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitLESS(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class FNCALLContext extends ExprContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public List<ObjcallContext> objcall() {
      return getRuleContexts(ObjcallContext.class);
    }

    public ObjcallContext objcall(int i) {
      return getRuleContext(ObjcallContext.class, i);
    }

    public ArgsContext args() {
      return getRuleContext(ArgsContext.class, 0);
    }

    public FNCALLContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterFNCALL(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitFNCALL(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ARRVALSContext extends ExprContext {
    public ArgsContext args() {
      return getRuleContext(ArgsContext.class, 0);
    }

    public ARRVALSContext(ExprContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterARRVALS(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitARRVALS(this);
    }
  }

  public final ExprContext expr() throws RecognitionException {
    return expr(0);
  }

  private ExprContext expr(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    ExprContext _localctx = new ExprContext(_ctx, _parentState);
    ExprContext _prevctx = _localctx;
    int _startState = 4;
    enterRecursionRule(_localctx, 4, RULE_expr, _p);
    int _la;
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(139);
        _errHandler.sync(this);
        switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
          case 1:
            {
              _localctx = new NEWContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;

              setState(98);
              match(T__1);
              setState(99);
              match(ID);
              setState(100);
              match(T__2);
              setState(102);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 347497214115852L) != 0)) {
                {
                  setState(101);
                  args();
                }
              }

              setState(104);
              match(T__3);
            }
            break;
          case 2:
            {
              _localctx = new COLContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(105);
              match(T__2);
              setState(106);
              expr(0);
              setState(107);
              match(T__3);
            }
            break;
          case 3:
            {
              _localctx = new ARRVALSContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(109);
              match(T__16);
              setState(110);
              args();
              setState(111);
              match(T__17);
            }
            break;
          case 4:
            {
              _localctx = new INCDECWRAPContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(113);
              incDec();
            }
            break;
          case 5:
            {
              _localctx = new NOTContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(114);
              match(NOT);
              setState(115);
              expr(7);
            }
            break;
          case 6:
            {
              _localctx = new BOOLContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(116);
              match(BOOL);
            }
            break;
          case 7:
            {
              _localctx = new CHARContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(117);
              match(CHAR);
            }
            break;
          case 8:
            {
              _localctx = new INTContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(118);
              match(INT);
            }
            break;
          case 9:
            {
              _localctx = new IDContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(119);
              match(ID);
            }
            break;
          case 10:
            {
              _localctx = new FNCALLContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(123);
              _errHandler.sync(this);
              _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
              while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                if (_alt == 1) {
                  {
                    {
                      setState(120);
                      objcall();
                    }
                  }
                }
                setState(125);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
              }
              setState(126);
              match(ID);
              setState(127);
              match(T__2);
              setState(129);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 347497214115852L) != 0)) {
                {
                  setState(128);
                  args();
                }
              }

              setState(131);
              match(T__3);
            }
            break;
          case 11:
            {
              _localctx = new OBJMEMContext(_localctx);
              _ctx = _localctx;
              _prevctx = _localctx;
              setState(135);
              _errHandler.sync(this);
              _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
              while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                if (_alt == 1) {
                  {
                    {
                      setState(132);
                      objcall();
                    }
                  }
                }
                setState(137);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
              }
              setState(138);
              match(ID);
            }
            break;
        }
        _ctx.stop = _input.LT(-1);
        setState(190);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null) triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(188);
              _errHandler.sync(this);
              switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                case 1:
                  {
                    _localctx = new MULContext(new ExprContext(_parentctx, _parentState));
                    ((MULContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(141);
                    if (!(precpred(_ctx, 23)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 23)");
                    setState(142);
                    match(T__4);
                    setState(143);
                    ((MULContext) _localctx).e2 = expr(24);
                  }
                  break;
                case 2:
                  {
                    _localctx = new DIVContext(new ExprContext(_parentctx, _parentState));
                    ((DIVContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(144);
                    if (!(precpred(_ctx, 22)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 22)");
                    setState(145);
                    match(T__5);
                    setState(146);
                    ((DIVContext) _localctx).e2 = expr(23);
                  }
                  break;
                case 3:
                  {
                    _localctx = new ADDContext(new ExprContext(_parentctx, _parentState));
                    ((ADDContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(147);
                    if (!(precpred(_ctx, 21)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 21)");
                    setState(148);
                    match(T__6);
                    setState(149);
                    ((ADDContext) _localctx).e2 = expr(22);
                  }
                  break;
                case 4:
                  {
                    _localctx = new SUBContext(new ExprContext(_parentctx, _parentState));
                    ((SUBContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(150);
                    if (!(precpred(_ctx, 20)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 20)");
                    setState(151);
                    match(T__7);
                    setState(152);
                    ((SUBContext) _localctx).e2 = expr(21);
                  }
                  break;
                case 5:
                  {
                    _localctx = new EQUALSContext(new ExprContext(_parentctx, _parentState));
                    ((EQUALSContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(153);
                    if (!(precpred(_ctx, 19)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 19)");
                    setState(154);
                    match(T__8);
                    setState(155);
                    ((EQUALSContext) _localctx).e2 = expr(20);
                  }
                  break;
                case 6:
                  {
                    _localctx = new NEAQUALSContext(new ExprContext(_parentctx, _parentState));
                    ((NEAQUALSContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(156);
                    if (!(precpred(_ctx, 18)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 18)");
                    setState(157);
                    match(T__9);
                    setState(158);
                    ((NEAQUALSContext) _localctx).e2 = expr(19);
                  }
                  break;
                case 7:
                  {
                    _localctx = new GREATERContext(new ExprContext(_parentctx, _parentState));
                    ((GREATERContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(159);
                    if (!(precpred(_ctx, 17)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 17)");
                    setState(160);
                    match(T__10);
                    setState(161);
                    ((GREATERContext) _localctx).e2 = expr(18);
                  }
                  break;
                case 8:
                  {
                    _localctx = new LESSContext(new ExprContext(_parentctx, _parentState));
                    ((LESSContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(162);
                    if (!(precpred(_ctx, 16)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 16)");
                    setState(163);
                    match(T__11);
                    setState(164);
                    ((LESSContext) _localctx).e2 = expr(17);
                  }
                  break;
                case 9:
                  {
                    _localctx = new GEAQUALSContext(new ExprContext(_parentctx, _parentState));
                    ((GEAQUALSContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(165);
                    if (!(precpred(_ctx, 15)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 15)");
                    setState(166);
                    match(T__12);
                    setState(167);
                    ((GEAQUALSContext) _localctx).e2 = expr(16);
                  }
                  break;
                case 10:
                  {
                    _localctx = new LEAQUALSContext(new ExprContext(_parentctx, _parentState));
                    ((LEAQUALSContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(168);
                    if (!(precpred(_ctx, 14)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 14)");
                    setState(169);
                    match(T__13);
                    setState(170);
                    ((LEAQUALSContext) _localctx).e2 = expr(15);
                  }
                  break;
                case 11:
                  {
                    _localctx = new ANDContext(new ExprContext(_parentctx, _parentState));
                    ((ANDContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(171);
                    if (!(precpred(_ctx, 13)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 13)");
                    setState(172);
                    match(T__14);
                    setState(173);
                    ((ANDContext) _localctx).e2 = expr(14);
                  }
                  break;
                case 12:
                  {
                    _localctx = new ORContext(new ExprContext(_parentctx, _parentState));
                    ((ORContext) _localctx).e1 = _prevctx;
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(174);
                    if (!(precpred(_ctx, 12)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 12)");
                    setState(175);
                    match(T__15);
                    setState(176);
                    ((ORContext) _localctx).e2 = expr(13);
                  }
                  break;
                case 13:
                  {
                    _localctx = new ARRACCContext(new ExprContext(_parentctx, _parentState));
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(177);
                    if (!(precpred(_ctx, 11)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 11)");
                    setState(182);
                    _errHandler.sync(this);
                    _alt = 1;
                    do {
                      switch (_alt) {
                        case 1:
                          {
                            {
                              setState(178);
                              match(LEFTBRACKET);
                              setState(179);
                              expr(0);
                              setState(180);
                              match(RIGHTBRACKET);
                            }
                          }
                          break;
                        default:
                          throw new NoViableAltException(this);
                      }
                      setState(184);
                      _errHandler.sync(this);
                      _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
                    } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                  }
                  break;
                case 14:
                  {
                    _localctx = new OBJContext(new ExprContext(_parentctx, _parentState));
                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                    setState(186);
                    if (!(precpred(_ctx, 8)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                    setState(187);
                    match(T__18);
                  }
                  break;
              }
            }
          }
          setState(192);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ClassDefContext extends ParserRuleContext {
    public Token cName;
    public Token pName;

    public List<TerminalNode> ID() {
      return getTokens(CppParser.ID);
    }

    public TerminalNode ID(int i) {
      return getToken(CppParser.ID, i);
    }

    public List<ClassMemberContext> classMember() {
      return getRuleContexts(ClassMemberContext.class);
    }

    public ClassMemberContext classMember(int i) {
      return getRuleContext(ClassMemberContext.class, i);
    }

    public ClassDefContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_classDef;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterClassDef(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitClassDef(this);
    }
  }

  public final ClassDefContext classDef() throws RecognitionException {
    ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_classDef);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(193);
        match(T__19);
        setState(194);
        ((ClassDefContext) _localctx).cName = match(ID);
        setState(197);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__20) {
          {
            setState(195);
            match(T__20);
            setState(196);
            ((ClassDefContext) _localctx).pName = match(ID);
          }
        }

        setState(199);
        match(T__16);
        setState(203);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 282437108105216L) != 0)) {
          {
            {
              setState(200);
              classMember();
            }
          }
          setState(205);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(206);
        match(T__17);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ClassMemberContext extends ParserRuleContext {
    public OverrideFndeclContext overrideFndecl() {
      return getRuleContext(OverrideFndeclContext.class, 0);
    }

    public FndefContext fndef() {
      return getRuleContext(FndefContext.class, 0);
    }

    public DestructorContext destructor() {
      return getRuleContext(DestructorContext.class, 0);
    }

    public VirtualContext virtual() {
      return getRuleContext(VirtualContext.class, 0);
    }

    public VardeclContext vardecl() {
      return getRuleContext(VardeclContext.class, 0);
    }

    public OverrideFndefContext overrideFndef() {
      return getRuleContext(OverrideFndefContext.class, 0);
    }

    public ConstructorContext constructor() {
      return getRuleContext(ConstructorContext.class, 0);
    }

    public ClassMemberContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_classMember;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterClassMember(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitClassMember(this);
    }
  }

  public final ClassMemberContext classMember() throws RecognitionException {
    ClassMemberContext _localctx = new ClassMemberContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_classMember);
    try {
      setState(219);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(208);
            overrideFndecl();
            setState(209);
            match(T__0);
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(211);
            fndef();
          }
          break;
        case 3:
          enterOuterAlt(_localctx, 3);
          {
            setState(212);
            destructor();
          }
          break;
        case 4:
          enterOuterAlt(_localctx, 4);
          {
            setState(213);
            virtual();
          }
          break;
        case 5:
          enterOuterAlt(_localctx, 5);
          {
            setState(214);
            vardecl();
            setState(215);
            match(T__0);
          }
          break;
        case 6:
          enterOuterAlt(_localctx, 6);
          {
            setState(217);
            overrideFndef();
          }
          break;
        case 7:
          enterOuterAlt(_localctx, 7);
          {
            setState(218);
            constructor();
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class OverrideFndeclContext extends ParserRuleContext {
    public FndeclContext fndecl() {
      return getRuleContext(FndeclContext.class, 0);
    }

    public OverrideFndeclContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_overrideFndecl;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterOverrideFndecl(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitOverrideFndecl(this);
    }
  }

  public final OverrideFndeclContext overrideFndecl() throws RecognitionException {
    OverrideFndeclContext _localctx = new OverrideFndeclContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_overrideFndecl);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(221);
        fndecl();
        setState(223);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__21) {
          {
            setState(222);
            match(T__21);
          }
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class VirtualContext extends ParserRuleContext {
    public FndefContext fndef() {
      return getRuleContext(FndefContext.class, 0);
    }

    public FndeclContext fndecl() {
      return getRuleContext(FndeclContext.class, 0);
    }

    public TerminalNode EQUSIGN() {
      return getToken(CppParser.EQUSIGN, 0);
    }

    public TerminalNode INT() {
      return getToken(CppParser.INT, 0);
    }

    public VirtualContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_virtual;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterVirtual(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitVirtual(this);
    }
  }

  public final VirtualContext virtual() throws RecognitionException {
    VirtualContext _localctx = new VirtualContext(_ctx, getState());
    enterRule(_localctx, 12, RULE_virtual);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(225);
        match(T__22);
        setState(234);
        _errHandler.sync(this);
        switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
          case 1:
            {
              {
                setState(226);
                fndecl();
                setState(229);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == EQUSIGN) {
                  {
                    setState(227);
                    match(EQUSIGN);
                    setState(228);
                    match(INT);
                  }
                }

                setState(231);
                match(T__0);
              }
            }
            break;
          case 2:
            {
              setState(233);
              fndef();
            }
            break;
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class DestructorContext extends ParserRuleContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public DestructorContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_destructor;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterDestructor(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitDestructor(this);
    }
  }

  public final DestructorContext destructor() throws RecognitionException {
    DestructorContext _localctx = new DestructorContext(_ctx, getState());
    enterRule(_localctx, 14, RULE_destructor);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(237);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__22) {
          {
            setState(236);
            match(T__22);
          }
        }

        setState(239);
        match(T__23);
        setState(240);
        match(ID);
        setState(241);
        match(T__2);
        setState(242);
        match(T__3);
        setState(243);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class BindingContext extends ParserRuleContext {
    public IdentifierContext identifier() {
      return getRuleContext(IdentifierContext.class, 0);
    }

    public AssignopContext assignop() {
      return getRuleContext(AssignopContext.class, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public List<ObjcallContext> objcall() {
      return getRuleContexts(ObjcallContext.class);
    }

    public ObjcallContext objcall(int i) {
      return getRuleContext(ObjcallContext.class, i);
    }

    public BindingContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_binding;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterBinding(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitBinding(this);
    }
  }

  public final BindingContext binding() throws RecognitionException {
    BindingContext _localctx = new BindingContext(_ctx, getState());
    enterRule(_localctx, 16, RULE_binding);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(248);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            {
              {
                setState(245);
                objcall();
              }
            }
          }
          setState(250);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
        }
        setState(251);
        identifier();
        setState(252);
        assignop();
        setState(253);
        expr(0);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ObjcallContext extends ParserRuleContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public ArgsContext args() {
      return getRuleContext(ArgsContext.class, 0);
    }

    public ObjcallContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_objcall;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterObjcall(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitObjcall(this);
    }
  }

  public final ObjcallContext objcall() throws RecognitionException {
    ObjcallContext _localctx = new ObjcallContext(_ctx, getState());
    enterRule(_localctx, 18, RULE_objcall);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        {
          setState(255);
          match(ID);
          setState(261);
          _errHandler.sync(this);
          _la = _input.LA(1);
          if (_la == T__2) {
            {
              setState(256);
              match(T__2);
              setState(258);
              _errHandler.sync(this);
              _la = _input.LA(1);
              if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 347497214115852L) != 0)) {
                {
                  setState(257);
                  args();
                }
              }

              setState(260);
              match(T__3);
            }
          }

          setState(263);
          match(T__18);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class VardeclContext extends ParserRuleContext {
    public TypeContext type() {
      return getRuleContext(TypeContext.class, 0);
    }

    public IdentifierContext identifier() {
      return getRuleContext(IdentifierContext.class, 0);
    }

    public TerminalNode EQUSIGN() {
      return getToken(CppParser.EQUSIGN, 0);
    }

    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public VardeclContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_vardecl;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterVardecl(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitVardecl(this);
    }
  }

  public final VardeclContext vardecl() throws RecognitionException {
    VardeclContext _localctx = new VardeclContext(_ctx, getState());
    enterRule(_localctx, 20, RULE_vardecl);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(265);
        type();
        setState(266);
        identifier();
        setState(269);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == EQUSIGN) {
          {
            setState(267);
            match(EQUSIGN);
            setState(268);
            expr(0);
          }
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ConstructorCallContext extends ParserRuleContext {
    public Token cName;
    public Token objName;

    public List<TerminalNode> ID() {
      return getTokens(CppParser.ID);
    }

    public TerminalNode ID(int i) {
      return getToken(CppParser.ID, i);
    }

    public ArgsContext args() {
      return getRuleContext(ArgsContext.class, 0);
    }

    public ConstructorCallContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_constructorCall;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterConstructorCall(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitConstructorCall(this);
    }
  }

  public final ConstructorCallContext constructorCall() throws RecognitionException {
    ConstructorCallContext _localctx = new ConstructorCallContext(_ctx, getState());
    enterRule(_localctx, 22, RULE_constructorCall);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(271);
        ((ConstructorCallContext) _localctx).cName = match(ID);
        setState(272);
        ((ConstructorCallContext) _localctx).objName = match(ID);
        setState(273);
        match(T__2);
        setState(275);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 347497214115852L) != 0)) {
          {
            setState(274);
            args();
          }
        }

        setState(277);
        match(T__3);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class FndeclContext extends ParserRuleContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public TypeContext type() {
      return getRuleContext(TypeContext.class, 0);
    }

    public ParamlistContext paramlist() {
      return getRuleContext(ParamlistContext.class, 0);
    }

    public FndeclContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_fndecl;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterFndecl(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitFndecl(this);
    }
  }

  public final FndeclContext fndecl() throws RecognitionException {
    FndeclContext _localctx = new FndeclContext(_ctx, getState());
    enterRule(_localctx, 24, RULE_fndecl);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(281);
        _errHandler.sync(this);
        switch (_input.LA(1)) {
          case TYPEINT:
          case TYPECHAR:
          case TYPEBOOL:
          case ID:
            {
              setState(279);
              type();
            }
            break;
          case T__24:
            {
              setState(280);
              match(T__24);
            }
            break;
          default:
            throw new NoViableAltException(this);
        }
        setState(283);
        match(ID);
        setState(284);
        match(T__2);
        setState(286);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 282437049384960L) != 0)) {
          {
            setState(285);
            paramlist();
          }
        }

        setState(288);
        match(T__3);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ConstructorContext extends ParserRuleContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public ParamlistContext paramlist() {
      return getRuleContext(ParamlistContext.class, 0);
    }

    public ConstructorContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_constructor;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterConstructor(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitConstructor(this);
    }
  }

  public final ConstructorContext constructor() throws RecognitionException {
    ConstructorContext _localctx = new ConstructorContext(_ctx, getState());
    enterRule(_localctx, 26, RULE_constructor);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(290);
        match(ID);
        setState(291);
        match(T__2);
        setState(293);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 282437049384960L) != 0)) {
          {
            setState(292);
            paramlist();
          }
        }

        setState(295);
        match(T__3);
        setState(296);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ReturnContext extends ParserRuleContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public ReturnContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_return;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterReturn(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitReturn(this);
    }
  }

  public final ReturnContext return_() throws RecognitionException {
    ReturnContext _localctx = new ReturnContext(_ctx, getState());
    enterRule(_localctx, 28, RULE_return);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(298);
        match(T__25);
        setState(300);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 347497214115852L) != 0)) {
          {
            setState(299);
            expr(0);
          }
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class FndefContext extends ParserRuleContext {
    public FndeclContext fndecl() {
      return getRuleContext(FndeclContext.class, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public FndefContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_fndef;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterFndef(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitFndef(this);
    }
  }

  public final FndefContext fndef() throws RecognitionException {
    FndefContext _localctx = new FndefContext(_ctx, getState());
    enterRule(_localctx, 30, RULE_fndef);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(302);
        fndecl();
        setState(303);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class OverrideFndefContext extends ParserRuleContext {
    public FndeclContext fndecl() {
      return getRuleContext(FndeclContext.class, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public OverrideFndefContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_overrideFndef;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterOverrideFndef(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitOverrideFndef(this);
    }
  }

  public final OverrideFndefContext overrideFndef() throws RecognitionException {
    OverrideFndefContext _localctx = new OverrideFndefContext(_ctx, getState());
    enterRule(_localctx, 32, RULE_overrideFndef);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(305);
        fndecl();
        setState(306);
        match(T__21);
        setState(307);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class PrintContext extends ParserRuleContext {
    public PrintContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_print;
    }

    public PrintContext() {}

    public void copyFrom(PrintContext ctx) {
      super.copyFrom(ctx);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class PboolContext extends PrintContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public PboolContext(PrintContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterPbool(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitPbool(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class PintContext extends PrintContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public PintContext(PrintContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterPint(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitPint(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class PcharContext extends PrintContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public PcharContext(PrintContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterPchar(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitPchar(this);
    }
  }

  public final PrintContext print() throws RecognitionException {
    PrintContext _localctx = new PrintContext(_ctx, getState());
    enterRule(_localctx, 34, RULE_print);
    try {
      setState(324);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
        case T__26:
          _localctx = new PboolContext(_localctx);
          enterOuterAlt(_localctx, 1);
          {
            setState(309);
            match(T__26);
            setState(310);
            match(T__2);
            setState(311);
            expr(0);
            setState(312);
            match(T__3);
          }
          break;
        case T__27:
          _localctx = new PintContext(_localctx);
          enterOuterAlt(_localctx, 2);
          {
            setState(314);
            match(T__27);
            setState(315);
            match(T__2);
            setState(316);
            expr(0);
            setState(317);
            match(T__3);
          }
          break;
        case T__28:
          _localctx = new PcharContext(_localctx);
          enterOuterAlt(_localctx, 3);
          {
            setState(319);
            match(T__28);
            setState(320);
            match(T__2);
            setState(321);
            expr(0);
            setState(322);
            match(T__3);
          }
          break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class WhileContext extends ParserRuleContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public WhileContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_while;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterWhile(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitWhile(this);
    }
  }

  public final WhileContext while_() throws RecognitionException {
    WhileContext _localctx = new WhileContext(_ctx, getState());
    enterRule(_localctx, 36, RULE_while);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(326);
        match(T__29);
        setState(327);
        match(T__2);
        setState(328);
        expr(0);
        setState(329);
        match(T__3);
        setState(330);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class IfContext extends ParserRuleContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public List<ElseifContext> elseif() {
      return getRuleContexts(ElseifContext.class);
    }

    public ElseifContext elseif(int i) {
      return getRuleContext(ElseifContext.class, i);
    }

    public ElseContext else_() {
      return getRuleContext(ElseContext.class, 0);
    }

    public IfContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_if;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterIf(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitIf(this);
    }
  }

  public final IfContext if_() throws RecognitionException {
    IfContext _localctx = new IfContext(_ctx, getState());
    enterRule(_localctx, 38, RULE_if);
    int _la;
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        {
          setState(332);
          match(T__30);
          setState(333);
          match(T__2);
          setState(334);
          expr(0);
          setState(335);
          match(T__3);
          setState(336);
          block();
        }
        setState(341);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            {
              {
                setState(338);
                elseif();
              }
            }
          }
          setState(343);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
        }
        setState(345);
        _errHandler.sync(this);
        _la = _input.LA(1);
        if (_la == T__31) {
          {
            setState(344);
            else_();
          }
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ElseifContext extends ParserRuleContext {
    public ExprContext expr() {
      return getRuleContext(ExprContext.class, 0);
    }

    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public ElseifContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_elseif;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterElseif(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitElseif(this);
    }
  }

  public final ElseifContext elseif() throws RecognitionException {
    ElseifContext _localctx = new ElseifContext(_ctx, getState());
    enterRule(_localctx, 40, RULE_elseif);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(347);
        match(T__31);
        setState(348);
        match(T__30);
        setState(349);
        match(T__2);
        setState(350);
        expr(0);
        setState(351);
        match(T__3);
        setState(352);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ElseContext extends ParserRuleContext {
    public BlockContext block() {
      return getRuleContext(BlockContext.class, 0);
    }

    public ElseContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_else;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterElse(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitElse(this);
    }
  }

  public final ElseContext else_() throws RecognitionException {
    ElseContext _localctx = new ElseContext(_ctx, getState());
    enterRule(_localctx, 42, RULE_else);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(354);
        match(T__31);
        setState(355);
        block();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class IdentifierContext extends ParserRuleContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public List<TerminalNode> LEFTBRACKET() {
      return getTokens(CppParser.LEFTBRACKET);
    }

    public TerminalNode LEFTBRACKET(int i) {
      return getToken(CppParser.LEFTBRACKET, i);
    }

    public List<TerminalNode> RIGHTBRACKET() {
      return getTokens(CppParser.RIGHTBRACKET);
    }

    public TerminalNode RIGHTBRACKET(int i) {
      return getToken(CppParser.RIGHTBRACKET, i);
    }

    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public IdentifierContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_identifier;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterIdentifier(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitIdentifier(this);
    }
  }

  public final IdentifierContext identifier() throws RecognitionException {
    IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
    enterRule(_localctx, 44, RULE_identifier);
    int _la;
    try {
      setState(384);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 34, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(358);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == T__32) {
              {
                setState(357);
                match(T__32);
              }
            }

            setState(360);
            match(ID);
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(361);
            match(T__2);
            setState(362);
            match(T__32);
            setState(363);
            match(ID);
            setState(364);
            match(T__3);
            setState(370);
            _errHandler.sync(this);
            _la = _input.LA(1);
            do {
              {
                {
                  setState(365);
                  match(LEFTBRACKET);
                  setState(367);
                  _errHandler.sync(this);
                  _la = _input.LA(1);
                  if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 347497214115852L) != 0)) {
                    {
                      setState(366);
                      expr(0);
                    }
                  }

                  setState(369);
                  match(RIGHTBRACKET);
                }
              }
              setState(372);
              _errHandler.sync(this);
              _la = _input.LA(1);
            } while (_la == LEFTBRACKET);
          }
          break;
        case 3:
          enterOuterAlt(_localctx, 3);
          {
            setState(374);
            match(ID);
            setState(380);
            _errHandler.sync(this);
            _la = _input.LA(1);
            do {
              {
                {
                  setState(375);
                  match(LEFTBRACKET);
                  setState(377);
                  _errHandler.sync(this);
                  _la = _input.LA(1);
                  if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 347497214115852L) != 0)) {
                    {
                      setState(376);
                      expr(0);
                    }
                  }

                  setState(379);
                  match(RIGHTBRACKET);
                }
              }
              setState(382);
              _errHandler.sync(this);
              _la = _input.LA(1);
            } while (_la == LEFTBRACKET);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class IncDecContext extends ParserRuleContext {
    public IncDecContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_incDec;
    }

    public IncDecContext() {}

    public void copyFrom(IncDecContext ctx) {
      super.copyFrom(ctx);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class POSTINCContext extends IncDecContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public POSTINCContext(IncDecContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterPOSTINC(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitPOSTINC(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class PREDECContext extends IncDecContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public PREDECContext(IncDecContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterPREDEC(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitPREDEC(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class POSTDECContext extends IncDecContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public POSTDECContext(IncDecContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterPOSTDEC(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitPOSTDEC(this);
    }
  }

  @SuppressWarnings("CheckReturnValue")
  public static class PREINCContext extends IncDecContext {
    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public PREINCContext(IncDecContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterPREINC(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitPREINC(this);
    }
  }

  public final IncDecContext incDec() throws RecognitionException {
    IncDecContext _localctx = new IncDecContext(_ctx, getState());
    enterRule(_localctx, 46, RULE_incDec);
    try {
      setState(394);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 35, _ctx)) {
        case 1:
          _localctx = new PREINCContext(_localctx);
          enterOuterAlt(_localctx, 1);
          {
            setState(386);
            match(T__33);
            setState(387);
            match(ID);
          }
          break;
        case 2:
          _localctx = new PREDECContext(_localctx);
          enterOuterAlt(_localctx, 2);
          {
            setState(388);
            match(T__34);
            setState(389);
            match(ID);
          }
          break;
        case 3:
          _localctx = new POSTINCContext(_localctx);
          enterOuterAlt(_localctx, 3);
          {
            setState(390);
            match(ID);
            setState(391);
            match(T__33);
          }
          break;
        case 4:
          _localctx = new POSTDECContext(_localctx);
          enterOuterAlt(_localctx, 4);
          {
            setState(392);
            match(ID);
            setState(393);
            match(T__34);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ArgsContext extends ParserRuleContext {
    public List<ExprContext> expr() {
      return getRuleContexts(ExprContext.class);
    }

    public ExprContext expr(int i) {
      return getRuleContext(ExprContext.class, i);
    }

    public ArgsContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_args;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterArgs(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitArgs(this);
    }
  }

  public final ArgsContext args() throws RecognitionException {
    ArgsContext _localctx = new ArgsContext(_ctx, getState());
    enterRule(_localctx, 48, RULE_args);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(396);
        expr(0);
        setState(401);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__35) {
          {
            {
              setState(397);
              match(T__35);
              setState(398);
              expr(0);
            }
          }
          setState(403);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ParamlistContext extends ParserRuleContext {
    public List<ParamContext> param() {
      return getRuleContexts(ParamContext.class);
    }

    public ParamContext param(int i) {
      return getRuleContext(ParamContext.class, i);
    }

    public ParamlistContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_paramlist;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterParamlist(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitParamlist(this);
    }
  }

  public final ParamlistContext paramlist() throws RecognitionException {
    ParamlistContext _localctx = new ParamlistContext(_ctx, getState());
    enterRule(_localctx, 50, RULE_paramlist);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(404);
        param();
        setState(409);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while (_la == T__35) {
          {
            {
              setState(405);
              match(T__35);
              setState(406);
              param();
            }
          }
          setState(411);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ParamContext extends ParserRuleContext {
    public TypeContext type() {
      return getRuleContext(TypeContext.class, 0);
    }

    public IdentifierContext identifier() {
      return getRuleContext(IdentifierContext.class, 0);
    }

    public ParamContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_param;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterParam(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitParam(this);
    }
  }

  public final ParamContext param() throws RecognitionException {
    ParamContext _localctx = new ParamContext(_ctx, getState());
    enterRule(_localctx, 52, RULE_param);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(412);
        type();
        setState(413);
        identifier();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class BlockContext extends ParserRuleContext {
    public List<StmtContext> stmt() {
      return getRuleContexts(StmtContext.class);
    }

    public StmtContext stmt(int i) {
      return getRuleContext(StmtContext.class, i);
    }

    public BlockContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_block;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterBlock(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitBlock(this);
    }
  }

  public final BlockContext block() throws RecognitionException {
    BlockContext _localctx = new BlockContext(_ctx, getState());
    enterRule(_localctx, 54, RULE_block);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(415);
        match(T__16);
        setState(419);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 348472139186188L) != 0)) {
          {
            {
              setState(416);
              stmt();
            }
          }
          setState(421);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(422);
        match(T__17);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class TypeContext extends ParserRuleContext {
    public TerminalNode TYPEBOOL() {
      return getToken(CppParser.TYPEBOOL, 0);
    }

    public TerminalNode TYPECHAR() {
      return getToken(CppParser.TYPECHAR, 0);
    }

    public TerminalNode TYPEINT() {
      return getToken(CppParser.TYPEINT, 0);
    }

    public TerminalNode ID() {
      return getToken(CppParser.ID, 0);
    }

    public TypeContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_type;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterType(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitType(this);
    }
  }

  public final TypeContext type() throws RecognitionException {
    TypeContext _localctx = new TypeContext(_ctx, getState());
    enterRule(_localctx, 56, RULE_type);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(424);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 282437049384960L) != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class AssignopContext extends ParserRuleContext {
    public TerminalNode ASSIGN() {
      return getToken(CppParser.ASSIGN, 0);
    }

    public TerminalNode EQUSIGN() {
      return getToken(CppParser.EQUSIGN, 0);
    }

    public AssignopContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_assignop;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).enterAssignop(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof CppListener) ((CppListener) listener).exitAssignop(this);
    }
  }

  public final AssignopContext assignop() throws RecognitionException {
    AssignopContext _localctx = new AssignopContext(_ctx, getState());
    enterRule(_localctx, 58, RULE_assignop);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(426);
        _la = _input.LA(1);
        if (!(_la == ASSIGN || _la == EQUSIGN)) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
      case 2:
        return expr_sempred((ExprContext) _localctx, predIndex);
    }
    return true;
  }

  private boolean expr_sempred(ExprContext _localctx, int predIndex) {
    switch (predIndex) {
      case 0:
        return precpred(_ctx, 23);
      case 1:
        return precpred(_ctx, 22);
      case 2:
        return precpred(_ctx, 21);
      case 3:
        return precpred(_ctx, 20);
      case 4:
        return precpred(_ctx, 19);
      case 5:
        return precpred(_ctx, 18);
      case 6:
        return precpred(_ctx, 17);
      case 7:
        return precpred(_ctx, 16);
      case 8:
        return precpred(_ctx, 15);
      case 9:
        return precpred(_ctx, 14);
      case 10:
        return precpred(_ctx, 13);
      case 11:
        return precpred(_ctx, 12);
      case 12:
        return precpred(_ctx, 11);
      case 13:
        return precpred(_ctx, 8);
    }
    return true;
  }

  public static final String _serializedATN =
      "\u0004\u00012\u01ad\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"
          + "\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"
          + "\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"
          + "\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"
          + "\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"
          + "\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"
          + "\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"
          + "\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"
          + "\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"
          + "\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0001\u0000\u0005\u0000"
          + ">\b\u0000\n\u0000\f\u0000A\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"
          + "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"
          + "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"
          + "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"
          + "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"
          + "\u0001\u0001\u0001\u0001\u0003\u0001`\b\u0001\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002g\b\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002z\b\u0002"
          + "\n\u0002\f\u0002}\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"
          + "\u0082\b\u0002\u0001\u0002\u0001\u0002\u0005\u0002\u0086\b\u0002\n\u0002"
          + "\f\u0002\u0089\t\u0002\u0001\u0002\u0003\u0002\u008c\b\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"
          + "\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002\u00b7\b\u0002"
          + "\u000b\u0002\f\u0002\u00b8\u0001\u0002\u0001\u0002\u0005\u0002\u00bd\b"
          + "\u0002\n\u0002\f\u0002\u00c0\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003"
          + "\u0001\u0003\u0003\u0003\u00c6\b\u0003\u0001\u0003\u0001\u0003\u0005\u0003"
          + "\u00ca\b\u0003\n\u0003\f\u0003\u00cd\t\u0003\u0001\u0003\u0001\u0003\u0001"
          + "\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"
          + "\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u00dc"
          + "\b\u0004\u0001\u0005\u0001\u0005\u0003\u0005\u00e0\b\u0005\u0001\u0006"
          + "\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00e6\b\u0006\u0001\u0006"
          + "\u0001\u0006\u0001\u0006\u0003\u0006\u00eb\b\u0006\u0001\u0007\u0003\u0007"
          + "\u00ee\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"
          + "\u0001\u0007\u0001\b\u0005\b\u00f7\b\b\n\b\f\b\u00fa\t\b\u0001\b\u0001"
          + "\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0003\t\u0103\b\t\u0001\t\u0003"
          + "\t\u0106\b\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u010e"
          + "\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0114"
          + "\b\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0003\f\u011a\b\f\u0001"
          + "\f\u0001\f\u0001\f\u0003\f\u011f\b\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"
          + "\r\u0003\r\u0126\b\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0003"
          + "\u000e\u012d\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"
          + "\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"
          + "\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"
          + "\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003"
          + "\u0011\u0145\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"
          + "\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"
          + "\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u0154\b\u0013\n\u0013\f\u0013"
          + "\u0157\t\u0013\u0001\u0013\u0003\u0013\u015a\b\u0013\u0001\u0014\u0001"
          + "\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"
          + "\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0003\u0016\u0167\b\u0016\u0001"
          + "\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"
          + "\u0016\u0003\u0016\u0170\b\u0016\u0001\u0016\u0004\u0016\u0173\b\u0016"
          + "\u000b\u0016\f\u0016\u0174\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016"
          + "\u017a\b\u0016\u0001\u0016\u0004\u0016\u017d\b\u0016\u000b\u0016\f\u0016"
          + "\u017e\u0003\u0016\u0181\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001"
          + "\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u018b"
          + "\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u0190\b\u0018"
          + "\n\u0018\f\u0018\u0193\t\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0005"
          + "\u0019\u0198\b\u0019\n\u0019\f\u0019\u019b\t\u0019\u0001\u001a\u0001\u001a"
          + "\u0001\u001a\u0001\u001b\u0001\u001b\u0005\u001b\u01a2\b\u001b\n\u001b"
          + "\f\u001b\u01a5\t\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c"
          + "\u0001\u001d\u0001\u001d\u0001\u001d\u0000\u0001\u0004\u001e\u0000\u0002"
          + "\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"
          + " \"$&(*,.02468:\u0000\u0002\u0002\u0000%\'00\u0001\u0000()\u01dc\u0000"
          + "?\u0001\u0000\u0000\u0000\u0002_\u0001\u0000\u0000\u0000\u0004\u008b\u0001"
          + "\u0000\u0000\u0000\u0006\u00c1\u0001\u0000\u0000\u0000\b\u00db\u0001\u0000"
          + "\u0000\u0000\n\u00dd\u0001\u0000\u0000\u0000\f\u00e1\u0001\u0000\u0000"
          + "\u0000\u000e\u00ed\u0001\u0000\u0000\u0000\u0010\u00f8\u0001\u0000\u0000"
          + "\u0000\u0012\u00ff\u0001\u0000\u0000\u0000\u0014\u0109\u0001\u0000\u0000"
          + "\u0000\u0016\u010f\u0001\u0000\u0000\u0000\u0018\u0119\u0001\u0000\u0000"
          + "\u0000\u001a\u0122\u0001\u0000\u0000\u0000\u001c\u012a\u0001\u0000\u0000"
          + "\u0000\u001e\u012e\u0001\u0000\u0000\u0000 \u0131\u0001\u0000\u0000\u0000"
          + "\"\u0144\u0001\u0000\u0000\u0000$\u0146\u0001\u0000\u0000\u0000&\u014c"
          + "\u0001\u0000\u0000\u0000(\u015b\u0001\u0000\u0000\u0000*\u0162\u0001\u0000"
          + "\u0000\u0000,\u0180\u0001\u0000\u0000\u0000.\u018a\u0001\u0000\u0000\u0000"
          + "0\u018c\u0001\u0000\u0000\u00002\u0194\u0001\u0000\u0000\u00004\u019c"
          + "\u0001\u0000\u0000\u00006\u019f\u0001\u0000\u0000\u00008\u01a8\u0001\u0000"
          + "\u0000\u0000:\u01aa\u0001\u0000\u0000\u0000<>\u0003\u0002\u0001\u0000"
          + "=<\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000"
          + "\u0000?@\u0001\u0000\u0000\u0000@B\u0001\u0000\u0000\u0000A?\u0001\u0000"
          + "\u0000\u0000BC\u0005\u0000\u0000\u0001C\u0001\u0001\u0000\u0000\u0000"
          + "DE\u0003\u0006\u0003\u0000EF\u0005\u0001\u0000\u0000F`\u0001\u0000\u0000"
          + "\u0000GH\u0003\u0010\b\u0000HI\u0005\u0001\u0000\u0000I`\u0001\u0000\u0000"
          + "\u0000JK\u0003\u0014\n\u0000KL\u0005\u0001\u0000\u0000L`\u0001\u0000\u0000"
          + "\u0000MN\u0003\u0018\f\u0000NO\u0005\u0001\u0000\u0000O`\u0001\u0000\u0000"
          + "\u0000PQ\u0003\u001c\u000e\u0000QR\u0005\u0001\u0000\u0000R`\u0001\u0000"
          + "\u0000\u0000ST\u0003\u0016\u000b\u0000TU\u0005\u0001\u0000\u0000U`\u0001"
          + "\u0000\u0000\u0000V`\u0003\u001e\u000f\u0000WX\u0003\"\u0011\u0000XY\u0005"
          + "\u0001\u0000\u0000Y`\u0001\u0000\u0000\u0000Z`\u0003$\u0012\u0000[`\u0003"
          + "&\u0013\u0000\\]\u0003\u0004\u0002\u0000]^\u0005\u0001\u0000\u0000^`\u0001"
          + "\u0000\u0000\u0000_D\u0001\u0000\u0000\u0000_G\u0001\u0000\u0000\u0000"
          + "_J\u0001\u0000\u0000\u0000_M\u0001\u0000\u0000\u0000_P\u0001\u0000\u0000"
          + "\u0000_S\u0001\u0000\u0000\u0000_V\u0001\u0000\u0000\u0000_W\u0001\u0000"
          + "\u0000\u0000_Z\u0001\u0000\u0000\u0000_[\u0001\u0000\u0000\u0000_\\\u0001"
          + "\u0000\u0000\u0000`\u0003\u0001\u0000\u0000\u0000ab\u0006\u0002\uffff"
          + "\uffff\u0000bc\u0005\u0002\u0000\u0000cd\u00050\u0000\u0000df\u0005\u0003"
          + "\u0000\u0000eg\u00030\u0018\u0000fe\u0001\u0000\u0000\u0000fg\u0001\u0000"
          + "\u0000\u0000gh\u0001\u0000\u0000\u0000h\u008c\u0005\u0004\u0000\u0000"
          + "ij\u0005\u0003\u0000\u0000jk\u0003\u0004\u0002\u0000kl\u0005\u0004\u0000"
          + "\u0000l\u008c\u0001\u0000\u0000\u0000mn\u0005\u0011\u0000\u0000no\u0003"
          + "0\u0018\u0000op\u0005\u0012\u0000\u0000p\u008c\u0001\u0000\u0000\u0000"
          + "q\u008c\u0003.\u0017\u0000rs\u0005*\u0000\u0000s\u008c\u0003\u0004\u0002"
          + "\u0007t\u008c\u0005-\u0000\u0000u\u008c\u0005,\u0000\u0000v\u008c\u0005"
          + "+\u0000\u0000w\u008c\u00050\u0000\u0000xz\u0003\u0012\t\u0000yx\u0001"
          + "\u0000\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000"
          + "{|\u0001\u0000\u0000\u0000|~\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000"
          + "\u0000~\u007f\u00050\u0000\u0000\u007f\u0081\u0005\u0003\u0000\u0000\u0080"
          + "\u0082\u00030\u0018\u0000\u0081\u0080\u0001\u0000\u0000\u0000\u0081\u0082"
          + "\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u008c"
          + "\u0005\u0004\u0000\u0000\u0084\u0086\u0003\u0012\t\u0000\u0085\u0084\u0001"
          + "\u0000\u0000\u0000\u0086\u0089\u0001\u0000\u0000\u0000\u0087\u0085\u0001"
          + "\u0000\u0000\u0000\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u008a\u0001"
          + "\u0000\u0000\u0000\u0089\u0087\u0001\u0000\u0000\u0000\u008a\u008c\u0005"
          + "0\u0000\u0000\u008ba\u0001\u0000\u0000\u0000\u008bi\u0001\u0000\u0000"
          + "\u0000\u008bm\u0001\u0000\u0000\u0000\u008bq\u0001\u0000\u0000\u0000\u008b"
          + "r\u0001\u0000\u0000\u0000\u008bt\u0001\u0000\u0000\u0000\u008bu\u0001"
          + "\u0000\u0000\u0000\u008bv\u0001\u0000\u0000\u0000\u008bw\u0001\u0000\u0000"
          + "\u0000\u008b{\u0001\u0000\u0000\u0000\u008b\u0087\u0001\u0000\u0000\u0000"
          + "\u008c\u00be\u0001\u0000\u0000\u0000\u008d\u008e\n\u0017\u0000\u0000\u008e"
          + "\u008f\u0005\u0005\u0000\u0000\u008f\u00bd\u0003\u0004\u0002\u0018\u0090"
          + "\u0091\n\u0016\u0000\u0000\u0091\u0092\u0005\u0006\u0000\u0000\u0092\u00bd"
          + "\u0003\u0004\u0002\u0017\u0093\u0094\n\u0015\u0000\u0000\u0094\u0095\u0005"
          + "\u0007\u0000\u0000\u0095\u00bd\u0003\u0004\u0002\u0016\u0096\u0097\n\u0014"
          + "\u0000\u0000\u0097\u0098\u0005\b\u0000\u0000\u0098\u00bd\u0003\u0004\u0002"
          + "\u0015\u0099\u009a\n\u0013\u0000\u0000\u009a\u009b\u0005\t\u0000\u0000"
          + "\u009b\u00bd\u0003\u0004\u0002\u0014\u009c\u009d\n\u0012\u0000\u0000\u009d"
          + "\u009e\u0005\n\u0000\u0000\u009e\u00bd\u0003\u0004\u0002\u0013\u009f\u00a0"
          + "\n\u0011\u0000\u0000\u00a0\u00a1\u0005\u000b\u0000\u0000\u00a1\u00bd\u0003"
          + "\u0004\u0002\u0012\u00a2\u00a3\n\u0010\u0000\u0000\u00a3\u00a4\u0005\f"
          + "\u0000\u0000\u00a4\u00bd\u0003\u0004\u0002\u0011\u00a5\u00a6\n\u000f\u0000"
          + "\u0000\u00a6\u00a7\u0005\r\u0000\u0000\u00a7\u00bd\u0003\u0004\u0002\u0010"
          + "\u00a8\u00a9\n\u000e\u0000\u0000\u00a9\u00aa\u0005\u000e\u0000\u0000\u00aa"
          + "\u00bd\u0003\u0004\u0002\u000f\u00ab\u00ac\n\r\u0000\u0000\u00ac\u00ad"
          + "\u0005\u000f\u0000\u0000\u00ad\u00bd\u0003\u0004\u0002\u000e\u00ae\u00af"
          + "\n\f\u0000\u0000\u00af\u00b0\u0005\u0010\u0000\u0000\u00b0\u00bd\u0003"
          + "\u0004\u0002\r\u00b1\u00b6\n\u000b\u0000\u0000\u00b2\u00b3\u0005.\u0000"
          + "\u0000\u00b3\u00b4\u0003\u0004\u0002\u0000\u00b4\u00b5\u0005/\u0000\u0000"
          + "\u00b5\u00b7\u0001\u0000\u0000\u0000\u00b6\u00b2\u0001\u0000\u0000\u0000"
          + "\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00b6\u0001\u0000\u0000\u0000"
          + "\u00b8\u00b9\u0001\u0000\u0000\u0000\u00b9\u00bd\u0001\u0000\u0000\u0000"
          + "\u00ba\u00bb\n\b\u0000\u0000\u00bb\u00bd\u0005\u0013\u0000\u0000\u00bc"
          + "\u008d\u0001\u0000\u0000\u0000\u00bc\u0090\u0001\u0000\u0000\u0000\u00bc"
          + "\u0093\u0001\u0000\u0000\u0000\u00bc\u0096\u0001\u0000\u0000\u0000\u00bc"
          + "\u0099\u0001\u0000\u0000\u0000\u00bc\u009c\u0001\u0000\u0000\u0000\u00bc"
          + "\u009f\u0001\u0000\u0000\u0000\u00bc\u00a2\u0001\u0000\u0000\u0000\u00bc"
          + "\u00a5\u0001\u0000\u0000\u0000\u00bc\u00a8\u0001\u0000\u0000\u0000\u00bc"
          + "\u00ab\u0001\u0000\u0000\u0000\u00bc\u00ae\u0001\u0000\u0000\u0000\u00bc"
          + "\u00b1\u0001\u0000\u0000\u0000\u00bc\u00ba\u0001\u0000\u0000\u0000\u00bd"
          + "\u00c0\u0001\u0000\u0000\u0000\u00be\u00bc\u0001\u0000\u0000\u0000\u00be"
          + "\u00bf\u0001\u0000\u0000\u0000\u00bf\u0005\u0001\u0000\u0000\u0000\u00c0"
          + "\u00be\u0001\u0000\u0000\u0000\u00c1\u00c2\u0005\u0014\u0000\u0000\u00c2"
          + "\u00c5\u00050\u0000\u0000\u00c3\u00c4\u0005\u0015\u0000\u0000\u00c4\u00c6"
          + "\u00050\u0000\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001"
          + "\u0000\u0000\u0000\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u00cb\u0005"
          + "\u0011\u0000\u0000\u00c8\u00ca\u0003\b\u0004\u0000\u00c9\u00c8\u0001\u0000"
          + "\u0000\u0000\u00ca\u00cd\u0001\u0000\u0000\u0000\u00cb\u00c9\u0001\u0000"
          + "\u0000\u0000\u00cb\u00cc\u0001\u0000\u0000\u0000\u00cc\u00ce\u0001\u0000"
          + "\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005\u0012"
          + "\u0000\u0000\u00cf\u0007\u0001\u0000\u0000\u0000\u00d0\u00d1\u0003\n\u0005"
          + "\u0000\u00d1\u00d2\u0005\u0001\u0000\u0000\u00d2\u00dc\u0001\u0000\u0000"
          + "\u0000\u00d3\u00dc\u0003\u001e\u000f\u0000\u00d4\u00dc\u0003\u000e\u0007"
          + "\u0000\u00d5\u00dc\u0003\f\u0006\u0000\u00d6\u00d7\u0003\u0014\n\u0000"
          + "\u00d7\u00d8\u0005\u0001\u0000\u0000\u00d8\u00dc\u0001\u0000\u0000\u0000"
          + "\u00d9\u00dc\u0003 \u0010\u0000\u00da\u00dc\u0003\u001a\r\u0000\u00db"
          + "\u00d0\u0001\u0000\u0000\u0000\u00db\u00d3\u0001\u0000\u0000\u0000\u00db"
          + "\u00d4\u0001\u0000\u0000\u0000\u00db\u00d5\u0001\u0000\u0000\u0000\u00db"
          + "\u00d6\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00db"
          + "\u00da\u0001\u0000\u0000\u0000\u00dc\t\u0001\u0000\u0000\u0000\u00dd\u00df"
          + "\u0003\u0018\f\u0000\u00de\u00e0\u0005\u0016\u0000\u0000\u00df\u00de\u0001"
          + "\u0000\u0000\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0\u000b\u0001"
          + "\u0000\u0000\u0000\u00e1\u00ea\u0005\u0017\u0000\u0000\u00e2\u00e5\u0003"
          + "\u0018\f\u0000\u00e3\u00e4\u0005)\u0000\u0000\u00e4\u00e6\u0005+\u0000"
          + "\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000"
          + "\u0000\u00e6\u00e7\u0001\u0000\u0000\u0000\u00e7\u00e8\u0005\u0001\u0000"
          + "\u0000\u00e8\u00eb\u0001\u0000\u0000\u0000\u00e9\u00eb\u0003\u001e\u000f"
          + "\u0000\u00ea\u00e2\u0001\u0000\u0000\u0000\u00ea\u00e9\u0001\u0000\u0000"
          + "\u0000\u00eb\r\u0001\u0000\u0000\u0000\u00ec\u00ee\u0005\u0017\u0000\u0000"
          + "\u00ed\u00ec\u0001\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000\u0000\u0000"
          + "\u00ee\u00ef\u0001\u0000\u0000\u0000\u00ef\u00f0\u0005\u0018\u0000\u0000"
          + "\u00f0\u00f1\u00050\u0000\u0000\u00f1\u00f2\u0005\u0003\u0000\u0000\u00f2"
          + "\u00f3\u0005\u0004\u0000\u0000\u00f3\u00f4\u00036\u001b\u0000\u00f4\u000f"
          + "\u0001\u0000\u0000\u0000\u00f5\u00f7\u0003\u0012\t\u0000\u00f6\u00f5\u0001"
          + "\u0000\u0000\u0000\u00f7\u00fa\u0001\u0000\u0000\u0000\u00f8\u00f6\u0001"
          + "\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fb\u0001"
          + "\u0000\u0000\u0000\u00fa\u00f8\u0001\u0000\u0000\u0000\u00fb\u00fc\u0003"
          + ",\u0016\u0000\u00fc\u00fd\u0003:\u001d\u0000\u00fd\u00fe\u0003\u0004\u0002"
          + "\u0000\u00fe\u0011\u0001\u0000\u0000\u0000\u00ff\u0105\u00050\u0000\u0000"
          + "\u0100\u0102\u0005\u0003\u0000\u0000\u0101\u0103\u00030\u0018\u0000\u0102"
          + "\u0101\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000\u0000\u0103"
          + "\u0104\u0001\u0000\u0000\u0000\u0104\u0106\u0005\u0004\u0000\u0000\u0105"
          + "\u0100\u0001\u0000\u0000\u0000\u0105\u0106\u0001\u0000\u0000\u0000\u0106"
          + "\u0107\u0001\u0000\u0000\u0000\u0107\u0108\u0005\u0013\u0000\u0000\u0108"
          + "\u0013\u0001\u0000\u0000\u0000\u0109\u010a\u00038\u001c\u0000\u010a\u010d"
          + "\u0003,\u0016\u0000\u010b\u010c\u0005)\u0000\u0000\u010c\u010e\u0003\u0004"
          + "\u0002\u0000\u010d\u010b\u0001\u0000\u0000\u0000\u010d\u010e\u0001\u0000"
          + "\u0000\u0000\u010e\u0015\u0001\u0000\u0000\u0000\u010f\u0110\u00050\u0000"
          + "\u0000\u0110\u0111\u00050\u0000\u0000\u0111\u0113\u0005\u0003\u0000\u0000"
          + "\u0112\u0114\u00030\u0018\u0000\u0113\u0112\u0001\u0000\u0000\u0000\u0113"
          + "\u0114\u0001\u0000\u0000\u0000\u0114\u0115\u0001\u0000\u0000\u0000\u0115"
          + "\u0116\u0005\u0004\u0000\u0000\u0116\u0017\u0001\u0000\u0000\u0000\u0117"
          + "\u011a\u00038\u001c\u0000\u0118\u011a\u0005\u0019\u0000\u0000\u0119\u0117"
          + "\u0001\u0000\u0000\u0000\u0119\u0118\u0001\u0000\u0000\u0000\u011a\u011b"
          + "\u0001\u0000\u0000\u0000\u011b\u011c\u00050\u0000\u0000\u011c\u011e\u0005"
          + "\u0003\u0000\u0000\u011d\u011f\u00032\u0019\u0000\u011e\u011d\u0001\u0000"
          + "\u0000\u0000\u011e\u011f\u0001\u0000\u0000\u0000\u011f\u0120\u0001\u0000"
          + "\u0000\u0000\u0120\u0121\u0005\u0004\u0000\u0000\u0121\u0019\u0001\u0000"
          + "\u0000\u0000\u0122\u0123\u00050\u0000\u0000\u0123\u0125\u0005\u0003\u0000"
          + "\u0000\u0124\u0126\u00032\u0019\u0000\u0125\u0124\u0001\u0000\u0000\u0000"
          + "\u0125\u0126\u0001\u0000\u0000\u0000\u0126\u0127\u0001\u0000\u0000\u0000"
          + "\u0127\u0128\u0005\u0004\u0000\u0000\u0128\u0129\u00036\u001b\u0000\u0129"
          + "\u001b\u0001\u0000\u0000\u0000\u012a\u012c\u0005\u001a\u0000\u0000\u012b"
          + "\u012d\u0003\u0004\u0002\u0000\u012c\u012b\u0001\u0000\u0000\u0000\u012c"
          + "\u012d\u0001\u0000\u0000\u0000\u012d\u001d\u0001\u0000\u0000\u0000\u012e"
          + "\u012f\u0003\u0018\f\u0000\u012f\u0130\u00036\u001b\u0000\u0130\u001f"
          + "\u0001\u0000\u0000\u0000\u0131\u0132\u0003\u0018\f\u0000\u0132\u0133\u0005"
          + "\u0016\u0000\u0000\u0133\u0134\u00036\u001b\u0000\u0134!\u0001\u0000\u0000"
          + "\u0000\u0135\u0136\u0005\u001b\u0000\u0000\u0136\u0137\u0005\u0003\u0000"
          + "\u0000\u0137\u0138\u0003\u0004\u0002\u0000\u0138\u0139\u0005\u0004\u0000"
          + "\u0000\u0139\u0145\u0001\u0000\u0000\u0000\u013a\u013b\u0005\u001c\u0000"
          + "\u0000\u013b\u013c\u0005\u0003\u0000\u0000\u013c\u013d\u0003\u0004\u0002"
          + "\u0000\u013d\u013e\u0005\u0004\u0000\u0000\u013e\u0145\u0001\u0000\u0000"
          + "\u0000\u013f\u0140\u0005\u001d\u0000\u0000\u0140\u0141\u0005\u0003\u0000"
          + "\u0000\u0141\u0142\u0003\u0004\u0002\u0000\u0142\u0143\u0005\u0004\u0000"
          + "\u0000\u0143\u0145\u0001\u0000\u0000\u0000\u0144\u0135\u0001\u0000\u0000"
          + "\u0000\u0144\u013a\u0001\u0000\u0000\u0000\u0144\u013f\u0001\u0000\u0000"
          + "\u0000\u0145#\u0001\u0000\u0000\u0000\u0146\u0147\u0005\u001e\u0000\u0000"
          + "\u0147\u0148\u0005\u0003\u0000\u0000\u0148\u0149\u0003\u0004\u0002\u0000"
          + "\u0149\u014a\u0005\u0004\u0000\u0000\u014a\u014b\u00036\u001b\u0000\u014b"
          + "%\u0001\u0000\u0000\u0000\u014c\u014d\u0005\u001f\u0000\u0000\u014d\u014e"
          + "\u0005\u0003\u0000\u0000\u014e\u014f\u0003\u0004\u0002\u0000\u014f\u0150"
          + "\u0005\u0004\u0000\u0000\u0150\u0151\u00036\u001b\u0000\u0151\u0155\u0001"
          + "\u0000\u0000\u0000\u0152\u0154\u0003(\u0014\u0000\u0153\u0152\u0001\u0000"
          + "\u0000\u0000\u0154\u0157\u0001\u0000\u0000\u0000\u0155\u0153\u0001\u0000"
          + "\u0000\u0000\u0155\u0156\u0001\u0000\u0000\u0000\u0156\u0159\u0001\u0000"
          + "\u0000\u0000\u0157\u0155\u0001\u0000\u0000\u0000\u0158\u015a\u0003*\u0015"
          + "\u0000\u0159\u0158\u0001\u0000\u0000\u0000\u0159\u015a\u0001\u0000\u0000"
          + "\u0000\u015a\'\u0001\u0000\u0000\u0000\u015b\u015c\u0005 \u0000\u0000"
          + "\u015c\u015d\u0005\u001f\u0000\u0000\u015d\u015e\u0005\u0003\u0000\u0000"
          + "\u015e\u015f\u0003\u0004\u0002\u0000\u015f\u0160\u0005\u0004\u0000\u0000"
          + "\u0160\u0161\u00036\u001b\u0000\u0161)\u0001\u0000\u0000\u0000\u0162\u0163"
          + "\u0005 \u0000\u0000\u0163\u0164\u00036\u001b\u0000\u0164+\u0001\u0000"
          + "\u0000\u0000\u0165\u0167\u0005!\u0000\u0000\u0166\u0165\u0001\u0000\u0000"
          + "\u0000\u0166\u0167\u0001\u0000\u0000\u0000\u0167\u0168\u0001\u0000\u0000"
          + "\u0000\u0168\u0181\u00050\u0000\u0000\u0169\u016a\u0005\u0003\u0000\u0000"
          + "\u016a\u016b\u0005!\u0000\u0000\u016b\u016c\u00050\u0000\u0000\u016c\u0172"
          + "\u0005\u0004\u0000\u0000\u016d\u016f\u0005.\u0000\u0000\u016e\u0170\u0003"
          + "\u0004\u0002\u0000\u016f\u016e\u0001\u0000\u0000\u0000\u016f\u0170\u0001"
          + "\u0000\u0000\u0000\u0170\u0171\u0001\u0000\u0000\u0000\u0171\u0173\u0005"
          + "/\u0000\u0000\u0172\u016d\u0001\u0000\u0000\u0000\u0173\u0174\u0001\u0000"
          + "\u0000\u0000\u0174\u0172\u0001\u0000\u0000\u0000\u0174\u0175\u0001\u0000"
          + "\u0000\u0000\u0175\u0181\u0001\u0000\u0000\u0000\u0176\u017c\u00050\u0000"
          + "\u0000\u0177\u0179\u0005.\u0000\u0000\u0178\u017a\u0003\u0004\u0002\u0000"
          + "\u0179\u0178\u0001\u0000\u0000\u0000\u0179\u017a\u0001\u0000\u0000\u0000"
          + "\u017a\u017b\u0001\u0000\u0000\u0000\u017b\u017d\u0005/\u0000\u0000\u017c"
          + "\u0177\u0001\u0000\u0000\u0000\u017d\u017e\u0001\u0000\u0000\u0000\u017e"
          + "\u017c\u0001\u0000\u0000\u0000\u017e\u017f\u0001\u0000\u0000\u0000\u017f"
          + "\u0181\u0001\u0000\u0000\u0000\u0180\u0166\u0001\u0000\u0000\u0000\u0180"
          + "\u0169\u0001\u0000\u0000\u0000\u0180\u0176\u0001\u0000\u0000\u0000\u0181"
          + "-\u0001\u0000\u0000\u0000\u0182\u0183\u0005\"\u0000\u0000\u0183\u018b"
          + "\u00050\u0000\u0000\u0184\u0185\u0005#\u0000\u0000\u0185\u018b\u00050"
          + "\u0000\u0000\u0186\u0187\u00050\u0000\u0000\u0187\u018b\u0005\"\u0000"
          + "\u0000\u0188\u0189\u00050\u0000\u0000\u0189\u018b\u0005#\u0000\u0000\u018a"
          + "\u0182\u0001\u0000\u0000\u0000\u018a\u0184\u0001\u0000\u0000\u0000\u018a"
          + "\u0186\u0001\u0000\u0000\u0000\u018a\u0188\u0001\u0000\u0000\u0000\u018b"
          + "/\u0001\u0000\u0000\u0000\u018c\u0191\u0003\u0004\u0002\u0000\u018d\u018e"
          + "\u0005$\u0000\u0000\u018e\u0190\u0003\u0004\u0002\u0000\u018f\u018d\u0001"
          + "\u0000\u0000\u0000\u0190\u0193\u0001\u0000\u0000\u0000\u0191\u018f\u0001"
          + "\u0000\u0000\u0000\u0191\u0192\u0001\u0000\u0000\u0000\u01921\u0001\u0000"
          + "\u0000\u0000\u0193\u0191\u0001\u0000\u0000\u0000\u0194\u0199\u00034\u001a"
          + "\u0000\u0195\u0196\u0005$\u0000\u0000\u0196\u0198\u00034\u001a\u0000\u0197"
          + "\u0195\u0001\u0000\u0000\u0000\u0198\u019b\u0001\u0000\u0000\u0000\u0199"
          + "\u0197\u0001\u0000\u0000\u0000\u0199\u019a\u0001\u0000\u0000\u0000\u019a"
          + "3\u0001\u0000\u0000\u0000\u019b\u0199\u0001\u0000\u0000\u0000\u019c\u019d"
          + "\u00038\u001c\u0000\u019d\u019e\u0003,\u0016\u0000\u019e5\u0001\u0000"
          + "\u0000\u0000\u019f\u01a3\u0005\u0011\u0000\u0000\u01a0\u01a2\u0003\u0002"
          + "\u0001\u0000\u01a1\u01a0\u0001\u0000\u0000\u0000\u01a2\u01a5\u0001\u0000"
          + "\u0000\u0000\u01a3\u01a1\u0001\u0000\u0000\u0000\u01a3\u01a4\u0001\u0000"
          + "\u0000\u0000\u01a4\u01a6\u0001\u0000\u0000\u0000\u01a5\u01a3\u0001\u0000"
          + "\u0000\u0000\u01a6\u01a7\u0005\u0012\u0000\u0000\u01a77\u0001\u0000\u0000"
          + "\u0000\u01a8\u01a9\u0007\u0000\u0000\u0000\u01a99\u0001\u0000\u0000\u0000"
          + "\u01aa\u01ab\u0007\u0001\u0000\u0000\u01ab;\u0001\u0000\u0000\u0000\'"
          + "?_f{\u0081\u0087\u008b\u00b8\u00bc\u00be\u00c5\u00cb\u00db\u00df\u00e5"
          + "\u00ea\u00ed\u00f8\u0102\u0105\u010d\u0113\u0119\u011e\u0125\u012c\u0144"
          + "\u0155\u0159\u0166\u016f\u0174\u0179\u017e\u0180\u018a\u0191\u0199\u01a3";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}
