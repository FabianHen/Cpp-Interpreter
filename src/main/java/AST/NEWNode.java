package AST;

public class NEWNode extends ExprNode {
  // Fields
  private IDNode idNode;
  private ArgsNode args;

  // Constructor
  public NEWNode(IDNode idNode, ArgsNode args) {
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

  public ArgsNode getArgsNode() {
    return args;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
