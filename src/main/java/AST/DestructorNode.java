package AST;

public class DestructorNode extends ASTNode {
  // Fields
  private IDNode idNode;
  private BlockNode block;
  private boolean isVirtual;

  // Constructor
  public DestructorNode(IDNode idNode, BlockNode block, boolean isVirtual) {
    super();
    this.idNode = idNode;
    this.block = block;
    this.isVirtual = isVirtual;

    addChild(idNode);
    addChild(block);
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
  }

  public BlockNode getBlock() {
    return this.block;
  }

  public boolean isVirtual() {
    return this.isVirtual;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
