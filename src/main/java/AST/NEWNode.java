package AST;

import java.util.*;

public class NEWNode extends ExprNode {
  // Fields
  private IDNode id;
  private ArgsNode args;

  // Constructor
  public NEWNode(IDNode id, ArgsNode args) {
    super();
    this.id = id;
    this.args = args;

    addChild(id);
    if (args != null) {
      addChild(args);
    }
  }

  // Getters and Setters

  public IDNode getId() {
    return this.id;
  }

  public ArgsNode getArgs() {
    return args;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
