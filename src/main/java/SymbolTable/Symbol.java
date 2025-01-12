package SymbolTable;

public interface Symbol {
  String getName();

  STType getType();

  Scope getScope();

  void setScope(Scope scope);
}
