package blocksworld;

import java.util.Map;
import java.util.Random;

import cp.AbstractSolver;
import cp.BacktrackSolver;
import cp.HeuristicMACSolver;
import cp.MACSolver;
import cp.NbConstraintsVariableHeuristic;
import cp.RandomValueHeuristic;
import modelling.Variable;

public class CPExecutable {
    
    public static void main(String[] args) {

        Integer nbBlock = 7;
        Integer nbPile = 5;

        long debut, fin;

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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
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
            Executable.showBlocksworld(solution, nbBlock, "solution");
        }
            
        else 
            System.out.println("Solution non trouvée en " + (fin - debut)/1000 + "s \n");
    }
}
