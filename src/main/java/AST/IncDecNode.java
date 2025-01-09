package AST;

public class INCDECNode extends ASTNode {
    public INCDECNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
