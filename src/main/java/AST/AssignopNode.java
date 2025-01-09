package AST;

public class AssignopNode extends ASTNode {
    public AssignopNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
