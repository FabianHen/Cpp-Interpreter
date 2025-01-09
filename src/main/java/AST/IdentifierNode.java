package AST;

public class IdentifierNode extends ASTNode {
    public IdentifierNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
