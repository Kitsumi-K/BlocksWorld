package cp;

import java.util.*;
import java.util.Map.*;
import modelling.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic{
    
    boolean largestDomain;

    public DomainSizeVariableHeuristic(boolean largestDomain) {
        this.largestDomain = largestDomain;
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
        Map<Variable, Integer> sizes = new HashMap<>();
        for (Variable variable : variables) {
            sizes.put(variable,domains.get(variable).size());
        }
        if (largestDomain) {
            return max(sizes);
        }
        return min(sizes);
    }

}
