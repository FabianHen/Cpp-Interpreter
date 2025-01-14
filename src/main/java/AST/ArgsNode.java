package AST;

import java.util.*;

public class ArgsNode extends ExprNode {
  // Fields
  private List<ExprNode> arguments;

  // Constructor
  public ArgsNode(List<ExprNode> arguments) {
    super();
    this.arguments = arguments;
    for (var child : arguments) {
      addChild(child);
    }
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
