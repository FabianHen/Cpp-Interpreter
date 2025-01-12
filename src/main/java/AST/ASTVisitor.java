package AST;

public interface ASTVisitor<T> {
  T visit(ProgramNode programNode);

  T visit(ClassDefNode classDefNode);

  T visit(DestructorNode destructorNode);

  T visit(BindingNode bindingNode);

  T visit(ObjcallNode objcallNode);

  T visit(VardeclNode vardeclNode);

  T visit(ConstructorCallNode constructorCallNode);

  T visit(FndeclNode fndeclNode);

  T visit(ReturnNode returnNode);

  T visit(FndefNode fndefNode);

  T visit(OverrideFndefNode overrideFndefNode);

  T visit(PrintNode printNode);

  T visit(WhileNode whileNode);

  T visit(IfNode ifNode);

  T visit(ElseifNode elseifNode);

  T visit(ElseNode elseNode);

  T visit(IdentifierNode identifierNode);

  T visit(IncDecNode incDecNode);

  T visit(ArgsNode argsNode);

  T visit(ParamNode paramNode);

  T visit(BlockNode blockNode);

  T visit(TypeNode typeNode);

  T visit(FNCALLNode fncallNode);

  T visit(OBJMEMNode objmemNode);

  T visit(NEWNode newNode);

  T visit(MULNode mulNode);

  T visit(DIVNode divNode);

  T visit(ADDNode addNode);

  T visit(SUBNode subNode);

  T visit(EQUALSNode equalsNode);

  T visit(NEAQUALSNode neequalsNode);

  T visit(GREATERNode greaterNode);

  T visit(LESSNode lessNode);

  T visit(GEAQUALSNode geaqualsNode);

  T visit(LEAQUALSNode leaqualsNode);

  T visit(ANDNode andNode);

  T visit(ORNode orNode);

  T visit(ARRACCNode arraccNode);

  T visit(OBJNode objNode);

  T visit(NOTNode notNode);

  T visit(BOOLNode boolNode);

  T visit(CHARNode charNode);

  T visit(INTNode intNode);

  T visit(IDNode idNode);

  T visit(OverrideFndeclNode overrideFndeclNode);

  T visit(VirtualNode virtualNode);

  T visit(ConstructorNode constructorNode);
}
