package blocksworld;

import java.util.*;
import modelling.*;

public class BlocksWorldWithConstraints extends BlocksWorld {

    protected Set<Constraint> constraints = new HashSet<>();
    
    public BlocksWorldWithConstraints(Integer nbBlock, Integer nbPile) {
        super(nbBlock, nbPile);
        
        createConstraints();
    }


    public void createConstraints() {
        Implication constraint;
        Implication constraintFree;
        DifferenceConstraint differenceConstraint;
        for (Variable var1 : variables) {
            for (Variable var2 : variables) {
                if (var1.getName().contains("on") && var2.getName().contains("on") && !var1.equals(var2)) {
                    differenceConstraint = new DifferenceConstraint(var1, var2);
                    constraints.add(differenceConstraint);
                }
                if (var1.getName().contains("on") && var2.getName().contains("fixed") && !var1.equals(var2)) {
                    Set<Object> S1 = new HashSet<>();
                    Set<Object> S2 = new HashSet<>();
                    S1.add(Integer.parseInt(var2.getName().replace("fixed", "")));
                    S2.add(true);
                    constraint = new Implication(var1, S1, var2, S2);
                    constraints.add(constraint);
                }
                if (var1.getName().contains("on") && var2.getName().contains("free") && !var1.equals(var2)) {
                    Set<Object> S1 = new HashSet<>();
                    Set<Object> S2 = new HashSet<>();
                    S1.add(Integer.parseInt(var2.getName().replace("free", "")));
                    S2.add(false);
                    constraintFree = new Implication(var1, S1, var2, S2);
                    constraints.add(constraintFree);
                }
            }
        }
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }
}
