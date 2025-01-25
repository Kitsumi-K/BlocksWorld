package planning;

import java.util.Map;
import modelling.Variable;

public interface Heuristic {
    
    abstract float estimate(Map<Variable,Object> state);
}
