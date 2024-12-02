package com.example.riveratumarava_comp228lab5;

public class Owner {
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Owner(String id, String name, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String toString(){
        return String.format("%s (%s)", name, email);
    }
}
