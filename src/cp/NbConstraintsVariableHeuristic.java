package cp;

import java.util.*;
import java.util.Map.*;
import modelling.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    
    Set<Constraint> constraints;
    boolean inMostConstraints;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean inMostConstraints) {
        this.constraints = constraints;
        this.inMostConstraints = inMostConstraints;
    }

    public <T> T min(Map<T,Integer> map) {
        Integer minValue = Integer.MAX_VALUE;
        T minKey = null;
        for (Entry<T, Integer> entry : map.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }
        return minKey;
    }

    public <T> T max(Map<T,Integer> map) {
        Integer maxValue = Integer.MIN_VALUE;
        T maxKey = null;
        for (Entry<T, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        return maxKey;
    }

    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Map<Variable, Integer> count = new HashMap<>();
        for (Variable variable : variables) {
            count.put(variable, 0);
            for (Constraint constraint : constraints) {
                if (constraint.getScope().contains(variable)) {
                    count.put(variable, count.get(variable) + 1);
                }
            }
        }
        if (inMostConstraints) {
            return max(count);
        }
        return min(count);
    }
}
