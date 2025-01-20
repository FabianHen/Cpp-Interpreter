package AST;

import java.util.*;

public class NOTNode extends ExprNode {
  // Fields
  private ExprNode expr;

  // Constructor
  public NOTNode(ExprNode expr, int line) {
    super(line);
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
