package blocksworld;

import java.util.*;
import modelling.*;

public class BlocksWorld {
    
    private Integer nbBlock;
    private Integer nbPile;
    private Set<Variable> piles;
    private Set<Variable> blocks;
    
    public BlocksWorld(Integer nbBlock, Integer nbPile) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;

        Set<Object> blockDomain = new HashSet<>();
        for (Integer i = 0; i < nbBlock; i++) {
            blockDomain.add(i);
        }
        for (Integer i = 0; i < nbBlock; i++) {
            Variable v = new Variable("block" + i, blockDomain);
            blocks.add(v);
        }

        Set<Object> pileDomain = new HashSet<>();
        for (Integer i = -1; i >= -nbPile; i--) {
            pileDomain.add(i);
        }
        for (Integer i = -1; i >= -nbPile; i--) {
            Variable v = new Variable("pile" + (-i), blockDomain);
            piles.add(v);
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

    public Set<Variable> getPiles() {
        return piles;
    }

    public void setPiles(Set<Variable> piles) {
        this.piles = piles;
    }

    public Set<Variable> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<Variable> blocks) {
        this.blocks = blocks;
    }
    
}
