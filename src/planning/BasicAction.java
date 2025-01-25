package planning;

import java.util.*;
import modelling.*;

public class BasicAction implements Action{

    private Map<Variable,Object> precondition;
    private Map<Variable,Object> effect;
    private int cost;

    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost) {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    public Map<Variable, Object> getPrecondition() {
        return precondition;
    }

    public void setPrecondition(Map<Variable, Object> precondition) {
        this.precondition = precondition;
    }

    public Map<Variable, Object> getEffect() {
        return effect;
    }

    public void setEffect(Map<Variable, Object> effect) {
        this.effect = effect;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
        for (Variable v : precondition.keySet()) {
            if (state.containsKey(v)) {
                if (!precondition.get(v).equals(state.get(v))) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    public Map<Variable,Object> successor(Map<Variable,Object> state) {
        Map<Variable,Object> res = new HashMap<Variable,Object>();
        if (state.size() == 0) {
            return effect;
        }
        for (Variable v : state.keySet()) {
            res.put(v, state.get(v));
        }
        for (Variable v : effect.keySet()) {
            res.put(v, effect.get(v));
        }
        return res;
    }
    
    @Override
    public String toString() {
        return "precondition : " + precondition + " effect : " + effect + " cost : " + cost;    }
    
}
