package AST;

public class FNCALLNode extends ASTNode {
    public FNCALLNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
