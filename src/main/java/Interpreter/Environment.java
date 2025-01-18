package Interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Environment {
  private Environment enclosing;
  private HashMap<String, List<Func>> functions;
  protected HashMap<String, Object> values;

  public Environment(Environment enclosing) {
    this.enclosing = enclosing;
    values = new HashMap<>();
    functions = new HashMap<>();
  }

  public void defineVariable(String name, Object value) {
    values.put(name, value);
  }

  public void assignVariable(String name, Object value) {
    if (values.containsKey(name)) {
      values.put(name, value);
    } else {
      enclosing.assignVariable(name, value);
    }
  }

  public void defineFunction(String name, Func func) {
    if (functions.containsKey(name)) {
      functions.get(name).add(func);
      return;
    }
    ArrayList<Func> arrayList = new ArrayList<Func>();
    arrayList.add(func);
    functions.put(name, arrayList);

  }


  public Object getVariable(String name) {
    Object value = values.get(name);
    if (value == null && enclosing != null) {
      value = enclosing.getVariable(name);
    }
    return value;
  }

  public List<Func> getFunctions(String name) {
    List<Func> values = new ArrayList<>();
    if(functions.containsKey(name)) {
      values = functions.get(name);
    }
    if(enclosing != null) {
      values.addAll(enclosing.getFunctions(name));
    }
    return values;
  }

  public Environment getEnclosing() {
    return enclosing;
  }

  public void clear(){
    values.clear();
  }

}
