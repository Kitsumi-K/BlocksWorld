package blocksworld;

import planning.*;
import java.util.*;

import modelling.*;

public class BlocksWorldWithAction extends BlocksWorldCroissant {
    
    protected Set<Action> actions = new HashSet<>();

    public BlocksWorldWithAction(Integer nbBlock, Integer nbPile) {
        super(nbBlock, nbPile);

        createCroissantConstraint();
        createWithRegularConstraint();
        createActions();
    }

    public void createActions() {

        for (Variable var : variables) {
            if (var.getName().contains("fixed")) {

                Map<Variable,Object> precondition;
                Map<Variable,Object> effect;

                for (Variable var2 : variables) {
                    for (Variable var3 : variables) {
                        if (!var2.equals(var3) && !var.equals(var3) && !var.equals(var2)) {

                            precondition = new HashMap<>();
                            effect = new HashMap<>();
                            precondition.put(var, false);

                            if (var2.getName().contains("fixed") && var3.getName().contains("fixed")) {
                                Variable varOn = new Variable("on" + var.getName().replace("fixed", ""), null);
                                Integer valueVar2 = Integer.parseInt(var2.getName().replace("fixed", ""));
                                Integer valueVar3 = Integer.parseInt(var3.getName().replace("fixed", ""));
                                precondition.put(var3, false);
                                precondition.put(varOn, valueVar2);
                                effect.put(varOn, valueVar3);
                                effect.put(var2, false);
                                effect.put(var3, true);
                                actions.add(new BasicAction(precondition, effect, 1));
                            }

                            if (var2.getName().contains("fixed") && var3.getName().contains("free")) {
                                Variable varOn = new Variable("on" + var.getName().replace("fixed", ""), null);
                                Integer valueVar2 = Integer.parseInt(var2.getName().replace("fixed", ""));
                                Integer valueVar3 = Integer.parseInt(var3.getName().replace("free", ""));
                                precondition.put(var3, true);
                                precondition.put(varOn, valueVar2);
                                effect.put(varOn, valueVar3);
                                effect.put(var2, false);
                                effect.put(var3, false);
                                actions.add(new BasicAction(precondition, effect, 1));
                            }

                            if (var2.getName().contains("free") && var3.getName().contains("fixed")) {
                                Variable varOn = new Variable("on" + var.getName().replace("fixed", ""), null);
                                Integer valueVar2 = Integer.parseInt(var2.getName().replace("free", ""));
                                Integer valueVar3 = Integer.parseInt(var3.getName().replace("fixed", ""));
                                precondition.put(var3, false);
                                precondition.put(varOn, valueVar2);
                                effect.put(varOn, valueVar3);
                                effect.put(var2, true);
                                effect.put(var3, true);
                                actions.add(new BasicAction(precondition, effect, 1));
                            }

                            if (var2.getName().contains("free") && var3.getName().contains("free")) {
                                Variable varOn = new Variable("on" + var.getName().replace("fixed", ""), null);
                                Integer valueVar2 = Integer.parseInt(var2.getName().replace("free", ""));
                                Integer valueVar3 = Integer.parseInt(var3.getName().replace("free", ""));
                                precondition.put(var3, true);
                                precondition.put(varOn, valueVar2);
                                effect.put(varOn, valueVar3);
                                effect.put(var2, true);
                                effect.put(var3, false);
                                actions.add(new BasicAction(precondition, effect, 1));
                            }
                        }
                    }
                }
            }
        }
    }

    public Set<Action> getActions() {
        return actions;
    }

}
