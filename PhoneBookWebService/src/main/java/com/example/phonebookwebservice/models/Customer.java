package com.example.phonebookwebservice.models;

import java.util.Set;

public class Customer {

    public Customer(String name, Set<String> phones) {
        this.name = name;
        this.phones = phones;
    }

    private String name;
    private Set<String> phones;

    public Set<String> getPhones() {
        return phones;
    }

    public String getName() {
        return name;
    }
}
