package AST;

public class ElseifNode extends ASTNode {
    public ElseifNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
