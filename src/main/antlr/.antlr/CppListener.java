// Generated from c://Users//Hannes//Documents//Duales
// Studium//HSBI//3.Semester//Compilerbau//Cpp-Compiler//src//main//antlr//Cpp.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/** This interface defines a complete listener for a parse tree produced by {@link CppParser}. */
public interface CppListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link CppParser#program}.
   *
   * @param ctx the parse tree
   */
  void enterProgram(CppParser.ProgramContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#program}.
   *
   * @param ctx the parse tree
   */
  void exitProgram(CppParser.ProgramContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterStmt(CppParser.StmtContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitStmt(CppParser.StmtContext ctx);

  /**
   * Enter a parse tree produced by the {@code NEW} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterNEW(CppParser.NEWContext ctx);

  /**
   * Exit a parse tree produced by the {@code NEW} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitNEW(CppParser.NEWContext ctx);

  /**
   * Enter a parse tree produced by the {@code ADD} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterADD(CppParser.ADDContext ctx);

  /**
   * Exit a parse tree produced by the {@code ADD} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitADD(CppParser.ADDContext ctx);

  /**
   * Enter a parse tree produced by the {@code CHAR} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterCHAR(CppParser.CHARContext ctx);

  /**
   * Exit a parse tree produced by the {@code CHAR} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitCHAR(CppParser.CHARContext ctx);

  /**
   * Enter a parse tree produced by the {@code NEAQUALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterNEAQUALS(CppParser.NEAQUALSContext ctx);

  /**
   * Exit a parse tree produced by the {@code NEAQUALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitNEAQUALS(CppParser.NEAQUALSContext ctx);

  /**
   * Enter a parse tree produced by the {@code INT} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterINT(CppParser.INTContext ctx);

  /**
   * Exit a parse tree produced by the {@code INT} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitINT(CppParser.INTContext ctx);

  /**
   * Enter a parse tree produced by the {@code LEAQUALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterLEAQUALS(CppParser.LEAQUALSContext ctx);

  /**
   * Exit a parse tree produced by the {@code LEAQUALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitLEAQUALS(CppParser.LEAQUALSContext ctx);

  /**
   * Enter a parse tree produced by the {@code ARRACC} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterARRACC(CppParser.ARRACCContext ctx);

  /**
   * Exit a parse tree produced by the {@code ARRACC} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitARRACC(CppParser.ARRACCContext ctx);

  /**
   * Enter a parse tree produced by the {@code INCDECWRAP} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterINCDECWRAP(CppParser.INCDECWRAPContext ctx);

  /**
   * Exit a parse tree produced by the {@code INCDECWRAP} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitINCDECWRAP(CppParser.INCDECWRAPContext ctx);

  /**
   * Enter a parse tree produced by the {@code OBJ} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterOBJ(CppParser.OBJContext ctx);

  /**
   * Exit a parse tree produced by the {@code OBJ} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitOBJ(CppParser.OBJContext ctx);

  /**
   * Enter a parse tree produced by the {@code ID} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterID(CppParser.IDContext ctx);

  /**
   * Exit a parse tree produced by the {@code ID} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitID(CppParser.IDContext ctx);

  /**
   * Enter a parse tree produced by the {@code COL} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterCOL(CppParser.COLContext ctx);

  /**
   * Exit a parse tree produced by the {@code COL} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitCOL(CppParser.COLContext ctx);

  /**
   * Enter a parse tree produced by the {@code SUB} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterSUB(CppParser.SUBContext ctx);

  /**
   * Exit a parse tree produced by the {@code SUB} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitSUB(CppParser.SUBContext ctx);

  /**
   * Enter a parse tree produced by the {@code OR} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterOR(CppParser.ORContext ctx);

  /**
   * Exit a parse tree produced by the {@code OR} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitOR(CppParser.ORContext ctx);

  /**
   * Enter a parse tree produced by the {@code EQUALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterEQUALS(CppParser.EQUALSContext ctx);

  /**
   * Exit a parse tree produced by the {@code EQUALS} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitEQUALS(CppParser.EQUALSContext ctx);

  /**
   * Enter a parse tree produced by the {@code MUL} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterMUL(CppParser.MULContext ctx);

  /**
   * Exit a parse tree produced by the {@code MUL} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitMUL(CppParser.MULContext ctx);

  /**
   * Enter a parse tree produced by the {@code GREATER} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterGREATER(CppParser.GREATERContext ctx);

  /**
   * Exit a parse tree produced by the {@code GREATER} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitGREATER(CppParser.GREATERContext ctx);

  /**
   * Enter a parse tree produced by the {@code GEAQUALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterGEAQUALS(CppParser.GEAQUALSContext ctx);

  /**
   * Exit a parse tree produced by the {@code GEAQUALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitGEAQUALS(CppParser.GEAQUALSContext ctx);

  /**
   * Enter a parse tree produced by the {@code OBJMEM} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterOBJMEM(CppParser.OBJMEMContext ctx);

  /**
   * Exit a parse tree produced by the {@code OBJMEM} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitOBJMEM(CppParser.OBJMEMContext ctx);

  /**
   * Enter a parse tree produced by the {@code DIV} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterDIV(CppParser.DIVContext ctx);

  /**
   * Exit a parse tree produced by the {@code DIV} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitDIV(CppParser.DIVContext ctx);

  /**
   * Enter a parse tree produced by the {@code NOT} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterNOT(CppParser.NOTContext ctx);

  /**
   * Exit a parse tree produced by the {@code NOT} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitNOT(CppParser.NOTContext ctx);

  /**
   * Enter a parse tree produced by the {@code BOOL} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterBOOL(CppParser.BOOLContext ctx);

  /**
   * Exit a parse tree produced by the {@code BOOL} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitBOOL(CppParser.BOOLContext ctx);

  /**
   * Enter a parse tree produced by the {@code AND} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterAND(CppParser.ANDContext ctx);

  /**
   * Exit a parse tree produced by the {@code AND} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitAND(CppParser.ANDContext ctx);

  /**
   * Enter a parse tree produced by the {@code LESS} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterLESS(CppParser.LESSContext ctx);

  /**
   * Exit a parse tree produced by the {@code LESS} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitLESS(CppParser.LESSContext ctx);

  /**
   * Enter a parse tree produced by the {@code FNCALL} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterFNCALL(CppParser.FNCALLContext ctx);

  /**
   * Exit a parse tree produced by the {@code FNCALL} labeled alternative in {@link CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitFNCALL(CppParser.FNCALLContext ctx);

  /**
   * Enter a parse tree produced by the {@code ARRVALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterARRVALS(CppParser.ARRVALSContext ctx);

  /**
   * Exit a parse tree produced by the {@code ARRVALS} labeled alternative in {@link
   * CppParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitARRVALS(CppParser.ARRVALSContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#classDef}.
   *
   * @param ctx the parse tree
   */
  void enterClassDef(CppParser.ClassDefContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#classDef}.
   *
   * @param ctx the parse tree
   */
  void exitClassDef(CppParser.ClassDefContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#classMember}.
   *
   * @param ctx the parse tree
   */
  void enterClassMember(CppParser.ClassMemberContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#classMember}.
   *
   * @param ctx the parse tree
   */
  void exitClassMember(CppParser.ClassMemberContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#overrideFndecl}.
   *
   * @param ctx the parse tree
   */
  void enterOverrideFndecl(CppParser.OverrideFndeclContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#overrideFndecl}.
   *
   * @param ctx the parse tree
   */
  void exitOverrideFndecl(CppParser.OverrideFndeclContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#virtual}.
   *
   * @param ctx the parse tree
   */
  void enterVirtual(CppParser.VirtualContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#virtual}.
   *
   * @param ctx the parse tree
   */
  void exitVirtual(CppParser.VirtualContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#destructor}.
   *
   * @param ctx the parse tree
   */
  void enterDestructor(CppParser.DestructorContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#destructor}.
   *
   * @param ctx the parse tree
   */
  void exitDestructor(CppParser.DestructorContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#binding}.
   *
   * @param ctx the parse tree
   */
  void enterBinding(CppParser.BindingContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#binding}.
   *
   * @param ctx the parse tree
   */
  void exitBinding(CppParser.BindingContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#objcall}.
   *
   * @param ctx the parse tree
   */
  void enterObjcall(CppParser.ObjcallContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#objcall}.
   *
   * @param ctx the parse tree
   */
  void exitObjcall(CppParser.ObjcallContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#vardecl}.
   *
   * @param ctx the parse tree
   */
  void enterVardecl(CppParser.VardeclContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#vardecl}.
   *
   * @param ctx the parse tree
   */
  void exitVardecl(CppParser.VardeclContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#constructorCall}.
   *
   * @param ctx the parse tree
   */
  void enterConstructorCall(CppParser.ConstructorCallContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#constructorCall}.
   *
   * @param ctx the parse tree
   */
  void exitConstructorCall(CppParser.ConstructorCallContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#fndecl}.
   *
   * @param ctx the parse tree
   */
  void enterFndecl(CppParser.FndeclContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#fndecl}.
   *
   * @param ctx the parse tree
   */
  void exitFndecl(CppParser.FndeclContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#constructor}.
   *
   * @param ctx the parse tree
   */
  void enterConstructor(CppParser.ConstructorContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#constructor}.
   *
   * @param ctx the parse tree
   */
  void exitConstructor(CppParser.ConstructorContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#return}.
   *
   * @param ctx the parse tree
   */
  void enterReturn(CppParser.ReturnContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#return}.
   *
   * @param ctx the parse tree
   */
  void exitReturn(CppParser.ReturnContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#fndef}.
   *
   * @param ctx the parse tree
   */
  void enterFndef(CppParser.FndefContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#fndef}.
   *
   * @param ctx the parse tree
   */
  void exitFndef(CppParser.FndefContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#overrideFndef}.
   *
   * @param ctx the parse tree
   */
  void enterOverrideFndef(CppParser.OverrideFndefContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#overrideFndef}.
   *
   * @param ctx the parse tree
   */
  void exitOverrideFndef(CppParser.OverrideFndefContext ctx);

  /**
   * Enter a parse tree produced by the {@code pbool} labeled alternative in {@link
   * CppParser#print}.
   *
   * @param ctx the parse tree
   */
  void enterPbool(CppParser.PboolContext ctx);

  /**
   * Exit a parse tree produced by the {@code pbool} labeled alternative in {@link CppParser#print}.
   *
   * @param ctx the parse tree
   */
  void exitPbool(CppParser.PboolContext ctx);

  /**
   * Enter a parse tree produced by the {@code pint} labeled alternative in {@link CppParser#print}.
   *
   * @param ctx the parse tree
   */
  void enterPint(CppParser.PintContext ctx);

  /**
   * Exit a parse tree produced by the {@code pint} labeled alternative in {@link CppParser#print}.
   *
   * @param ctx the parse tree
   */
  void exitPint(CppParser.PintContext ctx);

  /**
   * Enter a parse tree produced by the {@code pchar} labeled alternative in {@link
   * CppParser#print}.
   *
   * @param ctx the parse tree
   */
  void enterPchar(CppParser.PcharContext ctx);

  /**
   * Exit a parse tree produced by the {@code pchar} labeled alternative in {@link CppParser#print}.
   *
   * @param ctx the parse tree
   */
  void exitPchar(CppParser.PcharContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#while}.
   *
   * @param ctx the parse tree
   */
  void enterWhile(CppParser.WhileContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#while}.
   *
   * @param ctx the parse tree
   */
  void exitWhile(CppParser.WhileContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#if}.
   *
   * @param ctx the parse tree
   */
  void enterIf(CppParser.IfContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#if}.
   *
   * @param ctx the parse tree
   */
  void exitIf(CppParser.IfContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#elseif}.
   *
   * @param ctx the parse tree
   */
  void enterElseif(CppParser.ElseifContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#elseif}.
   *
   * @param ctx the parse tree
   */
  void exitElseif(CppParser.ElseifContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#else}.
   *
   * @param ctx the parse tree
   */
  void enterElse(CppParser.ElseContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#else}.
   *
   * @param ctx the parse tree
   */
  void exitElse(CppParser.ElseContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#identifier}.
   *
   * @param ctx the parse tree
   */
  void enterIdentifier(CppParser.IdentifierContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#identifier}.
   *
   * @param ctx the parse tree
   */
  void exitIdentifier(CppParser.IdentifierContext ctx);

  /**
   * Enter a parse tree produced by the {@code PREINC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void enterPREINC(CppParser.PREINCContext ctx);

  /**
   * Exit a parse tree produced by the {@code PREINC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void exitPREINC(CppParser.PREINCContext ctx);

  /**
   * Enter a parse tree produced by the {@code PREDEC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void enterPREDEC(CppParser.PREDECContext ctx);

  /**
   * Exit a parse tree produced by the {@code PREDEC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void exitPREDEC(CppParser.PREDECContext ctx);

  /**
   * Enter a parse tree produced by the {@code POSTINC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void enterPOSTINC(CppParser.POSTINCContext ctx);

  /**
   * Exit a parse tree produced by the {@code POSTINC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void exitPOSTINC(CppParser.POSTINCContext ctx);

  /**
   * Enter a parse tree produced by the {@code POSTDEC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void enterPOSTDEC(CppParser.POSTDECContext ctx);

  /**
   * Exit a parse tree produced by the {@code POSTDEC} labeled alternative in {@link
   * CppParser#incDec}.
   *
   * @param ctx the parse tree
   */
  void exitPOSTDEC(CppParser.POSTDECContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#args}.
   *
   * @param ctx the parse tree
   */
  void enterArgs(CppParser.ArgsContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#args}.
   *
   * @param ctx the parse tree
   */
  void exitArgs(CppParser.ArgsContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#paramlist}.
   *
   * @param ctx the parse tree
   */
  void enterParamlist(CppParser.ParamlistContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#paramlist}.
   *
   * @param ctx the parse tree
   */
  void exitParamlist(CppParser.ParamlistContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#param}.
   *
   * @param ctx the parse tree
   */
  void enterParam(CppParser.ParamContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#param}.
   *
   * @param ctx the parse tree
   */
  void exitParam(CppParser.ParamContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#block}.
   *
   * @param ctx the parse tree
   */
  void enterBlock(CppParser.BlockContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#block}.
   *
   * @param ctx the parse tree
   */
  void exitBlock(CppParser.BlockContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#type}.
   *
   * @param ctx the parse tree
   */
  void enterType(CppParser.TypeContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#type}.
   *
   * @param ctx the parse tree
   */
  void exitType(CppParser.TypeContext ctx);

  /**
   * Enter a parse tree produced by {@link CppParser#assignop}.
   *
   * @param ctx the parse tree
   */
  void enterAssignop(CppParser.AssignopContext ctx);

  /**
   * Exit a parse tree produced by {@link CppParser#assignop}.
   *
   * @param ctx the parse tree
   */
  void exitAssignop(CppParser.AssignopContext ctx);
}
