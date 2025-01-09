package AST;

public class OverrideFndefNode extends ASTNode {
    public OverrideFndefNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
