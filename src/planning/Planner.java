package planning;

import java.util.*;
import modelling.*;

public interface Planner {
    
    abstract List<Action> plan();
    abstract Map<Variable,Object> getInitialState();
    abstract Set<Action> getActions();
    abstract Goal getGoal();
}
