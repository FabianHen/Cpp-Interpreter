package AST;

import java.util.*;

public class FncallNode extends ExprNode {
  // Fields
  private List<ObjcallNode> objcalls;
  private IDNode idNode;
  private ArgsNode argsNode;
  private boolean hasThis;

  // Constructor
  public FncallNode(List<ObjcallNode> objcalls, IDNode idNode, ArgsNode argsNode, boolean hasThis) {
    super();
    this.objcalls = objcalls;
    this.idNode = idNode;
    this.argsNode = argsNode;
    this.hasThis = hasThis;

    for (ObjcallNode child : objcalls) {
      addChild(child);
    }
    addChild(idNode);
    if (argsNode != null) {
      addChild(argsNode);
    }
  }

  // Getters and Setters
  public List<ObjcallNode> getObjcalls() {
    return this.objcalls;
  }

  public ObjcallNode getObjcallNode(int index) {
    return this.objcalls.get(index);
  }

  public void setObjcalls(List<ObjcallNode> objcalls) {
    this.objcalls = objcalls;
  }

  public void setHasThis(boolean hasThis) {
    this.hasThis = hasThis;
  }

  public IDNode getIdNode() {
    return this.idNode;
  }

  public ArgsNode getArgsNode() {
    return this.argsNode;
  }

  public boolean hasThis() {
    return this.hasThis;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}