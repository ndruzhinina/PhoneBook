package com.example.phonebookwebservice;

import com.example.phonebookwebservice.business.PhoneBookService;
import com.example.phonebookwebservice.business.PhoneBookServiceImpl;
import com.example.phonebookwebservice.repositories.InMemoryRepository;
import com.example.phonebookwebservice.repositories.InMemoryRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.*;

@SpringBootApplication
@ComponentScan(value = {"com.example.phonebookwebservice"})
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Bean
    public Map<String, Set<String>> defaultData() {
        Map<String, Set<String>> data = new LinkedHashMap<>();
        data.put("Alex", new HashSet<>(Arrays.asList("+79601232233")));
        data.put("Billy", new HashSet<>(Arrays.asList("+79213215566", "+79213215567", "+79213215568")));
        return data;
    }

    @Bean(name = "repository")
    public InMemoryRepository inMemoryRepository(Map<String, Set<String>> defaultData) {
        return new InMemoryRepositoryImpl(defaultData);
    }

    @Bean(name = "phoneBookService")
    public PhoneBookService phoneBookService(InMemoryRepository repository) {
        return new PhoneBookServiceImpl(inMemoryRepository(defaultData()));
    }
}
