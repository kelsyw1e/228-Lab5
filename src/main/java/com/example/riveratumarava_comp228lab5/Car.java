package com.example.riveratumarava_comp228lab5;

public class Car {
    private String id;
    private String make;
    private String model;
    private String year;

    public Car(String id, String make, String model, String year) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String toString(){
        return this.make + " " + this.model + " (" + this.year + ")";
    }
}
