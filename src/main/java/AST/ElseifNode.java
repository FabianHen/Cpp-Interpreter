package AST;

import java.util.*;

public class ElseifNode extends ASTNode {
  // Fields
  private ExprNode condition;
  private BlockNode block;

  // Constructor
  public ElseifNode(ExprNode condition, BlockNode block, int line) {
    super(line);
    this.condition = condition;
    this.block = block;

    addChild(condition);
    addChild(block);
  }

  // Getters and Setters
  public ExprNode getCondition() {
    return this.condition;
  }

  public BlockNode getBlock() {
    return this.block;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
