import AST.*;
import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends CppBaseVisitor<ASTNode> {
  @Override
  public ASTNode visitProgram(CppParser.ProgramContext ctx) {
    ProgramNode programNode = new ProgramNode();
    int i = 1;
    for (var child : ctx.stmt()) {
      System.out.println(i++ + ": " + child.getText());
      programNode.addChild(visit(child));
    }
    return programNode;
  }

  @Override
  public ASTNode visitStmt(CppParser.StmtContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public ASTNode visitBlockStmt(CppParser.BlockStmtContext ctx) {
    return visit(ctx.getChild(0));
  }

  @Override
  public ASTNode visitADD(CppParser.ADDContext ctx) {
    return new ADDNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitCHAR(CppParser.CHARContext ctx) {
    if (ctx.CHAR().getText().contains("\\n")) {
      return new CHARNode('\n');
    }
    return new CHARNode(ctx.CHAR().getText().charAt(1));
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
    List<ExprNode> indices = new ArrayList<>();
    for (var expr : ctx.expr()) {
      indices.add((ExprNode) visit(expr));
    }
    return new ARRACCNode(new IDNode(ctx.ID().getText()), indices);
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
    IDNode idNode = new IDNode(ctx.ID().getText());
    List<ObjcallNode> objcalls = new ArrayList<>();
    for (var objcall : ctx.objcall()) {
      objcalls.add((ObjcallNode) visit(objcall));
    }
    if (!ctx.LEFTBRACKET().isEmpty()) {
      List<ExprNode> exprs = new ArrayList<>();
      for (var expr : ctx.expr()) {
        exprs.add((ExprNode) visit(expr));
      }
      return new OBJMEMNode(objcalls, null, ctx.THIS() != null, new ARRACCNode(idNode, exprs));
    }
    return new OBJMEMNode(objcalls, idNode, ctx.THIS() != null, null);
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
    return new ANDNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitLESS(CppParser.LESSContext ctx) {
    return new LESSNode((ExprNode) visit(ctx.e1), (ExprNode) visit(ctx.e2));
  }

  @Override
  public ASTNode visitFNCALLWRAP(CppParser.FNCALLWRAPContext ctx) {
    List<ObjcallNode> objcalls = new ArrayList<ObjcallNode>();
    for (var objcall : ctx.objcall()) {
      objcalls.add((ObjcallNode) visit(objcall));
    }
    FncallNode fncallNode = (FncallNode) visit(ctx.fncall());
    fncallNode.setObjcalls(objcalls);
    fncallNode.setHasThis(ctx.THIS() != null);
    return fncallNode;
  }

  @Override
  public ASTNode visitFncall(CppParser.FncallContext ctx) {
    return new FncallNode(
        new ArrayList<>(),
        new IDNode(ctx.ID().getText()),
        (ctx.args() != null ? (ArgsNode) visit(ctx.args()) : null),
        false);
  }

  @Override
  public ASTNode visitARRVALS(CppParser.ARRVALSContext ctx) {
    ArgsNode argsNode = (ArgsNode) visit(ctx.args());
    argsNode.setArrVals(true);
    return argsNode;
  }

  @Override
  public ASTNode visitClassDef(CppParser.ClassDefContext ctx) {
    List<ASTNode> classmembers = new ArrayList<>();
    for (var classmember : ctx.classMember()) {
      classmembers.add(visit(classmember));
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
  public ASTNode visitOperator(CppParser.OperatorContext ctx) {
    return new OperatorNode(
        new IDNode(ctx.returnType.getText()),
        new IDNode(ctx.paramType.getText()),
        new IDNode(ctx.paramName.getText()),
        (BlockNode) visit(ctx.block()));
  }

  @Override
  public ASTNode visitOverrideFndecl(CppParser.OverrideFndeclContext ctx) {
    FndeclNode fndeclNode = (FndeclNode) visit(ctx.fndecl());
    fndeclNode.setOverride(ctx.OVERRIDE() != null);
    return fndeclNode;
  }

  @Override
  public ASTNode visitVirtual(CppParser.VirtualContext ctx) {
    if (ctx.overrideFndef() != null) {
      FndefNode fndefNode = (FndefNode) visit(ctx.overrideFndef());
      fndefNode.getFndecl().setVirtual(true);
      return fndefNode;
    }
    FndeclNode fndeclNode = (FndeclNode) visit(ctx.overrideFndecl());
    fndeclNode.setVirtual(true);
    return fndeclNode;
  }

  @Override
  public ASTNode visitBinding(CppParser.BindingContext ctx) {
    List<ObjcallNode> objcalls = new ArrayList<>();
    for (var objcall : ctx.objcall()) {
      objcalls.add((ObjcallNode) visit(objcall));
    }
    IDNode idNode = new IDNode(ctx.ID().getText());
    ARRACCNode arraccNode = null;
    if (!ctx.LEFTBRACKET().isEmpty()) {
      List<ExprNode> exprs = new ArrayList<>();
      for (int i = 0; i < ctx.LEFTBRACKET().size(); i++) {
        exprs.add((ExprNode) visit(ctx.expr(i)));
      }
      arraccNode = new ARRACCNode(idNode, exprs);
      idNode = null;
    }
    return new BindingNode(
        objcalls,
        idNode,
        ctx.assignop().getChild(0).getText(),
        (ExprNode) visit(ctx.exprR),
        ctx.THIS() != null,
        arraccNode);
  }

  @Override
  public ASTNode visitObjcall(CppParser.ObjcallContext ctx) {
    List<ExprNode> exprNodes = new ArrayList<>();
    for (var expr : ctx.expr()) {
      exprNodes.add((ExprNode) visit(expr));
    }
    // Is Function call
    if (ctx.ID() == null) {
      return new ObjcallNode(null, (FncallNode) visit(ctx.fncall()), null);
    }
    IDNode idNode = new IDNode(ctx.ID().getText());
    // Is Array access
    if (!ctx.LEFTBRACKET().isEmpty()) {
      return new ObjcallNode(null, null, new ARRACCNode(idNode, exprNodes));
    }
    // Is Id
    return new ObjcallNode(idNode, null, null);
  }

  @Override
  public ASTNode visitVardecl(CppParser.VardeclContext ctx) {
    if (ctx.expr() != null) {
      return new VardeclNode(
          (TypeNode) visit(ctx.type()),
          (IdentifierNode) visit(ctx.identifier()),
          (ExprNode) visit(ctx.expr()));
    }
    return new VardeclNode(
        (TypeNode) visit(ctx.type()), (IdentifierNode) visit(ctx.identifier()), null);
  }

  @Override
  public ASTNode visitConstructorCall(CppParser.ConstructorCallContext ctx) {
    if (ctx.args() != null) {
      return new ConstructorCallNode(
          new IDNode(ctx.cName.getText()),
          new IDNode(ctx.objName.getText()),
          (ArgsNode) visit(ctx.args()));
    }
    return new ConstructorCallNode(
        new IDNode(ctx.cName.getText()), new IDNode(ctx.objName.getText()), null);
  }

  @Override
  public ASTNode visitFndecl(CppParser.FndeclContext ctx) {
    List<ParamNode> params = new ArrayList<>();
    TypeNode typeNode = null;
    if (ctx.paramlist() != null) {
      for (var param : ctx.paramlist().param()) {
        params.add((ParamNode) visit(param));
      }
    }
    if (ctx.type() != null) {
      typeNode = (TypeNode) visit(ctx.type());
    } else {
      typeNode = new TypeNode(Type.VOID);
    }
    return new FndeclNode(typeNode, new IDNode(ctx.ID().getText()), params);
  }

  @Override
  public ASTNode visitConstructor(CppParser.ConstructorContext ctx) {
    if (ctx.paramlist() != null) {
      List<ParamNode> params = new ArrayList<>();
      for (var param : ctx.paramlist().param()) {
        params.add((ParamNode) visit(param));
      }
      return new ConstructorNode(
          new IDNode(ctx.ID().getText()), params, (BlockNode) visit(ctx.block()));
    }
    return new ConstructorNode(
        new IDNode(ctx.ID().getText()), new ArrayList<>(), (BlockNode) visit(ctx.block()));
  }

  @Override
  public ASTNode visitReturn(CppParser.ReturnContext ctx) {
    if (ctx.expr() != null) {
      return new ReturnNode((ExprNode) visit(ctx.expr()), false);
    }
    return new ReturnNode(null, ctx.THIS() != null);
  }

  @Override
  public ASTNode visitFndef(CppParser.FndefContext ctx) {
    return new FndefNode((FndeclNode) visit(ctx.fndecl()), (BlockNode) visit(ctx.block()));
  }

  @Override
  public ASTNode visitOverrideFndef(CppParser.OverrideFndefContext ctx) {
    return new FndefNode((FndeclNode) visit(ctx.overrideFndecl()), (BlockNode) visit(ctx.block()));
  }

  @Override
  public ASTNode visitPbool(CppParser.PboolContext ctx) {
    return new PrintNode((ExprNode) visit(ctx.expr()), Type.BOOL);
  }

  @Override
  public ASTNode visitPint(CppParser.PintContext ctx) {
    return new PrintNode((ExprNode) visit(ctx.expr()), Type.INT);
  }

  @Override
  public ASTNode visitPchar(CppParser.PcharContext ctx) {
    return new PrintNode((ExprNode) visit(ctx.expr()), Type.CHAR);
  }

  @Override
  public ASTNode visitWhile(CppParser.WhileContext ctx) {
    return new WhileNode((ExprNode) visit(ctx.expr()), (BlockNode) visit(ctx.block()));
  }

  @Override
  public ASTNode visitIf(CppParser.IfContext ctx) {
    List<ElseifNode> elseifs = new ArrayList<>();
    for (var elseif : ctx.elseif()) {
      elseifs.add((ElseifNode) visit(elseif));
    }
    if (ctx.else_() != null) {
      return new IfNode(
          (ExprNode) visit(ctx.expr()),
          (BlockNode) visit(ctx.block()),
          elseifs,
          (ElseNode) visit(ctx.else_()));
    }
    return new IfNode((ExprNode) visit(ctx.expr()), (BlockNode) visit(ctx.block()), elseifs, null);
  }

  @Override
  public ASTNode visitINCDECWRAP(CppParser.INCDECWRAPContext ctx) {
    return visit(ctx.incDec());
  }

  @Override
  public ASTNode visitElseif(CppParser.ElseifContext ctx) {
    return new ElseifNode((ExprNode) visit(ctx.expr()), (BlockNode) visit(ctx.block()));
  }

  @Override
  public ASTNode visitElse(CppParser.ElseContext ctx) {
    return new ElseNode((BlockNode) visit(ctx.block()));
  }

  @Override
  public ASTNode visitIdentifier(CppParser.IdentifierContext ctx) {
    // Contains "[" -> is an array
    if (!ctx.LEFTBRACKET().isEmpty()) {
      List<ExprNode> sizes = new ArrayList<>();
      for (var expr : ctx.expr()) {
        sizes.add((ExprNode) visit(expr));
      }

      return new IdentifierNode(
          new IDNode(ctx.ID().getText()),
          sizes,
          ctx.LEFTBRACKET().size(),
          ctx.getChild(1).getText().equals("&"));
    }
    return new IdentifierNode(
        new IDNode(ctx.ID().getText()), ctx.getChild(0).getText().equals("&"));
  }

  @Override
  public ASTNode visitPREINC(CppParser.PREINCContext ctx) {
    return new IncDecNode(new IDNode(ctx.ID().getText()), true, true);
  }

  @Override
  public ASTNode visitPREDEC(CppParser.PREDECContext ctx) {
    return new IncDecNode(new IDNode(ctx.ID().getText()), false, true);
  }

  @Override
  public ASTNode visitPOSTINC(CppParser.POSTINCContext ctx) {
    return new IncDecNode(new IDNode(ctx.ID().getText()), true, false);
  }

  @Override
  public ASTNode visitPOSTDEC(CppParser.POSTDECContext ctx) {
    return new IncDecNode(new IDNode(ctx.ID().getText()), false, false);
  }

  @Override
  public ASTNode visitArgs(CppParser.ArgsContext ctx) {
    List<ExprNode> arguments = new ArrayList<>();
    for (var expr : ctx.expr()) {
      arguments.add((ExprNode) visit(expr));
    }
    return new ArgsNode(arguments);
  }

  @Override
  public ASTNode visitParamlist(CppParser.ParamlistContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitParam(CppParser.ParamContext ctx) {
    return new ParamNode((TypeNode) visit(ctx.type()), (IdentifierNode) visit(ctx.identifier()));
  }

  @Override
  public ASTNode visitBlock(CppParser.BlockContext ctx) {
    BlockNode block = new BlockNode();
    for (var child : ctx.blockStmt()) {
      block.addChild(visit(child));
    }
    return block;
  }

  @Override
  public ASTNode visitType(CppParser.TypeContext ctx) {
    if (ctx.TYPEINT() != null) {
      return new TypeNode(Type.INT);
    }
    if (ctx.TYPECHAR() != null) {
      return new TypeNode(Type.CHAR);
    }
    if (ctx.TYPEBOOL() != null) {
      return new TypeNode(Type.BOOL);
    }
    return new TypeNode(ctx.ID().getText());
  }

  @Override
  public ASTNode visitAssignop(CppParser.AssignopContext ctx) {
    // This method has no content and purpose
    return super.visitAssignop(ctx);
  }
}
