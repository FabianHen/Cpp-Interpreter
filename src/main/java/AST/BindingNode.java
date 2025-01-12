package AST;

import java.util.*;

public class BindingNode extends ASTNode {
  // Fields
  private List<ObjcallNode> objcalls;
  private IdentifierNode identifierNode;
  private String assignop;
  private ExprNode expr;

  // Constructor
  public BindingNode(
      List<ObjcallNode> objcalls, IdentifierNode identifierNode, String assignop, ExprNode expr) {
    super();
    this.objcalls = objcalls;
    this.identifierNode = identifierNode;
    this.assignop = assignop;
    this.expr = expr;

    for (ObjcallNode child : objcalls) {
      addChild(child);
    }
    addChild(identifierNode);
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

  public ExprNode getExpr() {
    return this.expr;
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
