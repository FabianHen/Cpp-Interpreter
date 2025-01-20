import AST.*;
import SymbolTable.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The STBuilder is a class that receives a root node of an AST and uses this tree to create a Tree
 * structured Symbol table. At the same time it checks that types were used correctly in the code.
 */
public class STBuilder implements ASTVisitor<Symbol> {
  private int errCounter = 0;
  private Scope currentScope;
  // Builtin types are defined globally for easier typechecking
  private final BuiltIn builtInVoid = new BuiltIn("void");
  private final BuiltIn builtInInt = new BuiltIn("int");
  private final BuiltIn builtInChar = new BuiltIn("char");
  private final BuiltIn builtInBool = new BuiltIn("bool");

  /**
   * This function binds built in types and functions and visits all children of the given
   * programNode
   *
   * @param programNode a node that represents the 'program' parser rule
   * @return {@code null}
   */
  @Override
  public Symbol visit(ProgramNode programNode) {
    this.currentScope = new Scope(null);
    programNode.setCurrentScope(this.currentScope);
    // built in types are bound to validate types of variables and functions
    this.currentScope.bind(this.builtInInt);
    this.currentScope.bind(this.builtInChar);
    this.currentScope.bind(this.builtInBool);
    this.currentScope.bind(this.builtInVoid);
    // builtin print functions ar bound to prevent redefinition of those
    this.currentScope.bind(
        new Function("print_int", this.builtInVoid, null, new ArrayList<>(), false));
    this.currentScope.bind(
        new Function("print_char", this.builtInVoid, null, new ArrayList<>(), false));
    this.currentScope.bind(
        new Function("print_bool", this.builtInVoid, null, new ArrayList<>(), false));
    // all children are visited
    for (var child : programNode.getChildren()) {
      child.accept(this);
    }
    if (errCounter > 0) {
      System.err.println(
          "---The Interpreter could not be started, because the source code contains '"
              + errCounter
              + "' errors---");
      System.exit(0);
    }
    return null;
  }

  /**
   * A Function that receives a classDef node and defines it and its children in the current scope
   *
   * @param classDefNode a node that represents the 'classDef' parser rule
   * @return a STClass object or {@code null} in case of an error
   */
  @Override
  public Symbol visit(ClassDefNode classDefNode) {
    classDefNode.setCurrentScope(this.currentScope);
    // Check if the name has already been declared in any way
    Symbol symbol = currentScope.resolve(classDefNode.getIdNode().getId());
    if (symbol != null) {
      logError(
          classDefNode.getIdNode().getId() + " has already been declared!", classDefNode.getLine());
      return null;
    }
    // Check if inheritScope is given and if it is valid
    Scope inheritScope = null;
    if (classDefNode.getParentclass() != null) {
      Symbol parentSymbol = currentScope.resolve(classDefNode.getParentclass().getId());
      if (parentSymbol instanceof STClass stClass) {
        inheritScope = stClass;
      } else {
        logError(
            "Cannot inherit from non class type '" + classDefNode.getIdNode().getId() + "'!",
            classDefNode.getLine());
        return null;
      }
    }
    // Create a new Class and bind it in current scope
    STClass stClass =
        new STClass(this.currentScope, classDefNode.getIdNode().getId(), inheritScope);
    this.currentScope.bind(stClass);
    // Add class members to class scope
    this.currentScope = stClass;
    for (var child : classDefNode.getClassmembers()) {
      child.accept(this);
    }
    this.currentScope = this.currentScope.getParent();

    return stClass;
  }

  /**
   * Checks if both sides of the binding are of same type and defined
   *
   * @param bindingNode a node that represents the 'binding' parser rule
   * @return the type of the variable bound or {@code null} if an error occured
   */
  @Override
  public Symbol visit(BindingNode bindingNode) {
    bindingNode.setCurrentScope(this.currentScope);
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(bindingNode.getObjcalls(), bindingNode.hasThis())) return null;
    STType varType = null;
    // validate array access if given
    if (bindingNode.hasArracc()) {
      Symbol arraccReturn = bindingNode.getArraccNode().accept(this);
      this.currentScope = currentObjScope;
      if (arraccReturn == null) {
        // Error will be handled in ArraccNode
        return null;
      }
      varType = (STType) arraccReturn;
    } else {
      // Check if id on left side is defined
      String id = bindingNode.getIdNode().getId();
      Symbol symbol = this.currentScope.resolve(id);
      this.currentScope = currentObjScope;
      if (symbol == null) {
        logError("Symbol " + id + " could not be found!", bindingNode.getLine());
        return null;
      }
      if (!(symbol instanceof Variable var)) {
        logError(symbol.getName() + " is not a variable!", bindingNode.getLine());
        return null;
      }
      varType = var.getType();
    }
    // Make sure that assign operators like '+=' and '-=' are not used with non int
    if (!bindingNode.getAssignop().equals("=")) {
      // Check symbol type (int)
      if (!varType.equals(this.builtInInt)) {
        logError(
            "Assign operator " + bindingNode.getAssignop() + " can only be used with type 'int'!",
            bindingNode.getLine());
        return null;
      }
    }
    // Compare Variable Type and Expr Type
    Symbol exprSymbol = bindingNode.getExpr().accept(this);
    if (exprSymbol == null) {
      // Error will be handled in expr
      return null;
    }
    Symbol type = compareTypes(varType, (STType) exprSymbol, true);
    if (type == null) {
      logError(
          "Cannot bind type '" + exprSymbol.getName() + "' to '" + varType.getName() + "'!",
          bindingNode.getLine());
    }
    return type;
  }

  /**
   * Checks if the object in the given objectcallNode can be called
   *
   * @param objcallNode a node that represents the 'objcall' parser rule
   * @return the type of the object called or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ObjcallNode objcallNode) {
    objcallNode.setCurrentScope(this.currentScope);
    if (objcallNode.getIdNode() != null) {
      Symbol symbol = currentScope.resolve(objcallNode.getIdNode().getId());
      if (symbol == null) {
        logError(
            "Object member " + objcallNode.getIdNode().getId() + " could not be found!",
            objcallNode.getLine());
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

  /**
   * Checks if Types in variable declaration match and creates a new variable if possible
   *
   * @param vardeclNode a node that represents the 'vardecl' parser rule
   * @return new {@code Variable} or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(VardeclNode vardeclNode) {
    // Check if Reference
    vardeclNode.setCurrentScope(this.currentScope);
    if (vardeclNode.getIdentifier().isReference() && !(vardeclNode.getExpr() instanceof IDNode)) {
      logError("Reference has to be defined with Variable!", vardeclNode.getLine());
      return null;
    }
    // Check if id can be declared
    String id = vardeclNode.getIdentifier().getIdNode().getId();
    if (!isAllowedVarID(id, vardeclNode.getLine())) {
      return null;
    }
    // Check if type is valid
    Symbol symbol = vardeclNode.getType().accept(this);
    if (!(symbol instanceof STType type)) {
      // Error will be handled in TypeNode
      return null;
    }
    // Check if type is array
    if (vardeclNode.isArray()) {
      // Override type to array of this type
      type = new Array(vardeclNode.getIdentifier().getDimension(), type);
    }
    // Check if expression is same type as declaration type
    if (vardeclNode.getExpr() != null) {
      Symbol exprType = vardeclNode.getExpr().accept(this);
      if (exprType == null) {
        // Error will be handled in expr
        return null;
      }
      if (compareTypes(type, (STType) exprType, true) == null) {
        logError(
            "Couldn't bind type '" + exprType.getName() + "' to '" + type.getName() + "'!",
            vardeclNode.getLine());

        return null;
      }
    }
    // Create and bind new variable
    Variable var = new Variable(id, type);
    currentScope.bind(var);
    return var;
  }

  /**
   * Checks if the variable can be created and a fitting constructor can be found. Creates a
   * variable if both apply.
   *
   * @param constructorCallNode a node that represents the 'constructorCall' parser rule
   * @return the type of the new Variable or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ConstructorCallNode constructorCallNode) {
    constructorCallNode.setCurrentScope(this.currentScope);
    // Check if the variable can be defined
    String id = constructorCallNode.getInstancename().getId();
    if (!isAllowedVarID(id, constructorCallNode.getLine())) {
      return null;
    }
    // Iterates through all constructors of the class
    List<Symbol> constructors =
        getAllConstructors(
            constructorCallNode.getTypename().getId(), constructorCallNode.getLine());
    for (var constructor : constructors) {
      Function constructorFunction = (Function) constructor;
      // saves arguments and amount of args
      int size = 0;
      List<ExprNode> argsList = new ArrayList<>();
      if (constructorCallNode.getArgsNode() != null) {
        argsList = constructorCallNode.getArgsNode().getArguments();
        size = argsList.size();
      }
      if (size == constructorFunction.getParams().size()) {
        if (compareListsOfTypes(constructorFunction.getParams(), argsList, true)) {
          // a matching constructor was found and a new variable is created
          Variable var =
              new Variable(
                  constructorCallNode.getInstancename().getId(), constructorFunction.getType());
          currentScope.bind(var);
          // Save which constructor was called
          constructorCallNode.setFunction(constructorFunction);
          return constructorFunction.getType();
        }
      }
    }
    logError("Couldn't find matching constructor!", constructorCallNode.getLine());
    return null;
  }

  /**
   * This function trys to declare a new function. It realizes overriding, overloading and checks
   * for virtual when overriding
   *
   * @param fndeclNode a node that represents the 'fndeclNode' parser rule
   * @return a new function or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(FndeclNode fndeclNode) {
    fndeclNode.setCurrentScope(this.currentScope);
    Function newFunction = checkFunctionCanBeDefined(fndeclNode, null);
    if (newFunction != null) {
      currentScope.bind(newFunction);
    }
    return newFunction;
  }

  /**
   * This function checks if the returned expression is valid and if it matches the function return
   * type
   *
   * @param returnNode a node that represents the 'return' parser rule
   * @return the returned type or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ReturnNode returnNode) {
    returnNode.setCurrentScope(this.currentScope);
    Function function = findMyFunction(currentScope);
    if (function == null) {
      logError("Cannot use return statement outside of a function!", returnNode.getLine());
      return null;
    }
    Symbol returnType;
    // if an expression is returned
    if (returnNode.getExpr() != null) {
      returnType = returnNode.getExpr().accept(this);
      // Error will be handled in visit expr
      if (returnType == null) {
        return null;
      } else if (returnType instanceof Array) {
        logError("Cannot return array!", returnNode.getLine());
        return null;
      }
    } else if (!returnNode.hasThis()) {
      // nothing is returned, therefore void
      returnType = this.builtInVoid;
    } else {
      // 'this' is returned
      Symbol symbol = findMyClass(currentScope);
      if (symbol != null) {
        returnType = symbol;
      } else {
        logError("'this' cannot be used in this context!", returnNode.getLine());
        return null;
      }
    }
    if (function.getType().equals(returnType)) {
      return returnType;
    }
    logError(
        "Return type '"
            + returnType.getName()
            + "' does not match type '"
            + function.getType().getName()
            + "'!",
        returnNode.getLine());
    return null;
  }

  /**
   * Trys to declare and define a function based on the given {@code FndefNode} and accepts the
   * definition block
   *
   * @param fndefNode a node that represents the 'fndef' parser rule
   * @return a new function or {@code null} if an error has occurred
   */
  @Override
  public Symbol visit(FndefNode fndefNode) {
    fndefNode.setCurrentScope(this.currentScope);
    Function function = checkFunctionCanBeDefined(fndefNode.getFndecl(), fndefNode);
    if (function != null) {
      function.getFndeclNode().setCurrentScope(this.currentScope);
      function.getFndeclNode().setFndefNode(fndefNode);
      function.setHasBeenDefined(true);
      this.currentScope.bind(function);
      this.currentScope = function;
      fndefNode.getBlock().accept(this);
      this.currentScope = this.currentScope.getParent();
      return function;
    }
    // Errors in function declaration and block will be handled in corresponding visitor methods
    return null;
  }

  /**
   * @param printNode node that represents the 'print' parser rule
   * @return the printed type or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(PrintNode printNode) {
    printNode.setCurrentScope(this.currentScope);
    Symbol expressionType = printNode.getExpr().accept(this);
    String printNodeType = printNode.getType().toString().toLowerCase();
    if (expressionType instanceof BuiltIn builtIn) {
      // comparing builtin name and type enum
      if (!builtIn.getName().equals(printNodeType)) {
        logError(
            "Print function expected " + printNodeType + " but found " + builtIn.getName() + "!",
            printNode.getLine());
        return null;
      }
      return builtIn;
    }
    // error will be handled in expression if 'expressionType' is null
    if (expressionType != null) {
      logError(
          "Print function expected "
              + printNodeType
              + " but found "
              + expressionType.getName()
              + "!",
          printNode.getLine());
    }
    return null;
  }

  /**
   * Checks if condition is of boolean type and accepts the block if this is the case
   *
   * @param whileNode node that represents the 'while' parser rule
   * @return boolean builtin type or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(WhileNode whileNode) {
    whileNode.setCurrentScope(this.currentScope);
    Symbol conditionType = whileNode.getCondition().accept(this);
    // Check if condition is valid and handle block
    boolean result = handleConditionBlock(conditionType, whileNode.getBlock());
    return (result ? conditionType : null);
  }

  /**
   * Checks if condition is of boolean type and accepts the ifs block and the else-if blocks and
   * else block if existing.
   *
   * @param ifNode node that represents the 'if' parser rule
   * @return boolean builtin type or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(IfNode ifNode) {
    ifNode.setCurrentScope(this.currentScope);
    Symbol conditionType = ifNode.getCondition().accept(this);
    // check if condition is a condition
    boolean result = handleConditionBlock(conditionType, ifNode.getBlock());
    // accept all else-ifs
    for (var elif : ifNode.getElseifblocks()) {
      elif.accept(this);
    }
    // accept all elses
    if (ifNode.getElseblock() != null) {
      ifNode.getElseblock().accept(this);
    }
    return (result ? conditionType : null);
  }

  /**
   * Checks if condition is of boolean type and accepts the block if this is the case
   *
   * @param elseifNode node that represents the 'elseif' parser rule
   * @return boolean builtin type or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ElseifNode elseifNode) {
    elseifNode.setCurrentScope(this.currentScope);
    Symbol conditionType = elseifNode.getCondition().accept(this);
    // Check if condition is valid and handle block
    boolean result = handleConditionBlock(conditionType, elseifNode.getBlock());
    return (result ? conditionType : null);
  }

  /**
   * Accepts the block of given {@code elseNode}
   *
   * @param elseNode node that represents the 'elseif' parser rule
   * @return {@code null}
   */
  @Override
  public Symbol visit(ElseNode elseNode) {
    elseNode.setCurrentScope(this.currentScope);
    this.currentScope = new Scope(currentScope);
    elseNode.getBlock().accept(this);
    this.currentScope = this.currentScope.getParent();
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
  public Symbol visit(IdentifierNode identifierNode) {
    // Unnecessary Function, because IdentifierNode won't be visited but only used
    return null;
  }

  /**
   * This functions checks if the increment and decrement operators are used with an int variable as
   * intended.
   *
   * @param incDecNode node that represents the 'incDec' parser rule
   * @return {@code builtInInt} or null if an error occurred
   */
  @Override
  public Symbol visit(IncDecNode incDecNode) {
    incDecNode.setCurrentScope(this.currentScope);
    Symbol symbol = currentScope.resolve(incDecNode.getIdNode().getId());
    // Check if accessed variable is declared
    if (symbol == null) {
      logError(
          "Could not find Variable " + incDecNode.getIdNode().getId() + "!", incDecNode.getLine());
      return null;
    }
    // Check if this variable is an integer
    if (!this.builtInInt.equals(symbol.getType())) {
      logError(
          "Could not"
              + (incDecNode.isInc() ? " increase " : " decrease ")
              + "'"
              + incDecNode.getIdNode().getId()
              + "'!",
          incDecNode.getLine());
      return null;
    }
    return this.builtInInt;
  }

  /**
   * This function handles {@code ArgsNodes}. It checks if all arguments are of the same type and
   * dimension Because argsNode is only used in {@code fncall()} and arrays and the {@code ArgsNode}
   * is not visited in {@code fncall()}, the focus of this function is on dealing with arrays and
   * not function call parameters.
   *
   * @param argsNode node that represents the 'args' parser rule
   * @return an array type with fitting dimension or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ArgsNode argsNode) {
    argsNode.setCurrentScope(this.currentScope);
    if (argsNode.isArrVals()) {
      Symbol type = null;
      for (var argument : argsNode.getArguments()) {
        Symbol symbol = argument.accept(this);
        if (symbol == null) {
          // Error will be handled in Expr
          return null;
        }
        // Check if all arguments are of same type (type meaning base type or array type and
        // dimension)
        if (type == null) {
          type = symbol;
        } else {
          if (compareTypes((STType) type, (STType) symbol, true) == null) {
            // Check both ways because we don't now the order of the array values
            Symbol newSymbol = compareTypes((STType) symbol, (STType) type, true);
            if (newSymbol == null) {
              logError("Array values are not of the same type!", argsNode.getLine());
              return null;
            }
            type = newSymbol;
          }
        }
      }
      int dim = 1;
      // if we have an array containing arrays with dimension x we have to return a new array type
      // with dimension x + 1
      if (type instanceof Array array) {
        dim += array.getDimension();
      }
      return type != null ? new Array(dim, type.getType()) : null;
    }
    // return if given arguments are not an array
    return null;
  }

  /**
   * This function validates the param type
   *
   * @param paramNode node that represents the 'param' parser rule
   * @return the type of the param or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ParamNode paramNode) {
    paramNode.setCurrentScope(this.currentScope);
    Symbol type = paramNode.getTypeNode().accept(this);
    if (type == null) {
      // Error is handled in typeNode
      return null;
    }
    if (paramNode.getIdentifier().isArray()) {
      // Create new Array type with dimension if param is declared as array
      return new Array(paramNode.getIdentifier().getDimension(), (STType) type);
    }
    return type;
  }

  /**
   * This function accepts all children of the given block node
   *
   * @param blockNode node that represents the 'block' parser rule
   * @return type void
   */
  @Override
  public Symbol visit(BlockNode blockNode) {
    blockNode.setCurrentScope(this.currentScope);
    for (var child : blockNode.getChildren()) {
      if (child instanceof BlockNode) {
        this.currentScope = new Scope(this.currentScope);
        child.accept(this);
        this.currentScope = this.currentScope.getParent();
      } else {
        child.accept(this);
      }
    }
    return this.builtInVoid;
  }

  /**
   * This function validates the type of the given {@code TypeNode}.
   *
   * @param typeNode node that represents the 'type' parser rule
   * @return the type or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(TypeNode typeNode) {
    typeNode.setCurrentScope(this.currentScope);
    Symbol type = currentScope.resolve(typeNode.getClassName());
    if (type instanceof STType) {
      return type;
    }
    // Check if found function is a Constructor of desired Class
    if (type instanceof Function function && function.getScope() instanceof STClass stClass) {
      return function.getName().equals(stClass.getName()) ? stClass : null;
    }
    logError("Invalid Type '" + typeNode.getClassName() + "'!", typeNode.getLine());
    return null;
  }

  /**
   * This function checks if there is a function with params that match the given arguments.
   *
   * @param fncallNode node that represents the 'fncall' parser rule
   * @return the return type of this function or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(FncallNode fncallNode) {
    fncallNode.setCurrentScope(this.currentScope);
    Scope currentObjScope = this.currentScope;
    // Changes the current scope to the scope where this function is supposed to be defined
    if (!validateObjectCalls(fncallNode.getObjcalls(), fncallNode.hasThis())) return null;
    // Find all symbols  with the called function name and search for a function with fitting params
    List<Symbol> symbols = this.currentScope.resolveAll(fncallNode.getIdNode().getId());
    this.currentScope = currentObjScope;
    for (var member : symbols) {
      // If a non function is found there can't be a function with this name due to checks in
      // function declaration
      if (!(member instanceof Function function)) {
        logError(
            "Function " + fncallNode.getIdNode().getId() + " could not be resolved!",
            fncallNode.getLine());
        return null;
      }
      // Check if param sizes match
      int size =
          (fncallNode.getArgsNode() != null ? fncallNode.getArgsNode().getArguments().size() : 0);
      List<ExprNode> arguments =
          fncallNode.getArgsNode() != null
              ? fncallNode.getArgsNode().getArguments()
              : new ArrayList<>();
      if (size == function.getParams().size()) {
        // Check if param and argument types match
        if (compareListsOfTypes(function.getParams(), arguments, true)) {
          // Save where the found function was declared
          fncallNode.setFndeclNode(function.getFndeclNode());
          return function.getType();
        }
      }
    }
    logError("Couldn't find function matching given parameters!", fncallNode.getLine());
    return null;
  }

  /**
   * This function validates a called object member and returns its type.
   *
   * @param objmemNode node that represents the 'objmem' parser rule
   * @return the type of the found object or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(OBJMEMNode objmemNode) {
    objmemNode.setCurrentScope(this.currentScope);
    Scope currentObjScope = this.currentScope;
    if (!validateObjectCalls(objmemNode.getObjcalls(), objmemNode.hasThis())) return null;
    // Get Type if an array is accessed
    if (objmemNode.hasArraccNode()) {
      Symbol type = objmemNode.getArraccNode().accept(this);
      this.currentScope = currentObjScope;
      return type;
    }
    Symbol symbol = this.currentScope.resolve(objmemNode.getIdNode().getId());
    this.currentScope = currentObjScope;
    // Throw error if found symbol is not an object
    if (!(symbol instanceof Variable variable)) {
      logError(
          "Variable " + objmemNode.getIdNode().getId() + " could not be resolved!",
          objmemNode.getLine());
      return null;
    }
    return variable.getType();
  }

  /**
   * This function handles and validates the 'operator=' function of classes.
   *
   * @param operatorNode node that represents the 'operator' parser rule
   * @return
   */
  @Override
  public Symbol visit(OperatorNode operatorNode) {
    operatorNode.setCurrentScope(this.currentScope);
    // operator can only be defined in class (grammar) therefor typecast without 'instanceof' or
    // 'try...catch'
    STClass stClass = (STClass) this.currentScope;
    // Check if operator returns the class that it's defined in
    if (!stClass.getName().equals(operatorNode.getReturnType().getId())) {
      logError("Operator function has to return Object of class!", operatorNode.getLine());
      return null;
    }
    // Check if param type is not the same as the class that operator is defined in
    if (!stClass.getName().equals(operatorNode.getParamType().getId())) {
      logError(
          "Operator function parameter type has to be Object of class!", operatorNode.getLine());
      return null;
    }
    // Create new function for 'operator='
    List<ParamNode> paramList = new ArrayList<>();
    ParamNode paramNode =
        new ParamNode(
            new TypeNode(stClass.getName(), operatorNode.getLine()),
            new IdentifierNode(operatorNode.getParamName(), true, operatorNode.getLine()),
            operatorNode.getLine());
    paramList.add(paramNode);
    Function operator = new Function("operator=", stClass, this.currentScope, paramList, false);
    this.currentScope.bind(operator);
    // Add parameter to function scope
    defineParams(operator, paramList);
    // handle function block
    this.currentScope = operator;
    for (var child : operatorNode.getChildren()) {
      child.accept(this);
    }
    this.currentScope = currentScope.getParent();
    return null;
  }

  /**
   * Checks if multiplication is called with two integers
   *
   * @param mulNode node that represents the 'MUL' parser rule
   * @return type int or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(MULNode mulNode) {
    mulNode.setCurrentScope(this.currentScope);
    Symbol e1Type = mulNode.getE1().accept(this);
    Symbol e2Type = mulNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, mulNode) ? this.builtInInt : null);
  }

  /**
   * Checks if division is called with two integers
   *
   * @param divNode node that represents the 'DIV' parser rule
   * @return type int or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(DIVNode divNode) {
    divNode.setCurrentScope(this.currentScope);
    Symbol e1Type = divNode.getE1().accept(this);
    Symbol e2Type = divNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, divNode) ? this.builtInInt : null);
  }

  /**
   * Checks if addition is called with two integers
   *
   * @param addNode node that represents the 'ADD' parser rule
   * @return type int or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ADDNode addNode) {
    addNode.setCurrentScope(this.currentScope);
    Symbol e1Type = addNode.getE1().accept(this);
    Symbol e2Type = addNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, addNode) ? this.builtInInt : null);
  }

  /**
   * Checks if subtraction is called with two integers
   *
   * @param subNode node that represents the 'SUB' parser rule
   * @return type int or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(SUBNode subNode) {
    subNode.setCurrentScope(this.currentScope);
    Symbol e1Type = subNode.getE1().accept(this);
    Symbol e2Type = subNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, subNode) ? this.builtInInt : null);
  }

  /**
   * Checks if {@code ==} is called with two built in types
   *
   * @param equalsNode node that represents the 'EQUALS' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(EQUALSNode equalsNode) {
    equalsNode.setCurrentScope(this.currentScope);
    Symbol e1Type = equalsNode.getE1().accept(this);
    Symbol e2Type = equalsNode.getE2().accept(this);
    return (checkComparison(e1Type, e2Type, equalsNode) ? this.builtInBool : null);
  }

  /**
   * Checks if {@code !=} is called with two built in types
   *
   * @param neaqualsNode node that represents the 'NEAQUALS' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(NEAQUALSNode neaqualsNode) {
    neaqualsNode.setCurrentScope(this.currentScope);
    Symbol e1Type = neaqualsNode.getE1().accept(this);
    Symbol e2Type = neaqualsNode.getE2().accept(this);
    return (checkComparison(e1Type, e2Type, neaqualsNode) ? this.builtInBool : null);
  }

  /**
   * Checks if {@code >} is called with two integers https://www.youtube.com/watch?v=YAgJ9XugGBo
   *
   * @param greaterNode node that represents the 'GREATER' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(GREATERNode greaterNode) {
    greaterNode.setCurrentScope(this.currentScope);
    Symbol e1Type = greaterNode.getE1().accept(this);
    Symbol e2Type = greaterNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, greaterNode) ? this.builtInBool : null);
  }

  /**
   * Checks if {@code <} is called with two integers
   *
   * @param lessNode node that represents the 'LESS' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(LESSNode lessNode) {
    lessNode.setCurrentScope(this.currentScope);
    Symbol e1Type = lessNode.getE1().accept(this);
    Symbol e2Type = lessNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, lessNode) ? this.builtInBool : null);
  }

  /**
   * Checks if {@code >=} is called with two integers
   *
   * @param geaqualsNode node that represents the 'GEAQUALS' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(GEAQUALSNode geaqualsNode) {
    geaqualsNode.setCurrentScope(this.currentScope);
    Symbol e1Type = geaqualsNode.getE1().accept(this);
    Symbol e2Type = geaqualsNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, geaqualsNode) ? this.builtInBool : null);
  }

  /**
   * Checks if {@code <=} is called with two integers
   *
   * @param leaqualsNode node that represents the 'LEAQUALS' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(LEAQUALSNode leaqualsNode) {
    leaqualsNode.setCurrentScope(this.currentScope);
    Symbol e1Type = leaqualsNode.getE1().accept(this);
    Symbol e2Type = leaqualsNode.getE2().accept(this);
    return (checkBothIntegers(e1Type, e2Type, leaqualsNode) ? this.builtInBool : null);
  }

  /**
   * Validates that both sides of an {@code &&} connector are of type bool
   *
   * @param andNode node that represents the 'AND' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ANDNode andNode) {
    andNode.setCurrentScope(this.currentScope);
    Symbol e1Type = andNode.getE1().accept(this);
    Symbol e2Type = andNode.getE2().accept(this);
    if (!this.builtInBool.equals(e1Type) || !this.builtInBool.equals(e2Type)) {
      logError(
          "Cannot use types "
              + e1Type.getName()
              + " and "
              + e2Type.getName()
              + " with '&&' operator",
          andNode.getLine());
      return null;
    }
    return this.builtInBool;
  }

  /**
   * Validates that both sides of an {@code ||} connector are of type bool
   *
   * @param orNode node that represents the 'OR' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ORNode orNode) {
    orNode.setCurrentScope(this.currentScope);
    Symbol e1Type = orNode.getE1().accept(this);
    Symbol e2Type = orNode.getE2().accept(this);
    if (!this.builtInBool.equals(e1Type) || !this.builtInBool.equals(e2Type)) {
      logError(
          "Cannot use types "
              + e1Type.getName()
              + " and "
              + e2Type.getName()
              + " with '||' operator",
          orNode.getLine());
      return null;
    }
    return this.builtInBool;
  }

  /**
   * Validates Array access definition and dimension.
   *
   * @param arraccNode node that represents the 'ARRACC' parser rule
   * @return the type of the value from the accessed array or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ARRACCNode arraccNode) {
    arraccNode.setCurrentScope(this.currentScope);
    Symbol symbol = currentScope.resolve(arraccNode.getIdNode().getId());
    // Check if any symbol was found
    if (symbol == null) {
      logError("Couldn't resolve variable " + arraccNode.getIdNode().getId(), arraccNode.getLine());
      return null;
    }
    // Check if a variable was found
    if (!(symbol instanceof Variable variable)) {
      logError("Couldn't resolve variable " + arraccNode.getIdNode().getId(), arraccNode.getLine());
      return null;
    }
    // Check if found variable is an array
    if (!(variable.getType() instanceof Array array)) {
      logError(
          "Cannot access index of non array variable " + arraccNode.getIdNode().getId(),
          arraccNode.getLine());
      return null;
    }
    // Check if array access dimension doesn't fit variable array dimension
    if (arraccNode.getDimension() > array.getDimension()) {
      logError("Dimension mismatch in " + arraccNode.getIdNode().getId(), arraccNode.getLine());
      return null;
    }
    // Check if array is accessed with int
    for (var expr : arraccNode.getExprNodes()) {
      if (!this.builtInInt.equals(expr.accept(this))) {
        logError(
            "Cannot access index of array with non int type expression!", arraccNode.getLine());
        return null;
      }
    }
    // return type or array with fitting dimension
    int newDimension = array.getDimension() - arraccNode.getDimension();
    return newDimension == 0 ? array.getType() : new Array(newDimension, array.getType());
  }

  /**
   * Checks if {@code !} operator is used with bool
   *
   * @param notNode node that represents the 'not' parser rule
   * @return type bool or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(NOTNode notNode) {
    notNode.setCurrentScope(this.currentScope);
    Symbol expressionType = notNode.getExpr().accept(this);
    if (!this.builtInBool.equals(expressionType)) {
      logError("Cannot negate variable of type " + expressionType.getName(), notNode.getLine());
      return null;
    }
    return this.builtInBool;
  }

  /**
   * Returns the builtin for the corresponding type
   *
   * @param boolNode node that represents the 'BOOL' parser rule
   * @return type bool
   */
  @Override
  public Symbol visit(BOOLNode boolNode) {
    boolNode.setCurrentScope(this.currentScope);
    return this.builtInBool;
  }

  /**
   * Returns the builtin for the corresponding type
   *
   * @param charNode node that represents the 'CHAR' parser rule
   * @return type char
   */
  @Override
  public Symbol visit(CHARNode charNode) {
    charNode.setCurrentScope(this.currentScope);
    return this.builtInChar;
  }

  /**
   * Returns the builtin for the corresponding type
   *
   * @param intNode node that represents the 'INT' parser rule
   * @return type int
   */
  @Override
  public Symbol visit(INTNode intNode) {
    intNode.setCurrentScope(this.currentScope);
    return this.builtInInt;
  }

  /**
   * Returns the type of the id
   *
   * @param idNode node that represents the 'ID' parser rule
   * @return type or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(IDNode idNode) {
    idNode.setCurrentScope(this.currentScope);
    Symbol var = currentScope.resolve(idNode.getId());
    if (var == null) {
      logError("Couldn't find variable " + idNode.getId(), idNode.getLine());
      return null;
    }
    return var.getType();
  }

  /**
   * This function checks if this constructor can be declared and does so if that's the case
   *
   * @param constructorNode node that represents the 'constructor' parser rule
   * @return the class of the constructor or {@code null} if an error occurred
   */
  @Override
  public Symbol visit(ConstructorNode constructorNode) {
    constructorNode.setCurrentScope(this.currentScope);
    // Check if the constructor is declared in a class
    if (this.currentScope instanceof STClass stClass) {
      if (!stClass.getName().equals(constructorNode.getIdNode().getId())) {
        logError(
            "Constructor name '"
                + constructorNode.getIdNode().getId()
                + "' does not match class name!",
            constructorNode.getLine());
        return null;
      }
    } else {
      logError("Cannot define Constructor outside of class", constructorNode.getLine());
      return null;
    }
    // Check if constructor has been defined with this parameters
    List<Symbol> symbols = stClass.resolveAllMember(constructorNode.getIdNode().getId());
    for (var member : symbols) {
      if (member instanceof Function foundConstructor) {
        // Found Constructor with same param length
        if (constructorNode.getParams().size() == foundConstructor.getParams().size()) {
          if (compareListsOfTypes(
              constructorNode.getParams(), foundConstructor.getParams(), false)) {
            logError(
                "Constructor "
                    + constructorNode.getIdNode().getId()
                    + " can't be overloaded with same parameters!",
                constructorNode.getLine());
            return null;
          }
        }
      }
    }
    // Defining a new constructor
    Function newConstructor =
        new Function(
            constructorNode.getIdNode().getId(),
            stClass,
            this.currentScope,
            constructorNode.getParams(),
            false);
    newConstructor.setHasBeenDefined(true);
    newConstructor.setConstructorNode(constructorNode);
    currentScope.bind(newConstructor);
    // Adding the params to the new constructor
    defineParams(newConstructor, constructorNode.getParams());
    // Handling the body of the constructor
    this.currentScope = newConstructor;
    constructorNode.getBlock().accept(this);
    this.currentScope = this.currentScope.getParent();
    return stClass;
  }

  /**
   * Iterates through the given list and checks if its members can be called
   *
   * @param objectCalls a list of object call nodes that will be verified
   * @param hasThis indicates if 'this.' is called at the start of object calls
   * @return if the given object calls are valid
   */
  private boolean validateObjectCalls(List<ObjcallNode> objectCalls, boolean hasThis) {
    Scope currentObjScope = this.currentScope;
    // checks if currently in class and changes scope to this class
    if (hasThis) {
      Symbol foundClass = findMyClass(currentObjScope);
      if (foundClass == null) {
        logError("Cannot use 'this' outside of class", objectCalls.getFirst().getLine());
        return false;
      }
      currentObjScope = (Scope) foundClass;
    }
    // Accepts each object call and checks if an object and not a builtin type is being accessed
    for (var child : objectCalls) {
      Symbol obj = child.accept(this);
      if (obj == null) {
        logError(
            child.getIdNode().getId() + " could not be found!", objectCalls.getFirst().getLine());
        this.currentScope = currentObjScope;
        return false;
      }
      if (obj.getType() instanceof STClass type) {
        this.currentScope = type;
      } else {
        // Builtin types don't have members so they can't be accessed
        logError(
            "Variable " + obj.getName() + " is from BuiltIn type and can't have any members!",
            objectCalls.getFirst().getLine());
        this.currentScope = currentObjScope;
        return false;
      }
    }
    return true;
  }

  /**
   * Compares two types and returns the type if they are of the same type.
   *
   * @param type1 the first type
   * @param type2 the second type
   * @param usePoly indicates if this function should check for inheritance
   * @return the common type if they are the same or {@code null} if not
   */
  private Symbol compareTypes(STType type1, STType type2, boolean usePoly) {
    STType returnType = type1;
    if (type1 instanceof Array varArray) {
      if (type2 instanceof Array exprArray) {
        if (varArray.getDimension() != exprArray.getDimension()) {
          // They are not the same when both are arrays but with a different dimension
          return null;
        }
        type1 = varArray.getType();
        type2 = exprArray.getType();
      } else {
        return null;
      }
    }
    // Check for polymorphie
    if (usePoly && type1 instanceof STClass stClass1 && type2 instanceof STClass stClass2) {
      while (stClass2 != null) {
        if (stClass1.equals(stClass2)) {
          return returnType;
        }
        stClass2 = (STClass) stClass2.getInheritedScope();
      }
      return null;
      // They can only be builtin types at this point and can simply be compared
    } else if (!type1.equals(type2)) {
      return null;
    }
    return returnType;
  }

  /**
   * Checks if the given id string is free or already defined in some way
   *
   * @param id the id name that should be checked
   * @param line the line-number, where the id is located
   * @return if the string is allowed to be a new id
   */
  private boolean isAllowedVarID(String id, int line) {
    Symbol symbolInScope = currentScope.resolveMember(id);
    if (symbolInScope != null) {
      logError("Variable " + id + " has already been declared in this scope!", line);
      return false;
    }
    Symbol symbolInAllScopes = currentScope.resolve(id);
    // If the found symbol is a scope it has to be a class or function not a variable
    if (symbolInAllScopes instanceof Scope) {
      logError(
          "Cannot use name of existing class or function '" + id + "' as variable name!", line);
      return false;
    }
    return true;
  }

  /**
   * Searches all constructors of a given class and returns them in a list of Symbols
   *
   * @param name the name of the class whose constructors will be returned
   * @param line the line, where the class is used
   * @return a list of found constructors
   */
  private List<Symbol> getAllConstructors(String name, int line) {
    Symbol symbol = currentScope.resolve(name);
    // Check if anything could be found
    if (symbol == null) {
      logError("Could not find class '" + name + "'!", line);
      return new ArrayList<>();
    }
    // If a possible constructor was found
    if (symbol instanceof Function function) {
      if (function.getScope() instanceof STClass stClass) {
        if (stClass.getName().equals(function.getName())) {
          // The found function was a constructor of a class
          return stClass.resolveAllMember(stClass.getName());
        } else {
          logError("'" + name + "' is not a class!", line);
          return new ArrayList<>();
        }
      } else {
        logError("'" + name + "' is not a class!", line);
        return new ArrayList<>();
      }
    }
    // If a class was found
    if (symbol instanceof STClass stClass) {
      return stClass.resolveAllMember(stClass.getName());
    } else {
      // If the found symbol was neither a function nor a class it has to be a variable
      logError("Cannot call variable '" + symbol.getName() + "' as constructor!", line);
      return new ArrayList<>();
    }
  }

  /**
   * Compares the types of two lists by index and returns if they all equal
   *
   * @param list1 the first list
   * @param list2 the second list
   * @param usePoly indicates if the second list is allowed to have subclasses of classes in the
   *     first list
   * @return {@code true } if the types in the list match
   */
  private boolean compareListsOfTypes(
      List<? extends ASTNode> list1, List<? extends ASTNode> list2, boolean usePoly) {
    for (int i = 0; i < list1.size(); i++) {
      // get and accept two nodes at the same index of both lists
      ASTNode node1 = list1.get(i);
      ASTNode node2 = list2.get(i);
      Symbol type1 = node1.accept(this);
      Symbol type2 = node2.accept(this);
      if (type1 == null || type2 == null) {
        // Error will be handled in visit expr
        return false;
      }
      if (compareTypes((STType) type1, (STType) type2, usePoly) == null) {
        return false;
      }
    }
    return true;
  }

  /**
   * This function trys to declare a new function. It realizes overriding, overloading and checks
   * for virtual when overriding
   *
   * @param fndeclNode a node that represents the 'fndecl' parser rule
   * @param fndefNode is not null if the declaration is part of a definition
   * @return the new function or {@code null} if an error occurred
   */
  private Function checkFunctionCanBeDefined(FndeclNode fndeclNode, FndefNode fndefNode) {
    // Checking valid return type
    Symbol type = fndeclNode.getReturntype().accept(this);
    if (type == null) {
      // Error will be handled in TypeNode
      return null;
    }
    // Check valid function name
    Symbol symbol = this.currentScope.resolve(fndeclNode.getIdNode().getId());
    if (symbol instanceof STClass stClass) {
      logError(
          "Function '" + stClass.getName() + "' cannot have the same name as a class",
          fndeclNode.getLine());
    }
    // Check if function can be declared with this params
    List<Symbol> memberSymbols = this.currentScope.resolveAll(fndeclNode.getIdNode().getId());
    boolean didOverride = false;
    for (var member : memberSymbols) {
      if (!(member instanceof Function foundFunction)) {
        // A not function with this name was found
        logError(
            fndeclNode.getIdNode().getId() + " conflicts with a previous declaration!",
            fndeclNode.getLine());
        return null;
      }
      if (fndeclNode.getParams().size() == foundFunction.getParams().size()) {
        if (compareListsOfTypes(fndeclNode.getParams(), foundFunction.getParams(), false)) {
          if (this.currentScope.equals(foundFunction.getScope())) {
            // Found function is in the same class
            if (fndefNode == null || foundFunction.hasBeenDefined()) {
              logError(
                  "Cannot override function '" + foundFunction.getName() + "' in same class!",
                  fndefNode.getLine());
              return null;
            }
            // Declared function is defined in given fndefNode
            foundFunction.getFndeclNode().setFndefNode(fndefNode);
            return foundFunction;
          } else if (!foundFunction.isVirtual()) {
            // found function is in parent class but not virtual
            logError(
                "Cannot override non virtual function '" + foundFunction.getName() + "'!",
                foundFunction.getFndeclNode().getLine());
            return null;
          } else {
            // found function is in parent class and virtual
            foundFunction.getFndeclNode().addOverridingFndecl(fndeclNode);
            didOverride = true;
          }
        }
      }
    }
    // Check if this function needs to override but doesn't
    if (!didOverride && fndeclNode.isOverride()) {
      logError(
          "There is no matching function '" + fndeclNode.getIdNode().getId() + "' to override!",
          fndeclNode.getLine());
      return null;
    }
    // create new function
    Function newFunction =
        new Function(
            fndeclNode.getIdNode().getId(),
            (STType) type,
            this.currentScope,
            fndeclNode.getParams(),
            (didOverride || fndeclNode.isVirtual()));
    // define all params in function scope
    defineParams(newFunction, fndeclNode.getParams());
    newFunction.setFndeclNode(fndeclNode);
    return newFunction;
  }

  /**
   * Checks if the given scope is somewhere inside a class scope
   *
   * @param scope the scope in which the search should start
   * @return the found class or {@code null} if nothing was found
   */
  private Symbol findMyClass(Scope scope) {
    while (scope != null) {
      // classes are scopes
      if (scope instanceof STClass stClass) {
        return stClass;
      }
      scope = scope.getParent();
    }
    return null;
  }

  /**
   * Checks if the given scope is somewhere inside a function scope
   *
   * @param scope the scope in which the search should start
   * @return the found function or {@code null} if nothing was found
   */
  private Function findMyFunction(Scope scope) {
    while (scope.getParent() != null) {
      // functions are scopes
      if (scope instanceof Function function) {
        return function;
      }
      scope = scope.getParent();
    }
    return null;
  }

  /**
   * Checks if the given type is a bool and accepts the lock if that is the case
   *
   * @param conditionType the Symbol containing the type of this condition
   * @param block the block that will be handled if the type is a bool
   * @return {@code true} if the condition type is valid
   */
  private boolean handleConditionBlock(Symbol conditionType, BlockNode block) {
    if (!this.builtInBool.equals(conditionType)) {
      logError(
          "Type of variable '"
              + (conditionType == null ? "null" : conditionType.getName())
              + "' is not a valid condition type!",
          block.getLine());
      return false;
    }
    this.currentScope = new Scope(currentScope);
    block.accept(this);
    this.currentScope = this.currentScope.getParent();
    return true;
  }

  /**
   * Binds all given parameters to the given function
   *
   * @param function the function that the params will be bound in
   * @param paramNodes the params to be bound
   */
  private void defineParams(Function function, List<ParamNode> paramNodes) {
    for (var child : paramNodes) {
      Symbol paramType = child.accept(this);
      if (paramType != null) {
        if (child.getIdentifier().isArray()) {
          paramType = new Array(child.getIdentifier().getDimension(), (STType) paramType);
        }
        function.bind(new Variable(child.getIdentifier().getIdNode().getId(), (STType) paramType));
      } else {
        // Error will be handled in TypeNode
        return;
      }
    }
  }

  /**
   * Checks if both given types are built in types
   *
   * @param type1 the first type to compare
   * @param type2 the second type to compare
   * @param node an ASTNode which is used for the line if an error was found
   * @return {@code true} if both types are built in types
   */
  private boolean checkComparison(Symbol type1, Symbol type2, ASTNode node) {
    if (!(type1 instanceof BuiltIn) || !(type2 instanceof BuiltIn)) {
      logError(
          "Cannot compare builtin types " + type1.getName() + " and " + type2.getName() + "!",
          node.getLine());
      return false;
    }
    return true;
  }

  /**
   * Checks if both given types are integers
   *
   * @param type1 the first type to compare
   * @param type2 the second type to compare
   * @param node ASTNode which is used for the line, if an error was found
   * @return {@code true} if both types are integers
   */
  private boolean checkBothIntegers(Symbol type1, Symbol type2, ASTNode node) {
    if (!this.builtInInt.equals(type1) || !this.builtInInt.equals(type2)) {
      logError(
          "Cannot use types "
              + (type1 == null ? "null" : type1.getName())
              + " and "
              + (type2 == null ? "null" : type2.getName())
              + " in this context!",
          node.getLine());
      return false;
    }
    return true;
  }

  private void logError(String msg, int line) {
    errCounter++;
    System.err.println("Error; [" + line + "]: " + msg);
  }
}
