package AST;

import java.util.*;

public class BindingNode extends ASTNode {
  // Fields
  private List<ObjcallNode> objcalls;

  private IdentifierNode identifierNode;
  private ARRACCNode arraccNode;

  private String assignop;
  private ExprNode expr;
  private boolean hasThis;

  // Constructor
  public BindingNode(
      List<ObjcallNode> objcalls,
      IdentifierNode identifierNode,
      String assignop,
      ExprNode expr,
      boolean hasThis,
      ARRACCNode arraccNode) {
    super();
    this.objcalls = objcalls;
    this.identifierNode = identifierNode;
    this.assignop = assignop;
    this.expr = expr;
    this.hasThis = hasThis;
    this.arraccNode = arraccNode;
    for (ObjcallNode child : objcalls) {
      addChild(child);
    }
    if(identifierNode != null) {
      addChild(identifierNode);
    } else if (arraccNode != null) {
      addChild(arraccNode);
    }
    addChild(expr);
  }

  // Getters and Setters
  public ObjcallNode getObjcall(int index) {
    return this.objcalls.get(index);
  }

  public List<ObjcallNode> getObjcalls() {
    return this.objcalls;
  }

  public IdentifierNode getIdentifierNode() {
    return this.identifierNode;
  }

  public String getAssignop() {
    return this.assignop;
  }
  public ARRACCNode getArraccNode() {
    return this.arraccNode;
  }
  public boolean hasArracc(){
    return this.arraccNode != null;
  }
  public ExprNode getExpr() {
    return this.expr;
  }

  public boolean hasThis() {
    return this.hasThis;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    return super.toString() + " (" + assignop + ")";
  }
}
