package AST;

public class ClassMemberNode extends ASTNode {
    public ClassMemberNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
