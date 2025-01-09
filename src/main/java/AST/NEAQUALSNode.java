package AST;

public class NEAQUALSNode extends ASTNode {
    public NEAQUALSNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
