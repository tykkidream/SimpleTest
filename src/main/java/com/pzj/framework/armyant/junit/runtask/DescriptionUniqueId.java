package com.pzj.framework.armyant.junit.runtask;

import java.io.Serializable;

/**
 * Created by Saber on 2017/4/4.
 */
public class DescriptionUniqueId implements Serializable {
    private String parentUniqueId;
    private String selfUniqueId;

    public DescriptionUniqueId(String parentUniqueId, String selfUniqueId){
        this.parentUniqueId = parentUniqueId;
        this.selfUniqueId = selfUniqueId;
    }

    public boolean equals(String compareUniqueId) {
        return parentUniqueId.equals(compareUniqueId) || selfUniqueId.equals(compareUniqueId);
    }

    public String getParentUniqueId() {
        return parentUniqueId;
    }

    public void setParentUniqueId(String parentUniqueId) {
        this.parentUniqueId = parentUniqueId;
    }

    public String getSelfUniqueId() {
        return selfUniqueId;
    }

    public void setSelfUniqueId(String selfUniqueId) {
        this.selfUniqueId = selfUniqueId;
    }

    public String toString(){
        return parentUniqueId;
    }
}
