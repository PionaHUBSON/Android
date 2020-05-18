package com.example.tastypizzav1;

public class Restaurant {
    private int id;
    private String name;
    private String city;
    private String street;
    private String apartment_number;
    private String postcode;
    private String min_price;
    private String delivery_cost;
    private String logo_img;
    private String phone_number;

    public Restaurant(int id, String name, String city, String street, String apartment_number, String postcode, String min_price, String delivery_cost, String logo_img, String phone_number) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.apartment_number = apartment_number;
        this.postcode = postcode;
        this.min_price = min_price;
        this.delivery_cost = delivery_cost;
        this.logo_img = logo_img;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getApartment_number() {
        return apartment_number;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getMin_price() {
        return min_price;
    }

    public String getDelivery_cost() {
        return delivery_cost;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getImage() {
        return logo_img;
    }
}
