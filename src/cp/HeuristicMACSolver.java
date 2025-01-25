package cp;

import java.util.*;
import modelling.*;

public class HeuristicMACSolver extends AbstractSolver {
    
    VariableHeuristic variableHeuristic;
    ValueHeuristic valueHeuristic;
    private ArcConsistency arcConsistency;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints,
            VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
        arcConsistency = new ArcConsistency(constraints);
    }

    public Map<Variable, Object> solve() {
        ArrayList<Variable> variables = new ArrayList<>();
        variables.addAll(super.variables);
        return HeuristicMAC(new HashMap<Variable,Object>() ,variables);
    }

    public Map<Variable, Object> HeuristicMAC(Map<Variable, Object> partialInstance, ArrayList<Variable> variables) {
        if (variables == null || variables.isEmpty()) {
            return partialInstance;
        }
        Map<Variable, Set<Object>> domains = new HashMap<>();
        for (Variable v : variables) {
            domains.put(v, v.getDomain());
        }
        if (!arcConsistency.ac1(domains)) {
            return null;
        }
        Variable variable = variableHeuristic.best(new HashSet<>(variables), domains);
        variables.remove(variable);

        domains.put(variable, new HashSet<>(valueHeuristic.ordering(variable,domains.get(variable))));
        
        for (Object value : domains.get(variable)) {
            partialInstance.put(variable, value);
            if (super.isConsistent(partialInstance)) {
                Map<Variable, Object> result = HeuristicMAC(partialInstance, variables);
                if (result != null) {
                    return result;
                }
            }       
        }
        partialInstance.remove(variable);
        variables.add(variable);
        return null;
    }
}
