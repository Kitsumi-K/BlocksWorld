package blocksworld;

import java.util.*;

import datamining.BooleanDatabase;
import modelling.*;

public class BlocksWorldDatabase extends BooleanDatabase{
    
    private Integer nbBlock;
    private Integer nbPile;

    public BlocksWorldDatabase(Integer nbBlock, Integer nbPile) {
        super();
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;

        createVariables();
    }

    public void createVariables() {
        for (int i = 0; i < nbBlock; i++) { 
            for (int j = 0; j< nbBlock; j++) {
                if (i != j) {
                    items.add(new BooleanVariable("on" + i + "," + j));
                }  
            }
            for (int j = -nbPile; j < 0; j++) { 
                items.add(new BooleanVariable("on-table" + i + "," + j));
            } 
            items.add(new BooleanVariable("fixed" + i));
       }
       for (int i = -nbPile; i < 0; i++) {
           items.add(new BooleanVariable("free" + i));
       }
    }

    public Integer getNbBlock() {
        return nbBlock;
    }

    public void setNbBlock(Integer nbBlock) {
        this.nbBlock = nbBlock;
    }

    public Integer getNbPile() {
        return nbPile;
    }

    public void setNbPile(Integer nbPile) {
        this.nbPile = nbPile;
    }

    public static Set<BooleanVariable> getInstance(List<List<Integer>> state) {
        Set<BooleanVariable> instance = new TreeSet<>(COMPARATOR);

        for (int i = 0; i < state.size(); i++) { 
            if (state.get(i).isEmpty())
                instance.add(new BooleanVariable("free" + i));
            else { 
                instance.add(new BooleanVariable("on-table" + state.get(i).get(0) + "," + i));
                for (int j = 1; j < state.get(i).size(); j++) {
                    instance.add(new BooleanVariable("on" + state.get(i).get(j) + "," + state.get(i).get(j-1)));
                    instance.add(new BooleanVariable("fixed" + state.get(i).get(j-1)));
                }
            }
        }

        return instance;
    }
}
