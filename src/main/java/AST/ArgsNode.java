package AST;

import java.util.*;

public class ArgsNode extends ASTNode {
    // Fields
    private List<ExprNode> arguments;

    // Constructor
    public ArgsNode(List<ExprNode> arguments) {
        super();
        this.arguments = arguments;
    }

    // Getters and Setters
    public void setArguments(List<ExprNode> arguments) {
        this.arguments = arguments;
    }
    public void addExprNode(ExprNode argument) {
        this.arguments.add(argument);
    }
    public List<ExprNode> getArguments() {
        return this.arguments;
    }
    public ExprNode getExprNode(int index) {
        return this.arguments.get(index);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
