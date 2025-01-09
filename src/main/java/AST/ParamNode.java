package AST;

import java.util.*;

public class ParamNode extends ASTNode {
    // Fields
    private TypeNode type;
    private IdentifierNode identifier;

    // Constructor
    public ParamNode(TypeNode type, IdentifierNode identifier) {
        super();
        this.type = type;
        this.identifier = identifier;
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

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
