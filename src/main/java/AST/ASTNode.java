package AST;

import SymbolTable.Scope;
import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {
  protected Scope currentScope;
  private final List<ASTNode> children;
  private int line;

  public ASTNode(int line) {
    children = new ArrayList<>();
    this.line = line;
  }

  public int getLine() {
    return line;
  }

  public void addChild(ASTNode child) {
    children.add(child);
  }

  public List<ASTNode> getChildren() {
    return children;
  }

  public ASTNode getChild(int i) {
    return children.get(i);
  }

  public Scope getCurrentScope() {
    return currentScope;
  }

  public void setCurrentScope(Scope currentScope) {
    this.currentScope = currentScope;
  }

  public abstract <T> T accept(ASTVisitor<T> visitor);

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
