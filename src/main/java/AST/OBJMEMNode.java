package AST;

import java.util.*;

public class OBJMEMNode extends ExprNode {
    // Fields
    private List<ObjcallNode> objcalls;
    private IDNode id;

    // Constructor
    public OBJMEMNode(List<ObjcallNode> objcalls, IDNode id) {
        super();
        this.objcalls = objcalls;
        this.id = id;
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

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
