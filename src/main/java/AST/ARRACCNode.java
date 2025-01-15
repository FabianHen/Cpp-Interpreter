package AST;

import java.util.*;

public class ARRACCNode extends ExprNode {
  // Fields
  private IDNode idNode;
  private List<ExprNode> exprNodes;

  // Constructor
  public ARRACCNode(IDNode idNode, List<ExprNode> exprNodes) {
    super();
    this.idNode = idNode;
    this.exprNodes = exprNodes;
    addChild(idNode);
    for (ExprNode child : exprNodes) {
      addChild(child);
    }
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
  }

  public List<ExprNode> getExprNodes() {
    return this.exprNodes;
  }

  public int getDimension() {
    return this.exprNodes.size();
  }

  public ExprNode getExprNode(int index) {
    return this.exprNodes.get(index);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
