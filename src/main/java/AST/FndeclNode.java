package AST;

public class FndeclNode extends ASTNode {
    // Fields
    private TypeNode returnType;
    private IDNode id;
    private ParamlistNode paramlist;

    // Constructor
    public FndeclNode(TypeNode returnType, IDNode id, ParamlistNode paramlist) {
        super();
        this.returnType = returnType;
        this.id = id;
        this.paramlist = paramlist;

        addChild(returnType);
        addChild(id);
        if (paramlist != null) {
            addChild(paramlist);
        }
    }

    // Getters and Setters
    public TypeNode getReturntype() {
        return this.returnType;
    }
    public IDNode getId() {
        return this.id;
    }
    public ParamlistNode getParamlist() {
        return this.paramlist;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
