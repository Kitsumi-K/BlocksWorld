package planning;

import java.util.*;
import modelling.*;

public class DFSPlanner implements Planner{
    
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    
    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

    public void setInitialState(Map<Variable, Object> initialState) {
        this.initialState = initialState;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public List<Action> plan() {
        if (goal.isSatisfiedBy(initialState)) {
            List<Action> res = new ArrayList<>();
            res.addAll(actions);
            return res;
        }
        for (Action action : actions) {
            if (action.isApplicable(initialState)) {
                Map<Variable,Object> next = action.successor(initialState);
                if (!)
            }
        }
    }

    public String toString() {
        return "initialState : " + initialState + ", actions : " + actions + ", goal " + goal; 
    }
    
}
