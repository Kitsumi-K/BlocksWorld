package planning;

import java.util.*;
import modelling.*;

public class Main {
    
    public static void main(String[] args) {

        Map<Variable, Object> precondition = new HashMap<>();
        precondition.put(new Variable("x0", null), 0);

        Map<Variable, Object> etat = new HashMap<>();
        etat.put(new Variable("x0", null), 0);
        
        BasicAction b = new BasicAction(precondition, null, 0);
        System.out.println(b.isApplicable(etat));

    }
}   
