package planning;

import java.util.Map;

import blocksworld.*;
import modelling.Variable;

public class RightPlaceHeuristic extends BlocksWorldHeuristic {
    
    private Map<Variable, Object> finalState;

    public RightPlaceHeuristic(BlocksWorld blocksWorld, Map<Variable, Object> finalState) {
        super(blocksWorld);
        this.finalState = finalState;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        float count = 0;
        for (Variable var : state.keySet()) {
            if (var.getName().contains("on")) {
                if (!isRightPlace(var, state))
                    count++;
            }
        }
        return count;
    }

    public Boolean isRightPlace(Variable block, Map<Variable, Object> state) {
        if (!state.containsKey(block))
            throw new IllegalArgumentException("Variable " + block + " not found in state");
        Integer value = (Integer) state.get(block);
        if (value.equals(finalState.get(block))) {
            if ((Integer) value > 0)
                return isRightPlace(new Variable("on" + value, null), state);
            else 
                return true;
        }
        else {
            return false;
        }
    }

    
}
