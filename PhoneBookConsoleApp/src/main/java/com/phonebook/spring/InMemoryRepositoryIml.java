package com.phonebook.spring;

import com.phonebook.main.InMemoryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Keeps phoneBook data in memory in ordered in accordance to addition.
 */
@Repository
public class InMemoryRepositoryIml implements InMemoryRepository {

    private Map<String, Set<String>> data;

    /**
     * no args constructor
     */
    public InMemoryRepositoryIml() {
        // LinkedHashMap is chosen because usually iteration order matters
        this(new LinkedHashMap<>());
    }

    /**
     * this constructor allows to inject initial data to the repository
     *
     * @param data
     */
    public InMemoryRepositoryIml(Map<String, Set<String>> data) {
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
    public String findNameByPhone(String phone) {
        for(Map.Entry<String, Set<String>> entry: data.entrySet()) {
            if(entry.getValue().contains(phone))
                return entry.getKey();
        }
        return null;
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
    public void removePhone(String phone) throws IllegalArgumentException {
        List<String> entriesToRemove = new ArrayList<>();
        for(Map.Entry<String, Set<String>> entry: data.entrySet()) {
            Set<String> phoneSet = entry.getValue();
            phoneSet.remove(phone);
            if(phoneSet.isEmpty())
                entriesToRemove.add(entry.getKey());
        }

        for(String key : entriesToRemove) {
            data.remove((key));
        }
    }
}
