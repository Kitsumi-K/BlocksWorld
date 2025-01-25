package modelling;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        
        Set<Object> pair = new HashSet<>();
        pair.add(2);
        pair.add(4);
        pair.add(6);
        pair.add(8);

        /*Set<Object> impair = new HashSet<>();
        pair.add(1);
        pair.add(3);
        pair.add(5);
        pair.add(7);
        pair.add(9);*/
        

        Variable v1 = new Variable("v1", pair);
        Variable v2 = new Variable("v2", pair);

        DifferenceConstraint diff = new DifferenceConstraint(v1, v2);

        Map<Variable, Object> map = new HashMap<>();
        map.put(v1,2);
        map.put(v2,4);

        System.out.println("la contrainte DifferenceConstraint est " + diff.isSatisfiedBy(map) + " avec map pour v1 et v2");
        
        map = new HashMap<>();
        map.put(v1,2);
        map.put(v2,2);
        System.out.println("la contrainte DifferenceConstraint est " + diff.isSatisfiedBy(map) + " avec map pour v1 et v1");
    }
    
}
