package AST;

import java.util.*;

public class BindingNode extends ASTNode {
    // Fields
    private List<ObjcallNode> objcalls;
    private IDNode id;
    private String assignop;
    private ExprNode expr;

    // Constructor
    public BindingNode(List<ObjcallNode> objcalls, IDNode id, String assignop, ExprNode expr) {
        super();
        this.objcalls = objcalls;
        this.id = id;
        this.assignop = assignop;
        this.expr = expr;
    }

    // Getters and Setters
    public ObjcallNode getObjcall(int index) {
        return this.objcalls.get(index);
    }
    public void addObjcall(ObjcallNode objcall) {
        this.objcalls.add(objcall);
    }
    public List<ObjcallNode> getObjcalls() {
        return this.objcalls;
    }
    public void setObjcalls(List<ObjcallNode> objcalls) {
        this.objcalls = objcalls;
    }
    
    public IDNode getId() {
        return this.id;
    }
    public void setId(IDNode id) {
        this.id = id;
    }
    
    public String getAssignop() {
        return this.assignop;
    }
    public void setAssignop(String assignop) {
        this.assignop = assignop;
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
