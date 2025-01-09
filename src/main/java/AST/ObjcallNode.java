package AST;

public class ObjcallNode extends ASTNode {
    public ObjcallNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
