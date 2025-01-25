package modelling;

import java.util.*;

public class Variable {
    
    private String name;
    private Set<Object> domain;

    public Variable(String name, Set<Object> domain){
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public Set<Object> getDomain() {
        return domain;
    }

    @Override
    public boolean equals(Object o){
        try {
            Variable oBis = (Variable) o;
            if (oBis.getName().equals(name)) {
                return true;
            }             
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "[name : " + name + ", " + domain + "]";
    }
}
