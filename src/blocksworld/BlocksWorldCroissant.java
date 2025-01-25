package blocksworld;

import java.util.HashSet;
import java.util.Set;

// import java.util.*;
import modelling.*;

public class BlocksWorldCroissant extends BlocksWorldRegular{
    
    public BlocksWorldCroissant (Integer nbBlock, Integer nbPile) {
        super(nbBlock, nbPile);
    }

    public BlocksWorldCroissant createCroissantConstraint() {
        Implication croissantConstraint;
        for (Variable var1 : variables) {
            for (Variable var2 : variables) {
                if (var1.getName().contains("on") && var2.getName().contains("on") && !var1.equals(var2)) {
                    Integer var2Nb = Integer.parseInt(var2.getName().replace("on", ""));
                    Integer var1Nb = Integer.parseInt(var1.getName().replace("on", ""));
                    Integer gap = var1Nb - var2Nb;

                    Set<Object> S1 = new HashSet<Object>();
                    S1.add(var2Nb);
                    Set<Object> S2;

                    if (gap > 0) {
                        S2 = new HashSet<Object>(initialSet);
                        for (int i = 0; i < var2Nb; i++) 
                            S2.add(i);
                    }
                    else {
                        S2 = new HashSet<Object>();
                        S2.add(var2Nb);
                    }           

                    croissantConstraint = new Implication(var1, S1, var2, S2);
                    constraints.add(croissantConstraint);
                }
            }
        }
        return this;
    }
}
