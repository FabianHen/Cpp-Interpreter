package SymbolTable;

public class Array implements Symbol, STType {
  private String name;
  private STType type;
  private Scope scope;
  private int dimension;

  public Array(int dimension, STType type) {
    this.name = type.getName() + " array";
    this.dimension = dimension;
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

  public int getDimension() {
    return this.dimension;
  }

  @Override
  public void setScope(Scope scope) {
    this.scope = scope;
  }
}
