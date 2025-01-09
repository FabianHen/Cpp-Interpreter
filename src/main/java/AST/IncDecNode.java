package AST;

public class IncDecNode extends ASTNode {
    public IncDecNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
