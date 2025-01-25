package modelling;

import java.util.*;

public class BooleanVariable extends Variable {
    
    public BooleanVariable(String name) {
        super(name,new HashSet<>());
        this.getDomain().add(true);
        this.getDomain().add(false);
    }
}
