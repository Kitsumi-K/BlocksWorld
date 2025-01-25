package datamining;

import java.util.*;
import modelling.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner {
    
    protected BooleanDatabase database;
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }

    public BooleanDatabase getDatabase() {
        return database;
    }

    public void setDatebase(BooleanDatabase database) {
        this.database = database;
    }

    public float frequency(Set<BooleanVariable> items) {
        Integer total = database.getTransactions().size();
        float count = 0;
        for (Set<BooleanVariable> transaction : database.getTransactions()) {
            if (transaction.containsAll(items)) {
                count++;
            }
        }
        return count/total;
    }
}
