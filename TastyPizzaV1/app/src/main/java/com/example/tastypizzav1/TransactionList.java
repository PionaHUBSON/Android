package com.example.tastypizzav1;

public class TransactionList {
    private String content;
    private String total_order_value;
    private String customer_name;
    private String customer_surname;
    private String customer_email;
    private String customer_city;
    private String customer_street;
    private String customer_apartment_number;
    private String customer_postcode;
    private String customer_phone_number;
    private String date;

    public TransactionList(String content, String total_order_value, String customer_name, String customer_surname, String customer_email, String customer_city, String customer_street, String customer_apartment_number, String customer_postcode, String customer_phone_number, String date) {
        this.content = content;
        this.total_order_value = total_order_value;
        this.customer_name = customer_name;
        this.customer_surname = customer_surname;
        this.customer_email = customer_email;
        this.customer_city = customer_city;
        this.customer_street = customer_street;
        this.customer_apartment_number = customer_apartment_number;
        this.customer_postcode = customer_postcode;
        this.customer_phone_number = customer_phone_number;
        this.date = date;
    }

    public String getContent2() {
        return content;
    }

    public String getTotal_order_value() {
        return total_order_value;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_surname() {
        return customer_surname;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getCustomer_city() {
        return customer_city;
    }

    public String getCustomer_street() {
        return customer_street;
    }

    public String getCustomer_apartment_number() {
        return customer_apartment_number;
    }

    public String getCustomer_postcode() {
        return customer_postcode;
    }

    public String getCustomer_phone_number() {
        return customer_phone_number;
    }

    public String getDate() {
        return date;
    }
}

