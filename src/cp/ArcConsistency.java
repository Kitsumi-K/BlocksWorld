package cp;

import java.util.*;
import modelling.*;

public class ArcConsistency {
    
    private Set<Constraint> constraints;

    public ArcConsistency(Set<Constraint> constraints) {
        for (Constraint constraint : constraints) {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() != 1 && scope.size() != 2) {
                throw new IllegalArgumentException("Une contrainte est Ni unaire ni binaire"); 
            }
        }
        this.constraints = constraints;
    }

    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains) {
        if (constraints != null) {
            Map<Variable, List<Object>> del = new HashMap<>();
            for (Variable var : domains.keySet()) {
                for (Object val : domains.get(var)) {
                    for (Constraint cons : constraints) {
                        if (cons.getScope().size() == 1 && cons.getScope().contains(var)) {
                            Map<Variable, Object> state = new HashMap<>();
                            state.put(var,val);
                            if (!cons.isSatisfiedBy(state)) {
                                if (del.containsKey(var))
                                    del.get(var).add(val);
                                else {
                                    List<Object> vals = new ArrayList<>();
                                    vals.add(val);
                                    del.put(var,vals);
                                }
                            }          
                        }
                    }  
                }
            }
            for (Variable var : domains.keySet()) {
                if (del.get(var) != null) {
                    Set<Object> domain = new HashSet<>();
                    for (Object val : domains.get(var)) {
                        if (!del.get(var).contains(val)) {
                            domain.add(val);
                        }
                    }
                    domains.put(var,domain);
                }
            }   
        }
        for (Variable var : domains.keySet()) {
            if (domains.get(var).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean revise(Variable variable1, Set<Object> domain1, Variable variable2, Set<Object> domain2) {
        boolean del = false;
        List<Object> suppr = new ArrayList<>();
        for (Object val1 : domain1) {
            boolean viable = false;
            for (Object val2 : domain2) {
                boolean satisfy = true;
                for (Constraint constraint : constraints) {
                    if (constraint.getScope().size() == 2 && constraint.getScope().contains(variable1) && constraint.getScope().contains(variable2)) {
                        Map<Variable, Object> state = new HashMap<>();
                        state.put(variable1, val1);
                        state.put(variable2, val2);
                        if (!constraint.isSatisfiedBy(state)) {
                            satisfy = false;
                            break;
                        }
                    }
                }
                if (satisfy) {
                    viable = true;
                    break;
                }
            }
            if (!viable) {
                suppr.add(val1);
                del = true;
            }
        }
        if (del) {
            for (Object val : suppr) {
                domain1.remove(val);
            }
        }
        return del;
    }

    public boolean ac1(Map<Variable, Set<Object>> domains) {
        if (!enforceNodeConsistency(domains)) {
            return false;
        }
        boolean change;
        do {
            change = false;
            for (Variable x1 : domains.keySet()) {
                for (Variable x2 : domains.keySet()) {
                    if (!x1.equals(x2)) {
                        if (revise(x2, x1.getDomain(), x1, x2.getDomain())) {
                            change = true;
                        }
                    }
                    
                }
            }
        } while (change);

        for (Variable x : domains.keySet()) {
            if (x.getDomain().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
