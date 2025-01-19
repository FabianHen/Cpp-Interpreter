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

  public HashMap<String, Object> getFields(ASTVisitor<Object> interpreter) {
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    if (superClass != null) {
      hashMap.putAll(superClass.getFields(interpreter));
    }
    for (ASTNode member : classDefNode.getChildren()) {
      if (member instanceof VardeclNode vardeclNode) {
        String varID = vardeclNode.getIdentifier().getIdNode().getId();
        Object value = vardeclNode.accept(interpreter);
        hashMap.put(varID, value);
      }
    }
    return hashMap;
  }

  @Override
  public Object call(Interpreter interpreter, List<ExprNode> args) {
    Environment env = interpreter.getEnvironment();
    Instance instance = new Instance(interpreter, this, interpreter.getEnvironment());
    interpreter.setEnvironment(instance);
    for (var func : instance.getFunctions(classDefNode.getIdNode().getId())) {
      if (func.getParameters().isEmpty()) {
        func.call(interpreter, args);
        interpreter.setEnvironment(env);
        return instance;
      }
    }
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
