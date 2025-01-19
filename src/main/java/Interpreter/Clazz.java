package Interpreter;

import AST.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clazz implements Callable {
  Clazz superClass;
  private final ClassDefNode classDefNode;
  private HashMap<String, List<Func>> functions;

  public Clazz(ClassDefNode classDefNode) {
    superClass = null;
    this.classDefNode = classDefNode;
  }

  public Clazz(Clazz superClass, ClassDefNode classDefNode) {
    this.superClass = superClass;
    this.classDefNode = classDefNode;
    functions = new HashMap<String, List<Func>>();
  }

  public void putFunc(Func func) {
    if (functions.containsKey(func.getFuncName())) {
      functions.get(func.getFuncName()).add(func);
      return;
    }
    ArrayList<Func> arrayList = new ArrayList<Func>();
    arrayList.add(func);
    functions.put(func.getFuncName(), arrayList);
  }

  public List<Func> getFuncs(String name) {
    List<Func> funcs = new ArrayList<Func>();
    if (functions.containsKey(name)) {
      funcs.addAll(functions.get(name));
    }
    if (this.superClass != null) {
      funcs.addAll(this.superClass.getFuncs(name));
    }
    return funcs;
  }

  public ClassDefNode getClassDefNode() {
    return classDefNode;
  }

  public void setFields(ASTVisitor<Object> interpreter) {
    if (superClass != null) {
      superClass.setFields(interpreter);
    }
    for (ASTNode member : classDefNode.getChildren()) {
      if (member instanceof VardeclNode vardeclNode) {
        vardeclNode.accept(interpreter);
      }
    }
  }

  public void callAllSuperConstructors(Instance instance, Interpreter interpreter) {
    Clazz currentClass = this.superClass;
    List<ExprNode> args = new ArrayList<>();
    while (currentClass != null) {
      for (var func : instance.getFunctions(currentClass.getClassDefNode().getIdNode().getId())) {
        if (func.getParameters().isEmpty()) {
          func.call(interpreter, args);
        }
      }
      currentClass = currentClass.superClass;
    }
  }

  @Override
  public Object call(Interpreter interpreter, List<ExprNode> args) {
    Environment env = interpreter.getEnvironment();
    Instance instance = new Instance(interpreter, this, interpreter.getEnvironment());
    interpreter.setEnvironment(instance);
    for (var func : instance.getFunctions(classDefNode.getIdNode().getId())) {
      if (func.getParameters().isEmpty()) {
        callAllSuperConstructors(instance, interpreter);
        func.call(interpreter, args);
        interpreter.setEnvironment(env);
        return instance;
      }
    }
    callAllSuperConstructors(instance, interpreter);
    interpreter.setEnvironment(env);
    return instance;
  }

  public Object call(
      Interpreter interpreter, List<ExprNode> args, ConstructorCallNode constructorCallNode) {
    Environment env = interpreter.getEnvironment();
    Instance instance = new Instance(interpreter, this, interpreter.getEnvironment());
    interpreter.setEnvironment(instance);
    for (var func : instance.getFunctions(classDefNode.getIdNode().getId())) {
      if (func.getConstructorNode()
          .equals(constructorCallNode.getFunction().getConstructorNode())) {
        callAllSuperConstructors(instance, interpreter);
        func.call(interpreter, args);
        interpreter.setEnvironment(env);
        return instance;
      }
    }
    interpreter.setEnvironment(env);
    System.err.println("Fitting constructor is missing!");
    System.exit(0);
    return null;
  }
}
