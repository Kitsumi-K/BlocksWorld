package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    
    protected BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    public BooleanDatabase getDatabase() {
        return database;
    }

    public void setDatabase(BooleanDatabase database) {
        this.database = database;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) throws IllegalArgumentException{
        for (Itemset itemset : itemsets) {
            if (itemset.getItems().equals(items)) {
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("items non contenu dans itemsets");
    }

    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        if (frequency(premise, itemsets) != 0){
            Set<BooleanVariable> solution = new HashSet<>(premise);
            solution.addAll(conclusion);
            return frequency(solution, itemsets)/frequency(premise, itemsets);
        }
        return 0.0f;
    }
    
}
