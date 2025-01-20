package SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class STClass extends Scope implements STType {
  private String name;
  private STType type;
  private Scope inheritedScope;

  public STClass(Scope definingScope, String name, Scope inheritedScope) {
    super(definingScope);
    this.name = name;
    this.type = this;
    this.inheritedScope = inheritedScope;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public STType getType() {
    return this.type;
  }

  public Scope getInheritedScope() {
    return inheritedScope;
  }

  public void setInheritedScope(Scope inheritedScope) {
    this.inheritedScope = inheritedScope;
  }

  @Override
  public Symbol resolve(String name) {
    List<Symbol> list = symbols.get(name);
    if (list != null) {
      return list.getFirst();
    }
    // Check super class first
    if (inheritedScope != null) {
      Symbol symbol = this.inheritedScope.resolve(name);
      if (symbol != null) {
        return symbol;
      }
    }
    try {
      return parent.resolve(name);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<Symbol> resolveAll(String name) {
    List<Symbol> list = symbols.get(name);
    if (list == null) {
      list = new ArrayList<Symbol>();
    }
    if (inheritedScope != null) {
      list.addAll(inheritedScope.resolveAll(name));
    }
    return list;
  }

  /** Returns the Scope in which this Class is defined */
  @Override
  public Scope getScope() {
    return this.parent;
  }

  /** Sets the Scope in which this Class is defined */
  @Override
  public void setScope(Scope scope) {

    this.parent = scope;
  }
}
