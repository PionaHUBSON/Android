package com.example.matcar;

public class Mechanic {
    private String name;
    private String surname;
    private String phone_number;
    private String email;
    private String description;
    private String id;

    public Mechanic() {
    }

    public Mechanic(String name, String surname, String phone_number, String email, String description, String id) {
        this.name = name;
        this.surname = surname;
        this.phone_number = phone_number;
        this.email = email;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
