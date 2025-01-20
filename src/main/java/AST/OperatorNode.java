package AST;

public class OperatorNode extends ASTNode {
  private IDNode returnType;
  private IDNode paramType;
  private IDNode paramName;
  private BlockNode blockNode;

  public OperatorNode(
      IDNode returnType, IDNode paramType, IDNode paramName, BlockNode blockNode, int line) {
    super(line);
    this.returnType = returnType;
    addChild(returnType);

    this.paramType = paramType;
    addChild(paramType);

    this.paramName = paramName;
    addChild(paramName);

    this.blockNode = blockNode;
    addChild(blockNode);
  }

  public BlockNode getBlock() {
    return blockNode;
  }

  public IDNode getParamName() {
    return paramName;
  }

  public IDNode getParamType() {
    return paramType;
  }

  public IDNode getReturnType() {
    return returnType;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
