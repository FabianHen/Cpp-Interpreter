package AST;

import java.util.List;

public class ObjcallNode extends ASTNode {
  // Fields
  private IDNode idNode;
  private FncallNode fncallNode;
  private List<ExprNode> indices;

  // Constructor
  public ObjcallNode(IDNode idNode, FncallNode fncallNode, List<ExprNode> indices) {
    super();
    this.idNode = idNode;
    this.fncallNode = fncallNode;
    this.indices = indices;
    for (var index : indices) {
      addChild(index);
    }
    if (idNode != null) {
      addChild(idNode);
    }
    if (fncallNode != null) {
      addChild(fncallNode);
    }
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
  }

  public FncallNode getFncallNode() {
    return this.fncallNode;
  }

  public boolean isFunctionCall() {
    return fncallNode != null;
  }

  public List<ExprNode> getIndices() {
    return indices;
  }

  public ExprNode getIndex(int index) {
    return indices.get(index);
  }

  public boolean isArray() {
    return getDimension() > 0;
  }

  public int getDimension() {
    return indices.size();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
