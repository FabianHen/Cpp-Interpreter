package AST;

import java.util.*;

public class PrintNode extends ASTNode {
    // Fields
    private ExprNode expr;
    private Type type;

    // Constructor
    public PrintNode(ExprNode expr, Type type) {
        super();
        this.expr = expr;
        this.type = type;
    }

    // Getters and Setters
    public ExprNode getExpr() {
        return this.expr;
    }
    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    public Type getType() {return this.type;}
    public void setType(Type type) {this.type = type;}

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
