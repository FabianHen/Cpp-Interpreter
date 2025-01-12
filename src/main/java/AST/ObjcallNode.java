package AST;

public class ObjcallNode extends ASTNode {
  // Fields
  private IDNode idNode;
  private ArgsNode args;

  // Constructor
  public ObjcallNode(IDNode idNode, ArgsNode args) {
    super();
    this.idNode = idNode;
    this.args = args;

    addChild(idNode);
    if (args != null) {
      addChild(args);
    }
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
  }

  public ArgsNode getArgs() {
    return this.args;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
