package modelling;

import java.util.*;

public class DifferenceConstraint implements Constraint{
    
    protected Variable v1;
    protected Variable v2;

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
            throw new IllegalArgumentException("Une des variables n'est pas contenu dans l'état");
        }
        return !(map.get(v1).equals(map.get(v2)));       
    }

    public Variable getV1() {
        return v1;
    }

    public Variable getV2() {
        return v2;
    }

    public String toString() {
        return "[ "+ v1.getName() + " != " + v2.getName() + " ]";
    }
}
