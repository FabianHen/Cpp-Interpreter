package Interpreter;

import AST.*;
import SymbolTable.STClass;
import java.util.ArrayList;
import java.util.List;

public class Interpreter implements ASTVisitor<Object> {
  private Environment currentEnvironment;

  public Environment getEnvironment() {
    return currentEnvironment;
  }

  public void setEnvironment(Environment env) {
    currentEnvironment = env;
  }

  @Override
  public Object visit(ProgramNode programNode) {
    this.currentEnvironment = new Environment(null);
    for (var child : programNode.getChildren()) {
      child.accept(this);
    }
    List<Func> mains = this.currentEnvironment.getFunctions("main");
    for (var main : mains) {
      if (main.getParameters().isEmpty()
          && main.getFndefNode().getFndecl().getReturntype().getType() == Type.INT) {
        main.call(this, new ArrayList<>());
        System.out.println("\n |=-=-=-=-=-=-Successfully-executed-main-function!-=-=-=-=-=-=|");
        System.exit(0);
      }
    }
    return null;
  }

  @Override
  public Object visit(ClassDefNode classDefNode) {
    Clazz parentClazz = null;
    if (classDefNode.getParentclass() != null) {
      parentClazz = (Clazz) currentEnvironment.getVariable(classDefNode.getParentclass().getId());
    }
    Clazz clazz = new Clazz(parentClazz, classDefNode);
    for (ASTNode member : classDefNode.getClassmembers()) {
      if (member instanceof FndefNode fndefNode) {
        Object value = fndefNode.accept(this);
        clazz.putFunc((Func) value);
      } else if (member instanceof ConstructorNode constructorNode) {
        Object value = constructorNode.accept(this);
        clazz.putFunc((Func) value);
      } else if (member instanceof OperatorNode operatorNode) {
        Object value = operatorNode.accept(this);
        clazz.putFunc((Func) value);
      }
    }
    currentEnvironment.defineVariable(classDefNode.getIdNode().getId(), clazz);
    return null;
  }

  @Override
  public Object visit(BindingNode bindingNode) {
    Environment currentObjEnv = this.currentEnvironment;
    String id =
        (bindingNode.getIdNode() == null
            ? bindingNode.getArraccNode().getIdNode().getId()
            : bindingNode.getIdNode().getId());
    Value value = (Value) bindingNode.getExpr().accept(this);
    validateObjectCalls(bindingNode.getObjcalls(), bindingNode.hasThis());
    if (bindingNode.getAssignop().equals("=")) {
      Value variable = (Value) this.currentEnvironment.getVariable(id);
      if (bindingNode.getArraccNode() != null) {
        setArrayValue(
            variable.getValue(), value.getValue(), bindingNode.getArraccNode().getExprNodes(), 0);
        this.currentEnvironment = currentObjEnv;
        return null;
      }
      if (variable.getValue() instanceof Instance instance) {
        Environment env = this.currentEnvironment;
        this.currentEnvironment = instance;
        List<Func> funcs = instance.getFunctions("operator=");
        if (funcs.isEmpty()) {
          instance.copyInstance(this, (Instance) value.getValue());
        } else {
          List<ExprNode> args = new ArrayList<>();
          args.add(bindingNode.getExpr());
          funcs.getFirst().call(this, args);
        }
        this.currentEnvironment = currentObjEnv;
        return null;
      }
      this.currentEnvironment.assignVariable(id, value.getValue());
      this.currentEnvironment = currentObjEnv;
      return null;
    }
    int varValue = (int) ((Value) this.currentEnvironment.getVariable(id)).getValue();
    switch (bindingNode.getAssignop()) {
      case "+=":
        this.currentEnvironment.assignVariable(id, varValue + (int) value.getValue());
        break;
      case "-=":
        this.currentEnvironment.assignVariable(id, varValue - (int) value.getValue());
        break;
      case "*=":
        this.currentEnvironment.assignVariable(id, varValue * (int) value.getValue());
        break;
      case "/=":
        if ((int) value.getValue() == 0) {
          System.err.println("RUNTIME ERROR: Cannot divide by zero");
          System.exit(0);
        }
        this.currentEnvironment.assignVariable(id, varValue * (int) value.getValue());
        break;
    }
    this.currentEnvironment = currentObjEnv;
    return null;
  }

  @Override
  public Object visit(ObjcallNode objcallNode) {
    if (objcallNode.getIdNode() != null) {
      return currentEnvironment.getVariable(objcallNode.getIdNode().getId());
    }
    if (objcallNode.getFncallNode() != null) {
      return objcallNode.getFncallNode().accept(this);
    }
    if (objcallNode.getArracNode() != null) {
      return objcallNode.getArracNode().accept(this);
    }
    return null;
  }

  @Override
  public Object visit(VardeclNode vardeclNode) {
    String id = vardeclNode.getIdentifier().getIdNode().getId();
    Object array = null;
    Value value = null;
    if (vardeclNode.getExpr() == null) {
      switch (vardeclNode.getType().getType()) {
          // Define base values if no expression is given
        case INT:
          value = new Value(0);
          break;
        case BOOL:
          value = new Value(false);
          break;
        case CHAR:
          value = new Value(' ');
          break;
        case CUSTOM:
          Clazz clazz =
              (Clazz) this.currentEnvironment.getVariable(vardeclNode.getType().getClassName());
          value = new Value(clazz.call(this, new ArrayList<>()));
          break;
      }
    } else {
      if (vardeclNode.getIdentifier().isReference()) {
        value = (Value) vardeclNode.getExpr().accept(this);
      } else {
        value = new Value(((Value) vardeclNode.getExpr().accept(this)).getValue());
      }
    }
    if (vardeclNode.isArray()) {
      array = createArray(vardeclNode.getIdentifier().getSizes(), 0);
      if (vardeclNode.getExpr() != null) {
        if (!compareArrays(array, value.getValue())) {
          System.err.println("RUNTIME ERROR: Too many initializer values!");
          System.exit(0);
        }
      } else {
        value = new Value(array);
      }
    } else if (vardeclNode.getType().getType() == Type.CUSTOM) {
      if (vardeclNode.getExpr() != null && !vardeclNode.getIdentifier().isReference()) {
        // Copy constructor
        Clazz clazz =
            (Clazz) this.currentEnvironment.getVariable(vardeclNode.getType().getClassName());
        Instance instance = new Instance(this, clazz, this.currentEnvironment);
        List<ExprNode> args = new ArrayList<>();
        args.add(vardeclNode.getExpr());
        instance.call(this, args);
        value = new Value(instance);
      }
    }
    this.currentEnvironment.defineVariable(vardeclNode.getIdentifier().getIdNode().getId(), value);
    return null;
  }

  @Override
  public Object visit(ConstructorCallNode constructorCallNode) {
    Clazz clazz =
        (Clazz) this.currentEnvironment.getVariable(constructorCallNode.getTypename().getId());
    Instance instance =
        (Instance)
            clazz.call(this, constructorCallNode.getArgsNode().getArguments(), constructorCallNode);
    currentEnvironment.defineVariable(
        constructorCallNode.getInstancename().getId(), new Value(instance));
    return null;
  }

  @Override
  public Object visit(FndeclNode fndeclNode) {
    FndefNode fndefNode = fndeclNode.getFndefNode();
    if (fndefNode == null) {
      System.err.println(
          "RUNTIME ERROR: Cannot find definition of declared function '"
              + fndeclNode.getIdNode().getId()
              + "'!");
      System.exit(0);
    }
    fndefNode.accept(this);
    return null;
  }

  @Override
  public Object visit(ReturnNode returnNode) {
    if (returnNode.getExpr() != null) {
      return returnNode.getExpr().accept(this);
    }
    if (returnNode.hasThis()) {
      // TODO find current object of class
      return new Value(findMyInstance(this.currentEnvironment));
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
    System.out.print(((Value) printNode.getExpr().accept(this)).getValue());
    return null;
  }

  @Override
  public Object visit(WhileNode whileNode) {
    this.currentEnvironment = new Environment(this.currentEnvironment);
    while ((boolean) ((Value) whileNode.getCondition().accept(this)).getValue()) {
      whileNode.getBlock().accept(this);
      currentEnvironment.clear();
    }
    this.currentEnvironment = this.currentEnvironment.getEnclosing();
    return null;
  }

  @Override
  public Object visit(IfNode ifNode) {
    if ((boolean) ((Value) ifNode.getCondition().accept(this)).getValue()) {
      ifNode.getBlock().accept(this);
      return null;
    }
    for (var elif : ifNode.getElseifblocks()) {
      if ((boolean) elif.accept(this)) {
        return null;
      }
    }
    if (ifNode.getElseblock() != null) {
      ifNode.getElseblock().accept(this);
    }
    return null;
  }

  @Override
  public Object visit(ElseifNode elseifNode) {
    if ((boolean) ((Value) elseifNode.getCondition().accept(this)).getValue()) {
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
    int value = (int) ((Value) incDecNode.getIdNode().accept(this)).getValue();
    if (incDecNode.isInc()) {
      currentEnvironment.assignVariable(incDecNode.getIdNode().getId(), value + 1);
    } else {
      currentEnvironment.assignVariable(incDecNode.getIdNode().getId(), value - 1);
    }
    if (incDecNode.isPre()) {
      return incDecNode.getIdNode().accept(this);
    }
    return new Value(value);
  }

  @Override
  public Object visit(ArgsNode argsNode) {
    if (!argsNode.isArrVals()) {
      return null;
    }
    Object array[] = new Object[argsNode.getArguments().size()];
    for (int i = 0; i < argsNode.getArguments().size(); i++) {
      array[i] = ((Value) argsNode.getArguments().get(i).accept(this)).getValue();
    }
    return new Value(array);
  }

  @Override
  public Object visit(ParamNode paramNode) {
    return null;
  }

  @Override
  public Object visit(BlockNode blockNode) {
    for (var child : blockNode.getChildren()) {
      if (child instanceof ReturnNode returnNode) {
        return ((Value) returnNode.accept(this)).getValue();
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
    for (Func func : foundFunctions) {
      if (func.getFndefNode().equals(fncallNode.getFndeclNode().getFndefNode())) {
        List<ExprNode> arguments =
            fncallNode.getArgsNode() == null
                ? new ArrayList<>()
                : fncallNode.getArgsNode().getArguments();
        if (fncallNode.getFndeclNode().getOverridingFndecls().isEmpty()) {
          Object value = func.call(this, arguments);
          this.currentEnvironment = currentObjEnvironment;
          return new Value(value);
        }
        Object value = null;
        Clazz currentClass = ((Instance) this.currentEnvironment).getClazz();
        String funcClassName = ((STClass) fncallNode.getFndeclNode().getCurrentScope()).getName();
        while (currentClass != null) {
          String className = currentClass.getClassDefNode().getIdNode().getId();
          if (className.equals(funcClassName)) {
            value = func.call(this, arguments);
            break;
          }
          for (var fndecl : fncallNode.getFndeclNode().getOverridingFndecls()) {
            String currFuncClassName = ((STClass) fndecl.getCurrentScope()).getName();
            if (className.equals(currFuncClassName)) {
              for (var func2 : foundFunctions) {
                if (func2.getFndefNode().equals(fndecl.getFndefNode())) {
                  value = func2.call(this, arguments);
                  this.currentEnvironment = currentObjEnvironment;
                  return new Value(value);
                }
              }
              break;
            }
          }
          currentClass = currentClass.getSuperClass();
        }
        this.currentEnvironment = currentObjEnvironment;
        return new Value(value);
      }
    }
    this.currentEnvironment = currentObjEnvironment;
    System.err.println("RUNTIME ERROR: Function was declared but never defined!");
    return null;
  }

  @Override
  public Object visit(OBJMEMNode objmemNode) {
    Environment env = this.currentEnvironment;
    validateObjectCalls(objmemNode.getObjcalls(), objmemNode.hasThis());
    Object value = this.currentEnvironment.getVariable(objmemNode.getIdNode().getId());
    this.currentEnvironment = env;
    return value;
  }

  @Override
  public Object visit(OperatorNode operatorNode) {
    Func func = new Func("operator=", this.currentEnvironment);
    func.setOperatorNode(operatorNode);
    this.currentEnvironment.defineFunction("operator=", func);
    return func;
  }

  @Override
  public Object visit(MULNode mulNode) {
    int e1 = (int) ((Value) mulNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) mulNode.getE2().accept(this)).getValue();
    return new Value(e1 * e2);
  }

  @Override
  public Object visit(DIVNode divNode) {
    int e1 = (int) ((Value) divNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) divNode.getE2().accept(this)).getValue();
    if (e2 == 0) {
      System.err.println("RUNTIME ERROR: Cannot divide by zero");
      System.exit(0);
    }
    return new Value(e1 / e2);
  }

  @Override
  public Object visit(ADDNode addNode) {
    int e1 = (int) ((Value) addNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) addNode.getE2().accept(this)).getValue();
    return new Value(e1 + e2);
  }

  @Override
  public Object visit(SUBNode subNode) {
    int e1 = (int) ((Value) subNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) subNode.getE2().accept(this)).getValue();
    return new Value(e1 - e2);
  }

  @Override
  public Object visit(EQUALSNode equalsNode) {
    Object obj1 = ((Value) equalsNode.getE1().accept(this)).getValue();
    Object obj2 = ((Value) equalsNode.getE2().accept(this)).getValue();
    return new Value(obj1 == obj2);
  }

  @Override
  public Object visit(NEAQUALSNode neaqualsNode) {
    Object obj1 = ((Value) neaqualsNode.getE1().accept(this)).getValue();
    Object obj2 = ((Value) neaqualsNode.getE2().accept(this)).getValue();
    return new Value(obj1 != obj2);
  }

  @Override
  public Object visit(GREATERNode greaterNode) {
    int e1 = (int) ((Value) greaterNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) greaterNode.getE2().accept(this)).getValue();
    return new Value(e1 > e2);
  }

  @Override
  public Object visit(LESSNode lessNode) {
    int e1 = (int) ((Value) lessNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) lessNode.getE2().accept(this)).getValue();
    return new Value(e1 < e2);
  }

  @Override
  public Object visit(GEAQUALSNode geaqualsNode) {
    int e1 = (int) ((Value) geaqualsNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) geaqualsNode.getE2().accept(this)).getValue();
    return new Value(e1 >= e2);
  }

  @Override
  public Object visit(LEAQUALSNode leaqualsNode) {
    int e1 = (int) ((Value) leaqualsNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) leaqualsNode.getE2().accept(this)).getValue();
    return new Value(e1 <= e2);
  }

  @Override
  public Object visit(ANDNode andNode) {
    boolean e1 = (boolean) ((Value) andNode.getE1().accept(this)).getValue();
    boolean e2 = (boolean) ((Value) andNode.getE2().accept(this)).getValue();
    return new Value(e1 && e2);
  }

  @Override
  public Object visit(ORNode orNode) {
    boolean e1 = (boolean) ((Value) orNode.getE1().accept(this)).getValue();
    boolean e2 = (boolean) ((Value) orNode.getE2().accept(this)).getValue();
    return new Value(e1 || e2);
  }

  @Override
  public Object visit(ARRACCNode arraccNode) {
    Value array = (Value) this.currentEnvironment.getVariable(arraccNode.getIdNode().getId());
    return new Value(getArrayValue(array.getValue(), arraccNode.getExprNodes(), 0));
  }

  @Override
  public Object visit(NOTNode notNode) {
    boolean condition = (boolean) ((Value) notNode.getExpr().accept(this)).getValue();
    return new Value(!condition);
  }

  @Override
  public Object visit(BOOLNode boolNode) {
    return new Value(boolNode.getValue());
  }

  @Override
  public Object visit(CHARNode charNode) {
    return new Value(charNode.getValue());
  }

  @Override
  public Object visit(INTNode intNode) {
    return new Value(intNode.getValue());
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
      Value obj = (Value) child.accept(this);
      if (obj.getValue() instanceof Instance instance) {
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

  private Object createArray(List<ExprNode> sizes, int dimensionIndex) {
    int size = (int) ((Value) sizes.get(dimensionIndex).accept(this)).getValue();
    if (dimensionIndex == sizes.size() - 1) {
      return new Object[size];
    } else {
      Object[] array = new Object[size];
      for (int i = 0; i < size; i++) {
        array[i] = createArray(sizes, dimensionIndex + 1);
      }
      return array;
    }
  }

  private Object getArrayValue(Object array, List<ExprNode> indices, int dimensionIndex) {
    int index = (int) ((Value) indices.get(dimensionIndex).accept(this)).getValue();
    Object arr[] = (Object[]) array;
    if (index >= arr.length) {
      System.err.println("RUNTIME ERROR: Index out of bounds!");
      System.exit(0);
    }

    if (dimensionIndex == indices.size() - 1) {
      return arr[index];
    } else {
      return getArrayValue(arr[index], indices, ++dimensionIndex);
    }
  }

  private void setArrayValue(
      Object array, Object value, List<ExprNode> indices, int dimensionIndex) {
    int index = (int) ((Value) indices.get(dimensionIndex).accept(this)).getValue();
    Object arr[] = (Object[]) array;
    if (index >= arr.length) {
      System.err.println("RUNTIME ERROR: Index out of bounds!");
      System.exit(0);
    }
    if (dimensionIndex == indices.size() - 1) {
      arr[index] = value;
    } else {
      setArrayValue(arr[index], value, indices, ++dimensionIndex);
    }
  }

  private boolean compareArrays(Object array1, Object array2) {
    if (array1 instanceof Object[] arr1) {
      Object arr2[] = (Object[]) array2;
      if (arr1.length != arr2.length) {
        return false;
      }
      for (int i = 0; i < arr1.length; i++) {
        if (!compareArrays(arr1[i], arr2[i])) {
          return false;
        }
      }
    }
    return true;
  }
}
