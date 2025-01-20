package AST;

import SymbolTable.Function;

public class ConstructorCallNode extends ASTNode {
  // Fields
  private IDNode typeName;
  private IDNode instanceName;
  private ArgsNode args;
  private Function function;

  // Constructor
  public ConstructorCallNode(IDNode typeName, IDNode instanceName, ArgsNode args, int line) {
    super(line);
    this.typeName = typeName;
    this.instanceName = instanceName;
    this.args = args;
    this.function = null;

    addChild(typeName);
    addChild(instanceName);
    if (args == null) return;
    addChild(args);
  }

  // Getters and Setters
  public IDNode getTypename() {
    return this.typeName;
  }

  public IDNode getInstancename() {
    return this.instanceName;
  }

  public ArgsNode getArgsNode() {
    return this.args;
  }

  public Function getFunction() {
    return function;
  }

  public void setFunction(Function function) {
    this.function = function;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
