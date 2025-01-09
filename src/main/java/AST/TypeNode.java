package AST;

import java.util.*;

public class TypeNode extends ASTNode {
    // Fields
    private Type type;
    private String className;

    // Constructor
    public TypeNode(Type type) {
        super();
        this.type = type;
        this.className = "";
    }

    public TypeNode(String className) {
        super();
        this.className = className;
        this.type = Type.CUSTOM;
    }

    // Getters and Setters
    public Type getType() {
        return this.type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public String getClassName() {
        return this.className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
