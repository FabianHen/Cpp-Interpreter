package AST;

import java.util.List;

public class FndeclNode extends ASTNode {
  // Fields
  private TypeNode returnType;
  private IDNode idNode;
  private List<ParamNode> params;
  private boolean isVirtual;
  private boolean isOverride;
  // indicates where the function of this declaration is defined
  private FndefNode fndefNode;

  // Constructor
  public FndeclNode(TypeNode returnType, IDNode idNode, List<ParamNode> params) {
    super();
    this.returnType = returnType;
    this.idNode = idNode;
    this.params = params;
    this.isVirtual = false;
    this.isOverride = false;
    fndefNode = null;
    addChild(returnType);
    addChild(idNode);
  }

  // Getters and Setters
  public TypeNode getReturntype() {
    return this.returnType;
  }

  public IDNode getIdNode() {
    return this.idNode;
  }

  public List<ParamNode> getParams() {
    return this.params;
  }

  public ParamNode getParamNode(int index) {
    return this.params.get(index);
  }

  public boolean isOverride() {
    return isOverride;
  }

  public void setOverride(boolean override) {
    isOverride = override;
  }

  public boolean isVirtual() {
    return isVirtual;
  }

  public void setVirtual(boolean virtual) {
    isVirtual = virtual;
  }

  public FndefNode getFndefNode() {
    return fndefNode;
  }

  public void setFndefNode(FndefNode fndefNode) {
    this.fndefNode = fndefNode;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
