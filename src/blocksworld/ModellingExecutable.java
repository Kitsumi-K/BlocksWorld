package blocksworld;

import java.util.*;
import modelling.*;

public class ModellingExecutable {
    
    public static void main(String[] args) {
        
        BlocksWorldWithConstraints withConstraints = new BlocksWorldWithConstraints(10,5);
        BlocksWorldCroissant croissant = new BlocksWorldCroissant(10,5).createCroissantConstraint();
        BlocksWorldRegular regular = new BlocksWorldRegular(10,5).createWithRegularConstraint();

        Integer[][] piles = {{0,1,2,3,4,5,6,8},{7,9},{},{},{}};
        Map<Variable, Object> state = Executable.createInstance(piles); // croissant

        Integer[][] piles2 = {{0,1,2,3,4,5,6},{9,8,7},{},{},{}};    
        Map<Variable, Object> state2 = Executable.createInstance(piles2); // regulier

        Integer[][] piles3 = {{0,1,2,3,4,5,6,7,8,9},{},{},{},{}};
        Map<Variable, Object> state3 = Executable.createInstance(piles3); // croissant et regulier

        Boolean satisfie = true;

        System.out.println(":::::::::::\n:: Etat1 ::\n:::::::::::\n");

        System.out.println(":: BlocksWorld avec Constraintes ::\n");
        for (Constraint c : withConstraints.getConstraints()) {
            if (!c.isSatisfiedBy(state)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;

        System.out.println(":: BlocksWorld Croissant ::\n");
        for (Constraint c : croissant.getConstraints()) {
            if (!c.isSatisfiedBy(state)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;

        System.out.println(":: BlocksWorld Regulier ::\n");
        for (Constraint c : regular.getConstraints()) {
            if (!c.isSatisfiedBy(state)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;

        System.out.println(":::::::::::\n:: Etat2 ::\n:::::::::::\n");

        System.out.println(":: BlocksWorld avec Constraintes ::\n");
        for (Constraint c : withConstraints.getConstraints()) {
            if (!c.isSatisfiedBy(state2)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;

        System.out.println(":: BlocksWorld Croissant ::\n");
        for (Constraint c : croissant.getConstraints()) {
            if (!c.isSatisfiedBy(state2)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
            satisfie = true;

        System.out.println(":: BlocksWorld Regulier ::\n");
        for (Constraint c : regular.getConstraints()) {
            if (!c.isSatisfiedBy(state2)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;

        System.out.println(":::::::::::\n:: Etat3 ::\n:::::::::::\n");

        System.out.println(":: BlocksWorld avec Constraintes ::\n");
        for (Constraint c : withConstraints.getConstraints()) {
            if (!c.isSatisfiedBy(state3)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;

        System.out.println(":: BlocksWorld Croissant ::\n");
        for (Constraint c : croissant.getConstraints()) {
            if (!c.isSatisfiedBy(state3)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;

        System.out.println(":: BlocksWorld Regulier ::\n");
        for (Constraint c : regular.getConstraints()) {
            if (!c.isSatisfiedBy(state3)) {
                System.out.println("Non Satisfié\n");
                satisfie = false;
                break;
            }
        }
        if (satisfie)
            System.out.println("Satifié\n");
        satisfie = true;
    }
}
