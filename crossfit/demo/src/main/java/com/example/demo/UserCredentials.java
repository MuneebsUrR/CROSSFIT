package com.example.demo;

public class UserCredentials {
    private static UserCredentials instance;

    public String name;
    public String email;


    private UserCredentials(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public static synchronized UserCredentials getInstance(String name, String email) {
        if (instance == null) {
            instance = new UserCredentials(name, email);
        }
        return instance;
    }
    public static void resetInstance() {
        instance = null;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
