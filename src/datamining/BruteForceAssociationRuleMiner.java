package datamining;

import java.util.*;
import modelling.*;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner{
    
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> set) {
        LinkedList<SortedSet<BooleanVariable>> subSets = new LinkedList<>();
        List<SortedSet<BooleanVariable>> subsetsConstant = new ArrayList<>();
        SortedSet<BooleanVariable> sortedSet;
        
        Set<Set<BooleanVariable>> res = new HashSet<>();
        for (BooleanVariable var : set) {
            sortedSet = new TreeSet<>(Apriori.COMPARATOR);
            sortedSet.add(var);         
            subSets.add(sortedSet);
            subsetsConstant.add(sortedSet);
            if (!sortedSet.equals(set))
                res.add(sortedSet);
        }   

        while (!subSets.isEmpty()) {
            sortedSet = new TreeSet<>(Apriori.COMPARATOR);
            sortedSet.addAll(subSets.pollFirst());
            List<SortedSet<BooleanVariable>> newSubsets = new ArrayList<>();
            for (SortedSet<BooleanVariable> subset : subSets) {
                if (Apriori.allSubsetsFrequent(subset, subsetsConstant)) {
                    SortedSet<BooleanVariable> newSubset = Apriori.combine(sortedSet, subset);
                    if (newSubset != null && !newSubset.equals(set)) {
                        newSubsets.add(newSubset);
                        res.add(newSubset);
                    }
                }
                
            }
            subSets.addAll(newSubsets);
            subsetsConstant.addAll(newSubsets);
        }
        return res;
    }

    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        
        Set<AssociationRule> associationRules = new HashSet<>();
        Set<Set<BooleanVariable>> subsets = allCandidatePremises(super.database.getItems());
        Apriori apriori = new Apriori(database);

        for (Set<BooleanVariable> premise : subsets) {

            for (Set<BooleanVariable> conclusion : subsets) {

                Set<BooleanVariable> intersection = new HashSet<>(premise);
                intersection.retainAll(conclusion);

                if (intersection.isEmpty() && apriori.frequency(premise) >= minFrequency && apriori.frequency(conclusion) >= minFrequency){

                    Set<Itemset> itemsets = new HashSet<>();
                    itemsets.add(new Itemset(premise, apriori.frequency(premise)));
                    itemsets.add(new Itemset(conclusion, apriori.frequency(conclusion)));
                    Set<BooleanVariable> solution = new HashSet<>(premise);
                    solution.addAll(conclusion);
                    itemsets.add(new Itemset(solution, apriori.frequency(solution)));

                    if (confidence(premise, conclusion, itemsets) >= minConfidence && apriori.frequency(solution) >= minFrequency) {
                        AssociationRule associationRule = new AssociationRule(premise, conclusion, frequency(solution, itemsets), confidence(premise, conclusion, itemsets));
                        associationRules.add(associationRule);
                    }   
                }
            }
        }
        return associationRules;
    }

    

}
