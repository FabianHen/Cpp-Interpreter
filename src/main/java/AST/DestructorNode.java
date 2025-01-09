package AST;

public class DestructorNode extends ASTNode {
    // Fields
    private IDNode id;
    private BlockNode block;

    // Constructor
    public DestructorNode(IDNode id, BlockNode block) {
        super();
        this.id = id;
        this.block = block;
    }

    // Getters and Setters
    public IDNode getId() {
        return this.id;
    }
    public void setId(IDNode id) {
        this.id = id;
    }
    
    public BlockNode getBlock() {
        return this.block;
    }
    public void setBlock(BlockNode block) {
        this.block = block;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
