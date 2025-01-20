package AST;

public class VardeclNode extends ASTNode {
  // Fields
  private TypeNode type;
  private IdentifierNode identifier;
  private ExprNode expr;

  // Constructor
  public VardeclNode(TypeNode type, IdentifierNode identifier, ExprNode expr, int line) {
    super(line);
    this.type = type;
    this.identifier = identifier;
    this.expr = expr;

    addChild(type);
    addChild(identifier);
    if (expr != null) {
      addChild(expr);
    }
  }

  // Getters and Setters
  public TypeNode getType() {
    return this.type;
  }

  public IdentifierNode getIdentifier() {
    return this.identifier;
  }

  public boolean isArray() {
    return this.identifier.isArray();
  }

  public ExprNode getExpr() {
    return this.expr;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
