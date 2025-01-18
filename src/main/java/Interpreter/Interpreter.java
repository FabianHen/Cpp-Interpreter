package Interpreter;

import AST.*;
import SymbolTable.STClass;
import SymbolTable.Scope;
import SymbolTable.Symbol;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements ASTVisitor<Object> {
  private Environment currentEnvironment;
  public Environment getEnvironment(){
    return currentEnvironment;
  }
  public void setEnvironment(Environment env){
    currentEnvironment = env;
  }
  @Override
  public Object visit(ProgramNode programNode) {
    this.currentEnvironment = new Environment(null);
    for(var child : programNode.getChildren()) {
      child.accept(this);
    }
    return null;
  }

  @Override
  public Object visit(ClassDefNode classDefNode) {
    Clazz parentClazz = null;
    if(classDefNode.getParentclass() != null) {
      parentClazz = (Clazz) currentEnvironment.getVariable(classDefNode.getParentclass().getId());
    }
    Clazz clazz = new Clazz(parentClazz, classDefNode);
    for(ASTNode member : classDefNode.getClassmembers()){
      if(member instanceof FndefNode fndefNode){
        Object value = fndefNode.accept(this);
        clazz.putFunc((Func) value);
      }else if(member instanceof ConstructorNode constructorNode){
        Object value = constructorNode.accept(this);
        clazz.putFunc((Func) value);
      }
    }
    currentEnvironment.defineVariable(classDefNode.getIdNode().getId(), clazz);
    return null;
  }

  @Override
  public Object visit(BindingNode bindingNode) {
    //TODO Arrays and objects
    String id = bindingNode.getIdNode().getId();
    Object value = bindingNode.getExpr().accept(this);
    if(bindingNode.getAssignop().equals("=")){
      this.currentEnvironment.assignVariable(id, value);
      return null;
    }
    int varValue = (int) this.currentEnvironment.getVariable(id);
    switch (bindingNode.getAssignop()){
      case "+=":
        this.currentEnvironment.assignVariable(id, varValue + (int) value);
        break;
      case "-=":
        this.currentEnvironment.assignVariable(id, varValue - (int) value);
        break;
      case "*=":
        this.currentEnvironment.assignVariable(id, varValue * (int) value);
        break;
      case "/=":
        if((int) value == 0){
          System.err.println("RUNTIME ERROR: Cannot divide by zero");
          System.exit(0);
        }
        this.currentEnvironment.assignVariable(id, varValue * (int) value);
        break;
    }
    return null;
  }

  @Override
  public Object visit(ObjcallNode objcallNode) {
    if(objcallNode.getIdNode() != null) {
      return currentEnvironment.getVariable(objcallNode.getIdNode().getId());
    }
    if(objcallNode.getFncallNode() != null) {
      return objcallNode.getFncallNode().accept(this);
    }
    // TODO ARRACC
    return null;
  }

  @Override
  public Object visit(VardeclNode vardeclNode) {
    String id = vardeclNode.getIdentifier().getIdNode().getId();
    if(vardeclNode.getExpr() == null){
      switch(vardeclNode.getType().getType()){
        // Define base values if no expression is given
        case INT:
          this.currentEnvironment.defineVariable(id, 0);
          break;
        case BOOL:
          this.currentEnvironment.defineVariable(id, false);
          break;
        case CHAR:
          this.currentEnvironment.defineVariable(id, ' ');
          break;
        case CUSTOM:
          Clazz clazz = (Clazz) this.currentEnvironment.getVariable(vardeclNode.getType().getClassName());
          this.currentEnvironment.defineVariable(id, clazz.call(this,new ArrayList<>()));
          break;
      }
      return null;
    }
    this.currentEnvironment.defineVariable(vardeclNode.getIdentifier().getIdNode().getId(), vardeclNode.getExpr().accept(this));
    return null;
  }

  @Override
  public Object visit(ConstructorCallNode constructorCallNode) {
    Clazz clazz = (Clazz) this.currentEnvironment.getVariable(constructorCallNode.getTypename().getId());
    Instance instance = (Instance) clazz.call(this, constructorCallNode.getArgsNode().getArguments(), constructorCallNode);
    currentEnvironment.defineVariable(constructorCallNode.getInstancename().getId(), instance);
    return null;
  }

  @Override
  public Object visit(FndeclNode fndeclNode) {
    FndefNode fndefNode = fndeclNode.getFndefNode();
    if(fndefNode == null){
      System.err.println("RUNTIME ERROR: Cannot find definition of declared function '" + fndeclNode.getIdNode().getId() + "'!");
      System.exit(0);
    }
    fndefNode.accept(this);
    return null;
  }

  @Override
  public Object visit(ReturnNode returnNode) {
    if(returnNode.getExpr() != null){
      return returnNode.getExpr().accept(this);
    }
    if(returnNode.hasThis()){
      //TODO find current object of class
    }
    return null;
  }

  @Override
  public Object visit(FndefNode fndefNode) {
    Func func = new Func(fndefNode.getFndecl().getIdNode().getId(), this.currentEnvironment);
    func.setFndefNode(fndefNode);
    currentEnvironment.defineFunction(func.getFuncName(), func);
    return func;
  }

  @Override
  public Object visit(PrintNode printNode) {
    System.out.print(printNode.getExpr().accept(this));
    return null;
  }

  @Override
  public Object visit(WhileNode whileNode) {
    this.currentEnvironment = new Environment(this.currentEnvironment);
    while((boolean) whileNode.getCondition().accept(this)){
      whileNode.getBlock().accept(this);
      currentEnvironment.clear();
    }
    this.currentEnvironment = this.currentEnvironment.getEnclosing();
    return null;
  }

  @Override
  public Object visit(IfNode ifNode) {
    if((boolean)ifNode.getCondition().accept(this)){
      ifNode.getBlock().accept(this);
      return null;
    }
    for(var elif : ifNode.getElseifblocks()){
      if((boolean) elif.accept(this)){
        return null;
      };
    }
    if(ifNode.getElseblock() != null){
      ifNode.getElseblock().accept(this);
    }
    return null;
  }

  @Override
  public Object visit(ElseifNode elseifNode) {
    if((boolean)elseifNode.getCondition().accept(this)){
      elseifNode.getBlock().accept(this);
      return true;
    }
    return false;
  }

  @Override
  public Object visit(ElseNode elseNode) {
    elseNode.getBlock().accept(this);
    return null;
  }

  /**
   * This function does nothing. The reason for that is, that te identifier node is never visited in
   * our STBuilder. It is only accessed.
   *
   * @param identifierNode node that represents the 'identifier' parser rule
   * @return {@code null}
   */
  @Override
  public Object visit(IdentifierNode identifierNode) {
    // Unnecessary Function, because IdentifierNode won't be visited but only used
    return null;
  }

  @Override
  public Object visit(IncDecNode incDecNode) {
    int value = (int) incDecNode.getIdNode().accept(this);
    if(incDecNode.isInc()){
      currentEnvironment.assignVariable(incDecNode.getIdNode().getId(), value + 1);
    }else{
      currentEnvironment.assignVariable(incDecNode.getIdNode().getId(), value - 1);
    }
    if(incDecNode.isPre()){
      return incDecNode.getIdNode().accept(this);
    }
    return value;
  }

  @Override
  public Object visit(ArgsNode argsNode) {
    return null;
  }

  @Override
  public Object visit(ParamNode paramNode) {
    return null;
  }

  @Override
  public Object visit(BlockNode blockNode) {
    for(var child : blockNode.getChildren()){
      if(child instanceof ReturnNode returnNode){
        return returnNode.accept(this);
      }
      child.accept(this);
    }
    return null;
  }

  @Override
  public Object visit(TypeNode typeNode) {
    return null;
  }

  @Override
  public Object visit(FncallNode fncallNode) {
    Environment currentObjEnvironment = this.currentEnvironment;
    validateObjectCalls(fncallNode.getObjcalls(), fncallNode.hasThis());
    List<Func> foundFunctions = currentEnvironment.getFunctions(fncallNode.getIdNode().getId());
    this.currentEnvironment = currentObjEnvironment;
    for(Func func : foundFunctions){
      if(func.getFndefNode().equals(fncallNode.getFndeclNode().getFndefNode())){
        List<ExprNode> arguments = fncallNode.getArgsNode() == null ? new ArrayList<>() : fncallNode.getArgsNode().getArguments();
        return func.call(this,arguments);
      }
    }
    System.err.println("RUNTIME ERROR: Function was declared but never defined!");
    return null;
  }

  @Override
  public Object visit(OBJMEMNode objmemNode) {
    return null;
  }

  @Override
  public Object visit(OperatorNode operatorNode) {
    return null;
  }

  @Override
  public Object visit(MULNode mulNode) {
    int e1 = (int) mulNode.getE1().accept(this);
    int e2 = (int) mulNode.getE2().accept(this);
    return e1 * e2;
  }

  @Override
  public Object visit(DIVNode divNode) {
    int e1 = (int) divNode.getE1().accept(this);
    int e2 = (int) divNode.getE2().accept(this);
    if(e2 == 0){
      System.err.println("RUNTIME ERROR: Cannot divide by zero");
      System.exit(0);
    }
    return e1 / e2;
  }

  @Override
  public Object visit(ADDNode addNode) {
    int e1 = (int) addNode.getE1().accept(this);
    int e2 = (int) addNode.getE2().accept(this);
    return e1 + e2;
  }

  @Override
  public Object visit(SUBNode subNode) {
    int e1 = (int) subNode.getE1().accept(this);
    int e2 = (int) subNode.getE2().accept(this);
    return e1 - e2;
  }

  @Override
  public Object visit(EQUALSNode equalsNode) {
    return equalsNode.getE1().accept(this) == equalsNode.getE2().accept(this);
  }

  @Override
  public Object visit(NEAQUALSNode neaqualsNode) {
    return neaqualsNode.getE1().accept(this) != neaqualsNode.getE2().accept(this);
  }

  @Override
  public Object visit(GREATERNode greaterNode) {
    int e1 = (int) greaterNode.getE1().accept(this);
    int e2 = (int) greaterNode.getE2().accept(this);
    return e1 > e2;
  }

  @Override
  public Object visit(LESSNode lessNode) {
    int e1 = (int) lessNode.getE1().accept(this);
    int e2 = (int) lessNode.getE2().accept(this);
    return e1 < e2;
  }

  @Override
  public Object visit(GEAQUALSNode geaqualsNode) {
    int e1 = (int) geaqualsNode.getE1().accept(this);
    int e2 = (int) geaqualsNode.getE2().accept(this);
    return e1 >= e2;
  }

  @Override
  public Object visit(LEAQUALSNode leaqualsNode) {
    int e1 = (int) leaqualsNode.getE1().accept(this);
    int e2 = (int) leaqualsNode.getE2().accept(this);
    return e1 <= e2;
  }

  @Override
  public Object visit(ANDNode andNode) {
    boolean e1 = (boolean) andNode.getE1().accept(this);
    boolean e2 = (boolean) andNode.getE2().accept(this);
    return e1 && e2;
  }

  @Override
  public Object visit(ORNode orNode) {
    boolean e1 = (boolean) orNode.getE1().accept(this);
    boolean e2 = (boolean) orNode.getE2().accept(this);
    return e1 || e2;
  }

  @Override
  public Object visit(ARRACCNode arraccNode) {
    return null;
  }

  @Override
  public Object visit(NOTNode notNode) {
    boolean condition = (boolean) notNode.getExpr().accept(this);
    return !condition;
  }

  @Override
  public Object visit(BOOLNode boolNode) {
    return boolNode.getValue();
  }

  @Override
  public Object visit(CHARNode charNode) {
    return charNode.getValue();
  }

  @Override
  public Object visit(INTNode intNode) {
    return intNode.getValue();
  }

  @Override
  public Object visit(IDNode idNode) {
    return this.currentEnvironment.getVariable(idNode.getId());
  }

  @Override
  public Object visit(ConstructorNode constructorNode) {
    Func func = new Func(constructorNode.getIdNode().getId(), this.currentEnvironment);
    func.setConstructorNode(constructorNode);
    currentEnvironment.defineFunction(func.getFuncName(), func);
    return func;
  }

  /**
   * Iterates through the given list and checks if its members can be called
   *
   * @param objectCalls a list of object call nodes that will be verified
   * @param hasThis indicates if 'this.' is called at the start of object calls
   * @return if the given object calls are valid
   */
  private boolean validateObjectCalls(List<ObjcallNode> objectCalls, boolean hasThis) {
    Environment currentObjEnvironment = this.currentEnvironment;
    // checks if currently in class and changes scope to this class
    if (hasThis) {
      Environment foundInstance = findMyInstance(currentObjEnvironment);
      if (foundInstance == null) {
        System.err.println("Cannot use 'this' outside of class");
        return false;
      }
      this.currentEnvironment = foundInstance;
    }
    // Accepts each object call and checks if an object and not a builtin type is being accessed
    for (var child : objectCalls) {
      Object obj = child.accept(this);
      if (obj instanceof Instance instance) {
        this.currentEnvironment = instance;
        continue;
      }
      System.err.println("RUNTIME ERROR: Error while handling objcalls!");
      System.exit(0);
      this.currentEnvironment = currentObjEnvironment;
      return false;
    }
    return true;
  }

  private Environment findMyInstance(Environment currentEnvironment) {
    while (currentEnvironment != null) {
      // classes are scopes
      if (currentEnvironment instanceof Instance) {
        return currentEnvironment;
      }
      currentEnvironment = currentEnvironment.getEnclosing();
    }
    return null;
  }
}
