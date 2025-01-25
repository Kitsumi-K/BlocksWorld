package planning;

import java.util.*;

import blocksworld.BlocksWorld;
import modelling.*;

public class BlocksWorldHeuristicDiff extends BlocksWorldHeuristic{

    private Map<Variable, Object> finalState;

    public BlocksWorldHeuristicDiff(BlocksWorld blocksWorld, Map<Variable, Object> finalState) {
        super(blocksWorld);
        this.finalState = finalState;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        float count = 0;
        for (Variable var : state.keySet()) {
            if (!state.get(var).equals(finalState.get(var))) {
                count++;
            }
        }
        return count;
    }

    public Map<Variable, Object> getFinalState() {
        return finalState;
    }
    
    
}
