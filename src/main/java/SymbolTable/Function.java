package SymbolTable;

import AST.*;
import java.util.List;

public class Function extends Scope implements Symbol {
  private final String name;
  private final STType type;
  private final List<ParamNode> params;
  private final boolean isVirtual;
  private boolean hasBeenDefined;

  private FndeclNode fndeclNode;
  private ConstructorNode constructorNode;

  public Function(
      String name, STType type, Scope definingScope, List<ParamNode> params, boolean isVirtual) {
    super(definingScope);
    this.name = name;
    this.type = type;
    this.params = params;
    this.isVirtual = isVirtual;
    this.hasBeenDefined = false;
    fndeclNode = null;
    constructorNode = null;
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
    return this.parent;
  }

  public List<ParamNode> getParams() {
    return this.params;
  }

  public ParamNode getParamNode(int i) {
    return this.params.get(i);
  }

  public boolean isVirtual() {
    return this.isVirtual;
  }

  public boolean hasBeenDefined() {
    return hasBeenDefined;
  }

  public void setHasBeenDefined(boolean hasBeenDefined) {
    this.hasBeenDefined = hasBeenDefined;
  }

  public void setFndeclNode(FndeclNode fndefNode) {
    this.fndeclNode = fndefNode;
  }

  public FndeclNode getFndeclNode() {
    return this.fndeclNode;
  }

  public ConstructorNode getConstructorNode() {
    return constructorNode;
  }

  public void setConstructorNode(ConstructorNode constructorNode) {
    this.constructorNode = constructorNode;
  }

  @Override
  public void setScope(Scope scope) {
    this.parent = scope;
  }
}
