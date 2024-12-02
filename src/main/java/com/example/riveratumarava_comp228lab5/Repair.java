package com.example.riveratumarava_comp228lab5;

public class Repair {
    private String id;
    private String repairDate;
    private String description;
    private String cost;
    private String owner;
    private String ownerId;
    private String car;
    private String carId;

    public Repair(String id, String repairDate, String description, String cost, String owner, String ownerId, String car, String carId) {
        this.id = id;
        this.repairDate = repairDate;
        this.description = description;
        this.cost = cost;
        this.owner = owner;
        this.car = car;
        this.carId = carId;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public String getCost() {
        return cost;
    }

    public String getCar() {
        return car;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getCarId() {
        return carId;
    }
}
