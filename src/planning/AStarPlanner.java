package planning;

import java.util.*;
import modelling.*;

public class AStarPlanner implements Planner{
    
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristic;

    private Boolean sonde = false;
    
    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
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

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Map<Variable, Object> argMin(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> distance) {
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

    public List<Action> getBfsPlan(Map<Map<Variable,Object>, Map<Variable,Object>> father, Map<Map<Variable,Object>, Action> plan, Map<Variable, Object> goal) {
        LinkedList<Action> BFS_plan = new LinkedList<>();
        while (father.get(goal) != null) {
            BFS_plan.add(plan.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(BFS_plan);
        return BFS_plan;
    }

    public List<Action> astar(Map<Map<Variable,Object>, Action> plan, Map<Map<Variable,Object>, Map<Variable,Object>> father, Map<Map<Variable,Object>, Float> distance, Map<Map<Variable,Object>, Float> value) {
        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(initialState);
        father.put(initialState, null);
        distance.put(initialState, 0f);
        value.put(initialState, heuristic.estimate(initialState));

        Map<Variable, Object> instantiation = new HashMap<>();
        instantiation.putAll(initialState);
        Map<Variable, Object> next;

        int nodeCount = 0;

        while (!open.isEmpty()) {
            instantiation = argMin(open, value);
            if (goal.isSatisfiedBy(instantiation)){
                if (sonde)
                    System.out.println("Nombre de noeuds exploré : " + nodeCount);
                return getBfsPlan(father, plan, instantiation);
            }
            nodeCount ++;
            open.remove(instantiation);
            for (Action action : actions) {
                if (action.isApplicable(instantiation)) {
                    next = action.successor(instantiation);
                    if (!distance.containsKey(next)) 
                        distance.put(next, Float.MAX_VALUE);
                    if (distance.get(next) > distance.get(instantiation) + action.getCost()) {
                        distance.put(next, distance.get(instantiation) + action.getCost());
                        value.put(next, distance.get(next) + heuristic.estimate(next));
                        father.put(next, instantiation);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }

    public List<Action> plan() {
        Map<Map<Variable,Object>, Map<Variable,Object>> father = new HashMap<>();
        Map<Map<Variable,Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        return astar(plan, father, distance, value);
    }

    
    public String toString() {
        return "heuristic : " + heuristic;
    }

}
