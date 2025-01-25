package planning;

import java.util.*;
import modelling.*;

public class BasicGoal implements Goal {
    
    private Map<Variable,Object> instanciation;

    public BasicGoal(Map<Variable, Object> instanciation) {
        this.instanciation = instanciation;
    }

    public Map<Variable, Object> getInstanciation() {
        return instanciation;
    }

    public void setInstanciation(Map<Variable, Object> instanciation) {
        this.instanciation = instanciation;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        for (Variable v : instanciation.keySet()) {
            if (!instanciation.get(v).equals(state.get(v))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "instantiation : " + instanciation;
    }
}
