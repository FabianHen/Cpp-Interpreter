package AST;

import java.util.*;

public class ClassDefNode extends ASTNode {
    // Fields
    private IDNode cName;
    private IDNode parentClass;
    private List<ASTNode> classMembers;

    // Constructor
    public ClassDefNode(IDNode cName, IDNode parentClass, List<ASTNode> classMembers) {
        super();
        this.cName = cName;
        this.parentClass = parentClass;
        this.classMembers = classMembers;

        addChild(cName);
        if (parentClass != null) {
            addChild(parentClass);
        }
        for (ASTNode child : classMembers) {
            addChild(child);
        }
    }

    // Getters and Setters
    public IDNode getCname() {
        return this.cName;
    }
    public IDNode getParentclass() {
        return this.parentClass;
    }
    public List<ASTNode> getClassmembers() {
        return this.classMembers;
    }
    public ASTNode getClassMemberNode(int index) {
        return this.classMembers.get(index);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
