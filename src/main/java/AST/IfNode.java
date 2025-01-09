package AST;

import java.util.*;

public class IfNode extends ASTNode {
    // Fields
    private ExprNode condition;
    private BlockNode block;
    private List<ElseifNode> elseifBlocks;
    private ElseNode elseBlock;

    // Constructor
    public IfNode(ExprNode condition, BlockNode block, List<ElseifNode> elseifBlocks, ElseNode elseBlock) {
        super();
        this.condition = condition;
        this.block = block;
        this.elseifBlocks = elseifBlocks;
        this.elseBlock = elseBlock;
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
    
    public void setElseifblocks(List<ElseifNode> elseifBlocks) {
        this.elseifBlocks = elseifBlocks;
    }
    public void addElseifNode(ElseifNode elseifBlock) {
        this.elseifBlocks.add(elseifBlock);
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
    public void setElseblock(ElseNode elseBlock) {
        this.elseBlock = elseBlock;
    }


    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
