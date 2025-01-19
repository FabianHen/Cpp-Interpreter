package Interpreter;

import AST.ExprNode;
import java.util.List;

public class Instance extends Environment implements Callable {
  private final Clazz clazz;
  private Interpreter interpreter;

  public Instance(Interpreter interpreter, Clazz clazz, Environment enclosing) {
    super(enclosing);
    this.clazz = clazz;

    Environment environment = interpreter.getEnvironment();
    interpreter.setEnvironment(this);
    clazz.setFields(interpreter);
    interpreter.setEnvironment(environment);

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

  public void copyInstance(Interpreter interpreter, Instance instance) {
    for (var key : this.values.keySet()) {
      assignVariable(key, ((Value) instance.getVariable(key)).getValue());
    }
  }

  @Override
  public Object call(Interpreter interpreter, List<ExprNode> args) {
    Environment env = interpreter.getEnvironment();
    interpreter.setEnvironment(this);
    for (var func : this.getFunctions(clazz.getClassDefNode().getIdNode().getId())) {
      if (func.getConstructorNode().isCopyCon()) {
        clazz.callAllSuperConstructors(this, interpreter);
        func.call(interpreter, args);
        interpreter.setEnvironment(env);
        return this;
      }
    }
    interpreter.setEnvironment(env);
    Instance instance = (Instance) ((Value) args.getFirst().accept(interpreter)).getValue();
    interpreter.setEnvironment(this);
    clazz.callAllSuperConstructors(this, interpreter);
    this.copyInstance(interpreter, instance);
    interpreter.setEnvironment(env);
    return this;
  }
}
