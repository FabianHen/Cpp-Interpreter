package AST;

import java.util.*;

public class ConstructorNode extends ASTNode {
  // Fields
  private IDNode id;
  private List<ParamNode> params;
  private BlockNode block;

  // Constructor
  public ConstructorNode(IDNode id, List<ParamNode> params, BlockNode block) {
    super();
    this.id = id;
    this.params = params;
    this.block = block;

    addChild(id);
    addChild(block);
  }

  // Getters and Setters
  public IDNode getId() {
    return this.id;
  }

  public List<ParamNode> getParams() {
    return this.params;
  }

  public ParamNode getParam(int index) {
    return this.params.get(index);
  }

  public BlockNode getBlock() {
    return this.block;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
