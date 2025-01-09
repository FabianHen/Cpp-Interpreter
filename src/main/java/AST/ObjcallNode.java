package AST;

public class ObjcallNode extends ASTNode {
  // Fields
  private IDNode id;
  private ArgsNode args;

  // Constructor
  public ObjcallNode(IDNode id, ArgsNode args) {
    super();
    this.id = id;
    this.args = args;

    addChild(id);
    if (args != null) {
      addChild(args);
    }
  }

  // Getters and Setters
  public IDNode getId() {
    return this.id;
  }

  public ArgsNode getArgs() {
    return this.args;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
