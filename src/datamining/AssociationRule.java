package datamining;

import java.util.*;
import modelling.*;

public class AssociationRule {
    
    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;
    
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency,
            float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }
    public Set<BooleanVariable> getPremise() {
        return premise;
    }
    public void setPremise(Set<BooleanVariable> premise) {
        this.premise = premise;
    }
    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }
    public void setConclusion(Set<BooleanVariable> conclusion) {
        this.conclusion = conclusion;
    }
    public float getFrequency() {
        return frequency;
    }
    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
    public float getConfidence() {
        return confidence;
    }
    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public String toString() {
        return "[premise : <"+ premise +">, conclusion : <"+ conclusion +">, frequency : "+ frequency +", confidence : " + confidence + "]";
    }
    
}
