package AST;

import java.util.*;

public class ProgramNode extends ASTNode {
  // Constructor
  public ProgramNode(int line) {
    super(line);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
