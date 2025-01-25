package modelling;

import java.util.*;

public interface Constraint {

    abstract Set<Variable> getScope();
    abstract boolean isSatisfiedBy(Map<Variable, Object> map);
}
