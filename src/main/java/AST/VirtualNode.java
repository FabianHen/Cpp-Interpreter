package AST;

public class VirtualNode extends ASTNode {
    // Fields
    private FndeclNode fndecl;
    private INTNode intValue;
    private boolean isAbstract;
    private FndefNode fndef;

    // Constructor
    public VirtualNode(FndeclNode fndecl, INTNode intValue, boolean isAbstract, FndefNode fndef) {
        super();
        this.fndecl = fndecl;
        this.intValue = intValue;
        this.isAbstract = isAbstract;
        this.fndef = fndef;
    }

    // Getters and Setters
    
    public FndeclNode getFndecl() {
        return this.fndecl;
    }
    public void setFndecl(FndeclNode fndecl) {
        this.fndecl = fndecl;
    }

    public INTNode getIntvalue() {
        return this.intValue;
    }
    public void setIntvalue(INTNode intValue) {
        this.intValue = intValue;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }
    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public FndefNode getFndef() {
        return this.fndef;
    }
    public void setFndef(FndefNode fndef) {
        this.fndef = fndef;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
