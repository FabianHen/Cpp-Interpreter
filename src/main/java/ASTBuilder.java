import AST.*;
import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends CppBaseVisitor<ASTNode> {
  @Override
  public ASTNode visitProgram(CppParser.ProgramContext ctx) {
    ProgramNode programNode = new ProgramNode();
    for (var child : ctx.children) {
      programNode.addChild(visit(child));
    }
    return programNode;
  }

  @Override
  public ASTNode visitStmt(CppParser.StmtContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public ASTNode visitNEW(CppParser.NEWContext ctx) {
    return new NEWNode((IDNode) visit(ctx.ID()), (ArgsNode) visit(ctx.args()));
  }

  @Override
  public ASTNode visitADD(CppParser.ADDContext ctx) {
    return new ADDNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitCHAR(CppParser.CHARContext ctx) {
    return new CHARNode(ctx.CHAR().getText().charAt(0));
  }

  @Override
  public ASTNode visitNEAQUALS(CppParser.NEAQUALSContext ctx) {
    return new NEAQUALSNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitINT(CppParser.INTContext ctx) {
    return new INTNode(Integer.parseInt(ctx.INT().getText()));
  }

  @Override
  public ASTNode visitLEAQUALS(CppParser.LEAQUALSContext ctx) {
    return new LEAQUALSNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitARRACC(CppParser.ARRACCContext ctx) {
    ExprNode expr = (ExprNode) visit(ctx.expr(0));
    List<ExprNode> indices = new ArrayList<ExprNode>();
    for (int i = 1; i < ctx.expr().size(); i++) {
      indices.add((ExprNode) visit(ctx.expr(i)));
    }
    return new ARRACCNode(expr, indices);
  }

  @Override
  public ASTNode visitOBJ(CppParser.OBJContext ctx) {
    return new OBJNode((ExprNode) visit(ctx.expr()));
  }

  @Override
  public ASTNode visitID(CppParser.IDContext ctx) {
    return new IDNode(ctx.ID().getText());
  }

  @Override
  public ASTNode visitCOL(CppParser.COLContext ctx) {
    return visit(ctx.expr());
  }

  @Override
  public ASTNode visitSUB(CppParser.SUBContext ctx) {
    return new SUBNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitOR(CppParser.ORContext ctx) {
    return new ORNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitEQUALS(CppParser.EQUALSContext ctx) {
    return new EQUALSNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitMUL(CppParser.MULContext ctx) {
    return new MULNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitGREATER(CppParser.GREATERContext ctx) {
    return new GREATERNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitGEAQUALS(CppParser.GEAQUALSContext ctx) {
    return new GEAQUALSNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitOBJMEM(CppParser.OBJMEMContext ctx) {
    IDNode idNode = (IDNode) visit(ctx.ID());
    List<ObjcallNode> objcalls = new ArrayList<ObjcallNode>();
    for (var objcall : ctx.objcall()) {
      objcalls.add((ObjcallNode) visit(objcall));
    }
    return new OBJMEMNode(objcalls, idNode);
  }

  @Override
  public ASTNode visitDIV(CppParser.DIVContext ctx) {
    return new DIVNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitNOT(CppParser.NOTContext ctx) {
    return new NOTNode((ExprNode) visit(ctx.expr()));
  }

  @Override
  public ASTNode visitBOOL(CppParser.BOOLContext ctx) {
    return new BOOLNode(Boolean.parseBoolean(ctx.BOOL().getText()));
  }

  @Override
  public ASTNode visitAND(CppParser.ANDContext ctx) {
    return new ADDNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitLESS(CppParser.LESSContext ctx) {
    return new LESSNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitFNCALL(CppParser.FNCALLContext ctx) {
    List<ObjcallNode> objcalls = new ArrayList<ObjcallNode>();
    for (var objcall : ctx.objcall()) {
      objcalls.add((ObjcallNode) visit(objcall));
    }
    if (ctx.args() != null) {
      return new FNCALLNode(objcalls, (IDNode) visit(ctx.ID()), (ArgsNode) visit(ctx.args()));
    }
    return new FNCALLNode(objcalls, (IDNode) visit(ctx.ID()), null);
  }

  @Override
  public ASTNode visitARRVALS(CppParser.ARRVALSContext ctx) {
    return visit(ctx.args());
  }

  @Override
  public ASTNode visitClassDef(CppParser.ClassDefContext ctx) {
    List<ASTNode> classmembers = new ArrayList<>();
    for (var classmember : ctx.classMember()) {
      classmembers.add((ASTNode) visit(classmember));
    }
    if (ctx.pName != null) {
      return new ClassDefNode(
          new IDNode(ctx.cName.getText()), new IDNode(ctx.pName.getText()), classmembers);
    }
    return new ClassDefNode(new IDNode(ctx.cName.getText()), null, classmembers);
  }

  @Override
  public ASTNode visitClassMember(CppParser.ClassMemberContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public ASTNode visitOverrideFndecl(CppParser.OverrideFndeclContext ctx) {
    return new OverrideFndeclNode((FndeclNode) visit(ctx.fndecl()), ctx.getChildCount() > 1);
  }

  @Override
  public ASTNode visitVirtual(CppParser.VirtualContext ctx) {
    if (ctx.getChildCount() == 2) {
      return new VirtualNode(null, -1, false, (FndefNode) visit(ctx.fndef()));
    }
    if (ctx.INT() != null) {
      return new VirtualNode(
          (FndeclNode) visit(ctx.fndecl()), Integer.parseInt(ctx.INT().getText()), true, null);
    }
    return new VirtualNode((FndeclNode) visit(ctx.fndecl()), -1, false, null);
  }

  @Override
  public ASTNode visitDestructor(CppParser.DestructorContext ctx) {
    return new DestructorNode(
        new IDNode(ctx.ID().getText()),
        (BlockNode) visit(ctx.block()),
        ctx.getChild(0).getText().equals("virtual"));
  }

  @Override
  public ASTNode visitBinding(CppParser.BindingContext ctx) {
    List<ObjcallNode> objcalls = new ArrayList<>();
    for (var objcall : ctx.objcall()) {
      objcalls.add((ObjcallNode) visit(objcall));
    }
    return new BindingNode(
        objcalls,
        (IdentifierNode) visit(ctx.identifier()),
        ctx.assignop().getChild(0).getText(),
        (ExprNode) visit(ctx.expr()));
  }

  @Override
  public ASTNode visitObjcall(CppParser.ObjcallContext ctx) {
    if (ctx.args() == null) {
      return new ObjcallNode((IDNode) visit(ctx.ID()), null);
    }
    return new ObjcallNode((IDNode) visit(ctx.ID()), (ArgsNode) visit(ctx.args()));
  }

  @Override
  public ASTNode visitVardecl(CppParser.VardeclContext ctx) {
    return super.visitVardecl(ctx);
  }

  @Override
  public ASTNode visitConstructorCall(CppParser.ConstructorCallContext ctx) {
    return super.visitConstructorCall(ctx);
  }

  @Override
  public ASTNode visitFndecl(CppParser.FndeclContext ctx) {
    return super.visitFndecl(ctx);
  }

  @Override
  public ASTNode visitConstructor(CppParser.ConstructorContext ctx) {
    return super.visitConstructor(ctx);
  }

  @Override
  public ASTNode visitReturn(CppParser.ReturnContext ctx) {
    return super.visitReturn(ctx);
  }

  @Override
  public ASTNode visitFndef(CppParser.FndefContext ctx) {
    return super.visitFndef(ctx);
  }

  @Override
  public ASTNode visitOverrideFndef(CppParser.OverrideFndefContext ctx) {
    return super.visitOverrideFndef(ctx);
  }

  @Override
  public ASTNode visitPbool(CppParser.PboolContext ctx) {
    return super.visitPbool(ctx);
  }

  @Override
  public ASTNode visitPint(CppParser.PintContext ctx) {
    return super.visitPint(ctx);
  }

  @Override
  public ASTNode visitPchar(CppParser.PcharContext ctx) {
    return super.visitPchar(ctx);
  }

  @Override
  public ASTNode visitWhile(CppParser.WhileContext ctx) {
    return super.visitWhile(ctx);
  }

  @Override
  public ASTNode visitIf(CppParser.IfContext ctx) {
    return super.visitIf(ctx);
  }

  @Override
  public ASTNode visitINCDECWRAP(CppParser.INCDECWRAPContext ctx) {
    return visit(ctx.incDec());
  }

  @Override
  public ASTNode visitElseif(CppParser.ElseifContext ctx) {
    return super.visitElseif(ctx);
  }

  @Override
  public ASTNode visitElse(CppParser.ElseContext ctx) {
    return super.visitElse(ctx);
  }

  @Override
  public ASTNode visitIdentifier(CppParser.IdentifierContext ctx) {
    return super.visitIdentifier(ctx);
  }

  @Override
  public ASTNode visitPREINC(CppParser.PREINCContext ctx) {
      return new IncDecNode((IDNode) visit(ctx.ID()),true,true);
  }

  @Override
  public ASTNode visitPREDEC(CppParser.PREDECContext ctx) {
      return new IncDecNode((IDNode) visit(ctx.ID()),false,true);
  }

  @Override
  public ASTNode visitPOSTINC(CppParser.POSTINCContext ctx) {
      return new IncDecNode((IDNode) visit(ctx.ID()),true,false);
  }

  @Override
  public ASTNode visitPOSTDEC(CppParser.POSTDECContext ctx) {
    return new IncDecNode((IDNode) visit(ctx.ID()),false,false);
  }

  @Override
  public ASTNode visitArgs(CppParser.ArgsContext ctx) {
    return super.visitArgs(ctx);
  }

  @Override
  public ASTNode visitParamlist(CppParser.ParamlistContext ctx) {
    return super.visitParamlist(ctx);
  }

  @Override
  public ASTNode visitParam(CppParser.ParamContext ctx) {
    return super.visitParam(ctx);
  }

  @Override
  public ASTNode visitBlock(CppParser.BlockContext ctx) {
    return super.visitBlock(ctx);
  }

  @Override
  public ASTNode visitType(CppParser.TypeContext ctx) {
    return super.visitType(ctx);
  }

  @Override
  public ASTNode visitAssignop(CppParser.AssignopContext ctx) {
    return super.visitAssignop(ctx);
  }
}
