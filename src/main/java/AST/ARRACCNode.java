package AST;

import java.util.*;

public class ARRACCNode extends ExprNode {
  // Fields
  private ExprNode expr;
  private List<ExprNode> indices;

  // Constructor
  public ARRACCNode(ExprNode expr, List<ExprNode> indices) {
    super();
    this.expr = expr;
    this.indices = indices;
    addChild(expr);
    for (ExprNode child : indices) {
      addChild(child);
    }
  }

  // Getters and Setters
  public ExprNode getExpr() {
    return this.expr;
  }

  public List<ExprNode> getIndices() {
    return this.indices;
  }

  public ExprNode getExprNode(int index) {
    return this.indices.get(index);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
