package AST;

import java.util.*;

public class FNCALLNode extends ExprNode {
    // Fields
    private List<ObjcallNode> objcalls;
    private IDNode id;
    private ArgsNode args;

    // Constructor
    public FNCALLNode(List<ObjcallNode> objcalls, IDNode id, ArgsNode args) {
        super();
        this.objcalls = objcalls;
        this.id = id;
        this.args = args;

        for (ObjcallNode child : objcalls) {
            addChild(child);
        }
        addChild(id);
        if (args != null) {
            addChild(args);
        }
    }

    // Getters and Setters
    public List<ObjcallNode> getObjcalls() {
        return this.objcalls;
    }
    public ObjcallNode getObjcallNode(int index) {
        return this.objcalls.get(index);
    }
    public IDNode getId() {
        return this.id;
    }
    public ArgsNode getArgs() {
        return this.args;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
