package AST;

public class ARRVALSNode extends ASTNode {
    public ARRVALSNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
