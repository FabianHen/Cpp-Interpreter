package SymbolTable;

// TODO: Could be combined with Variable class
public class BuiltIn implements STType {
  private String name;
  private STType type;
  private Scope scope;

  public BuiltIn(String name, Scope scope) {
    this.name = name;
    this.type = this;
    this.scope = scope;
  }

  public BuiltIn(String name) {
    this.name = name;
    this.type = this;
    this.scope = null;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public STType getType() {
    return this.type;
  }

  @Override
  public Scope getScope() {
    return this.scope;
  }

  @Override
  public void setScope(Scope scope) {
    this.scope = scope;
  }
}
