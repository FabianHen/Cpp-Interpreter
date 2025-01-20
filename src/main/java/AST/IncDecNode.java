package AST;

public class IncDecNode extends ExprNode {
  // Fields
  private IDNode idNode;
  private boolean isInc;
  private boolean isPre;

  // Constructor
  public IncDecNode(IDNode idNode, boolean isInc, boolean isPre, int line) {
    super(line);
    this.idNode = idNode;
    this.isInc = isInc;
    this.isPre = isPre;

    addChild(idNode);
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
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

  @Override
  public String toString() {
    return (isInc ? "INC" : "DEC") + " " + (isPre ? "PRE" : "POST");
  }
}
