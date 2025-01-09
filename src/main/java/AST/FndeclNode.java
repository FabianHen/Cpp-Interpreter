package AST;

public class FndeclNode extends ASTNode {
    public FndeclNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
