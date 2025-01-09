package AST;

public class GREATERNode extends ASTNode {
    public GREATERNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
