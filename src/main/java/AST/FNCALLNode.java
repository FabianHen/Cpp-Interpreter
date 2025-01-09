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
    }

    // Getters and Setters
    public void setObjcalls(List<ObjcallNode> objcalls) {
        this.objcalls = objcalls;
    }
    public void addObjcallNode(ObjcallNode objcall) {
        this.objcalls.add(objcall);
    }
    public List<ObjcallNode> getObjcalls() {
        return this.objcalls;
    }
    public ObjcallNode getObjcallNode(int index) {
        return this.objcalls.get(index);
    }
    
    public IDNode getId() {
        return this.id;
    }
    public void setId(IDNode id) {
        this.id = id;
    }
    
    public void setArgs(ArgsNode args) {
        this.args = args;
    }
    public ArgsNode getArgs() {
        return this.args;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
