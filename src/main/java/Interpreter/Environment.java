package Interpreter;

import java.util.HashMap;

public class Environment {
    private Environment enclosing;
    private HashMap<String,Object> values;

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
        values = new HashMap<>();
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }

    public void assign(String name, Object value) {
        if(values.containsKey(name)){
            values.put(name,value);
        }else{
            enclosing.assign(name,value);
        }
    }

    public Object get(String name){
        Object value = values.get(name);
        if(value == null){
            value = enclosing.get(name);
        }
        return value;
    }

    public Environment getEnclosing() {
        return enclosing;
    }
}
