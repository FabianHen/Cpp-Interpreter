package AST;

public class ObjcallNode extends ASTNode {
  // Fields
  private IDNode idNode;
  private FncallNode fncallNode;
  private ARRACCNode arracNode;

  // Constructor
  public ObjcallNode(IDNode idNode, FncallNode fncallNode, ARRACCNode arracNode, int line) {
    super(line);
    this.idNode = idNode;
    this.fncallNode = fncallNode;
    this.arracNode = arracNode;
    if (idNode != null) {
      addChild(idNode);
    }
    if (fncallNode != null) {
      addChild(fncallNode);
    }
    if (arracNode != null) {
      addChild(arracNode);
    }
  }

  // Getters and Setters
  public IDNode getIdNode() {
    return this.idNode;
  }

  public FncallNode getFncallNode() {
    return this.fncallNode;
  }

  public ARRACCNode getArracNode() {
    return this.arracNode;
  }

  public boolean isFunctionCall() {
    return fncallNode != null;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
