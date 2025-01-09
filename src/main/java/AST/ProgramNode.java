package AST;

public class ProgramNode extends ASTNode {
    public ProgramNode(String value) {
        super(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
