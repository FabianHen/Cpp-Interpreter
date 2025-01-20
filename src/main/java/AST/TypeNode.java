package AST;

import java.util.*;

public class TypeNode extends ASTNode {
  // Fields
  private Type type;
  private String className;

  // Constructor
  public TypeNode(Type type, int line) {
    super(line);
    this.type = type;
    this.className = type.name().toLowerCase();
  }

  public TypeNode(String className, int line) {
    super(line);
    this.className = className;
    this.type = Type.CUSTOM;
  }

  // Getters and Setters
  public Type getType() {
    return this.type;
  }

  public String getClassName() {
    return this.className;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    return super.toString() + " " + (type == Type.CUSTOM ? className : type.toString());
  }
}
