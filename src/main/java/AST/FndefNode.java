package AST;

import java.util.*;

public class FndefNode extends ASTNode {
  // Fields
  private FndeclNode fndecl;
  private BlockNode block;

  // Constructor
  public FndefNode(FndeclNode fndecl, BlockNode block, int line) {
    super(line);
    this.fndecl = fndecl;
    this.block = block;

    addChild(fndecl);
    addChild(block);
  }

  // Getters and Setters
  public FndeclNode getFndecl() {
    return this.fndecl;
  }

  public BlockNode getBlock() {
    return this.block;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
