package AST;

import java.util.*;

public class OBJMEMNode extends ExprNode {
  // Fields
  private List<ObjcallNode> objcalls;
  private IDNode idNode;
  private ARRACCNode arraccNode;
  private boolean hasThis;

  // Constructor
  public OBJMEMNode(
      List<ObjcallNode> objcalls, IDNode idNode, boolean hasThis, ARRACCNode arraccNode, int line) {
    super(line);
    this.objcalls = objcalls;
    this.idNode = idNode;
    this.hasThis = hasThis;
    this.arraccNode = arraccNode;

    for (ObjcallNode child : objcalls) {
      addChild(child);
    }
    if (idNode != null) {
      addChild(idNode);
    }
    if (arraccNode != null) {
      addChild(arraccNode);
    }
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

  public ARRACCNode getArraccNode() {
    return this.arraccNode;
  }

  public boolean hasArraccNode() {
    return this.arraccNode != null;
  }

  public boolean hasThis() {
    return hasThis;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
