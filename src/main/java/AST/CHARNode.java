package AST;

import java.util.*;

public class CHARNode extends ExprNode {
  // Fields
  private char value;

  // Constructor
  public CHARNode(char value, int line) {
    super(line);
    this.value = value;
  }

  // Getters and Setters
  public char getValue() {
    return this.value;
  }

  public void setValue(char value) {
    this.value = value;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    return super.toString() + " " + this.value;
  }
}
