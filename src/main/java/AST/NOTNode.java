package AST;

import java.util.*;

public class NOTNode extends ExprNode {
    // Fields
    private ExprNode expr;

    // Constructor
    public NOTNode(ExprNode expr) {
        super();
        this.expr = expr;
    }
    // Getters and Setters
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
