package AST;

import java.util.*;

public class ParamlistNode extends ASTNode {
    // Fields
    private List<ParamNode> params;

    // Constructor
    public ParamlistNode(List<ParamNode> params) {
        super();
        this.params = params;
    }

    // Getters and Setters
    public void setParams(List<ParamNode> params) {
        this.params = params;
    }
    public void addParamNode(ParamNode param) {
        this.params.add(param);
    }
    public List<ParamNode> getParams() {
        return this.params;
    }
    public ParamNode getParamNode(int index) {
        return this.params.get(index);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
