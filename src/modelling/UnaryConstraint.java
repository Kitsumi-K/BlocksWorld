package modelling;

import java.util.*;

public class UnaryConstraint implements Constraint {

    private Variable v;
    private Set<Object> S;

    public UnaryConstraint(Variable v, Set<Object> s) {
        this.v = v;
        S = s;
    }

    public Variable getV() {
        return v;
    }

    public void setV(Variable v) {
        this.v = v;
    }

    public Set<Object> getS() {
        return S;
    }

    public void setS(Set<Object> s) {
        S = s;
    }

    public Set<Variable> getScope() {
        HashSet<Variable> res = new HashSet<Variable>();
        res.add(v);
        return res;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> map) throws IllegalArgumentException {
        if (map.containsKey(v)) {
            for (Object vBis : S) {
                if (vBis.equals(map.get(v))) {
                    return true;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("euh");
    }
    
}
