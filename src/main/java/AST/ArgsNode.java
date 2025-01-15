package AST;

import java.util.*;

public class ArgsNode extends ExprNode {
  // Fields
  private List<ExprNode> arguments;
  private boolean isArrVals;
  // Constructor
  public ArgsNode(List<ExprNode> arguments) {
    super();
    isArrVals = false;
    this.arguments = arguments;
    for (var child : arguments) {
      addChild(child);
    }
  }

  public void setArrVals(boolean isArrVals) {
    this.isArrVals = isArrVals;
  }
  public boolean isArrVals() {
    return isArrVals;
  }
  // Getters and Setters
  public List<ExprNode> getArguments() {
    return this.arguments;
  }

  public ExprNode getArgument(int index) {
    return this.arguments.get(index);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
