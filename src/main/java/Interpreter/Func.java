package Interpreter;

import AST.*;
import java.util.ArrayList;
import java.util.List;

public class Func implements Callable {
  private FndefNode fndefNode;
  private ConstructorNode constructorNode;
  private OperatorNode operatorNode;

  private Environment environment;
  private List<ParamNode> parameters;
  private final String funcName;

  public Func(String name, Environment environment) {
    this.fndefNode = null;
    this.constructorNode = null;
    this.operatorNode = null;
    this.environment = environment;
    this.funcName = name;
    parameters = new ArrayList<>();
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

  public void setOperatorNode(OperatorNode operatorNode) {
    this.operatorNode = operatorNode;
    ArrayList<ParamNode> params = new ArrayList<>();
    ParamNode param =
        new ParamNode(
            new TypeNode(operatorNode.getParamType().getId(), operatorNode.getLine()),
            new IdentifierNode(operatorNode.getParamName(), true, operatorNode.getLine()),
            operatorNode.getLine());
    params.add(param);
    this.parameters = params;
  }

  @Override
  public Object call(Interpreter interpreter, List<ExprNode> args) {
    Environment preEnv = interpreter.getEnvironment();
    Environment funcEnv = new Environment(environment);
    interpreter.setEnvironment(funcEnv);
    for (int i = 0; i < parameters.size(); i++) {
      ParamNode param = parameters.get(i);
      ExprNode value = args.get(i);
      interpreter.setEnvironment(preEnv);
      Value argsValue = (Value) value.accept(interpreter);
      interpreter.setEnvironment(funcEnv);
      if (param.getIdentifier().isReference()) {
        funcEnv.defineVariable(
            param.getIdentifier().getIdNode().getId(),argsValue);
      } else {
        funcEnv.defineVariable(
            param.getIdentifier().getIdNode().getId(), new Value(argsValue.getValue()));
      }
    }
    Object returnValue;
    if (constructorNode != null) {
      returnValue = constructorNode.getBlock().accept(interpreter);
    } else if (fndefNode != null) {
      returnValue = fndefNode.getBlock().accept(interpreter);
    } else {
      returnValue = operatorNode.getBlock().accept(interpreter);
    }
    interpreter.setEnvironment(preEnv);
    return returnValue;
  }
}
