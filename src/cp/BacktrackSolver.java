package cp;

import java.util.*;
import modelling.*;

public class BacktrackSolver extends AbstractSolver{

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    public Map<Variable, Object> solve() {
        LinkedList<Variable> variables = new LinkedList<>();
        variables.addAll(super.variables);
        return BT(new HashMap<Variable,Object>() ,variables);
    }

    public Map<Variable, Object> BT(Map<Variable,Object> partialInstance, LinkedList<Variable> variables) {
        if (variables == null || variables.isEmpty()) {
            return partialInstance;
        }
        Variable variable = variables.pop();
        for (Object value : variable.getDomain()) {
            partialInstance.put(variable, value);
            if (super.isConsistent(partialInstance)) {
                Map<Variable, Object> result = BT(partialInstance, variables);
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
