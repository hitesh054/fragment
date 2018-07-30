package com.example.hitesh054.fragment.Models;

/**
 * Created by hitesh054 on 28-05-2018.
 */

public class Route { private String Rid, name,Did;

    public Route() {
    }
    public Route(String Rid, String name,String Did) {
        this.name = name;
        this.Rid= Rid;
        this.Did= Did;

    }

    public String getRId() {
        return Rid;
    }

    public void setRId(String id) {
        this.Rid = id;
    }

    public String getDId() {
        return Did;
    }

    public void setDId(String id) {
        this.Did = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
