package blocksworld;

import bwui.*;
import bwmodel.*;
import bwgenerator.*;

import java.util.*;
import javax.swing.JFrame;

import bwgenerator.BWGenerator;
import cp.*;
import datamining.*;
import modelling.*;
import planning.*;

public class Executable {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String entry = new String();

        while (!entry.equals("1") && !entry.equals("2") && !entry.equals("3")) {
            System.out.println("Que voulez vous simuler ? ('1' pour un plan, '2' pour une solution, '3' pour l'extraction de connaissance) : \n");
            entry = scanner.nextLine();
        }

        Integer nbBlock = askNbElt(scanner, "block");
        Integer nbPile = askNbElt(scanner, "pile");

        long debut, fin;

    ////////////////////////////////////
    /////// :: premier choix :: ////////
    ////////////////////////////////////

        switch (entry) {
            case "1":
                System.out.println("\nnbBlock : " + nbBlock);
                System.out.println("nbPile : " + nbPile);

                BlocksWorldWithAction blocksWorld = new BlocksWorldWithAction(nbBlock, nbPile);

                HashSet<Integer> unUsed = new HashSet<>();

                for (int i = 0; i < nbBlock; i++) {
                    unUsed.add(i);
                }

                System.out.println(":: creation de l'etat initial ::");

                Map<Variable, Object> initialState = createInstance(createPiles(nbBlock, nbPile, scanner));

                System.out.println("voulez vous afficher l'etat initial (le programme ne reprendra qu'une fois la fenetre fermee) ? (o/<any>) : \n");
                entry = scanner.nextLine();
                if (entry.equals("o")) {
                    showBlocksworld(initialState, nbBlock, "InitialState");
                }

                System.out.println(":: creation de l'etat final ::");

                Map<Variable, Object> goalState = createInstance(createPiles(nbBlock, nbPile, scanner));

                System.out.println("voulez vous afficher l'etat final (le programme ne reprendra qu'une fois la fenetre fermee) ? (o/<any>) : \n");
                entry = scanner.nextLine();
                if (entry.equals("o")) {
                    showBlocksworld(goalState, nbBlock, "FinalState");
                }

                System.out.println("voulez vous afficher le nombre d'actions possible ? (o/<any>) : \n");
                entry = scanner.nextLine();
                if (entry.equals("o")) {
                    System.out.println(blocksWorld.getActions().size() + "\n");
                }

                Goal goal = new BasicGoal(goalState);

                Set<Action> actions = blocksWorld.getActions();

                Heuristic heuristicDiff = new BlocksWorldHeuristicDiff(blocksWorld, goalState);
                Heuristic heuristicRightPlace = new RightPlaceHeuristic(blocksWorld, goalState);

                Boolean afficher = true;
                List<Action> plan = null;
                Boolean showPlanTerminal;
                Integer algo;
                Planner planner;
                entry = new String();

                while (afficher) {
                    System.out.println("Choisissez l'algorithme qui produira le plan (attention certains prendront du temps) : \n");
                    System.out.println("1. DFS");
                    System.out.println("2. BFS");
                    System.out.println("3. Dijkstra");
                    System.out.println("4. A* heuristique = nombre de variables ayant une valeur differente de celle de l'etat final\n");
                    System.out.println("5. A* heuristique = nombre de blocks mal placé (meilleure)\n");

                    try {
                        algo = scanner.nextInt();
                        scanner.nextLine();
                        showPlanTerminal = false;

                        System.out.println("voulez vous afficher le plan dans le terminal? (o/<any>) : \n");
                        entry = scanner.nextLine();
                        if (entry.equals("o")) {
                            showPlanTerminal = true;
                        }

                        switch (algo) {
                            case 1:
                                System.out.println(":: DFS ::\n");
                                planner = new DFSPlanner(initialState, actions, goal);
                                planner.activateNodeCount();
                                break;
                            case 2:
                                System.out.println(":: BFS ::\n");
                                planner = new BFSPlanner(initialState, actions, goal);
                                planner.activateNodeCount();
                                break;
                            case 3:
                                System.out.println(":: Dijkstra :: \n");
                                planner = new DijkstraPlanner(initialState, actions, goal);
                                planner.activateNodeCount();
                                break;  
                            case 4:
                                System.out.println(":: AStar Heuristic Diff ::\n");
                                planner = new AStarPlanner(initialState, actions, goal, heuristicDiff);
                                planner.activateNodeCount();
                                break;
                            default:
                                System.out.println(":: AStar Heuristic Diff ::\n");
                                planner = new AStarPlanner(initialState, actions, goal, heuristicRightPlace);
                                planner.activateNodeCount();
                                break;
                        }

                        debut = System.currentTimeMillis();
                        plan = planner.plan();
                        fin = System.currentTimeMillis();
                        if (showPlanTerminal) 
                            System.out.println(plan);
                        
                        if (plan != null) {
                            System.out.println("Plan trouve en " + (fin-debut)/1000 +"s \n");
                            System.out.println("voulez vous afficher le plan (le programme ne reprendra qu'une fois la fenetre fermee) ? (o/<any>) : \n");
                            entry = scanner.nextLine();
                            if (entry.equals("o")) {
                                showPlan(initialState, plan, nbBlock, "Plan");
                            }
                        }
                        else 
                            System.out.println("Plan non trouve");

                        System.out.println("continuer ? (o/<any>)");
                        entry = scanner.nextLine();
                        if (!entry.equals("o")) 
                            afficher = false;
                        
                    } catch (Exception e) {
                        System.out.println("nombre entre 1 et 5 s'il vous plait");
                    }
                    
                }

                System.out.println("Simulation du plan : terminee.");
                break;

            

    ////////////////////////////////////
    /////// :: deuxième choix :: ///////
    ////////////////////////////////////

            case "2":

                BlocksWorldWithConstraints blocksWorldWithImplication = new BlocksWorldRegular(nbBlock, nbPile).createWithRegularConstraint();
                BlocksWorldWithConstraints blocksWorldCroissant = new BlocksWorldCroissant(nbBlock, nbPile).createCroissantConstraint();
                BlocksWorldWithConstraints blocksWorldCroissantAndRegular = new BlocksWorldCroissant(nbBlock, nbPile).createCroissantConstraint().createWithRegularConstraint();

                AbstractSolver solver;

                System.out.println(":: Regular Constraints :: \n");

                System.out.println(":: BacktrackSolver Regular ::\n");
                solver = new BacktrackSolver(blocksWorldWithImplication.getVariables(), blocksWorldWithImplication.getConstraints());
                debut = System.currentTimeMillis();
                Map<Variable, Object> solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                

                System.out.println(":: MACSolver Regular ::\n");
                solver = new MACSolver(blocksWorldWithImplication.getVariables(), blocksWorldWithImplication.getConstraints());
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                

                System.out.println(":: HeuristicMACSolver Regular ::\n");
                solver = new HeuristicMACSolver(blocksWorldWithImplication.getVariables(), blocksWorldWithImplication.getConstraints(), new NbConstraintsVariableHeuristic(blocksWorldWithImplication.getConstraints(), true), new RandomValueHeuristic(new Random()));
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                


                
                System.out.println(":: Croissant Constraints :: \n");

                System.out.println(":: BacktrackSolver Croissant ::\n");
                solver = new BacktrackSolver(blocksWorldCroissant.getVariables(), blocksWorldCroissant.getConstraints());
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                

                System.out.println(":: MACSolver Croissant ::\n");
                solver = new MACSolver(blocksWorldCroissant.getVariables(), blocksWorldCroissant.getConstraints());
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                

                System.out.println(":: HeuristicMACSolver Croissant ::\n");
                solver = new HeuristicMACSolver(blocksWorldCroissant.getVariables(), blocksWorldCroissant.getConstraints(), new NbConstraintsVariableHeuristic(blocksWorldCroissant.getConstraints(), true), new RandomValueHeuristic(new Random()));
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                


                System.out.println(":: Croissant & Regular Constraints :: \n");

                System.out.println(":: BacktrackSolver Croissant & Regular ::\n");
                solver = new BacktrackSolver(blocksWorldCroissantAndRegular.getVariables(), blocksWorldCroissantAndRegular.getConstraints());
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                

                System.out.println(":: MACSolver Croissant & Regular ::\n");
                solver = new MACSolver(blocksWorldCroissantAndRegular.getVariables(), blocksWorldCroissantAndRegular.getConstraints());
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                

                System.out.println(":: HeuristicMACSolver Croissant & Regular ::\n");
                solver = new HeuristicMACSolver(blocksWorldCroissantAndRegular.getVariables(), blocksWorldCroissantAndRegular.getConstraints(), new NbConstraintsVariableHeuristic(blocksWorldCroissantAndRegular.getConstraints(), true), new RandomValueHeuristic(new Random()));
                debut = System.currentTimeMillis();
                solution = solver.solve();
                fin = System.currentTimeMillis();
                if (solution != null){
                    System.out.println("solution trouvée en " + (fin - debut)/1000 + "s \n" + solution + "\n");
                    showBlocksworld(solution, nbBlock, "solution");
                }
                    
                else 
                    System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
                break;

    ////////////////////////////////////
    /////// :: deuxième choix :: ///////
    ////////////////////////////////////
                
            default:
                BlocksWorldDatabase db = new BlocksWorldDatabase(nbBlock,nbPile);
                BWGenerator generator = new BWGenerator(nbBlock, nbPile);

                float minFrequency = 2/3f;
                float minConfidence = 95/100f;

                for (int i = 0; i < 10_000; i++) {
                    // Créer un état au hasard
                    List<List<Integer>> state = generator.generate(new Random());

                    // Convertir l'état en instance
                    Set<BooleanVariable> instance = db.getInstance(state);
                    
                    // Ajouter l'état à la bdd
                    db.add(instance);
                }

                // System.out.println(db.getTransactions().size());

                System.out.println("Début de l'extraction de données\n");

                ItemsetMiner apriori = new Apriori(db);

                debut = System.currentTimeMillis();
                apriori.extract(minFrequency);
                System.out.println("\n");
                fin = System.currentTimeMillis();

                System.out.println("solution trouvée en " + (fin - debut)/1000 + "s" + " en utilisant Apriori\n");

                AssociationRuleMiner bruteforceMiner = new BruteForceAssociationRuleMiner(db);

                debut = System.currentTimeMillis();
                bruteforceMiner.extract(minFrequency, minConfidence);
                System.out.println("\n");
                fin = System.currentTimeMillis();

                System.out.println("solution trouvée en " + (fin - debut)/1000 + "s" + " en utilisant Bruteforce\n");
                
                break;
        }
        
        scanner.close();
    }


    ////////////////////////////////////
    ///////// :: Fonctions :: //////////
    ////////////////////////////////////


    /**
     * @param scanner un scanner de typer Scanner
     * @param elt le nom de l'element dont on veut le nombre
     * @return le nombre d'element voulu après demande
     */
    public static Integer askNbElt(Scanner scanner, String elt) {
        Boolean integer = false;
        Integer nbElt = null;
        do {
            System.out.println("Entrez un nombre de " + elt + " : \n");
            try {
                nbElt = scanner.nextInt();
                if (nbElt <= 0) 
                    System.out.println("le nombre de " + elt + " doit être strictement positif\n");
                else 
                    integer = true;
            } catch (Exception e) {
                System.out.println("le nombre de " + elt + " doit être un nombre (strictement positif)\n");
                scanner.nextLine();
            }
        } while (!integer);
        return nbElt;
    }
    

    /**
     * @param nbBlock le nombre de blocks du blocksworld
     * @param nbPile le nombre de pile du blocksworld
     * @param scanner un scanner de typer Scanner
     * @return le blocksworld sous forme de liste de liste d'integer 
     */
    public static Integer[][] createPiles(Integer nbBlock, Integer nbPile, Scanner scanner) {

        HashSet<Integer> unUsed = new HashSet<>();
        for (int i = 0; i < nbBlock; i++) {
            unUsed.add(i);
        }

        Boolean finished = false;
        Integer blockValue = null;
        Integer noPile = 1;
        ArrayList<ArrayList<Integer>> piles = new ArrayList<>();
        for (int i = 0; i < nbPile; i++)
            piles.add(new ArrayList<>());

        while (!finished) {
            System.out.println("Entrez une pile dans laquelle rentrer un block (<no de la pile> pour choisir cette pile) : \n");
            for (int i = 0; i < piles.size(); i++) 
                System.out.println("pile " + (i+1) + " : " + piles.get(i));
            try {
                noPile = scanner.nextInt();
                if (noPile <= 0 || noPile > nbPile ) 
                    System.out.println("le no de pile doit être strictement positif et inferieur ou egal au nombre de pile\n");
                else {
                    System.out.println("rentrer un bloc à rentrer dans la pile no"+ noPile +" : \n");
                    try {
                        blockValue = scanner.nextInt();
                        if (!unUsed.contains(blockValue)) 
                            System.out.println("le block est deja place ou n'est pas compris dans les blocks à placer (pour rappel les blocks à placer vont de 0 à nbBlock-1) (<any letter> pour rechoisir la pile):\n");
                        else 
                            piles.get(noPile-1).add(blockValue);
                            unUsed.remove(blockValue);
                        if (unUsed.isEmpty()) {
                            scanner.nextLine();
                            finished = true;
                        }
                    } catch (Exception e) {
                        System.out.println("la valeur du block doit être un nombre retour au choix de pile\n");
                        scanner.nextLine();
                    }
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("le no de la pile doit être un nombre\n");
            }
        }

        Integer[][] pilesState = new Integer[nbPile][];

        for (int i = 0; i < nbPile; i++) {
            Object[] arrayPile = piles.get(i).toArray();
            pilesState[i] = new Integer[arrayPile.length];

            for (int j = 0; j < arrayPile.length; j++) {
                pilesState[i][j] = (Integer) arrayPile[j];
            }
        }
        return pilesState;
    }

    /**
     * @param state état à afficher
     * @param nbBlock nombre de blocks du blocksworld
     * @param title titre de la fenetre
     * @return créé une fenetre dans laquelle on aperçois l'état state 
     * @hidden Code pris dans l'énoncé du Fil Rouge
     */
    public static void showBlocksworld(Map<Variable, Object> state, Integer nbBlock, String title) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(nbBlock);
        for(int b = 0; b < nbBlock; b++) {
            Variable onB = new Variable("on" + b, null); //getinstanceofVariablefor"on_b" 
            int under= (int) state.get(onB); 
            if (under>=0) { //if the value is a block (as opposed to a stack) 
                builder.setOn(b,under); 
            } 
        } 
        BWState<Integer> stateBis = builder.getState();

        BWIntegerGUI gui = new BWIntegerGUI(nbBlock);
        JFrame frame = new JFrame(title); 
        frame.add(gui.getComponent(stateBis)); 
        frame.pack(); 
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.isVisible();

        while (frame.isVisible()) {
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param initialState l'état initial
     * @param plan liste des action à appliquer à l'état initial
     * @param nbBlock nombre de blocks du blocksworld
     * @param title titre de la fenetre
     * @return créé une fenetre dans laquelle les action sont appliquée les une après les autres 
     * @hidden Code pris dans l'énoncé du Fil Rouge
     */
    public static void showPlan(Map<Variable, Object> initialState, List<Action> plan, Integer nbBlock, String title) {
        BWIntegerGUI gui = new BWIntegerGUI(nbBlock);
        JFrame frame = new JFrame("Simulation");
        BWState<Integer> bwState = makeBWState(initialState, nbBlock);
        BWComponent<Integer> component = gui.getComponent(bwState);
        
        frame.add(component);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);

        Map<Variable, Object> state = new HashMap<>(initialState);

        // Plan de jeu
        for (Action a : plan) {
            if (!frame.isVisible()) 
                break;
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = a.successor(state);
            component.setState(makeBWState(state, nbBlock));
        }

        while (frame.isVisible()) {
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param listPiles la liste des piles du blocksworld sous forme d'array d'array d'Integer
     * @return un état du blocksworld sous forme de Map<Variable, Object>
     */
    public static Map<Variable, Object> createInstance(Integer[][] listPiles) {
        Map<Variable, Object> instance = new HashMap<>();
        Integer nbPile = -1;
        Set<Object> domain = new HashSet<>();
        int nb = 0;
        for (Integer[] pile : listPiles) {
            domain.add(nbPile);
            for (Integer b : pile) {
                domain.add(nb);
                nb++;
            }
            nbPile--;
        }
        nbPile = -1;
        for (Integer[] pile : listPiles) {
            if (pile.length == 0) {
                instance.put(new BooleanVariable("free" + nbPile),true);
            }
            else {
                instance.put(new BooleanVariable("free" + nbPile),false);
                instance.put(new Variable("on" + pile[0], domain), nbPile);
                if (pile.length == 1)
                    instance.put(new BooleanVariable("fixed" + pile[0]), false);
                else    
                    instance.put(new BooleanVariable("fixed" + pile[0]), true);
                for (int i = 1; i < pile.length; i++) {
                    instance.put(new Variable("on" + pile[i], domain), pile[i-1]);
                    if (pile.length-1 == i)
                        instance.put(new BooleanVariable("fixed" + pile[i]), false);
                    else 
                        instance.put(new BooleanVariable("fixed" + pile[i]), true);
                }
            }
            nbPile--;
        }
        return instance;
    }

    /**
     * @param map un état du blocksworld sous forme de Map<Variable, Object>
     * @param n le nombre de blocks du blocksworld
     * @return une instance de BWState pour l'affichage
     */
    public static BWState<Integer> makeBWState(Map<Variable, Object> map, int n) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for(int b = 0; b < n; b++) {
            Variable onB = new Variable("on" + b, null); //getinstanceofVariablefor"on_b" 
            int under= (int) map.get(onB); 
            if (under>=0) { //if the value is a block (as opposed to a stack) 
                builder.setOn(b,under); 
            } 
        } 
        return builder.getState();
    }
}
