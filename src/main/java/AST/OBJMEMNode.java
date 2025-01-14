package AST;

import java.util.*;

public class OBJMEMNode extends ExprNode {
  // Fields
  private List<ObjcallNode> objcalls;
  private IDNode idNode;
  private boolean hasThis;

  // Constructor
  public OBJMEMNode(List<ObjcallNode> objcalls, IDNode idNode, boolean hasThis) {
    super();
    this.objcalls = objcalls;
    this.idNode = idNode;
    this.hasThis = hasThis;

    for (ObjcallNode child : objcalls) {
      addChild(child);
    }
    addChild(idNode);
  }

  // Getters and Setters
  public List<ObjcallNode> getObjcalls() {
    return this.objcalls;
  }

  public ObjcallNode getObjcallNode(int index) {
    return this.objcalls.get(index);
  }

  public IDNode getIdNode() {
    return this.idNode;
  }

  public boolean hasThis() {
    return hasThis;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
