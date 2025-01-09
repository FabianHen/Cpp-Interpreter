package AST;

public class OBJMEMNode extends ASTNode {
    public OBJMEMNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
