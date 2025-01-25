package planning;

import java.util.*;
import modelling.*;

public class DijkstraPlanner implements Planner{
    
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    private Boolean sonde = false;
    
    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
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

    public Map<Variable, Object> argMin(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> distance) {
        if (open.isEmpty()) {
            return null;
        }
        Float min = Float.MAX_VALUE;
        Map<Variable, Object> minState = null;
        for (Map<Variable, Object> state : open) {
            if (distance.get(state) < min) {
                min = distance.get(state);
                minState = state;
            }
        }
        return minState;
    }

    public List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Float> distance) {
        LinkedList<Action> DIJ_plan = new LinkedList<>();
        Map<Variable, Object> goal = argMin(goals, distance);
        while (father.get(goal) != null) {
            DIJ_plan.add(plan.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(DIJ_plan);
        return DIJ_plan;
    }

    public List<Action> dijkstra(Map<Map<Variable, Object>, Action> plan, Map<Map<Variable, Object>, Float> distance, Map<Map<Variable, Object>, Map<Variable, Object>> father) {
        Set<Map<Variable, Object>> goals = new HashSet<>();
        father.put(initialState, null);
        distance.put(initialState, 0f);
        Set<Map<Variable, Object>> open = new HashSet<>();

        Map<Variable, Object> instantiation = initialState;
        open.add(instantiation);
        Map<Variable, Object> next;
        
        int nodeCount = 0;

        while (!open.isEmpty()) {
            
            instantiation = argMin(open, distance);
            open.remove(instantiation);
            if (goal.isSatisfiedBy(instantiation)) {
                goals.add(instantiation);
            }
            
            nodeCount ++;
            for (Action action : actions) {
                if (action.isApplicable(instantiation)) {
                    next = action.successor(instantiation);
                    if (!distance.containsKey(next)) {
                        distance.put(next, Float.MAX_VALUE);
                    }
                    if (distance.get(next) > distance.get(instantiation) + (float) action.getCost()) {
                        distance.put(next, distance.get(instantiation) + (float) action.getCost());
                        father.put(next, instantiation);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        if (goals.isEmpty()) {
            return null;
        }
        else {
            if (sonde)
                System.out.println("Nombre de noeuds explore : " + nodeCount);
            return getDijkstraPlan(father, plan, goals, distance);
        }
    }
    
    public List<Action> plan() {
        Map<Map<Variable,Object>, Map<Variable,Object>> father = new HashMap<>();
        Map<Map<Variable,Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        return dijkstra(plan, distance, father);
    }
}


