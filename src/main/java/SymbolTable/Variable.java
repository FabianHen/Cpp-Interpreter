package SymbolTable;

public class Variable implements Symbol {
  private String name;
  private STType type;
  private Scope scope;

  public Variable(String name, STType type, Scope scope) {
    this.name = name;
    this.type = type;
    this.scope = scope;
  }

  public Variable(String name, STType type) {
    this.name = name;
    this.type = type;
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
