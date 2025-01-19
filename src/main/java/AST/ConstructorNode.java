package AST;

import java.util.*;

public class ConstructorNode extends ASTNode {
  // Fields
  private IDNode idNode;
  private List<ParamNode> params;
  private BlockNode block;
  private boolean isCopyCon;

  // Constructor
  public ConstructorNode(
      IDNode idNode, List<ParamNode> params, BlockNode block, boolean isCopyCon) {
    super();
    this.idNode = idNode;
    this.params = params;
    this.block = block;
    this.isCopyCon = isCopyCon;
    addChild(idNode);
    addChild(block);
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
  }

  public List<ParamNode> getParams() {
    return this.params;
  }

  public ParamNode getParamNode(int index) {
    return this.params.get(index);
  }

  public BlockNode getBlock() {
    return this.block;
  }

  public boolean isCopyCon() {
    return this.isCopyCon;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    return super.toString() + " is copy con: " + isCopyCon;
  }
}
