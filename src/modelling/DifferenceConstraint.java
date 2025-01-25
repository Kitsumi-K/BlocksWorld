package modelling;

import java.util.*;

public class DifferenceConstraint implements Constraint{
    
    private Variable v1;
    private Variable v2;

    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Set<Variable> getScope() {
        HashSet<Variable> res = new HashSet<Variable>();
        res.add(v1);
        res.add(v2);
        return res;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> map) throws IllegalArgumentException{
        if (!map.containsKey(v1) || !map.containsKey(v2)) {
            throw new IllegalArgumentException("euh");
        }
        return !(map.get(v1).equals(map.get(v2)));       
    }

    public Variable getV1() {
        return v1;
    }

    public Variable getV2() {
        return v2;
    }
}
