package planning;

import java.util.*;
import modelling.*;

public interface Action {

    abstract boolean isApplicable(Map<Variable,Object> state);
    abstract Map<Variable,Object> successor(Map<Variable,Object> state);
    abstract int getCost();
}
