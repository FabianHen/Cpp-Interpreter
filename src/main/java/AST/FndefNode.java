package AST;

import java.util.*;

public class FndefNode extends ASTNode {
    // Fields
    private FndeclNode fndecl;
    private BlockNode block;

    // Constructor
    public FndefNode(FndeclNode fndecl, BlockNode block) {
        super();
        this.fndecl = fndecl;
        this.block = block;
    }
    // Getters and Setters
    public FndeclNode getFndecl() {
        return this.fndecl;
    }
    public void setFndecl(FndeclNode fndecl) {
        this.fndecl = fndecl;
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
