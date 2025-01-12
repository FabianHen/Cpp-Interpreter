package AST;

import java.util.*;

public class IdentifierNode extends ASTNode {
  // Fields
  private IDNode idNode;
  private List<ExprNode> sizes;
  private boolean isReference;
  private int dimension;

  // Constructor
  public IdentifierNode(IDNode idNode, boolean isReference) {
    super();
    this.idNode = idNode;
    this.sizes = new ArrayList<>();
    this.dimension = 0;
    this.isReference = isReference;

    addChild(this.idNode);
  }

  public IdentifierNode(IDNode idNode, List<ExprNode> sizes, int dimension, boolean isReference) {
    super();
    this.idNode = idNode;
    this.sizes = sizes;
    this.dimension = dimension;
    this.isReference = isReference;

    addChild(this.idNode);
    for (ExprNode child : sizes) {
      addChild(child);
    }
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
  }

  public ExprNode getExpr(int index) {
    return this.sizes.get(index);
  }

  public List<ExprNode> getSizes() {
    return sizes;
  }

  public boolean isReference() {
    return isReference;
  }

  public int getDimension() {
    return dimension;
  }

  public boolean isArray() {
    return (this.dimension > 0);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append((isReference ? "&" : "") + " ");
    sb.append(dimension);
    return sb.toString();
  }
}
