package planning;

import java.util.Map;

import modelling.Variable;

public class BlocksWorldGoal extends BasicGoal {
    
    public BlocksWorldGoal(Map<Variable, Object> instanciation) {
        super(instanciation);
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        for (Variable v : instanciation.keySet()) {
            if (v.getName().contains("on") && !instanciation.get(v).equals(state.get(v))) {
                return false;
            }
        }
        return true;
    }


}
