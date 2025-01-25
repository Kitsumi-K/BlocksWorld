package cp;

import java.util.*;
import modelling.*;

public interface ValueHeuristic {
    
    abstract List<Object> ordering(Variable variable, Set<Object> domain);
}
