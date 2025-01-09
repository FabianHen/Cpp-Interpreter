package AST;

public class ConstructorCallNode extends ASTNode {
    public ConstructorCallNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
