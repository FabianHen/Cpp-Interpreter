package AST;

public class EQUALSNode extends ASTNode {
    public EQUALSNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
