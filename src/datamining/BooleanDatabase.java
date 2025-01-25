package datamining;

import java.util.*;
import modelling.*;

public class BooleanDatabase {
    
    protected Set<BooleanVariable> items;
    protected List<Set<BooleanVariable>> transactions = new ArrayList<>();
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    public BooleanDatabase() {
        this.items = new TreeSet<>(COMPARATOR);
    }

    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public void setItems(Set<BooleanVariable> items) {
        this.items = items;
    }
    
    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Set<BooleanVariable>> transactions) {
        this.transactions = transactions;
    } 

    public void add(Set<BooleanVariable> transaction) {
        transactions.add(transaction);
    }    
}
