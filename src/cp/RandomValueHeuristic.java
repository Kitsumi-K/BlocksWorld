package cp;

import java.util.*;
import modelling.*;

public class RandomValueHeuristic implements ValueHeuristic{
    
    Random random;

    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    public List<Object> ordering(Variable variable, Set<Object> domain) {
        List<Object> list = new ArrayList<>();
        list.addAll(domain);
        for (int i = 0; i < list.size(); i++) {
            Collections.swap(list, i, random.nextInt(list.size()));
        }
        return list;
    }
}
