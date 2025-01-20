package AST;

import java.util.*;

public class BlockNode extends ASTNode {
  // Constructor
  public BlockNode(int line) {
    super(line);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
