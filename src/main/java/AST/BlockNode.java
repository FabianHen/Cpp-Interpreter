package AST;

import java.util.*;

public class BlockNode extends ASTNode {
  // Constructor
  public BlockNode() {
    super();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
