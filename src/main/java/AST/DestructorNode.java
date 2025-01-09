package AST;

public class DestructorNode extends ASTNode {
  // Fields
  private IDNode id;
  private BlockNode block;
  private boolean isVirtual;

  // Constructor
  public DestructorNode(IDNode id, BlockNode block, boolean isVirtual) {
    super();
    this.id = id;
    this.block = block;
    this.isVirtual = isVirtual;

    addChild(id);
    addChild(block);
  }

  // Getters and Setters
  public IDNode getId() {
    return this.id;
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
