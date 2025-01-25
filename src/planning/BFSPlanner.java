package planning;

import java.util.*;
import modelling.*;

public class BFSPlanner implements Planner {
    
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;

    private Boolean sonde = false;
    private int nodeCount = 0;
    
    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
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

    public List<Action> getBfsPlan(Map<Map<Variable,Object>, Map<Variable,Object>> father, Map<Map<Variable,Object>, Action> plan, Map<Variable, Object> goal) {
        LinkedList<Action> BFS_plan = new LinkedList<>();
        while (father.get(goal) != null) {
            BFS_plan.add(plan.get(goal));
            goal = father.get(goal);
        }
        Collections.reverse(BFS_plan);
        return BFS_plan;
    }

    public List<Action> bfs(Map<Map<Variable,Object>, Map<Variable,Object>> father, Map<Map<Variable,Object>, Action> plan) {
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(initialState);
        LinkedList<Map<Variable, Object>> open = new LinkedList<>();
        open.add(initialState);

        father.put(initialState, null);
        if (goal.isSatisfiedBy(initialState)) {
            if (sonde)
                System.out.println("Nombre de noeuds explore : " + nodeCount);
            return new ArrayList<Action>();
        }

        Map<Variable, Object> instantiation;
        Map<Variable, Object> next;

        while (!open.isEmpty()) {
            instantiation = open.pollLast();
            closed.add(instantiation);
            nodeCount++;
            for (Action action : actions) {
                if (action.isApplicable(instantiation)) {
                    next = action.successor(instantiation);
                    if (!closed.contains(next) && !open.contains(next)) {
                        father.put(next, instantiation);
                        plan.put(next, action);
                        if (goal.isSatisfiedBy(next)) {
                            if (sonde)
                                System.out.println("Nombre de noeuds explore : " + nodeCount);
                            return getBfsPlan(father, plan, next);
                        }
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
        return bfs(father, plan);
    }
}
