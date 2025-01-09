package AST;

import java.util.*;

public class IdentifierNode extends ASTNode {
    // Fields
    private IDNode id;
    private List<ExprNode> sizes;
    private boolean isReference;
    private int dimension;

    // Constructor
    public IdentifierNode(IDNode id, boolean isReference) {
        super();
        this.id = id;
        this.sizes = new ArrayList<>();
        this.dimension = 0;
        this.isReference = isReference;
    }

    public IdentifierNode(IDNode id, List<ExprNode> sizes, int dimension, boolean isReference) {
        super();
        this.id = id;
        this.sizes = sizes;
        this.dimension = dimension;
        this.isReference = isReference;
    }

    // Getters and Setters
    public IDNode getId() {
        return this.id;
    }
    public void setId(IDNode id) {
        this.id = id;
    }
    
    public ExprNode getExpr(int index) {
        return this.sizes.get(index);
    }
    public void setExpr(ExprNode size) {
        this.sizes.add(size);
    }

    public List<ExprNode> getSizes() {
        return sizes;
    }
    public void setSizes(List<ExprNode> sizes) {
        this.sizes = sizes;
    }
    public boolean isReference() {
        return isReference;
    }
    public void setReference(boolean reference) {
        isReference = reference;
    }
    public int getDimension() {
        return dimension;
    }
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public boolean isArray() {
        return (this.dimension > 0);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
