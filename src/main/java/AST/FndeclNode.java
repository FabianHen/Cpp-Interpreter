package AST;

import java.util.List;

public class FndeclNode extends ASTNode {
  // Fields
  private TypeNode returnType;
  private IDNode idNode;
  private List<ParamNode> params;

  // Constructor
  public FndeclNode(TypeNode returnType, IDNode idNode, List<ParamNode> params) {
    super();
    this.returnType = returnType;
    this.idNode = idNode;
    this.params = params;

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

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
