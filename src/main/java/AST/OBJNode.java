package AST;

import java.util.*;

public class OBJNode extends ExprNode {
  // Fields
  private ExprNode expr;

  // Constructor
  public OBJNode(ExprNode expr) {
    super();
    this.expr = expr;

    addChild(expr);
  }

  // Getters and Setters
  public ExprNode getExpr() {
    return this.expr;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
