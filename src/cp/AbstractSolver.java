package cp;

import java.util.*;
import modelling.*;

public abstract class AbstractSolver implements Solver{
    
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public boolean isConsistent(Map<Variable, Object> partialAffectation) {
        for (Constraint constraint : constraints) {
            if (partialAffectation.keySet().containsAll(constraint.getScope())) {
                if (!constraint.isSatisfiedBy(partialAffectation)) 
                return false;
            }
            
        }
        return true;
    }
}
