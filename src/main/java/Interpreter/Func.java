package Interpreter;

import AST.*;

import java.util.ArrayList;
import java.util.List;

public class Func implements Callable {
  private FndefNode fndefNode;
  private ConstructorNode constructorNode;
  private Environment environment;
  private List<ParamNode> parameters;
  private final String funcName;

  public Func(String name, Environment environment) {
    this.fndefNode = null;
    this.environment = environment;
    this.funcName = name;
    parameters = new ArrayList<ParamNode>();
  }

  public void bind(Environment environment) {
    this.environment = environment;
  }

  public List<ParamNode> getParameters() {
    return parameters;
  }

  public String getFuncName() {
    return funcName;
  }

  public ConstructorNode getConstructorNode() {
    return constructorNode;
  }

  public void setConstructorNode(ConstructorNode constructorNode) {
    this.constructorNode = constructorNode;
    this.parameters = constructorNode.getParams();
  }

  public FndefNode getFndefNode() {
    return fndefNode;
  }

  public void setFndefNode(FndefNode fndefNode) {
    this.fndefNode = fndefNode;
    this.parameters = fndefNode.getFndecl().getParams();
  }

  @Override
  public Object call(Interpreter interpreter, List<ExprNode> args) {
    Environment preEnv = interpreter.getEnvironment();
    Environment funcEnv = new Environment(environment);
    interpreter.setEnvironment(funcEnv);
    for(int i = 0; i < parameters.size(); i++) {
      ParamNode param = parameters.get(i);
      ExprNode value = args.get(i);
      funcEnv.defineVariable(param.getIdentifier().getIdNode().getId(), value.accept(interpreter));
    }
    Object returnValue;
    if(fndefNode == null){
      returnValue = constructorNode.getBlock().accept(interpreter);
    }else {
      returnValue = fndefNode.getBlock().accept(interpreter);
    }
    interpreter.setEnvironment(preEnv);
    return returnValue;
  }
}
