package AST;

import java.util.*;

public class WhileNode extends ASTNode {
    // Fields
    private ExprNode condition;
    private BlockNode block;

    // Constructor
    public WhileNode(ExprNode condition, BlockNode block) {
        super();
        this.condition = condition;
        this.block = block;
    }

    // Getters and Setters
    public ExprNode getCondition() {
        return this.condition;
    }
    public void setCondition(ExprNode condition) {
        this.condition = condition;
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
