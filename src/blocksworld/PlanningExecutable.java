package blocksworld;

import java.util.*;
import bwgenerator.*;
import modelling.*;
import planning.*;

public class PlanningExecutable {
    
    public static void main(String[] args) {

        Integer nbBlock = 7;
        Integer nbPile = 4;

        BlocksWorldWithAction blocksWorld = new BlocksWorldWithAction(nbBlock,nbPile);
        
////////// instantiation aléatoire ///////////////////////////////////////////////////
        // BWGenerator generator = new BWGenerator(nbBlock,nbPile);
        // List<List<Integer>> state = generator.generate(new Random());
        // Integer[][] piles = listToInteger(state);
        // Map<Variable, Object> initialState = Executable.createInstance(piles); 
        // System.out.println(Demo.stateToString(state));
        // state = generator.generate(new Random());
        // Integer[][] goalPiles = listToInteger(state);
        // Map<Variable, Object> goalState = Executable.createInstance(goalPiles); 
        // System.out.println(Demo.stateToString(state)); 
//////////////////////////////////////////////////////////////////////////////////////


////////// instanciation manuelle ////////////////////////////////////////////////////
        Integer[][] piles = {{0,1,2,3,4,5,6},{},{},{}};
        Map<Variable, Object> initialState = Executable.createInstance(piles); 
        Integer[][] goalPiles = {{0,1},{2,3},{},{}};
        Map<Variable, Object> goalState = Executable.createInstance(goalPiles);  
//////////////////////////////////////////////////////////////////////////////////////


        Goal goal = new BlocksWorldGoal(goalState);

        Set<Action> actions = blocksWorld.getActions();
        List<Action> plan;

        Heuristic heuristicDiff = new BlocksWorldHeuristicDiff(blocksWorld, goalState);
        Heuristic heuristicRightPlace = new RightPlaceHeuristic(blocksWorld, goalState);

        long debut, fin;

        // System.out.println(":: DFSPlanner :: \n");
        // Planner dfsPlanner = new DFSPlanner(initialState, actions, goal);
        // dfsPlanner.activateNodeCount();
        // plan = dfsPlanner.plan();
        // debut = System.currentTimeMillis();
        // System.out.println(plan);
        // fin = System.currentTimeMillis();
        // System.out.println("solution trouvee en : " + (fin - debut) + " ms");
        // Executable.showPlan(initialState, plan, nbBlock, "DFSPlanner");

        // System.out.println(":: BFSPlanner :: \n");
        // Planner bfsPlanner = new BFSPlanner(initialState, actions, goal);
        // bfsPlanner.activateNodeCount();
        // debut = System.currentTimeMillis();
        // plan = bfsPlanner.plan();
        // fin = System.currentTimeMillis();
        // System.out.println("solution trouvee en : " + (fin - debut) + " ms");
        // System.out.println(plan);
        // Executable.showPlan(initialState, plan, nbBlock, "BFSPlanner");

        // System.out.println(":: Dijkstra :: \n");
        // Planner dijkstra = new DijkstraPlanner(initialState, actions, goal);
        // dijkstra.activateNodeCount();
        // debut = System.currentTimeMillis();
        // plan = dijkstra.plan();
        // fin = System.currentTimeMillis();
        // System.out.println("solution trouvee en : " + (fin - debut) + " ms");
        // System.out.println(plan);
        // Executable.showPlan(initialState, plan, nbBlock, "Dijkstra");

        System.out.println(":: AStar Heuristic Difference ::\n");
        Planner astar = new AStarPlanner(initialState, actions, goal, heuristicDiff);
        astar.activateNodeCount();
        debut = System.currentTimeMillis();
        plan = astar.plan();
        fin = System.currentTimeMillis();
        System.out.println("solution trouvee en : " + (fin - debut) + " ms");
        System.out.println(plan);
        Executable.showPlan(initialState, plan, nbBlock, "AStar Heuristic Difference");

        System.out.println(":: AStar Heuristic Right Place ::\n");
        astar = new AStarPlanner(initialState, actions, goal, heuristicRightPlace);
        astar.activateNodeCount();
        debut = System.currentTimeMillis();
        plan = astar.plan();
        fin = System.currentTimeMillis();
        System.out.println("solution trouvee en : " + (fin - debut) + " ms");
        System.out.println(plan);
        Executable.showPlan(initialState, plan, nbBlock, "AStar Heuristic Right Place");
    }

    public static Integer[][] listToInteger(List<List<Integer>> liste) {
        Integer[][] tab = new Integer[liste.size()][];

        for (int i = 0; i < liste.size(); i++) {
            List<Integer> newLine = liste.get(i);
            int colonnes = newLine.size();
            tab[i] = new Integer[colonnes];

            for (int j = 0; j < colonnes; j++) {
                tab[i][j] = newLine.get(j);
            }
        }

        return tab;
    }
}
