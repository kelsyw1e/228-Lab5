package com.example.riveratumarava_comp228lab5;

public class Owner {
    private String id;
    private String name;

    public Owner(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String toString(){
        return this.name;
    }
}
