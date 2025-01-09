package AST;

public class BOOLNode extends ASTNode {
    public BOOLNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
