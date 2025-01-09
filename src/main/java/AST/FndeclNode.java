package AST;

import java.util.*;

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
    }

    // Getters and Setters
    public TypeNode getReturntype() {
        return this.returnType;
    }
    public void setReturntype(TypeNode returnType) {
        this.returnType = returnType;
    }
    
    public IDNode getId() {
        return this.id;
    }
    public void setId(IDNode id) {
        this.id = id;
    }

    public ParamlistNode getParamlist() {
        return this.paramlist;
    }
    public void setParamlist(ParamlistNode paramlist) {
        this.paramlist = paramlist;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
