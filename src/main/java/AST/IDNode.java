package AST;

public class IDNode extends ExprNode {
  // Fields
  private String id;

  // Constructor
  public IDNode(String id) {
    super();
    this.id = id;
  }

  // Getters and Setters
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    return super.toString() + " " + id;
  }
}
