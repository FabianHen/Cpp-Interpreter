package AST;

import java.util.*;

public class ReturnNode extends ASTNode {
  // Fields
  private ExprNode expr;
  private boolean hasThis;

  // Constructor
  public ReturnNode(ExprNode expr, boolean hasThis, int line) {
    super(line);
    this.expr = expr;
    this.hasThis = hasThis;

    if (expr != null) {
      addChild(expr);
    }
  }

  // Getters and Setters
  public ExprNode getExpr() {
    return this.expr;
  }

  public boolean hasThis() {
    return this.hasThis;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
