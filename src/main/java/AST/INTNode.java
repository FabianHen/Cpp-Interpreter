package AST;

import java.util.*;

public class INTNode extends ExprNode {
  // Fields
  private int value;

  // Constructor
  public INTNode(int value, int line) {
    super(line);
    this.value = value;
  }

  // Getters and Setters
  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    return super.toString() + " " + value;
  }
}
