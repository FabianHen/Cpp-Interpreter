package AST;

public class DestructorNode extends ASTNode {
    public DestructorNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
