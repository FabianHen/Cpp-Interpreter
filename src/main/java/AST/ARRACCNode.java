package AST;

public class ARRACCNode extends ASTNode {
    public ARRACCNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
