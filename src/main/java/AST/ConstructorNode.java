package AST;

import java.util.*;

public class ConstructorNode extends ASTNode {
    // Fields
    private IDNode id;
    private ParamlistNode paramlist;
    private BlockNode block;

    // Constructor
    public ConstructorNode(IDNode id, ParamlistNode paramlist, BlockNode block) {
        super();
        this.id = id;
        this.paramlist = paramlist;
        this.block = block;

        addChild(id);
        if (paramlist != null) {
            addChild(paramlist);
        }
        addChild(block);
    }

    // Getters and Setters
    public IDNode getId() {
        return this.id;
    }
    public ParamlistNode getParamlist() {
        return this.paramlist;
    }
    public BlockNode getBlock() {
        return this.block;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
