package AST;

public class ConstructorCallNode extends ASTNode {
  // Fields
  private IDNode typeName;
  private IDNode instanceName;
  private ArgsNode args;

  // Constructor
  public ConstructorCallNode(IDNode typeName, IDNode instanceName, ArgsNode args) {
    super();
    this.typeName = typeName;
    this.instanceName = instanceName;
    this.args = args;

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

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
