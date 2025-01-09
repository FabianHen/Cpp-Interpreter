package AST;

import java.util.*;

public class VardeclNode extends ASTNode {
    // Fields
    private TypeNode type;
    private IdentifierNode identifier;
    private ExprNode expr;

    // Constructor
    public VardeclNode(TypeNode type, IdentifierNode identifier, ExprNode expr) {
        super();
        this.type = type;
        this.identifier = identifier;
        this.expr = expr;
    }

    // Getters and Setters
    public TypeNode getType() {
        return this.type;
    }
    public void setType(TypeNode type) {
        this.type = type;
    }
    
    public IdentifierNode getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier(IdentifierNode identifier) {
        this.identifier = identifier;
    }

    public ExprNode getExpr() {
        return this.expr;
    }
    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
