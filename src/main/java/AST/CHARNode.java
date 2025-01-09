package AST;

import java.util.*;

public class CHARNode extends ExprNode {
    // Fields
    private char value;

    // Constructor
    public CHARNode(char value) {
        super();
        this.value = value;
    }

    // Getters and Setters
    public char getValue() {
        return this.value;
    }
    public void setValue(char value) {
        this.value = value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
