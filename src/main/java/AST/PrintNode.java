package AST;

import java.util.*;

public class PrintNode extends ASTNode {
  // Fields
  private ExprNode expr;
  private Type type;

  // Constructor
  public PrintNode(ExprNode expr, Type type, int line) {
    super(line);
    this.expr = expr;
    this.type = type;

    addChild(expr);
  }

  // Getters and Setters
  public ExprNode getExpr() {
    return this.expr;
  }

  public Type getType() {
    return this.type;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    return super.toString() + type.toString();
  }
}
