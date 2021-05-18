package com.example.phonebookwebservice.repositories;

import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Keeps phoneBook data in memory in ordered in accordance to addition.
 */
@Repository
public class InMemoryRepositoryImpl implements InMemoryRepository {

    private Map<String, Set<String>> data;

    /**
     * no args constructor
     */
    public InMemoryRepositoryImpl() {
        this(new LinkedHashMap<>());
    }

    /**
     * this constructor allows to inject initial data to the repository
     *
     * @param data
     */
    public InMemoryRepositoryImpl(Map<String, Set<String>> data) {
        this.data = new LinkedHashMap<>(data);
    }

    @Override
    public Map<String, Set<String>> findAll() {
        return new LinkedHashMap<>(this.data);
    }

    @Override
    public Set<String> findAllPhonesByName(String name) {
        return data.get(name);
    }

    @Override
    public void addPhone(String name, String phone) {
        if(data.containsKey(name)) {
            data.get(name).add(phone);
        } else {
            Set<String> newSet = new HashSet<>();
            newSet.add(phone);
            data.put(name, newSet);
        }
    }

    @Override
    public void removeEntry(String name) {
        data.remove(name);
    }
}
