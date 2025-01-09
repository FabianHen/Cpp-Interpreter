package AST;

import java.util.*;

public class IncDecNode extends ASTNode {
    // Fields
    private IDNode id;
    private boolean isInc;
    private boolean isPre;
    // Constructor
    public IncDecNode(IDNode id, boolean isInc, boolean isPre) {
        super();
        this.id = id;
        this.isInc = isInc;
        this.isPre = isPre;
    }

    // Getters and Setters
    public IDNode getId() {
        return this.id;
    }
    public void setId(IDNode id) {
        this.id = id;
    }

    public boolean isInc() {
        return isInc;
    }
    public void setInc(boolean inc) {
        isInc = inc;
    }

    public boolean isPre() {
        return isPre;
    }
    public void setPre(boolean pre) {
        isPre = pre;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
