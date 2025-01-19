package Interpreter;

import AST.ExprNode;

import java.util.List;

public class Instance extends Environment implements Callable {
  private final Clazz clazz;
  private Interpreter interpreter;

  public Instance(Interpreter interpreter, Clazz clazz, Environment enclosing) {
    super(enclosing);
    this.clazz = clazz;
    this.values = clazz.getFields(interpreter);
    this.interpreter = interpreter;
  }

  @Override
  public void assignVariable(String name, Object value) {
    super.assignVariable(name, value);
  }

  @Override
  public Object getVariable(String name) {
    return super.getVariable(name);
  }

  @Override
  public List<Func> getFunctions(String name) {
    List<Func> funcs = clazz.getFuncs(name);
    for (var func : funcs) {
      func.bind(interpreter.getEnvironment());
    }
    return funcs;
  }

  private void copyCon(Interpreter interpreter, List<ExprNode> args){
    Instance instance = (Instance) args.getFirst().accept(interpreter);
    for(var key: this.values.keySet()){
      assignVariable(key, instance.getVariable(key));
    }
  }

  @Override
  public Object call(Interpreter interpreter, List<ExprNode> args) {
    Environment env = interpreter.getEnvironment();
    interpreter.setEnvironment(this);
    for (var func : this.getFunctions(clazz.getClassDefNode().getIdNode().getId())) {
      if (func.getConstructorNode().isCopyCon()) {
        func.call(interpreter, args);
        interpreter.setEnvironment(env);
        return this;
      }
    }
    this.copyCon(interpreter, args);
    interpreter.setEnvironment(env);
    return this;
  }
}
