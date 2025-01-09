package AST;

public class VardeclNode extends ASTNode {
    public VardeclNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
