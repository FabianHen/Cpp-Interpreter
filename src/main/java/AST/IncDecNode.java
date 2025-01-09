package AST;

public class IncDecNode extends ASTNode {
  // Fields
  private IDNode id;
  private boolean isInc;
  private boolean isPre;

  // Constructor
  public IncDecNode(IDNode id, boolean isInc, boolean isPre) {
    super();
    this.id = id;
    this.isInc = isInc;
    this.isPre = isPre;

    addChild(id);
  }

  // Getters and Setters
  public IDNode getId() {
    return this.id;
  }

  public boolean isInc() {
    return isInc;
  }

  public boolean isPre() {
    return isPre;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
