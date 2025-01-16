import AST.*;
import SymbolTable.*;
import java.util.ArrayList;
import java.util.List;

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
    this.currentScope.bind(new Function("print_int", this.builtInVoid, null, new ArrayList<>(),false));
    this.currentScope.bind(new Function("print_char", this.builtInVoid, null, new ArrayList<>(), false));
    this.currentScope.bind(new Function("print_bool", this.builtInVoid, null, new ArrayList<>(),false));

    for (var child : programNode.getChildren()) {
      child.accept(this);
    }
    return null;
  }

  @Override
  public Symbol visit(ClassDefNode classDefNode) {
    Symbol symbol = currentScope.resolve(classDefNode.getIdNode().getId());
    if (symbol != null) {
      System.err.println(classDefNode.getIdNode().getId() + " has already been declared!");
      return null;
    }
    Scope inheritScope = null;
    if (classDefNode.getParentclass() != null) {
      Symbol parentSymbol = currentScope.resolve(classDefNode.getParentclass().getId());
      if (parentSymbol instanceof STClass stClass) {
        inheritScope = stClass;
      } else {
        System.err.println(
            "Cannot inherit from non class type '" + classDefNode.getIdNode().getId() + "'!");
        return null;
      }
    }
    STClass stClass =
        new STClass(this.currentScope, classDefNode.getIdNode().getId(), inheritScope);
    this.currentScope.bind(stClass);
    // Add classmembers to class
    this.currentScope = stClass;
    for (var child : classDefNode.getClassmembers()) {
      child.accept(this);
    }
    this.currentScope = this.currentScope.getParent();

    return stClass;
  }

  private boolean validateObjectCalls(List<ObjcallNode> objectCalls) {
    Scope currentObjScope = this.currentScope;
    for (var child : objectCalls) {
      Symbol obj = child.accept(this);
      if (obj == null) {
        System.err.println(child.getIdNode().getId() + " could not be found!");
        this.currentScope = currentObjScope;
        return false;
      }
      if (obj.getType() instanceof STClass type) {
        this.currentScope = type;
      } else {
        System.err.println(
            "Variable " + obj.getName() + " is from BuiltIn type and can't have any members!");
        this.currentScope = currentObjScope;
        return false;
      }
    }
    return true;
  }

  private Symbol compareTypes(STType type1, STType type2) {
    if (type1 instanceof Array varArray) {
      if (type2 instanceof Array exprArray) {
        if (!varArray.getType().equals(exprArray.getType())) {
          return null;
        }
        if (varArray.getDimension() != exprArray.getDimension()) {
          return null;
        }
      } else {
        return null;
      }
    } else if (!type1.equals(type2)) {
      return null;
    }
    return type1;
  }

  @Override
  public Symbol visit(BindingNode bindingNode) {
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(bindingNode.getObjcalls())) return null;
    STType varType = null;
    if (bindingNode.hasArracc()) {
      Symbol arraccReturn = bindingNode.getArraccNode().accept(this);
      if (arraccReturn == null) {
        // Error will be handled in ArraccNode
        return null;
      }
      varType = (STType) arraccReturn;
    } else {
      String id = bindingNode.getIdNode().getId();
      Symbol symbol = this.currentScope.resolve(id);
      if (symbol == null) {
        System.err.println("Symbol " + id + " could not be found!");
        this.currentScope = currentObjScope;
        return null;
      }
      this.currentScope = currentObjScope;
      if (!(symbol instanceof Variable var)) {
        System.err.println(symbol.getName() + " is not a variable!");
        return null;
      }
      varType = var.getType();
    }
    if (!bindingNode.getAssignop().equals("=")) {
      // Check symbol type (int)
      if (!varType.equals(this.builtInInt)) {
        System.err.println(
            "Assign operator " + bindingNode.getAssignop() + " can only be used with type 'int'!");
        return null;
      }
    }
    // Compare Variable Type and Expr Type
    Symbol exprSymbol = bindingNode.getExpr().accept(this);
    if (exprSymbol == null) {
      // Error will be handled in expr
      return null;
    }
    Symbol type = compareTypes(varType, (STType) exprSymbol);
    if (type == null) {
      System.err.println(
          "Cannot bind type '" + exprSymbol.getName() + "' to '" + varType.getName() + "'!");
    }
    return type;
  }

  @Override
  public Symbol visit(ObjcallNode objcallNode) {
    if (objcallNode.getIdNode() != null) {
      Symbol symbol = currentScope.resolve(objcallNode.getIdNode().getId());
      if (symbol == null) {
        System.err.println(
            "Object member " + objcallNode.getIdNode().getId() + " could not be found!");
        return null;
      }
      STType type = symbol.getType();
      if (type instanceof STClass stClass) {
        return stClass;
      }
      return type;
    }
    if (objcallNode.getArracNode() != null) {
      return objcallNode.getArracNode().accept(this);
    }
    return objcallNode.getFncallNode().accept(this);
  }

  private boolean isAllowedVarID(String id) {
    Symbol symbolInScope = currentScope.resolveMember(id);
    if (symbolInScope != null) {
      System.err.println("Variable " + id + " has already been declared in this scope!");
      return false;
    }
    Symbol symbolInAllScopes = currentScope.resolve(id);
    // If the found symbol is a scope it has to be a class or function not a variable
    if (symbolInAllScopes instanceof Scope) {
      System.err.println(
          "Cannot use name of existing class or function '" + id + "' as variable name!");
      return false;
    }
    return true;
  }

  @Override
  public Symbol visit(VardeclNode vardeclNode) {
    String id = vardeclNode.getIdentifier().getIdNode().getId();
    if (!isAllowedVarID(id)) {
      return null;
    }

    Symbol symbol = currentScope.resolve(vardeclNode.getType().getClassName());
    if (!(symbol instanceof STType type)) {
      System.err.println(vardeclNode.getType().getClassName() + " is not a valid type!");
      return null;
    }
    if (vardeclNode.isArray()) {
      // Override type to array type
      type = new Array(vardeclNode.getIdentifier().getDimension(), type);
    }
    if (vardeclNode.getExpr() != null) {
      Symbol exprType = vardeclNode.getExpr().accept(this);
      if (exprType == null) {
        // Error will be handled in expr
        return null;
      }
      if (compareTypes(type, (STType) exprType) == null) {
        System.err.println(
            "Couldn't bind type '" + exprType.getName() + "' to '" + type.getName() + "'!");

        return null;
      }
    }
    Variable var = new Variable(id, type);
    currentScope.bind(var);
    return var;
  }

  private List<Symbol> getAllConstructors(Symbol symbol) {
    if (symbol instanceof Function function) {
      if (function.getScope() instanceof STClass stClass) {
        if (stClass.getName().equals(function.getName())) {
          return stClass.resolveAllMember(stClass.getName());
        } else {
          System.err.println("'" + function.getName() + "' is not a constructor!");
          return new ArrayList<>();
        }
      } else {
        System.err.println("'" + function.getName() + "' is not a class!");
        return new ArrayList<>();
      }
    }
    if (symbol instanceof STClass stClass) {
      return stClass.resolveAllMember(stClass.getName());
    } else {
      System.err.println("Cannot call variable '" + symbol.getName() + "' as constructor!");
      return new ArrayList<>();
    }
  }

  private boolean compareListOfTypes(List<? extends ASTNode> list1, List<? extends ASTNode> list2) {
    for (int i = 0; i < list1.size(); i++) {
      ASTNode node1 = list2.get(i);
      ASTNode node2 = list1.get(i);
      Symbol type1 = node1.accept(this);
      Symbol type2 = node2.accept(this);
      if (type1 == null || type2 == null) {
        // Error will be handled in visit expr
        return false;
      }
      if (compareTypes((STType) type1, (STType) type2) == null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Symbol visit(ConstructorCallNode constructorCallNode) {
    String id = constructorCallNode.getInstancename().getId();
    if (!isAllowedVarID(id)) {
      return null;
    }

    Symbol symbol = currentScope.resolve(constructorCallNode.getTypename().getId());
    if (symbol == null) {
      System.err.println(
          "Could not find class '" + constructorCallNode.getTypename().getId() + "'!");
      return null;
    }
    List<Symbol> constructors = getAllConstructors(symbol);
    for (var constructor : constructors) {
      Function constructorFunction = (Function) constructor;
      int size =
          (constructorCallNode.getArgsNode() != null
              ? constructorCallNode.getArgsNode().getArguments().size()
              : 0);
      if (size == constructorFunction.getParams().size()) {
        // TODO: Check Errorhandling (Error messages)
        if (compareListOfTypes(
            constructorFunction.getParams(), constructorCallNode.getArgsNode().getArguments())) {
          Variable var =
              new Variable(
                  constructorCallNode.getInstancename().getId(), constructorFunction.getType());
          currentScope.bind(var);
          return constructorFunction.getType();
        }
      }
    }
    System.err.println("Couldn't find matching constructor!");
    return null;
  }

  @Override
  public Symbol visit(FndeclNode fndeclNode) {
    // Checking valid return type
    Symbol type = this.currentScope.resolve(fndeclNode.getReturntype().getClassName());
    if (!(type instanceof STType)) {
      System.err.println(
              "Invalid return type for function " + fndeclNode.getIdNode().getId() + "!");
      return null;
    }
    // Check valid function name
    Symbol symbol = this.currentScope.resolve(fndeclNode.getIdNode().getId());
    if (symbol instanceof STClass stClass) {
      System.err.println(
          "Function '" + stClass.getName() + "' cannot have the same name as a class");
    }
    // Check if function can be declared with this params
    List<Symbol> memberSymbols = this.currentScope.resolveAll(fndeclNode.getIdNode().getId());
    boolean didOverride = false;
    for (var member : memberSymbols) {
      if (!(member instanceof Function foundFunction)) {
        System.err.println(
            fndeclNode.getIdNode().getId() + " conflicts with a previous declaration!");
        return null;
      }
      if (fndeclNode.getParams().size() == foundFunction.getParams().size()) {
        if (compareListOfTypes(fndeclNode.getParams(), foundFunction.getParams())) {
          if(!foundFunction.isVirtual()){
            System.err.println("Cannot override non virtual function '" + foundFunction.getName() + "'!");
            return null;
          }
          didOverride = true;
        }
      }
    }
    //Check if this function needs to override but doesn't
    if(!didOverride && fndeclNode.isOverride()){
      System.err.println("There is no matching function '" + fndeclNode.getIdNode().getId() + "' to override!");
      return null;
    }
    Function newFunction =
        new Function(
            fndeclNode.getIdNode().getId(),
            (STType) type,
            this.currentScope,
            fndeclNode.getParams(),
                (didOverride || fndeclNode.isVirtual())
        );
    for (var child : fndeclNode.getParams()) {
      Symbol childVar = child.accept(this);
      if (childVar != null) {
        newFunction.bind(childVar);
      } else {
        return null;
      }
    }
    currentScope.bind(newFunction);
    return newFunction;
  }

  @Override
  public Symbol visit(ReturnNode returnNode) {
    Function function = findMyFunction(currentScope);
    if (function == null) {
      System.err.println("Cannot use return statement outside of a function!");
      return null;
    }
    Symbol returnType;
    if (returnNode.getExpr() != null) {
      returnType = returnNode.getExpr().accept(this);
      // Error will be handled in visit expr
      if (returnType == null) {
        return null;
      } else if (returnType instanceof Array) {
        System.err.println("Cannot return array!");
        return null;
      }
    } else if (!returnNode.hasThis()) {
      returnType = this.builtInVoid;
    } else {
      Symbol symbol = findMyClass(currentScope);
      if (symbol != null) {
        returnType = symbol;
      } else {
        System.err.println("'this' cannot be used in this context!");
        return null;
      }
    }
    if (function.getType().equals(returnType)) {
      return returnType;
    }
    System.err.println(
        "Return type '"
            + returnType.getName()
            + "' does not match type '"
            + function.getType().getName()
            + "'!");
    return null;
  }

  private Symbol findMyClass(Scope scope) {
    while (scope.getParent() != null) {
      if (scope instanceof STClass stClass) {
        return stClass;
      }
      scope = scope.getParent();
    }
    return null;
  }

  private Function findMyFunction(Scope scope) {
    while (scope.getParent() != null) {
      if (scope instanceof Function function) {
        return function;
      }
      scope = scope.getParent();
    }
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
    if (expressionType != null) {
      System.err.println(
          "Print function expected "
              + printNodeType
              + " but found "
              + expressionType.getName()
              + "!");
    }
    return null;
  }

  @Override
  public Symbol visit(WhileNode whileNode) {
    Symbol conditionType = whileNode.getCondition().accept(this);
    boolean result = handleConditionBlock(conditionType, whileNode.getBlock());
    return (result ? conditionType : null);
  }

  @Override
  public Symbol visit(IfNode ifNode) {
    Symbol conditionType = ifNode.getCondition().accept(this);
    boolean result = handleConditionBlock(conditionType, ifNode.getBlock());
    for (var elif : ifNode.getElseifblocks()) {
      elif.accept(this);
    }
    if (ifNode.getElseblock() != null) {
      ifNode.getElseblock().accept(this);
    }
    return (result ? conditionType : null);
  }

  @Override
  public Symbol visit(ElseifNode elseifNode) {
    Symbol conditionType = elseifNode.getCondition().accept(this);
    boolean result = handleConditionBlock(conditionType, elseifNode.getBlock());
    return (result ? conditionType : null);
  }

  @Override
  public Symbol visit(ElseNode elseNode) {
    this.currentScope = new Scope(currentScope);
    elseNode.getBlock().accept(this);
    this.currentScope = this.currentScope.getParent();
    return null;
  }

  private boolean handleConditionBlock(Symbol conditionType, BlockNode block) {
    if (!this.builtInBool.equals(conditionType)) {
      System.err.println(
          "Type of variable '"
              + (conditionType == null ? "null" : conditionType.getName())
              + "' is not a valid condition type!");
      return false;
    }
    this.currentScope = new Scope(currentScope);
    block.accept(this);
    this.currentScope = this.currentScope.getParent();
    return true;
  }

  @Override
  public Symbol visit(IdentifierNode identifierNode) {
    // Unnecessary Function, because IdentifierNode won't be visited but only used
    return null;
  }

  @Override
  public Symbol visit(IncDecNode incDecNode) {
    Symbol symbol = currentScope.resolve(incDecNode.getIdNode().getId());
    if (symbol == null) {
      System.err.println("Could not find Variable " + incDecNode.getIdNode().getId() + "!");
      return null;
    }
    if (!this.builtInInt.equals(symbol.getType())) {
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
    if (argsNode.isArrVals()) {
      Symbol type = null;
      for (var argument : argsNode.getArguments()) {
        Symbol symbol = argument.accept(this);
        if (symbol == null) {
          // Error will be handled in Expr
          return null;
        }
        if (type == null) {
          type = symbol;
        } else {
          if (compareTypes((STType) type, (STType) symbol) == null) {
            System.err.println("Array values are not of the same type!");
            return null;
          }
        }
      }
      int dim = 1;
      if (type instanceof Array array) {
        dim += array.getDimension();
      }
      return type != null ? new Array(dim, type.getType()) : null;
    }
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
    if (paramNode.getIdentifier().isArray()) {
      return new Array(paramNode.getIdentifier().getDimension(), (STType) type);
    }
    return type;
  }

  @Override
  public Symbol visit(BlockNode blockNode) {
    for (var child : blockNode.getChildren()) {
      child.accept(this);
    }
    return this.builtInVoid;
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
  public Symbol visit(FncallNode fncallNode) {
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(fncallNode.getObjcalls())) return null;
    List<Symbol> symbols = this.currentScope.resolveAll(fncallNode.getIdNode().getId());
    for (var member : symbols) {
      if (!(member instanceof Function function)) {
        System.err.println(
            "Function " + fncallNode.getIdNode().getId() + " could not be resolved!");
        this.currentScope = currentObjScope;
        return null;
      }
      int size =
          (fncallNode.getArgsNode() != null ? fncallNode.getArgsNode().getArguments().size() : 0);
      if (size == function.getParams().size()) {
        if (compareListOfTypes(function.getParams(), fncallNode.getArgsNode().getArguments())) {
          this.currentScope = currentObjScope;
          return function.getType();
        }
      }
    }
    System.err.println("Couldn't find function matching given parameters!");
    this.currentScope = currentObjScope;
    return null;
  }

  @Override
  public Symbol visit(OBJMEMNode objmemNode) {
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(objmemNode.getObjcalls())) return null;
    if (objmemNode.hasArraccNode()) {
      Symbol type = objmemNode.getArraccNode().accept(this);
      this.currentScope = currentObjScope;
      return type;
    }
    Symbol symbol = this.currentScope.resolve(objmemNode.getIdNode().getId());
    this.currentScope = currentObjScope;
    if (!(symbol instanceof Variable variable)) {
      System.err.println("Variable " + symbol.getName() + " could not be resolved!");
      return null;
    }
    return variable.getType();
  }

  @Override
  public Symbol visit(OperatorNode operatorNode) {
    // TODO
    return null;
  }

  @Override
  public Symbol visit(MULNode mulNode) {
    Symbol e1Type = mulNode.getE1().accept(this);
    Symbol e2Type = mulNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInInt : null);
  }

  @Override
  public Symbol visit(DIVNode divNode) {
    Symbol e1Type = divNode.getE1().accept(this);
    Symbol e2Type = divNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInInt : null);
  }

  @Override
  public Symbol visit(ADDNode addNode) {
    Symbol e1Type = addNode.getE1().accept(this);
    Symbol e2Type = addNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInInt : null);
  }

  @Override
  public Symbol visit(SUBNode subNode) {
    Symbol e1Type = subNode.getE1().accept(this);
    Symbol e2Type = subNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInInt : null);
  }

  @Override
  public Symbol visit(EQUALSNode equalsNode) {
    Symbol e1Type = equalsNode.getE1().accept(this);
    Symbol e2Type = equalsNode.getE2().accept(this);
    return (checkComparison(e1Type, e2Type) ? this.builtInBool : null);
  }

  @Override
  public Symbol visit(NEAQUALSNode neaqualsNode) {
    Symbol e1Type = neaqualsNode.getE1().accept(this);
    Symbol e2Type = neaqualsNode.getE2().accept(this);
    return (checkComparison(e1Type, e2Type) ? this.builtInBool : null);
  }

  private boolean checkComparison(Symbol type1, Symbol type2) {
    if (!(type1 instanceof BuiltIn) || !(type2 instanceof BuiltIn)) {
      System.err.println(
          "Cannot compare builtin types " + type1.getName() + " and " + type2.getName() + "!");
      return false;
    }
    return true;
  }

  @Override
  public Symbol visit(GREATERNode greaterNode) {
    Symbol e1Type = greaterNode.getE1().accept(this);
    Symbol e2Type = greaterNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInBool : null);
  }

  @Override
  public Symbol visit(LESSNode lessNode) {
    Symbol e1Type = lessNode.getE1().accept(this);
    Symbol e2Type = lessNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInBool : null);
  }

  @Override
  public Symbol visit(GEAQUALSNode geaqualsNode) {
    Symbol e1Type = geaqualsNode.getE1().accept(this);
    Symbol e2Type = geaqualsNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInBool : null);
  }

  @Override
  public Symbol visit(LEAQUALSNode leaqualsNode) {
    Symbol e1Type = leaqualsNode.getE1().accept(this);
    Symbol e2Type = leaqualsNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type) ? this.builtInBool : null);
  }

  private boolean checkBothIntegers(Symbol type1, Symbol type2) {
    if (!this.builtInInt.equals(type1) || !this.builtInInt.equals(type2)) {
      System.err.println(
          "Cannot use types "
              + (type1 == null ? "null" : type1.getName())
              + " and "
              + (type2 == null ? "null" : type2.getName())
              + " in this context!");
      return false;
    }
    return true;
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
    Symbol symbol = currentScope.resolve(arraccNode.getIdNode().getId());
    if (symbol == null) {
      System.err.println("Couldn't resolve variable " + arraccNode.getIdNode().getId());
      return null;
    }
    if (!(symbol instanceof Variable variable)) {
      System.err.println("Couldn't resolve variable " + arraccNode.getIdNode().getId());
      return null;
    }
    if (!(variable.getType() instanceof Array array)) {
      System.err.println(
          "Cannot access index of non array variable " + arraccNode.getIdNode().getId());
      return null;
    }
    if (arraccNode.getDimension() > array.getDimension()) {
      System.err.println("Dimension mismatch in " + arraccNode.getIdNode().getId());
      return null;
    }
    for (var expr : arraccNode.getExprNodes()) {
      if (!this.builtInInt.equals(expr.accept(this))) {
        System.err.println("Cannot access index of array with non int type expression!");
        return null;
      }
    }
    int newDimension = array.getDimension() - arraccNode.getDimension();
    return newDimension == 0 ? array.getType() : new Array(newDimension, array.getType());
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
    Symbol var = currentScope.resolve(idNode.getId());
    if (var == null) {
      System.err.println("Couldn't find variable " + idNode.getId());
      return null;
    }
    return var.getType();
  }

  @Override
  public Symbol visit(ConstructorNode constructorNode) {
    if (this.currentScope instanceof STClass stClass) {
      if (!stClass.getName().equals(constructorNode.getIdNode().getId())) {
        System.err.println(
            "Constructor name '"
                + constructorNode.getIdNode().getId()
                + "' does not match class name!");
        return null;
      }
    } else {
      System.err.println("Cannot define Constructor outside of class");
      return null;
    }
    List<Symbol> symbols = stClass.resolveAllMember(constructorNode.getIdNode().getId());
    for (var member : symbols) {
      if (member instanceof Function foundConstructor) {
        if (constructorNode.getParams().size() == foundConstructor.getParams().size()) {
          if (compareListOfTypes(constructorNode.getParams(), foundConstructor.getParams())) {
            System.err.println(
                "Constructor "
                    + constructorNode.getIdNode().getId()
                    + " can't be overloaded with same parameters!");
            return null;
          }
        }
      }
    }
    Function newConstructor =
        new Function(
            constructorNode.getIdNode().getId(),
            stClass,
            this.currentScope,
            constructorNode.getParams(),
                false);
    for (var child : constructorNode.getParams()) {
      Symbol childVar = child.accept(this);
      if (childVar != null) {
        newConstructor.bind(childVar);
      } else {
        return null;
      }
    }
    currentScope.bind(newConstructor);
    this.currentScope = newConstructor;
    constructorNode.getBlock().accept(this);
    this.currentScope = this.currentScope.getParent();
    return stClass;
  }
}
