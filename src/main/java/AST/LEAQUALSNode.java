package AST;

public class LEAQUALSNode extends ASTNode {
    public LEAQUALSNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
