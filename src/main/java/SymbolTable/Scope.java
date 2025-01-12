package SymbolTable;

import java.util.HashMap;

public class Scope {
  protected Scope parent;
  protected HashMap<String, Symbol> symbols;

  public Scope(Scope parent) {
    this.parent = parent;
    symbols = new HashMap<>();
  }

  public Scope getParent() {
    return parent;
  }

  public void bind(Symbol symbol) {
    symbol.setScope(this);
    symbols.put(symbol.getName(), symbol);
  }

  public Symbol resolve(String name) {
    Symbol symbol = symbols.get(name);
    if (symbol != null) {
      return symbol;
    }
    try {
      return parent.resolve(name);
    } catch (Exception e) {
      return null;
    }
  }

  public Symbol resolveMember(String name) {
    return this.symbols.get(name);
  }
}
