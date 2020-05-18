package com.example.matcar;

public class Basket {
    private String name;
    private String price;
    private String options;

    public Basket() {
    }

    public Basket(String name, String price, String options) {
        this.name = name;
        this.price = price;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getOptions() {
        return options;
    }
}
