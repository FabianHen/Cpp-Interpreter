package SymbolTable;

public class STClass extends Scope implements STType, Symbol {
  private String name;
  private STType type;
  private Scope scope;

  public STClass(Scope parent) {
    super(parent);
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
