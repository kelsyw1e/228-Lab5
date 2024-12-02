package com.example.riveratumarava_comp228lab5;

public class Car {
    private String id;
    private String make;
    private String model;
    private String year;
    private String vin;
    private String type;

    public Car(String id, String make, String model, String year, String vin, String type) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.vin = vin;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String toString(){
        return this.make + " " + this.model + " (" + this.year + ")";
    }

    public String getVin() {
        return vin;
    }

    public String getType() {
        return type;
    }
}
