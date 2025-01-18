package Interpreter;

import AST.ASTVisitor;
import SymbolTable.Function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Instance extends Environment {
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
        for(var func : funcs){
            func.bind(interpreter.getEnvironment());
        }
        return funcs;
    }
}
