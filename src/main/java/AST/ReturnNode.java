package AST;

import java.util.*;

public class ReturnNode extends ASTNode {
  // Fields
  private ExprNode expr;

  // Constructor
  public ReturnNode(ExprNode expr) {
    super();
    this.expr = expr;

    if (expr != null) {
      addChild(expr);
    }
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
