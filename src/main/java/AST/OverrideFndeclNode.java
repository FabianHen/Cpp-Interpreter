package AST;

import java.util.*;

public class OverrideFndeclNode extends ASTNode {
    // Fields
    private FndeclNode fndecl;
    private boolean isOverride;

    // Constructor
    public OverrideFndeclNode(FndeclNode fndecl, boolean isOverride) {
        super();
        this.fndecl = fndecl;
        this.isOverride = isOverride;
    }

    // Getters and Setters
    public FndeclNode getFndecl() {
        return this.fndecl;
    }
    public void setFndecl(FndeclNode fndecl) {
        this.fndecl = fndecl;
    }

    public boolean getIsoverride() {
        return this.isOverride;
    }
    public void setIsoverride(boolean isOverride) {
        this.isOverride = isOverride;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
