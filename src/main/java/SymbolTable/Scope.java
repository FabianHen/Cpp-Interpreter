package SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scope {
  protected Scope parent;
  protected HashMap<String, List<Symbol>> symbols;

  public Scope(Scope parent) {
    this.parent = parent;
    symbols = new HashMap<>();
  }

  public Scope getParent() {
    return parent;
  }

  public void bind(Symbol symbol) {
    symbol.setScope(this);
    List<Symbol> list = symbols.get(symbol.getName());
    if (list == null) {
      list = new ArrayList<>();
    }
    list.add(symbol);
    symbols.put(symbol.getName(), list);
  }

  public Symbol resolve(String name) {
    List<Symbol> list = symbols.get(name);
    if (list != null) {
      return list.getFirst();
    }
    try {
      return parent.resolve(name);
    } catch (Exception e) {
      return null;
    }
  }

  public List<Symbol> resolveAllMember(String name) {
    return symbols.get(name) == null ? new ArrayList<>() : symbols.get(name);
  }

  public List<Symbol> resolveAll(String name) {
    List<Symbol> list = symbols.get(name);
    if (list == null) {
      list = new ArrayList<>();
    }
    try {
      list.addAll(parent.resolveAll(name));
      return list;
    } catch (Exception e) {
      return list;
    }
  }

  public Symbol resolveMember(String name) {
    List<Symbol> list = symbols.get(name);
    if (list != null) {
      return list.getFirst();
    }
    return null;
  }
}
