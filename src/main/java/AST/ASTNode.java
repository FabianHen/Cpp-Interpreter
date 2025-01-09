package AST;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {
    private final List<ASTNode> children;

    public ASTNode() {
        children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public ASTNode getChild(int i) {
        return children.get(i);
    }

    public abstract <T> T accept(ASTVisitor<T> visitor);
}
