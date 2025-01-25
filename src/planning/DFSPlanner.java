package planning;

import java.util.*;
import modelling.*;

public class DFSPlanner implements Planner{
    
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    private final Integer MAX_DEPTH = 1000;

    private Boolean sonde = false;
    private int nodeCount = 0;
    
    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    public void activateNodeCount() {
        sonde = true;
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

    public List<Action> dfs(Map<Variable, Object> instantiation, List<Action> plan, Set<Map<Variable, Object>> closed, Integer depth) {
        Map<Variable, Object> next;
        if (goal.isSatisfiedBy(instantiation)) {
            if (sonde)
                System.out.println("Nombre de noeuds explore : " + nodeCount);
            return plan;
        }  
        if (sonde)
            nodeCount++;

        if (MAX_DEPTH < depth) 
            return null;

        for (Action action : actions) {
            if (action.isApplicable(instantiation)) {
                // System.out.println(action);
                next = action.successor(instantiation);
                // System.out.println(next);
                if (!closed.contains(next)) {
                    plan.add(action);
                    closed.add(next);
                    List<Action> subplan = dfs(next, plan, closed, depth + 1);
                    if (subplan != null) {
                        return subplan;
                    }
                    plan.remove(plan.size()-1);
                }
            }
        }
        return null;
    }  
    
    public List<Action> plan() {
        List<Action> plan = new ArrayList<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        return dfs(initialState, plan, closed, 0);
    }
}
