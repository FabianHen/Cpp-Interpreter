package AST;

import java.util.*;

public class DIVNode extends ExprNode {
    // Fields
    private ExprNode e1;
    private ExprNode e2;

    // Constructor
    public DIVNode(ExprNode e1, ExprNode e2) {
        super();
        this.e1 = e1;
        this.e2 = e2;
    }

    // Getters and Setters
    public ExprNode getE1() {
        return this.e1;
    }
    public void setE1(ExprNode e1) {
        this.e1 = e1;
    }
    
    public ExprNode getE2() {
        return this.e2;
    }
    public void setE2(ExprNode e2) {
        this.e2 = e2;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
