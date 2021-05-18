package com.phonebook.main;

import com.phonebook.spring.PhoneBook;
import com.phonebook.spring.PhoneBookFormatter;

import java.util.Arrays;
import java.util.List;

public class PhoneBookCommander {

    private PhoneBook phoneBook;
    private PhoneBookFormatter renderer;

    public PhoneBookCommander(PhoneBook phoneBook, PhoneBookFormatter renderer) {
        this.phoneBook = phoneBook;
        this.renderer = renderer;
    }

    void ExecuteCommand(String commandName, String parameters) throws Exception {
        String[] paramTokens = parameters == null
                ? null
                : parameters.split("\\s");

        switch(commandName) {
            case "ADD":
                executeAdd(paramTokens);
                break;
            case "REMOVE_PHONE":
                executeRemovePhone(paramTokens);
                break;
            case "SHOW":
                executeShow(paramTokens);
                break;
            default:
                renderer.error("Unknown command");
        }
    }

    private void executeAdd(String[] paramTokens) throws Exception  {
        if(paramTokens != null && paramTokens.length == 2){
            String name = paramTokens[0];
            List<String> phones = Arrays.asList(paramTokens[1].split(","));
            phoneBook.add(name, phones);
        } else
            throw new Exception("Invalid command format");
    }

    private void executeRemovePhone(String[] paramTokens) throws Exception {
        if(paramTokens != null && paramTokens.length == 1) {
            String phone = paramTokens[0];
            phoneBook.removePhone(phone);
        } else
            throw new Exception("Invalid command format");
    }

    private void executeShow(String[] paramTokens) throws Exception {
        if(paramTokens == null || paramTokens.length == 0)
            renderer.show(phoneBook.findAll());
        else
            throw new Exception("Invalid command format");;
    }
}

