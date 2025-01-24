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

  /**
   * Visits the root ProgramNode and executes the program's main function.
   *
   * @param programNode the root node of the program's AST
   * @return always {@code null}
   */
  @Override
  public Object visit(ProgramNode programNode) {
    // Initialize a new root environment
    this.currentEnvironment = new Environment(null);
    // Process each child node of the ProgramNode
    for (var child : programNode.getChildren()) {
      child.accept(this);
    }
    // Retrieve all "main" functions from the current environment
    List<Func> mains = this.currentEnvironment.getFunctions("main");
    // Look for a valid "main" function with no parameters and INT return type
    for (var main : mains) {
      if (main.getParameters().isEmpty()
          && main.getFndefNode().getFndecl().getReturntype().getType() == Type.INT) {

        // Execute the "main" function
        main.call(this, new ArrayList<>());

        // Print success message and terminate the program
        System.out.println("\n |=-=-=-=-=-=-Successfully-executed-main-function!-=-=-=-=-=-=|");
        System.exit(0);
      }
    }
    // Return null as the result of the visit
    return null;
  }

  /**
   * Visits a ClassDefNode and processes its class definition.
   *
   * <p>Creates a new class object, sets its parent class if specified, and adds its members
   * (methods, constructors, operators) to the class. Finally, the class is defined in the current
   * environment.
   *
   * @param classDefNode the AST node representing the class definition
   * @return always {@code null}
   */
  @Override
  public Object visit(ClassDefNode classDefNode) {
    // Initialize the parent class, if specified
    Clazz parentClazz = null;
    if (classDefNode.getParentclass() != null) {
      parentClazz = (Clazz) currentEnvironment.getVariable(classDefNode.getParentclass().getId());
    }
    // Create a new class object with the parent class and the current node
    Clazz clazz = new Clazz(parentClazz, classDefNode);
    // Process each class member
    for (ASTNode member : classDefNode.getClassmembers()) {
      // Handle function definitions
      if (member instanceof FndefNode fndefNode) {
        Object value = fndefNode.accept(this);
        clazz.putFunc((Func) value);
        // Handle constructors
      } else if (member instanceof ConstructorNode constructorNode) {
        Object value = constructorNode.accept(this);
        clazz.putFunc((Func) value);
        // Handle operator definitions
      } else if (member instanceof OperatorNode operatorNode) {
        Object value = operatorNode.accept(this);
        clazz.putFunc((Func) value);
      }
    }
    // Define the class in the current environment
    currentEnvironment.defineVariable(classDefNode.getIdNode().getId(), clazz);
    // Return null as the result of the visit
    return null;
  }

  /**
   * Visits a BindingNode and processes variable bindings and assignments.
   *
   * <p>Handles assignments, array updates, and object operations based on the binding's type and
   * operator. Supports standard assignment, compound assignment (e.g., +=, -=), and operator
   * overloading for objects.
   *
   * @param bindingNode the AST node representing a variable binding
   * @return always {@code null}
   */
  @Override
  public Object visit(BindingNode bindingNode) {
    // Save the current environment for restoration later
    Environment currentObjEnv = this.currentEnvironment;
    // Determine the variable identifier
    String id =
        (bindingNode.getIdNode() == null
            ? bindingNode.getArraccNode().getIdNode().getId()
            : bindingNode.getIdNode().getId());
    // Evaluate the expression to get its value
    Value value = (Value) bindingNode.getExpr().accept(this);
    // Validate object calls if applicable
    validateObjectCalls(bindingNode.getObjcalls(), bindingNode.hasThis());
    // Handle standard assignment ("=")
    if (bindingNode.getAssignop().equals("=")) {
      Value variable = (Value) this.currentEnvironment.getVariable(id);
      // Handle array assignments
      if (bindingNode.getArraccNode() != null) {
        setArrayValue(
            variable.getValue(),
            value.getValue(),
            bindingNode.getArraccNode().getExprNodes(),
            0,
            bindingNode.getLine());
        this.currentEnvironment = currentObjEnv;
        return null;
      }
      // Handle object assignments
      if (variable.getValue() instanceof Instance instance) {
        Environment env = this.currentEnvironment;
        this.currentEnvironment = instance;
        // Check for operator overloading of "="
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
      // Handle simple variable assignment
      this.currentEnvironment.assignVariable(id, value.getValue());
      this.currentEnvironment = currentObjEnv;
      return null;
    }
    // Handle compound assignments (e.g., +=, -=)
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
        // Prevent division by zero
        if ((int) value.getValue() == 0) {
          logError("RUNTIME ERROR: Cannot divide by zero", bindingNode.getLine());
        }
        this.currentEnvironment.assignVariable(id, varValue / (int) value.getValue());
        break;
    }
    // Restore the original environment
    this.currentEnvironment = currentObjEnv;
    return null;
  }

  /**
   * Visits an ObjcallNode and processes object calls.
   *
   * @param objcallNode the AST node representing an object call
   * @return the result of evaluating the object call, or {@code null} if no valid call is found
   */
  @Override
  public Object visit(ObjcallNode objcallNode) {
    // Return the value of the variable if the node contains an identifier
    if (objcallNode.getIdNode() != null) {
      return currentEnvironment.getVariable(objcallNode.getIdNode().getId());
    }
    // Process a function call if the node contains a function call
    if (objcallNode.getFncallNode() != null) {
      return objcallNode.getFncallNode().accept(this);
    }
    // Process an array access if the node contains an array access
    if (objcallNode.getArracNode() != null) {
      return objcallNode.getArracNode().accept(this);
    }
    // Return null if the node does not match any of the above cases
    return null;
  }

  /**
   * Visits a VardeclNode and processes variable declarations.
   *
   * @param vardeclNode the AST node representing a variable declaration
   * @return always {@code null}
   */
  @Override
  public Object visit(VardeclNode vardeclNode) {
    // Extract the identifier for the variable
    String id = vardeclNode.getIdentifier().getIdNode().getId();
    Object array = null;
    Value value = null;
    // Handle uninitialized variables
    if (vardeclNode.getExpr() == null) {
      switch (vardeclNode.getType().getType()) {
        case INT:
          value = new Value(0); // Default value for integers
          break;
        case BOOL:
          value = new Value(false); // Default value for booleans
          break;
        case CHAR:
          value = new Value(' '); // Default value for characters
          break;
        case CUSTOM:
          Clazz clazz =
              (Clazz) this.currentEnvironment.getVariable(vardeclNode.getType().getClassName());
          value = new Value(clazz.call(this, new ArrayList<>())); // Create a custom class instance
          break;
      }
    } else {
      // Evaluate the expression for initialization
      if (vardeclNode.getIdentifier().isReference()) {
        value = (Value) vardeclNode.getExpr().accept(this); // Reference value
      } else {
        value = new Value(((Value) vardeclNode.getExpr().accept(this)).getValue()); // Copy value
      }
    }
    // Handle array declarations
    if (vardeclNode.isArray()) {
      array = createArray(vardeclNode.getIdentifier().getSizes(), 0);
      if (vardeclNode.getExpr() != null) {
        if (!compareArrays(array, value.getValue())) {
          logError("RUNTIME ERROR: Too many initializer values!", vardeclNode.getLine());
        }
      } else {
        value = new Value(array); // Initialize with empty array
      }
    } else if (vardeclNode.getType().getType() == Type.CUSTOM) {
      // Handle custom types with copy constructor
      if (vardeclNode.getExpr() != null && !vardeclNode.getIdentifier().isReference()) {
        Clazz clazz =
            (Clazz) this.currentEnvironment.getVariable(vardeclNode.getType().getClassName());
        Instance instance = new Instance(this, clazz, this.currentEnvironment);
        List<ExprNode> args = new ArrayList<>();
        args.add(vardeclNode.getExpr());
        instance.call(this, args);
        value = new Value(instance); // Create new instance with arguments
      }
    }
    // Define the variable in the current environment
    this.currentEnvironment.defineVariable(vardeclNode.getIdentifier().getIdNode().getId(), value);
    return null;
  }

  /**
   * Visits a ConstructorCallNode and processes constructor calls.
   *
   * @param constructorCallNode the AST node representing a constructor call
   * @return always {@code null}
   */
  @Override
  public Object visit(ConstructorCallNode constructorCallNode) {
    // Retrieve the class associated with the typename
    Clazz clazz =
        (Clazz) this.currentEnvironment.getVariable(constructorCallNode.getTypename().getId());
    // Create a new instance by calling the class constructor with arguments
    Instance instance =
        (Instance)
            clazz.call(this, constructorCallNode.getArgsNode().getArguments(), constructorCallNode);
    // Define the newly created instance in the current environment
    currentEnvironment.defineVariable(
        constructorCallNode.getInstancename().getId(), new Value(instance));
    return null;
  }

  /**
   * Visits a FndeclNode and processes function declarations.
   *
   * @param fndeclNode the AST node representing a function declaration
   * @return always {@code null}
   */
  @Override
  public Object visit(FndeclNode fndeclNode) {
    // Retrieve the function definition associated with the declaration
    FndefNode fndefNode = fndeclNode.getFndefNode();
    // Log an error if the function definition is missing
    if (fndefNode == null) {
      logError(
          "RUNTIME ERROR: Cannot find definition of declared function '"
              + fndeclNode.getIdNode().getId()
              + "'!",
          fndeclNode.getLine());
    }
    // Visit the function definition if it exists
    fndefNode.accept(this);
    return null;
  }

  /**
   * Visits a ReturnNode and processes return statements.
   *
   * @param returnNode the AST node representing a return statement
   * @return the evaluated return value or {@code null} if none is specified
   */
  @Override
  public Object visit(ReturnNode returnNode) {
    // Evaluate and return the expression if present
    if (returnNode.getExpr() != null) {
      return returnNode.getExpr().accept(this);
    }
    // Return the current instance if 'this' is specified
    if (returnNode.hasThis()) {
      return new Value(findMyInstance(this.currentEnvironment));
    }
    // Return null for empty return statements
    return null;
  }

  /**
   * Visits a FndefNode and processes function definitions.
   *
   * @param fndefNode the AST node representing a function definition
   * @return the created function object
   */
  @Override
  public Object visit(FndefNode fndefNode) {
    // Create a new function based on the function declaration
    Func func = new Func(fndefNode.getFndecl().getIdNode().getId(), this.currentEnvironment);
    // Set the function's definition node
    func.setFndefNode(fndefNode);
    // Define the function in the current environment
    currentEnvironment.defineFunction(func.getFuncName(), func);
    // Return the created function
    return func;
  }

  /**
   * Visits a PrintNode and processes print statements.
   *
   * @param printNode the AST node representing a print statement
   * @return always {@code null}
   */
  @Override
  public Object visit(PrintNode printNode) {
    // Evaluate the expression and print its value
    System.out.print(((Value) printNode.getExpr().accept(this)).getValue());
    return null;
  }

  /**
   * Visits a WhileNode and processes while loops.
   *
   * @param whileNode the AST node representing a while loop
   * @return the result of the loop block or {@code null} if none is returned
   */
  @Override
  public Object visit(WhileNode whileNode) {
    // Create a new environment for the loop body
    this.currentEnvironment = new Environment(this.currentEnvironment);
    // Continue looping as long as the condition is true
    while ((boolean) ((Value) whileNode.getCondition().accept(this)).getValue()) {
      // Execute the block inside the loop
      Object returnObj = whileNode.getBlock().accept(this);
      // Return if a value is produced in the block
      if (returnObj != null) {
        return returnObj;
      }
      // Clear the environment after each loop iteration
      currentEnvironment.clear();
    }
    // Restore the enclosing environment after the loop ends
    this.currentEnvironment = this.currentEnvironment.getEnclosing();
    return null;
  }

  /**
   * Visits an IfNode and processes if-else conditional statements.
   *
   * @param ifNode the AST node representing an if-else statement
   * @return the result of the block that gets executed or {@code null} if no block is executed
   */
  @Override
  public Object visit(IfNode ifNode) {
    // Evaluate the condition for the 'if' block
    if ((boolean) ((Value) ifNode.getCondition().accept(this)).getValue()) {
      return ifNode.getBlock().accept(this); // Execute the 'if' block if the condition is true
    }
    // Check each 'elif' block if present
    for (var elif : ifNode.getElseifblocks()) {
      if ((boolean) ((Value) elif.getCondition().accept(this)).getValue()) {
        return elif.accept(this); // Execute the 'elif' block if the condition is true
      }
    }
    // Execute the 'else' block if the condition is false and the block exists
    if (ifNode.getElseblock() != null) {
      return ifNode.getElseblock().accept(this);
    }
    return null; // Return null if no block is executed
  }

  /**
   * Visits an ElseifNode and processes the elseif block.
   *
   * @param elseifNode the AST node representing an elseif statement
   * @return the result of the elseif block execution
   */
  @Override
  public Object visit(ElseifNode elseifNode) {
    return elseifNode.getBlock().accept(this); // Execute the elseif block
  }

  /**
   * Visits an ElseNode and processes the else block.
   *
   * @param elseNode the AST node representing an else statement
   * @return the result of the else block execution
   */
  @Override
  public Object visit(ElseNode elseNode) {
    return elseNode.getBlock().accept(this); // Execute the else block
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

  /**
   * Visits an IncDecNode and processes increment or decrement operations.
   *
   * @param incDecNode the AST node representing an increment or decrement operation
   * @return the result of the operation (pre-increment/decrement or value)
   */
  @Override
  public Object visit(IncDecNode incDecNode) {
    int value = (int) ((Value) incDecNode.getIdNode().accept(this)).getValue();
    // Increment or decrement the value based on the operation type
    if (incDecNode.isInc()) {
      currentEnvironment.assignVariable(incDecNode.getIdNode().getId(), value + 1);
    } else {
      currentEnvironment.assignVariable(incDecNode.getIdNode().getId(), value - 1);
    }
    // Return the value if post-operation, or the updated variable if pre-operation
    if (incDecNode.isPre()) {
      return incDecNode.getIdNode().accept(this);
    }
    return new Value(value);
  }

  /**
   * Visits an ArgsNode and processes argument nodes for function calls.
   *
   * @param argsNode the AST node representing function call arguments
   * @return an array of values for the arguments or {@code null} if no array values
   */
  @Override
  public Object visit(ArgsNode argsNode) {
    if (!argsNode.isArrVals()) {
      return null; // Return null if there are no array values
    }
    Object array[] = new Object[argsNode.getArguments().size()];
    // Collect values of arguments into an array
    for (int i = 0; i < argsNode.getArguments().size(); i++) {
      array[i] = ((Value) argsNode.getArguments().get(i).accept(this)).getValue();
    }
    return new Value(array); // Return the array of argument values
  }

  /**
   * Visits a ParamNode and processes parameter nodes (no operation in this case).
   *
   * @param paramNode the AST node representing a parameter
   * @return always {@code null}
   */
  @Override
  public Object visit(ParamNode paramNode) {
    return null; // No specific processing for parameters
  }

  /**
   * Visits a BlockNode and processes the block of statements.
   *
   * @param blockNode the AST node representing a block of statements
   * @return the result of the executed block, or {@code null} if no value is returned
   */
  @Override
  public Object visit(BlockNode blockNode) {
    // Iterate over the children of the block
    for (var child : blockNode.getChildren()) {
      // Return if a return statement is encountered
      if (child instanceof ReturnNode returnNode) {
        return ((Value) returnNode.accept(this)).getValue();
      }
      // Handle conditional statements (if)
      if (child instanceof IfNode ifNode) {
        Object ifReturnObj = ifNode.accept(this);
        if (ifReturnObj != null) {
          return ifReturnObj;
        }
      }
      // Handle loops (while)
      else if (child instanceof WhileNode whileNode) {
        Object whileReturnObj = whileNode.accept(this);
        if (whileReturnObj != null) {
          return whileReturnObj;
        }
      }
      // Handle other statements
      else {
        child.accept(this);
      }
    }
    return null; // Return null if no return value is encountered
  }

  /**
   * Visits a TypeNode (no specific processing).
   *
   * @param typeNode the AST node representing a type declaration
   * @return always {@code null}
   */
  @Override
  public Object visit(TypeNode typeNode) {
    return null; // No specific processing for type nodes
  }

  /**
   * Visits a FncallNode and processes function calls.
   *
   * @param fncallNode the AST node representing a function call
   * @return the result of the function call wrapped in a Value or {@code null} if an error occurs
   */
  @Override
  public Object visit(FncallNode fncallNode) {
    Environment currentObjEnvironment = this.currentEnvironment; // Save the current environment
    validateObjectCalls(fncallNode.getObjcalls(), fncallNode.hasThis()); // Validate object calls
    List<Func> foundFunctions =
        currentEnvironment.getFunctions(fncallNode.getIdNode().getId()); // Find functions
    // Iterate over the found functions to find the matching one
    for (Func func : foundFunctions) {
      if (func.getFndefNode().equals(fncallNode.getFndeclNode().getFndefNode())) {
        // Get arguments for the function call
        List<ExprNode> arguments =
            fncallNode.getArgsNode() == null
                ? new ArrayList<>()
                : fncallNode.getArgsNode().getArguments();
        // If no overriding functions, call the function
        if (fncallNode.getFndeclNode().getOverridingFndecls().isEmpty()) {
          Object value = func.call(this, arguments);
          this.currentEnvironment = currentObjEnvironment; // Restore environment
          return new Value(value); // Return function result
        }
        // Handle overriding function declarations
        Object value = null;
        Clazz currentClass = ((Instance) this.currentEnvironment).getClazz(); // Get current class
        String funcClassName =
            ((STClass) fncallNode.getFndeclNode().getCurrentScope())
                .getName(); // Get function class name
        // Traverse the class hierarchy to find the correct function
        while (currentClass != null) {
          String className = currentClass.getClassDefNode().getIdNode().getId();
          if (className.equals(funcClassName)) {
            value = func.call(this, arguments);
            break;
          }
          // Check for overridden functions in the class hierarchy
          for (var fndecl : fncallNode.getFndeclNode().getOverridingFndecls()) {
            String currFuncClassName = ((STClass) fndecl.getCurrentScope()).getName();
            if (className.equals(currFuncClassName)) {
              for (var func2 : foundFunctions) {
                if (func2.getFndefNode().equals(fndecl.getFndefNode())) {
                  value = func2.call(this, arguments);
                  this.currentEnvironment = currentObjEnvironment; // Restore environment
                  return new Value(value); // Return function result
                }
              }
              break;
            }
          }
          currentClass = currentClass.getSuperClass(); // Move up the class hierarchy
        }
        this.currentEnvironment = currentObjEnvironment; // Restore environment
        return new Value(value); // Return the final value
      }
    }
    this.currentEnvironment = currentObjEnvironment; // Restore environment if function not found
    logError(
        "RUNTIME ERROR: Function was declared but never defined!",
        fncallNode.getLine()); // Log error if function is not defined
    return null; // Return null if an error occurs
  }

  @Override
  public Object visit(OBJMEMNode objmemNode) {
    // Save the current environment
    Environment env = this.currentEnvironment;
    // Validate object calls and check for 'this'
    validateObjectCalls(objmemNode.getObjcalls(), objmemNode.hasThis());
    // Get the value of the object member from the environment
    Object value = this.currentEnvironment.getVariable(objmemNode.getIdNode().getId());
    // Restore the environment
    this.currentEnvironment = env;
    // Return the value of the object member
    return value;
  }

  @Override
  public Object visit(OperatorNode operatorNode) {
    // Create a new function for the operator
    Func func = new Func("operator=", this.currentEnvironment);
    // Set the operator node for the function
    func.setOperatorNode(operatorNode);
    // Define the operator function in the environment
    this.currentEnvironment.defineFunction("operator=", func);
    // Return the function representing the operator
    return func;
  }

  /**
   * Multiplies two operands.
   *
   * @param mulNode The node representing the multiplication operation.
   * @return A new Value object representing the result of the multiplication.
   */
  @Override
  public Object visit(MULNode mulNode) {
    int e1 = (int) ((Value) mulNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) mulNode.getE2().accept(this)).getValue();
    return new Value(e1 * e2);
  }

  /**
   * Divides the first operand by the second operand, checking for division by zero.
   *
   * @param divNode The node representing the division operation.
   * @return A new Value object representing the result of the division.
   * @throws RuntimeException If an attempt is made to divide by zero.
   */
  @Override
  public Object visit(DIVNode divNode) {
    int e1 = (int) ((Value) divNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) divNode.getE2().accept(this)).getValue();
    if (e2 == 0) {
      logError("RUNTIME ERROR: Cannot divide by zero", divNode.getLine());
    }
    return new Value(e1 / e2);
  }

  /**
   * Adds two operands.
   *
   * @param addNode The node representing the addition operation.
   * @return A new Value object representing the result of the addition.
   */
  @Override
  public Object visit(ADDNode addNode) {
    int e1 = (int) ((Value) addNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) addNode.getE2().accept(this)).getValue();
    return new Value(e1 + e2);
  }

  /**
   * Subtracts the second operand from the first operand.
   *
   * @param subNode The node representing the subtraction operation.
   * @return A new Value object representing the result of the subtraction.
   */
  @Override
  public Object visit(SUBNode subNode) {
    int e1 = (int) ((Value) subNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) subNode.getE2().accept(this)).getValue();
    return new Value(e1 - e2);
  }

  /**
   * Checks if two operands are equal.
   *
   * @param equalsNode The node representing the equality check.
   * @return A new Value object representing the result of the equality comparison (true or false).
   */
  @Override
  public Object visit(EQUALSNode equalsNode) {
    Object obj1 = ((Value) equalsNode.getE1().accept(this)).getValue();
    Object obj2 = ((Value) equalsNode.getE2().accept(this)).getValue();
    return new Value(obj1 == obj2);
  }

  /**
   * Checks if two operands are not equal.
   *
   * @param neaqualsNode The node representing the inequality check.
   * @return A new Value object representing the result of the inequality comparison (true or
   *     false).
   */
  @Override
  public Object visit(NEAQUALSNode neaqualsNode) {
    Object obj1 = ((Value) neaqualsNode.getE1().accept(this)).getValue();
    Object obj2 = ((Value) neaqualsNode.getE2().accept(this)).getValue();
    return new Value(obj1 != obj2);
  }

  /**
   * Checks if the first operand is greater than the second operand.
   *
   * @param greaterNode The node representing the greater-than comparison.
   * @return A new Value object representing the result of the comparison (true or false).
   */
  @Override
  public Object visit(GREATERNode greaterNode) {
    int e1 = (int) ((Value) greaterNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) greaterNode.getE2().accept(this)).getValue();
    return new Value(e1 > e2);
  }

  /**
   * Checks if the first operand is less than the second operand.
   *
   * @param lessNode The node representing the less-than comparison.
   * @return A new Value object representing the result of the comparison (true or false).
   */
  @Override
  public Object visit(LESSNode lessNode) {
    int e1 = (int) ((Value) lessNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) lessNode.getE2().accept(this)).getValue();
    return new Value(e1 < e2);
  }

  /**
   * Checks if the first operand is greater than or equal to the second operand.
   *
   * @param geaqualsNode The node representing the greater-than-or-equal comparison.
   * @return A new Value object representing the result of the comparison (true or false).
   */
  @Override
  public Object visit(GEAQUALSNode geaqualsNode) {
    int e1 = (int) ((Value) geaqualsNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) geaqualsNode.getE2().accept(this)).getValue();
    return new Value(e1 >= e2);
  }

  /**
   * Checks if the first operand is less than or equal to the second operand.
   *
   * @param leaqualsNode The node representing the less-than-or-equal comparison.
   * @return A new Value object representing the result of the comparison (true or false).
   */
  @Override
  public Object visit(LEAQUALSNode leaqualsNode) {
    int e1 = (int) ((Value) leaqualsNode.getE1().accept(this)).getValue();
    int e2 = (int) ((Value) leaqualsNode.getE2().accept(this)).getValue();
    return new Value(e1 <= e2);
  }

  /**
   * Returns the logical AND of the two operands.
   *
   * @param andNode The node representing the AND operation.
   * @return A new Value object representing the result of the AND operation.
   */
  @Override
  public Object visit(ANDNode andNode) {
    boolean e1 = (boolean) ((Value) andNode.getE1().accept(this)).getValue();
    boolean e2 = (boolean) ((Value) andNode.getE2().accept(this)).getValue();
    return new Value(e1 && e2);
  }

  /**
   * Returns the logical OR of the two operands.
   *
   * @param orNode The node representing the OR operation.
   * @return A new Value object representing the result of the OR operation.
   */
  @Override
  public Object visit(ORNode orNode) {
    boolean e1 = (boolean) ((Value) orNode.getE1().accept(this)).getValue();
    boolean e2 = (boolean) ((Value) orNode.getE2().accept(this)).getValue();
    return new Value(e1 || e2);
  }

  @Override
  public Object visit(ARRACCNode arraccNode) {
    Value array = (Value) this.currentEnvironment.getVariable(arraccNode.getIdNode().getId());
    return new Value(
        getArrayValue(array.getValue(), arraccNode.getExprNodes(), 0, arraccNode.getLine()));
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
        logError("Cannot use 'this' outside of class", objectCalls.getFirst().getLine());
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
      logError("RUNTIME ERROR: Error while handling objcalls!", objectCalls.getFirst().getLine());
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

  private Object getArrayValue(Object array, List<ExprNode> indices, int dimensionIndex, int line) {
    int index = (int) ((Value) indices.get(dimensionIndex).accept(this)).getValue();
    Object arr[] = (Object[]) array;
    if (index >= arr.length) {
      logError("RUNTIME ERROR: Index out of bounds!", line);
    }

    if (dimensionIndex == indices.size() - 1) {
      return arr[index];
    } else {
      return getArrayValue(arr[index], indices, ++dimensionIndex, line);
    }
  }

  private void setArrayValue(
      Object array, Object value, List<ExprNode> indices, int dimensionIndex, int line) {
    int index = (int) ((Value) indices.get(dimensionIndex).accept(this)).getValue();
    Object arr[] = (Object[]) array;
    if (index >= arr.length) {
      logError("RUNTIME ERROR: Index out of bounds!", line);
    }
    if (dimensionIndex == indices.size() - 1) {
      arr[index] = value;
    } else {
      setArrayValue(arr[index], value, indices, ++dimensionIndex, line);
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

  private void logError(String msg, int line) {
    System.err.println("Error; [" + line + "]: " + msg);
    System.exit(0);
  }
}
