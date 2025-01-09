package AST;

import java.util.*;

public class NEWNode extends ExprNode {
    // Fields
    private IDNode id;
    private List<ArgsNode> args;

    // Constructor
    public NEWNode(IDNode id, List<ArgsNode> args) {
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
    
    public void setArgs(List<ArgsNode> args) {
        this.args = args;
    }
    public void addArgsNode(ArgsNode arg) {
        this.args.add(arg);
    }
    public List<ArgsNode> getArgs() {
        return this.args;
    }
    public ArgsNode getArgsNode(int index) {
        return this.args.get(index);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
