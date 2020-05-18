package com.example.tastypizzav1;

public class PizzaProducts {
    private int id;
    private String name;
    private String ingredients;
    private String min_pizza_price;
    private String medium_pizza_price;
    private String max_pizza_price;
    private String restaurant_id;

    public PizzaProducts(int id, String name, String ingredients, String min_pizza_price, String medium_pizza_price, String max_pizza_price, String restaurant_id) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.min_pizza_price = min_pizza_price;
        this.medium_pizza_price = medium_pizza_price;
        this.max_pizza_price = max_pizza_price;
        this.restaurant_id = restaurant_id;
    }

    public int getId2() {
        return id;
    }
    public String getName2() {
        return name;
    }
    public String getIngredients() {
        return ingredients;
    }
    public String getMin_pizza_price() {
        return min_pizza_price;
    }
    public String getMedium_pizza_price() {
        return medium_pizza_price;
    }
    public String getMax_pizza_price() {
        return max_pizza_price;
    }
    public String getRestaurant_id() {
        return restaurant_id;
    }
}
