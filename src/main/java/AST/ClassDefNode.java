package AST;

import java.util.*;

public class ClassDefNode extends ASTNode {
  // Fields
  private IDNode idNode;
  private IDNode parentClass;
  private List<ASTNode> classMembers;

  // Constructor
  public ClassDefNode(IDNode idNode, IDNode parentClass, List<ASTNode> classMembers, int line) {
    super(line);
    this.idNode = idNode;
    this.parentClass = parentClass;
    this.classMembers = classMembers;

    addChild(idNode);
    if (parentClass != null) {
      addChild(parentClass);
    }
    for (ASTNode child : classMembers) {
      addChild(child);
    }
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
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
