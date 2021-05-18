package com.example.phonebookwebservice.business;

import com.example.phonebookwebservice.models.Customer;

import java.util.List;


public interface PhoneBookService {
    List<Customer> findAll();

    Customer findByName(String name);

    Customer addPhoneToCustomer(String name, String phone) throws Exception;

    Customer create(Customer customer) throws Exception;

    void removeCustomer(String name) throws Exception;
}
