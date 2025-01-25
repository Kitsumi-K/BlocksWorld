package planning;

import java.util.Map;
import modelling.Variable;

public interface Goal {
    
    abstract boolean isSatisfiedBy(Map<Variable,Object> state);
}
