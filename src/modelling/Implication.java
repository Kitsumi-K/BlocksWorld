package modelling;

import java.util.*;

public class Implication implements Constraint{
    
    private Variable v1;
    private Set<Variable> S1;
    private Variable v2;
    private Set<Variable> S2;

    public Implication(Variable v1, Set<Variable> S1, Variable v2, Set<Variable> S2) {
        this.v1 = v1;
        this.S1 = S1;
        this.v2 = v2;
        this.S2 = S2;
    }

    public Set<Variable> getScope() {
        HashSet<Variable> res = new HashSet<Variable>();
        res.add(v1);
        res.add(v2);
        return res;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> map) throws IllegalArgumentException{
        if (map.containsKey(v1) && map.containsKey(v2)) {
            for (Object v1Bis : S1) {
                if (v1Bis.equals(map.get(v1))) {
                    for (Object v2Bis : S2) {
                        if (v2Bis.equals(map.get(v2))) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            return true;
        }
        throw new IllegalArgumentException("euh");
    }
}
