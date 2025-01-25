package datamining;

import java.util.*;

public interface ItemsetMiner {
    
    abstract BooleanDatabase getDatabase();
    abstract Set<Itemset> extract(float frequency);
}
