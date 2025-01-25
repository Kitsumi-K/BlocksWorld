package datamining;

import java.util.*;
import modelling.*;

public class Apriori extends AbstractItemsetMiner{

    public static final float FR_MIN = 0.0f;

    public Apriori(BooleanDatabase database) {
        super(database);
    }

    public Set<Itemset> frequentSingletons(float minFrequency) {
        Set<Itemset> frequentSingletons = new HashSet<>();
        Itemset itemset; 
        for (BooleanVariable item : database.getItems()) {
            Set<BooleanVariable> items = new HashSet<>();
            items.add(item);
            itemset = new Itemset(items, this.frequency(items));
            if (minFrequency <= this.frequency(items))
                frequentSingletons.add(itemset); 
        }
        return frequentSingletons;
    }

    public Set<Itemset> extract(float frequency) {
        Set<Itemset> itemsets = new HashSet<>();
        List<SortedSet<BooleanVariable>> subsetsConstant = new ArrayList<>();
        SortedSet<BooleanVariable> sortedset; 

        for (Itemset itemset: frequentSingletons(frequency)) {
            sortedset = new TreeSet<>(COMPARATOR); 
            sortedset.addAll(itemset.getItems());
            subsetsConstant.add(sortedset);
            itemsets.add(new Itemset(sortedset, frequency(sortedset)));
        }
        
        LinkedList<SortedSet<BooleanVariable>> subsets = new LinkedList<>(subsetsConstant);

        while (!subsets.isEmpty()) {
            SortedSet<BooleanVariable> subset = subsets.pollFirst();

            if (frequency(subset) >= frequency && allSubsetsFrequent(subset, subsetsConstant)) {
                List<SortedSet<BooleanVariable>> newSubsets = new ArrayList<>();

                for (SortedSet<BooleanVariable> subsetBis : subsets) {
                    SortedSet<BooleanVariable> newSubset = combine(subset, subsetBis);
                    
                    if (newSubset != null && frequency(newSubset) >= frequency) {
                        newSubsets.add(newSubset);
                        itemsets.add(new Itemset(newSubset, frequency(newSubset)));
                    }
                }
                subsets.addAll(newSubsets);
                subsetsConstant.addAll(newSubsets);
            }
        }
        return itemsets;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> items1, SortedSet<BooleanVariable> items2) {
        if (items1.size() == items2.size() && items1.size() != 0) {
            if (items1.headSet(items1.last()).equals(items2.headSet(items2.last()))) {
                if (!items1.last().equals(items2.last())) {
                    SortedSet<BooleanVariable> res = new TreeSet<>(COMPARATOR);
                    res.addAll(items1);
                    res.add(items2.last());
                    return res;
                }
            }
        }
        return null;
    } 

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> collection) {
        Set<BooleanVariable> itemsBis = new HashSet<>(items);
        for (BooleanVariable item : items) {
            itemsBis.remove(item);
            if (!collection.contains(itemsBis) && !itemsBis.isEmpty()) {
                return false;
            }
            itemsBis.add(item);
        }
        return true;
    }
    
}
