package AST;

import java.util.*;

public class ConstructorCallNode extends ASTNode {
    // Fields
    private IDNode typeName;
    private IDNode instanceName;
    private ArgsNode args;

    // Constructor
    public ConstructorCallNode(IDNode typeName, IDNode instanceName, ArgsNode args) {
        super();
        this.typeName = typeName;
        this.instanceName = instanceName;
        this.args = args;
    }

    // Getters and Setters
    public IDNode getTypename() {
        return this.typeName;
    }
    public void setTypename(IDNode typeName) {
        this.typeName = typeName;
    }

    public IDNode getInstancename() {
        return this.instanceName;
    }
    public void setInstancename(IDNode instanceName) {
        this.instanceName = instanceName;
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
