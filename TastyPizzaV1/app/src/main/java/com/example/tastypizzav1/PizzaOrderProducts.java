package com.example.tastypizzav1;

public class PizzaOrderProducts {
    private String Price;
    private String Size;
    private String Name;

    public PizzaOrderProducts(String price, String size, String name) {
        Price = price;
        Size = size;
        Name = name;
    }
    public String getPrice() {
        return Price;
    }

    public String getSize() {
        return Size;
    }
    public String getName4() {
        return Name;
    }
}
