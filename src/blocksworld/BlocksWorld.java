package blocksworld;

import java.util.*;
import modelling.*;

public class BlocksWorld {
    
    protected Integer nbBlock;
    protected Integer nbPile;

    protected Set<Variable> variables;
    
    public BlocksWorld(Integer nbBlock, Integer nbPile) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;

        createVariables();
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


    private void createVariables() {
        Set<Object> domainOn;
        variables = new HashSet<>();
        
        Variable block;
        for (int i = 0; i < nbBlock; i++) {
            domainOn = new HashSet<>();
            for (int j = -nbPile; j < nbBlock; j++) {
                domainOn.add(j);
            }
            block = new BooleanVariable("fixed" + i);
            variables.add(block);
            domainOn.remove(i);
            block = new Variable("on" + i, domainOn);
            variables.add(block);
        }
        BooleanVariable pile;
        for (int i = -nbPile; i < 0; i++) {
            pile = new BooleanVariable("free" + i);
            variables.add(pile);
        }
    }

    public Set<Variable> getVariables() {
        return variables;
    } 
}
