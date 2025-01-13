import AST.*;
import SymbolTable.*;
import java.util.ArrayList;

public class STBuilder implements ASTVisitor<Symbol> {
  private Scope currentScope;
  private final BuiltIn builtInVoid = new BuiltIn("void");
  private final BuiltIn builtInInt = new BuiltIn("int");
  private final BuiltIn builtInChar = new BuiltIn("char");
  private final BuiltIn builtInBool = new BuiltIn("bool");

  @Override
  public Symbol visit(ProgramNode programNode) {
    this.currentScope = new Scope(null);
    this.currentScope.bind(this.builtInInt);
    this.currentScope.bind(this.builtInChar);
    this.currentScope.bind(this.builtInBool);
    this.currentScope.bind(this.builtInVoid);
    this.currentScope.bind(new Function("print_int", this.builtInVoid, null, new ArrayList<>()));
    this.currentScope.bind(new Function("print_char", this.builtInVoid, null, new ArrayList<>()));
    this.currentScope.bind(new Function("print_bool", this.builtInVoid, null, new ArrayList<>()));

    for (var child : programNode.getChildren()) {
      child.accept(this);
    }
    return null;
  }

  @Override
  public Symbol visit(ClassDefNode classDefNode) {
    STClass stClass = new STClass(this.currentScope);
    this.currentScope.bind(stClass);
    this.currentScope = stClass;
    for (var child : classDefNode.getChildren()) {
      child.accept(this);
    }
    this.currentScope = this.currentScope.getParent();
    return stClass;
  }

  @Override
  public Symbol visit(DestructorNode destructorNode) {
    return null;
  }

  @Override
  public Symbol visit(BindingNode bindingNode) {
    Scope currentObjScope = this.currentScope;
    for (var child : bindingNode.getObjcalls()) {
      Symbol obj = child.accept(this);
      if (obj == null) {
        System.err.println(child.getIdNode().getId() + " could not be found!");
        this.currentScope = currentObjScope;
        return null;
      }
      if (obj.getType() instanceof STClass type) {
        this.currentScope = type;
      } else {
        System.err.println(
            "Variable " + obj.getName() + " is from BuiltIn type and can't have any members!");
        this.currentScope = currentObjScope;
        return null;
      }
    }
    String id = bindingNode.getIdentifierNode().getIdNode().getId();
    Symbol symbol = this.currentScope.resolve(id);
    if (symbol == null) {
      System.err.println("Symbol " + id + " could not be found!");
      this.currentScope = currentObjScope;
      return null;
    }
    if (!(symbol instanceof Variable var)) {
      System.err.println(symbol.getName() + " is not a variable!");
      this.currentScope = currentObjScope;
      return null;
    }
    if (!bindingNode.getAssignop().equals("=")) {
      // Check symbol type (int)
      if (!var.getType().equals(this.builtInInt)) {
        System.err.println(
            "Assign operator " + bindingNode.getAssignop() + " can only be used with type 'int'!");
        this.currentScope = currentObjScope;
        return null;
      }
    }
    // Compare Variable Type and Expr Type
    Symbol exprSymbol = bindingNode.getExpr().accept(this);
    if (!var.getType().equals(exprSymbol.getType())) {
      System.err.println(
          "Types of variable " + var.getName() + " and " + exprSymbol.getName() + " do not match!");
    }
    this.currentScope = currentObjScope;
    return null;
  }

  @Override
  public Symbol visit(ObjcallNode objcallNode) {
    return this.currentScope.resolve(objcallNode.getIdNode().getId());
  }

  @Override
  public Symbol visit(VardeclNode vardeclNode) {
    String id = vardeclNode.getIdentifier().getIdNode().getId();
    Symbol symbol = currentScope.resolveMember(id);
    if (symbol != null) {
      System.err.println("Variable " + id + " has already been declared in this scope!");
      return null;
    }
    Symbol type = currentScope.resolve(vardeclNode.getType().getClassName());

    if (!(type instanceof STType)) {
      System.err.println(vardeclNode.getType().getClassName() + " is not a valid type!");
      return null;
    }
    Symbol exprType = vardeclNode.getExpr().accept(this);
    if (!type.equals(exprType)) {
      System.err.println(
          "Types of variable " + id + " and " + exprType.getName() + " do not match!");
      return null;
    }
    Variable var = new Variable(id, (STType) type);
    currentScope.bind(var);
    return var;
  }

  @Override
  public Symbol visit(ConstructorCallNode constructorCallNode) {
    return null;
  }

  @Override
  public Symbol visit(FndeclNode fndeclNode) {
    Symbol symbol = this.currentScope.resolveMember(fndeclNode.getIdNode().getId());
    if (symbol != null) {
      if (!(symbol instanceof Function foundFunction)) {
        System.err.println(
            fndeclNode.getIdNode().getId() + " conflicts with a previous declaration!");
        return null;
      }
      if (fndeclNode.getParams().size() == foundFunction.getParams().size()) {
        for (int i = 0; i < foundFunction.getParams().size(); i++) {
          ParamNode paramNodeNew = fndeclNode.getParamNode(i);
          ParamNode paramNodeOld = foundFunction.getParamNode(i);
          if (paramNodeOld.getTypeNode().getType() != paramNodeNew.getTypeNode().getType()) {
            break;
          }
        }
        System.err.println(
            "Function "
                + fndeclNode.getIdNode().getId()
                + " can't be overloaded with same parameters!");
      }
    }
    Symbol type = this.currentScope.resolve(fndeclNode.getReturntype().getClassName());
    if (!(type instanceof STType)) {
      System.err.println(
          "Invalid return type for function " + fndeclNode.getIdNode().getId() + "!");
      return null;
    }
    Function newFunction =
        new Function(
            fndeclNode.getIdNode().getId(),
            (STType) type,
            this.currentScope,
            fndeclNode.getParams());
    for (var child : fndeclNode.getParams()) {
      Symbol childVar = child.accept(this);
      if (childVar != null) {
        newFunction.bind(childVar);
      } else {
        return null;
      }
    }
    return newFunction;
  }

  @Override
  public Symbol visit(ReturnNode returnNode) {
    // TODO: Check if function has at least one return statement with correct return type
    if (returnNode.getExpr() == null) {
      return null;
    }
    // TODO:find Function that this returnNode is part of to find correct return type
    return null;
  }

  @Override
  public Symbol visit(FndefNode fndefNode) {
    Symbol functionSymbol = fndefNode.getFndecl().accept(this);
    if (functionSymbol instanceof Function function) {
      this.currentScope = function;
      fndefNode.getBlock().accept(this);
      this.currentScope = this.currentScope.getParent();
      return function;
    }
    // Errors in function declaration and block will be handled in corresponding visitor methods
    return null;
  }

  @Override
  public Symbol visit(OverrideFndefNode overrideFndefNode) {
    // TODO: Clear function scope and refill with new content?
    return null;
  }

  @Override
  public Symbol visit(PrintNode printNode) {
    Symbol expressionType = printNode.getExpr().accept(this);
    String printNodeType = printNode.getType().toString().toLowerCase();
    if (expressionType instanceof BuiltIn builtIn) {
      // comparing builtin name and type enum
      if (!builtIn.getName().equals(printNodeType)) {
        System.err.println(
            "Print function expected " + printNodeType + " but found " + builtIn.getName() + "!");
        return null;
      }
      return builtIn;
    }
    System.err.println(
        "Print function expected "
            + printNodeType
            + " but found "
            + expressionType.getName()
            + "!");
    return null;
  }

  @Override
  public Symbol visit(WhileNode whileNode) {
    Symbol conditionType = whileNode.getCondition().accept(this);
    if (!conditionType.equals(this.builtInBool)) {
      System.err.println(
          "Type of variable '" + conditionType.getName() + "' is not a valid condition type!");
      return null;
    }
    this.currentScope = new Scope(currentScope);
    whileNode.getBlock().accept(this);
    this.currentScope = this.currentScope.getParent();
    return conditionType;
  }

  @Override
  public Symbol visit(IfNode ifNode) {
    // TODO: Connect If/Elseif/Else blocks with scopes all
    return null;
  }

  @Override
  public Symbol visit(ElseifNode elseifNode) {
    return null;
  }

  @Override
  public Symbol visit(ElseNode elseNode) {
    return null;
  }

  @Override
  public Symbol visit(IdentifierNode identifierNode) {
    // Unnecessary Function, because IdentifierNode won't be visited but only used
    return null;
  }

  @Override
  public Symbol visit(IncDecNode incDecNode) {
    Symbol idNode = currentScope.resolve(incDecNode.getIdNode().getId());
    if (idNode == null) {
      System.err.println("Could not find Variable " + incDecNode.getIdNode().getId() + "!");
      return null;
    }
    if (!this.builtInInt.equals(idNode.getType())) {
      System.err.println(
          "Could not"
              + (incDecNode.isInc() ? " increase " : " decrease ")
              + "'"
              + incDecNode.getIdNode().getId()
              + "'!");
      return null;
    }
    return this.builtInInt;
  }

  @Override
  public Symbol visit(ArgsNode argsNode) {
    // TODO: What do we need to check here?
    return null;
  }

  @Override
  public Symbol visit(ParamNode paramNode) {
    Symbol type = this.currentScope.resolve(paramNode.getTypeNode().getClassName());
    if (!(type instanceof STType)) {
      System.err.println(
          "Invalid type of parameter " + paramNode.getIdentifier().getIdNode().getId() + "!");
      return null;
    }

    return new Variable(paramNode.getIdentifier().getIdNode().getId(), (STType) type);
  }

  @Override
  public Symbol visit(BlockNode blockNode) {
    for (var child : blockNode.getChildren()) {
      child.accept(this);
    }
    return null;
  }

  @Override
  public Symbol visit(TypeNode typeNode) {
    Symbol type = currentScope.resolve(typeNode.getClassName());
    // TODO: Does this make typechecking for not built in types in other functions unnecessary?
    if (type == null) {
      System.err.println("Invalid Type " + typeNode.getClassName() + "!");
    }
    return type;
  }

  @Override
  public Symbol visit(FNCALLNode fncallNode) {
    // TODO: check function existence (objcall iteration function), iterate over args and validate
    // them
    return null;
  }

  @Override
  public Symbol visit(OBJMEMNode objmemNode) {
    // TODO: (objcall iteration function)
    return null;
  }

  @Override
  public Symbol visit(NEWNode newNode) {
    // TODO: find Constructor and check params
    return null;
  }

  @Override
  public Symbol visit(MULNode mulNode) {
    Symbol e1Type = mulNode.getE1().accept(this);
    Symbol e2Type = mulNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  @Override
  public Symbol visit(DIVNode divNode) {
    Symbol e1Type = divNode.getE1().accept(this);
    Symbol e2Type = divNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  @Override
  public Symbol visit(ADDNode addNode) {
    Symbol e1Type = addNode.getE1().accept(this);
    Symbol e2Type = addNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  @Override
  public Symbol visit(SUBNode subNode) {
    Symbol e1Type = subNode.getE1().accept(this);
    Symbol e2Type = subNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  @Override
  public Symbol visit(EQUALSNode equalsNode) {
    Symbol e1Type = equalsNode.getE1().accept(this);
    Symbol e2Type = equalsNode.getE2().accept(this);
    return checkComparison(e1Type, e2Type);
  }

  @Override
  public Symbol visit(NEAQUALSNode neaqualsNode) {
    Symbol e1Type = neaqualsNode.getE1().accept(this);
    Symbol e2Type = neaqualsNode.getE2().accept(this);
    return checkComparison(e1Type, e2Type);
  }

  private Symbol checkComparison(Symbol type1, Symbol type2) {
    if (!(type1 instanceof BuiltIn) || !(type2 instanceof BuiltIn)) {
      System.err.println(
          "Cannot compare not builtin types " + type1.getName() + " and " + type2.getName() + "!");
      return null;
    }
    return this.builtInBool;
  }

  @Override
  public Symbol visit(GREATERNode greaterNode) {
    Symbol e1Type = greaterNode.getE1().accept(this);
    Symbol e2Type = greaterNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  @Override
  public Symbol visit(LESSNode lessNode) {
    Symbol e1Type = lessNode.getE1().accept(this);
    Symbol e2Type = lessNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  @Override
  public Symbol visit(GEAQUALSNode geaqualsNode) {
    Symbol e1Type = geaqualsNode.getE1().accept(this);
    Symbol e2Type = geaqualsNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  @Override
  public Symbol visit(LEAQUALSNode leaqualsNode) {
    Symbol e1Type = leaqualsNode.getE1().accept(this);
    Symbol e2Type = leaqualsNode.getE2().accept(this);
    return checkBothIntegers(e1Type, e2Type);
  }

  private Symbol checkBothIntegers(Symbol type1, Symbol type2) {
    if (!this.builtInInt.equals(type1) || !this.builtInInt.equals(type2)) {
      System.err.println(
          "Cannot use types " + type1.getName() + " and " + type2.getName() + " in this context!");
      return null;
    }
    return this.builtInBool;
  }

  @Override
  public Symbol visit(ANDNode andNode) {
    Symbol e1Type = andNode.getE1().accept(this);
    Symbol e2Type = andNode.getE2().accept(this);
    if (!this.builtInBool.equals(e1Type) || !this.builtInBool.equals(e2Type)) {
      System.err.println(
          "Cannot use types "
              + e1Type.getName()
              + " and "
              + e2Type.getName()
              + " with '&&' operator");
      return null;
    }
    return this.builtInBool;
  }

  @Override
  public Symbol visit(ORNode orNode) {
    Symbol e1Type = orNode.getE1().accept(this);
    Symbol e2Type = orNode.getE2().accept(this);
    if (!this.builtInBool.equals(e1Type) || !this.builtInBool.equals(e2Type)) {
      System.err.println(
          "Cannot use types "
              + e1Type.getName()
              + " and "
              + e2Type.getName()
              + " with '||' operator");
      return null;
    }
    return this.builtInBool;
  }

  @Override
  public Symbol visit(ARRACCNode arraccNode) {
    // TODO: Check if variable is array and has that many dimensions
    return null;
  }

  @Override
  public Symbol visit(OBJNode objNode) {
    Symbol objType = objNode.getExpr().accept(this);
    if (objType instanceof BuiltIn) {
      System.err.println("Cannot access members of builtin type " + objType.getName());
      return null;
    }
    return objType;
  }

  @Override
  public Symbol visit(NOTNode notNode) {
    Symbol expressionType = notNode.getExpr().accept(this);
    if (!this.builtInBool.equals(expressionType)) {
      System.err.println("Cannot negate variable of type " + expressionType.getName());
      return null;
    }
    return this.builtInBool;
  }

  @Override
  public Symbol visit(BOOLNode boolNode) {
    return this.builtInBool;
  }

  @Override
  public Symbol visit(CHARNode charNode) {
    return this.builtInChar;
  }

  @Override
  public Symbol visit(INTNode intNode) {
    return this.builtInInt;
  }

  @Override
  public Symbol visit(IDNode idNode) {
    return currentScope.resolve(idNode.getId());
  }

  @Override
  public Symbol visit(OverrideFndeclNode overrideFndeclNode) {
    return null;
  }

  @Override
  public Symbol visit(VirtualNode virtualNode) {
    return null;
  }

  @Override
  public Symbol visit(ConstructorNode constructorNode) {
    return null;
  }
}
