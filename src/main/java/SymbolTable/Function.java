package SymbolTable;

import AST.*;
import java.util.List;

public class Function extends Scope implements Symbol {
  private String name;
  private STType type;
  private Scope scope;
  private List<ParamNode> params;

  public Function(String name, STType type, Scope parent, List<ParamNode> params) {
    super(parent);
    this.name = name;
    this.type = type;
    this.params = params;
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

  public List<ParamNode> getParams() {
    return this.params;
  }

  public ParamNode getParamNode(int i) {
    return this.params.get(i);
  }

  @Override
  public void setScope(Scope scope) {
    this.scope = scope;
  }
}
