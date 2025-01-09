package AST;

import java.util.*;

public class ObjcallNode extends ASTNode {
    // Fields
    private IDNode id;
    private ArgsNode args;
    // Constructor
    public ObjcallNode(IDNode id, ArgsNode args) {
        super();
        this.id = id;
        this.args = args;
    }
    // Getters and Setters
    public IDNode getId() {
        return this.id;
    }
    public void setId(IDNode id) {
        this.id = id;
    }
    
    public ArgsNode getArgs() {
        return this.args;
    }
    public void setArgs(ArgsNode args) {
        this.args = args;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
