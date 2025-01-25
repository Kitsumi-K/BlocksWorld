package datamining;

import modelling.*;
import java.util.*;

public class Itemset {
    
    private Set<BooleanVariable> items;
    private float frequency;
    
    public Itemset(Set<BooleanVariable> items, float frequency) {
        this.items = items;
        this.frequency = frequency;
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public void setItems(Set<BooleanVariable> items) {
        this.items = items;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "[ items : <" + items + ">, frequency : " + frequency + "]";
    }
}
