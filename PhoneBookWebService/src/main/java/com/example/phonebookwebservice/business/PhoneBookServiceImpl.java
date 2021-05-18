package com.example.phonebookwebservice.business;

import com.example.phonebookwebservice.models.Customer;
import com.example.phonebookwebservice.repositories.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * PhoneBook service implementation
 */
@Service
public class PhoneBookServiceImpl implements PhoneBookService {

    private InMemoryRepository repository;

    /**
     * injection is supported on constructor level.
     *
     * @param repository
     */
    public PhoneBookServiceImpl(InMemoryRepository repository) {
        this.repository = repository;
    }

    /**
     * injection is supported on setter level
     *
     * @param repository
     */
    public void setRepository(InMemoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all entries in the phone book
     */
    @Override
    public List<Customer> findAll() {
        Map<String, Set<String>> rawData = repository.findAll();

        List<Customer> result = new ArrayList<>();
        for(Map.Entry<String, Set<String>> entry : rawData.entrySet()) {
            result.add(new Customer(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    /**
     * @return single entry from the phone book for the specified name
     *
     * @param name
     */
    @Override
    public Customer findByName(String name) {
        Set<String> phones = repository.findAllPhonesByName(name);
        return phones == null ? null : new Customer(name, phones);
    }

    /**
     * adds new phone number to an existing phone book entry
     *
     * @param name
     * @param phone
     *
     * @return updated phone book entry
     */
    @Override
    public Customer addPhoneToCustomer(String name, String phone) throws Exception {
        if(repository.findAllPhonesByName(name) != null)
            repository.addPhone(name, phone);
        else
            throw new Exception("Phonebook entry not found");

        return findByName(name);
    }

    /**
     * creates new phone book entry
     *
     * @param customer
     *
     * @return created phone book entry
     */
    @Override
    public Customer create(Customer customer) throws Exception {
        if(repository.findAllPhonesByName(customer.getName()) == null) {
            for(String phone: customer.getPhones())
                repository.addPhone(customer.getName(), phone);
        } else
            throw new Exception("Phonebook entry exists");

        return findByName(customer.getName());
    }

    /**
     * deletes an existing phone book entry
     *
     * @param name
     */
    @Override
    public void removeCustomer(String name) throws Exception {
        if(repository.findAllPhonesByName(name) == null)
            throw new Exception("Phonebook entry not found");
        else
            repository.removeEntry(name);
    }
}
