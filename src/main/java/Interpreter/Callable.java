package Interpreter;

import AST.ExprNode;
import java.util.List;

public interface Callable {
  public Object call(Interpreter interpreter, List<ExprNode> args);
}
