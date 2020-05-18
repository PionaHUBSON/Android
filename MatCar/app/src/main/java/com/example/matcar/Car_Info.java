package com.example.matcar;

public class Car_Info {
    private String VIN;
    private String brand;
    private String model;
    private String year;
    private String document_id;

    public Car_Info() {
    }

    public Car_Info(String brand, String model, String year, String VIN, String document_id) {
        this.VIN = VIN;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.document_id = document_id;
    }

    public String getDocument_id() {
        return document_id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getVIN() {
        return VIN;
    }
}
