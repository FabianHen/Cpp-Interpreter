package AST;

import java.util.*;

public class IfNode extends ASTNode {
  // Fields
  private ExprNode condition;
  private BlockNode block;
  private List<ElseifNode> elseifBlocks;
  private ElseNode elseBlock;

  // Constructor
  public IfNode(
      ExprNode condition,
      BlockNode block,
      List<ElseifNode> elseifBlocks,
      ElseNode elseBlock,
      int line) {
    super(line);
    this.condition = condition;
    this.block = block;
    this.elseifBlocks = elseifBlocks;
    this.elseBlock = elseBlock;

    addChild(condition);
    addChild(block);

    for (ElseifNode elseifBlock : elseifBlocks) {
      addChild(elseifBlock);
    }
    if (elseBlock != null) {
      addChild(elseBlock);
    }
  }

  // Getters and Setters
  public ExprNode getCondition() {
    return this.condition;
  }

  public BlockNode getBlock() {
    return this.block;
  }

  public List<ElseifNode> getElseifblocks() {
    return this.elseifBlocks;
  }

  public ElseifNode getElseifNode(int index) {
    return this.elseifBlocks.get(index);
  }

  public ElseNode getElseblock() {
    return this.elseBlock;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
