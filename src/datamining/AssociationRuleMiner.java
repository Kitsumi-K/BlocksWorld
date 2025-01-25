package datamining;

import java.util.Set;

public interface AssociationRuleMiner {
    
    abstract BooleanDatabase getDatabase();
    abstract Set<AssociationRule> extract(float minFrequency, float minConfidence); 
}
