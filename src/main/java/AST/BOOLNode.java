package AST;

import java.util.*;

public class BOOLNode extends ExprNode {
  // Fields
  private boolean value;

  // Constructor
  public BOOLNode(boolean value) {
    super();
    this.value = value;
  }

  // Getters and Setters
  public boolean getValue() {
    return this.value;
  }

  public void setValue(boolean value) {
    this.value = value;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
