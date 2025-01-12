package AST;

public class ParamNode extends ASTNode {
  // Fields
  private TypeNode type;
  private IdentifierNode identifier;

  // Constructor
  public ParamNode(TypeNode type, IdentifierNode identifier) {
    super();
    this.type = type;
    this.identifier = identifier;

    addChild(type);
    addChild(identifier);
  }

  // Getters and Setters
  public TypeNode getTypeNode() {
    return this.type;
  }

  public IdentifierNode getIdentifier() {
    return this.identifier;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
