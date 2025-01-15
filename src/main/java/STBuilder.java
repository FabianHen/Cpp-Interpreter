import AST.*;
import SymbolTable.*;
import java.util.ArrayList;
import java.util.List;

public class STBuilder implements ASTVisitor<Symbol> {
  // TODO: Refactor class hierarchy of STType
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
    // TODO: Inheritance
    STClass stClass = new STClass(this.currentScope, classDefNode.getName().getId());
    this.currentScope.bind(stClass);
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
        if(!varArray.getType().equals(exprArray.getType())){
          System.err.println(
                  "Types '" + type1.getName() + "' and '" + type2.getName() + "' do not match!");
          return null;
        }
        if(varArray.getDimension() != exprArray.getDimension()){
          System.err.println("Array dimensions don't match!");
          return null;
        }
      } else {
        System.err.println(
                "Type mismatch between '"
                        + varArray.getName()
                        + "' and '"
                        + (type2 == null ? "null" : type2.getName())
                        + "'!");
        return null;
      }
    } else if (!type1.equals(type2)) {
      System.err.println(
              "Types '" + type1.getName() + "' and '" + type2.getName() + "' do not match!");
      return null;
    }
    return (Symbol) type1;
  }
  @Override
  public Symbol visit(BindingNode bindingNode) {
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(bindingNode.getObjcalls())) return null;
    String id = bindingNode.getIdentifierNode().getIdNode().getId();
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
    if (!bindingNode.getAssignop().equals("=")) {
      // Check symbol type (int)
      if (!var.getType().equals(this.builtInInt)) {
        System.err.println(
            "Assign operator " + bindingNode.getAssignop() + " can only be used with type 'int'!");
        return null;
      }
    }
    // Compare Variable Type and Expr Type
    Symbol exprSymbol = bindingNode.getExpr().accept(this);
    if(exprSymbol == null){
      // Error will be handled in expr
      return null;
    }
    STType varType = var.getType();
    return compareTypes(varType, (STType) exprSymbol);
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
      return (Symbol) type;
    }
    if(objcallNode.getArracNode() != null){
      return objcallNode.getArracNode().accept(this);
    }
    return objcallNode.getFncallNode().accept(this);
  }

  @Override
  public Symbol visit(VardeclNode vardeclNode) {
    String id = vardeclNode.getIdentifier().getIdNode().getId();
    Symbol symbolInScope = currentScope.resolveMember(id);
    if (symbolInScope != null) {
      System.err.println("Variable " + id + " has already been declared in this scope!");
      return null;
    }
    Symbol symbolInAllScopes = currentScope.resolve(id);
    // If the found symbol is a scope it has to be a class or function not a variable
    if (symbolInAllScopes instanceof Scope) {
      System.err.println(
          "Cannot use name of existing class or function '" + id + "' as variable name!");
      return null;
    }

    Symbol symbol = currentScope.resolve(vardeclNode.getType().getClassName());
    if (!(symbol instanceof STType type)) {
      System.err.println(vardeclNode.getType().getClassName() + " is not a valid type!");
      return null;
    }
    if(vardeclNode.isArray()){
      //Override type to array type
      type = new Array(vardeclNode.getIdentifier().getDimension(), type);
    }
    if (vardeclNode.getExpr() != null) {
      Symbol exprType = vardeclNode.getExpr().accept(this);
      if(exprType == null) {
        // Error will be handled in expr
        return null;
      }
      if (compareTypes(type,(STType) exprType) == null) {
        return null;
      }
    }
    Variable var = new Variable(id, type);
    currentScope.bind(var);
    return var;
  }

  @Override
  public Symbol visit(ConstructorCallNode constructorCallNode) {
    String id = constructorCallNode.getInstancename().getId();
    Symbol symbolInScope = currentScope.resolveMember(id);
    if (symbolInScope != null) {
      System.err.println("Variable " + id + " has already been declared in this scope!");
      return null;
    }
    Symbol symbolInAllScopes = currentScope.resolve(id);
    // If the found symbol is a scope it has to be a class or function not a variable
    if (symbolInAllScopes instanceof Scope) {
      System.err.println(
          "Cannot use name of existing class or function '" + id + "' as variable name!");
      return null;
    }

    // Duplicated from NEWNode
    Symbol symbol = currentScope.resolve(constructorCallNode.getTypename().getId());
    if (symbol == null) {
      System.err.println(
          "Could not find constructor of class '"
              + constructorCallNode.getTypename().getId()
              + "'!");
      return null;
    }
    Function constructor;
    if (symbol instanceof Function function) {
      if (function.getScope() instanceof STClass stClass) {
        if (stClass.getName().equals(function.getName())) {
          constructor = function;
        } else {
          System.err.println("'" + function.getName() + "' is not a constructor!");
          return null;
        }
      } else {
        System.err.println("'" + function.getName() + "' is not a class!");
        return null;
      }
    }
    if (symbol instanceof STClass stClass) {
      Symbol constructorSymbol = stClass.resolveMember(stClass.getName());
      if (constructorSymbol instanceof Function function) {
        constructor = function;
      } else {
        System.err.println("Class '" + symbol.getName() + "' doesn't have a constructor!");
        return null;
      }
    } else {
      System.err.println("Cannot call variable '" + symbol.getName() + "' as constructor!");
      return null;
    }
    int size =
        (constructorCallNode.getArgsNode() != null
            ? constructorCallNode.getArgsNode().getArguments().size()
            : 0);
    if (size == constructor.getParams().size()) {
      for (int i = 0; i < constructor.getParams().size(); i++) {
        ExprNode argument = constructorCallNode.getArgsNode().getArgument(i);
        ParamNode paramNode = constructor.getParamNode(i);
        Symbol argumentType = argument.accept(this);
        if (argumentType == null) {
          // Error will be handled in visit expr
          return null;
        }
        Symbol paramType = this.currentScope.resolve(paramNode.getTypeNode().getClassName());
        if (paramType == null) {
          // Error will be handled in visit expr
          return null;
        }
        if(paramNode.getIdentifier().isArray()) {
          paramType = new Array(paramNode.getIdentifier().getDimension(), (STType) paramType);
        }
        if (compareTypes((STType) paramType, (STType) argumentType) == null) {
          return null;
        }
      }
      Variable var =
          new Variable(constructorCallNode.getInstancename().getId(), constructor.getType());
      currentScope.bind(var);
      return (Symbol) constructor.getType();
    } else {
      System.err.println("Constructor parameters do not match!");
      return null;
    }
  }

  @Override
  public Symbol visit(FndeclNode fndeclNode) {
    Symbol symbol = this.currentScope.resolve(fndeclNode.getIdNode().getId());
    if (symbol instanceof STClass stClass) {
      System.err.println(
          "Function '" + stClass.getName() + "' cannot have the same name as a class");
    }
    Symbol memberSymbol = this.currentScope.resolveMember(fndeclNode.getIdNode().getId());
    if (memberSymbol != null) {
      if (!(memberSymbol instanceof Function foundFunction)) {
        System.err.println(
            fndeclNode.getIdNode().getId() + " conflicts with a previous declaration!");
        return null;
      }
      if (fndeclNode.getParams().size() == foundFunction.getParams().size()) {
        boolean overloadFunction = false;
        for (int i = 0; i < foundFunction.getParams().size(); i++) {
          ParamNode paramNodeNew = fndeclNode.getParamNode(i);
          ParamNode paramNodeOld = foundFunction.getParamNode(i);
          if (paramNodeOld.getTypeNode().getType() != paramNodeNew.getTypeNode().getType()) {
            overloadFunction = true;
            break;
          } else if (paramNodeOld.getIdentifier().isArray() != paramNodeNew.getIdentifier().isArray())  {
            overloadFunction = true;
            break;
          }
        }
        if (!overloadFunction) {
          System.err.println(
              "Function "
                  + fndeclNode.getIdNode().getId()
                  + " can't be overloaded with same parameters!");
          return null;
        }
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
    if(argsNode.isArrVals()){
      Symbol type = null;
      for(var argument : argsNode.getArguments()){
        Symbol symbol = argument.accept(this);
        if(symbol == null){
          // Error will be handled in Expr
          return null;
        }
        if(type == null){
          type = symbol;
        }else{
          if(compareTypes((STType) type, (STType) symbol) == null){
            // Error will be handled in compareTypes
            return null;
          }
        }
      }
      int dim = 1;
      if(type instanceof Array array){
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
    if(paramNode.getIdentifier().isArray()){
      type = new Array(paramNode.getIdentifier().getDimension(), (STType) type);
    }
    return new Variable(paramNode.getIdentifier().getIdNode().getId(), (STType) type);
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
    // TODO: check for overloaded function declarations
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(fncallNode.getObjcalls())) return null;
    Symbol symbol = this.currentScope.resolve(fncallNode.getIdNode().getId());
    if (!(symbol instanceof Function function)) {
      System.err.println("Function " + fncallNode.getIdNode().getId() + " could not be resolved!");
      this.currentScope = currentObjScope;
      return null;
    }
    int size =
        (fncallNode.getArgsNode() != null ? fncallNode.getArgsNode().getArguments().size() : 0);
    if (size == function.getParams().size()) {
      for (int i = 0; i < function.getParams().size(); i++) {
        ExprNode argument = fncallNode.getArgsNode().getArgument(i);
        ParamNode paramNode = function.getParamNode(i);
        Symbol argumentType = argument.accept(this);
        if (argumentType == null) {
          // Error will be handled in visit expr
          this.currentScope = currentObjScope;
          return null;
        }
        Symbol paramType = this.currentScope.resolve(paramNode.getTypeNode().getClassName());
        if (paramType == null) {
          // Error will be handled in visit expr
          this.currentScope = currentObjScope;
          return null;
        }
        if(paramNode.getIdentifier().isArray()) {
          paramType = new Array(paramNode.getIdentifier().getDimension(), (STType) paramType);
        }
        if (compareTypes((STType) paramType, (STType) argumentType) == null) {
          this.currentScope = currentObjScope;
          return null;
        }
      }
      this.currentScope = currentObjScope;
      return (Symbol) function.getType();
    } else {
      System.err.println("Function parameters do not match!");
      this.currentScope = currentObjScope;
      return null;
    }
  }

  @Override
  public Symbol visit(OBJMEMNode objmemNode) {
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(objmemNode.getObjcalls())) return null;
    Symbol symbol = this.currentScope.resolve(objmemNode.getIdNode().getId());
    this.currentScope = currentObjScope;
    if (!(symbol instanceof Variable variable)) {
      System.err.println("Variable " + symbol.getName() + " could not be resolved!");
      return null;
    }
    return (Symbol) variable.getType();
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
    if(symbol == null) {
      System.err.println("Couldn't resolve variable " + arraccNode.getIdNode().getId());
      return null;
    }
    if(!(symbol instanceof Variable variable)) {
      System.err.println("Couldn't resolve variable " + arraccNode.getIdNode().getId());
      return null;

    }
    if(!(variable.getType() instanceof Array array)) {
      System.err.println("Cannot access index of non array variable " + arraccNode.getIdNode().getId());
      return null;
    }
    if(arraccNode.getDimension() > array.getDimension()){
      System.err.println("Dimension mismatch in " + arraccNode.getIdNode().getId());
      return null;
    }
    for(var expr : arraccNode.getExprNodes()){
      if(!this.builtInInt.equals(expr.accept(this))){
        System.err.println("Cannot access index of array with non int type expression!");
        return null;
      }
    }
    int newDimension = array.getDimension() - arraccNode.getDimension();
    return newDimension == 0 ? (Symbol) array.getType() : new Array(newDimension, array.getType());
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
    STType type = var.getType();
    return (Symbol) type;
  }

  @Override
  public Symbol visit(OverrideFndeclNode overrideFndeclNode) {
    // TODO
    return null;
  }

  @Override
  public Symbol visit(VirtualNode virtualNode) {
    // TODO
    return null;
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
    Symbol symbol = stClass.resolveMember(constructorNode.getIdNode().getId());
    // TODO: don't duplicate?
    if (symbol instanceof Function foundConstructor) {
      if (constructorNode.getParams().size() == foundConstructor.getParams().size()) {
        boolean overloadConstructor = false;
        for (int i = 0; i < foundConstructor.getParams().size(); i++) {
          ParamNode paramNodeNew = constructorNode.getParamNode(i);
          ParamNode paramNodeOld = foundConstructor.getParamNode(i);
          if (paramNodeOld.getTypeNode().getType() != paramNodeNew.getTypeNode().getType()) {
            overloadConstructor = true;
            break;
          } else if (paramNodeOld.getIdentifier().isArray() != paramNodeNew.getIdentifier().isArray())  {
            overloadConstructor = true;
            break;
          }
        }
        if (!overloadConstructor) {
          System.err.println(
              "Constructor "
                  + constructorNode.getIdNode().getId()
                  + " can't be overloaded with same parameters!");
          return null;
        }
      }
    }
    Function newConstructor =
        new Function(
            constructorNode.getIdNode().getId(),
            stClass,
            this.currentScope,
            constructorNode.getParams());
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
