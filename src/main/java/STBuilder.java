import AST.*;
import SymbolTable.*;
import java.util.ArrayList;

public class STBuilder implements ASTVisitor<Symbol> {
  private Scope currentScope;

  @Override
  public Symbol visit(ProgramNode programNode) {
    this.currentScope = new Scope(null);
    BuiltIn builtInVoid = new BuiltIn("void");
    this.currentScope.bind(new BuiltIn("int"));
    this.currentScope.bind(new BuiltIn("char"));
    this.currentScope.bind(new BuiltIn("bool"));
    this.currentScope.bind(builtInVoid);
    this.currentScope.bind(new Function("print_int", builtInVoid, null, new ArrayList<>()));
    this.currentScope.bind(new Function("print_char", builtInVoid, null, new ArrayList<>()));
    this.currentScope.bind(new Function("print_bool", builtInVoid, null, new ArrayList<>()));

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
    if (!(symbol instanceof Variable)) {
      System.err.println(symbol.getName() + " is not a variable!");
      this.currentScope = currentObjScope;
      return null;
    }
    Variable var = (Variable) symbol;
    if (!bindingNode.getAssignop().equals("=")) {
      // Check symbol type (int)
      BuiltIn builtInInt = (BuiltIn) currentObjScope.resolve("int");
      if (!var.getType().equals(builtInInt)) {
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
    if (type == null || !(type instanceof STType)) {
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
      if (!(symbol instanceof Function)) {
        System.err.println(
            fndeclNode.getIdNode().getId() + " conflicts with a previous declaration!");
        return null;
      }
      Function foundFunction = (Function) symbol;
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
    if (type == null || !(type instanceof STType)) {
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
          }
          else {
              return null;
          }
      }
    return newFunction;
  }

  @Override
  public Symbol visit(ReturnNode returnNode) {
    return null;
  }

  @Override
  public Symbol visit(FndefNode fndefNode) {
    return null;
  }

  @Override
  public Symbol visit(OverrideFndefNode overrideFndefNode) {
    return null;
  }

  @Override
  public Symbol visit(PrintNode printNode) {
    return null;
  }

  @Override
  public Symbol visit(WhileNode whileNode) {
    return null;
  }

  @Override
  public Symbol visit(IfNode ifNode) {
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
    return null;
  }

  @Override
  public Symbol visit(IncDecNode incDecNode) {
    return null;
  }

  @Override
  public Symbol visit(ArgsNode argsNode) {
    return null;
  }

  @Override
  public Symbol visit(ParamNode paramNode) {
      Symbol type = this.currentScope.resolve(paramNode.getTypeNode().getClassName());
      if (type == null || !(type instanceof STType)) {
        System.err.println("Invalid type of parameter " + paramNode.getIdentifier().getIdNode().getId() + "!");
          return null;
      }

    return new Variable(paramNode.getIdentifier().getIdNode().getId(), (STType) type);
  }

  @Override
  public Symbol visit(BlockNode blockNode) {
    return null;
  }

  @Override
  public Symbol visit(TypeNode typeNode) {
    return null;
  }

  @Override
  public Symbol visit(FNCALLNode fncallNode) {
    return null;
  }

  @Override
  public Symbol visit(OBJMEMNode objmemNode) {
    return null;
  }

  @Override
  public Symbol visit(NEWNode newNode) {
    return null;
  }

  @Override
  public Symbol visit(MULNode mulNode) {
    return null;
  }

  @Override
  public Symbol visit(DIVNode divNode) {
    return null;
  }

  @Override
  public Symbol visit(ADDNode addNode) {
    return null;
  }

  @Override
  public Symbol visit(SUBNode subNode) {
    return null;
  }

  @Override
  public Symbol visit(EQUALSNode equalsNode) {
    return null;
  }

  @Override
  public Symbol visit(NEAQUALSNode neequalsNode) {
    return null;
  }

  @Override
  public Symbol visit(GREATERNode greaterNode) {
    return null;
  }

  @Override
  public Symbol visit(LESSNode lessNode) {
    return null;
  }

  @Override
  public Symbol visit(GEAQUALSNode geaqualsNode) {
    return null;
  }

  @Override
  public Symbol visit(LEAQUALSNode leaqualsNode) {
    return null;
  }

  @Override
  public Symbol visit(ANDNode andNode) {
    return null;
  }

  @Override
  public Symbol visit(ORNode orNode) {
    return null;
  }

  @Override
  public Symbol visit(ARRACCNode arraccNode) {
    return null;
  }

  @Override
  public Symbol visit(OBJNode objNode) {
    return null;
  }

  @Override
  public Symbol visit(NOTNode notNode) {
    return null;
  }

  @Override
  public Symbol visit(BOOLNode boolNode) {
    return null;
  }

  @Override
  public Symbol visit(CHARNode charNode) {
    return null;
  }

  @Override
  public Symbol visit(INTNode intNode) {
    return null;
  }

  @Override
  public Symbol visit(IDNode idNode) {
    return null;
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
