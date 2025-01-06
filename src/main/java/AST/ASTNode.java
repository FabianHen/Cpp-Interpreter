package AST;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {
    private final List<ASTNode> children;
    private String value;

    public ASTNode(String value) {
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public abstract <T> T accept(ASTVisitor<T> visitor);
}
