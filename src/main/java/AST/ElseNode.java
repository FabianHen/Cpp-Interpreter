package AST;

import java.util.*;

public class ElseNode extends ASTNode {
  // Fields
  private BlockNode block;

  // Constructor
  public ElseNode(BlockNode block) {
    super();
    this.block = block;

    addChild(block);
  }

  // Getters and Setters
  public BlockNode getBlock() {
    return this.block;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
