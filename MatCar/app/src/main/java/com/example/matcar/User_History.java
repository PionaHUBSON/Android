package com.example.matcar;

public class User_History {
    private String client_id;
    private String date_and_time;
    private String date_of_order;
    private String mechanic;
    private String name_of_service;
    private String status;
    private String document_id;
    private String repair_price;
    private String car;
    private String car_id;

    public User_History() {
    }

    public User_History(String client_id, String date_and_time, String date_of_order, String mechanic, String name_of_service, String status, String repair_price
            , String car, String car_id) {
        this.client_id = client_id;
        this.date_and_time = date_and_time;
        this.date_of_order = date_of_order;
        this.mechanic = mechanic;
        this.name_of_service = name_of_service;
        this.status = status;
        this.repair_price = repair_price;
        this.car = car;
        this.car_id = car_id;
    }

    public String getCar() {
        return car;
    }

    public String getCar_id() {
        return car_id;
    }

    public String getDocument_id() {
        return document_id;
    }

    public String getRepair_price() {
        return repair_price;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public String getDate_of_order() {
        return date_of_order;
    }

    public String getMechanic() {
        return mechanic;
    }

    public String getName_of_service() {
        return name_of_service;
    }

    public String getStatus() {
        return status;
    }
}
