package cp;

import java.util.*;
import modelling.*;

public interface Solver {
    
    abstract Map<Variable, Object> solve();
    
}
