package blocksworld;

import java.util.*;

import modelling.*;

public class BlocksWorldRegular extends BlocksWorldWithConstraints{

    protected Set<Object> initialSet;
    
    public BlocksWorldRegular(Integer nbBlock, Integer nbPile) {
        super(nbBlock, nbPile);

        initialSet = new HashSet<Object>();
        for (Variable var : variables) {
            if (var.getName().contains("free")) {
                initialSet.add(Integer.parseInt(var.getName().replace("free", "")));
            }
        }
    }

    public BlocksWorldRegular createWithRegularConstraint() {
        Implication regularConstraint;
        for (Variable var1 : variables) {
            for (Variable var2 : variables) {
                if (var1.getName().contains("on") && var2.getName().contains("on") && !var1.equals(var2)) {
                    Integer var2Nb = Integer.parseInt(var2.getName().replace("on", ""));
                    Integer var1Nb = Integer.parseInt(var1.getName().replace("on", ""));
                    Integer gap = var1Nb - var2Nb;

                    Set<Object> S1 = new HashSet<Object>();
                    S1.add(var2Nb);
                    Set<Object> S2 = new HashSet<Object>(initialSet);
                    S2.add(var2Nb-gap);

                    regularConstraint = new Implication(var1,S1, var2, S2);
                    constraints.add(regularConstraint);
                }
            }
        }
        return this;
    }
}
