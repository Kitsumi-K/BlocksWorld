package cp;

import java.util.*;
import modelling.*;

public class MACSolver extends AbstractSolver {

    private ArcConsistency arcConsistency;
    
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
        arcConsistency = new ArcConsistency(constraints);
    }

    public Map<Variable, Object> solve() {
        LinkedList<Variable> variables = new LinkedList<>();
        variables.addAll(super.variables);
        Map<Variable, Set<Object>> domains = new HashMap<>();
        for (Variable v : variables) {
            domains.put(v, new HashSet<>(v.getDomain()));
        }
        return MAC(new HashMap<Variable,Object>() ,variables, domains);
    }

    public Map<Variable, Object> MAC(Map<Variable, Object> partialInstance, LinkedList<Variable> variables, Map<Variable, Set<Object>> domains) {
        if (variables == null || variables.isEmpty()) {
            return partialInstance;
        }
        if (!arcConsistency.ac1(domains)) {
            return null;
        }
        Variable variable = variables.pop();
        for (Object value : domains.get(variable)) {
            partialInstance.put(variable, value);
            if (super.isConsistent(partialInstance)) {
                Map<Variable, Object> result = MAC(partialInstance, variables, domains);
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
