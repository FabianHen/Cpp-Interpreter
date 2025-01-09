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

        if (fndecl != null) {
            addChild(fndecl);
        }
        if (fndef != null) {
            addChild(fndef);
        }
        if (intValue != null) {
            addChild(intValue);
        }
    }

    // Getters and Setters
    
    public FndeclNode getFndecl() {
        return this.fndecl;
    }
    public INTNode getIntvalue() {
        return this.intValue;
    }
    public boolean isAbstract() {
        return this.isAbstract;
    }
    public void setAbstract(boolean isAbstract) { this.isAbstract = isAbstract; }
    public FndefNode getFndef() {
        return this.fndef;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
