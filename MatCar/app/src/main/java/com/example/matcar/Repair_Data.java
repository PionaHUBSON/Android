package com.example.matcar;

public class Repair_Data {
    private String document_id;
    private String description;
    private String img;
    private String isFrontLeft;
    private String isFrontRight;
    private String isRearLeft;
    private String isRearRight;
    private String name;
    private String repair_price;


    public Repair_Data() {
    }

    public Repair_Data(String document_id, String description, String img, String isFrontLeft, String isFrontRight, String isRearLeft, String isRearRight, String name, String repair_price) {
        this.document_id = document_id;
        this.description = description;
        this.img = img;
        this.isFrontLeft = isFrontLeft;
        this.isFrontRight = isFrontRight;
        this.isRearLeft = isRearLeft;
        this.isRearRight = isRearRight;
        this.name = name;
        this.repair_price = repair_price;
    }

    public String getDocument_id() {
        return document_id;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public String getIsFrontLeft() {
        return isFrontLeft;
    }

    public String getIsFrontRight() {
        return isFrontRight;
    }

    public String getIsRearLeft() {
        return isRearLeft;
    }

    public String getIsRearRight() {
        return isRearRight;
    }

    public String getName() {
        return name;
    }

    public String getRepair_price() {
        return repair_price;
    }
}
