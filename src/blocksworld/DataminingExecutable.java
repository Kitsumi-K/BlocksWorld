package blocksworld;

import java.util.*;

import bwgenerator.BWGenerator;
import datamining.*;
import modelling.*;

public class DataminingExecutable {
    
    public static void main(String[] args) {

        Integer nbBlock = 100;
        Integer nbPile = 35;

        float minFrequency = 2/3f;
        // System.out.println(minFrequency);

        float minConfidence = 95/100f;
        // System.out.println(minConfidence);

        long debut, fin;

        BlocksWorldDatabase db = new BlocksWorldDatabase(nbBlock,nbPile);
        BWGenerator generator = new BWGenerator(nbBlock, nbPile);

        for (int i = 0; i < 10_000; i++) {
            // Créer un état au hasard
            List<List<Integer>> state = generator.generate(new Random());

            // Convertir l'état en instance
            Set<BooleanVariable> instance = BlocksWorldDatabase.getInstance(state);
            
            // Ajouter l'état à la bdd
            db.add(instance);
        }

        System.out.println("Début de l'extraction de données\n");

        ItemsetMiner apriori = new Apriori(db);

        debut = System.currentTimeMillis();
        Set<Itemset> result = apriori.extract(minFrequency);
        System.out.println(result + "\n");
        fin = System.currentTimeMillis();

        System.out.println("solution trouvée en " + (fin - debut)/1000 + "s" + " en utilisant Apriori\n");

        AssociationRuleMiner bruteforceMiner = new BruteForceAssociationRuleMiner(db);

        debut = System.currentTimeMillis();
        Set<AssociationRule> res = bruteforceMiner.extract(minFrequency, minConfidence);
        System.out.println(res + "\n");
        fin = System.currentTimeMillis();

        System.out.println("solution trouvée en " + (fin - debut)/1000 + "s" + " en utilisant Bruteforce\n");
    }
}
