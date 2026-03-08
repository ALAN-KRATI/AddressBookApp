package com.bridgeLabz.addressbook.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;


@RestController
@RequestMapping("/addressbook")
public class AddressBookController {
    private Map<String, List<Contact>> addressbooks = new HashMap<>();

    @PostMapping("/add/{bookName}")
    public String addContact(@PathVariable String bookName, @RequestBody Contact contact) {
        List<Contact> contacts = addressbooks.computeIfAbsent(bookName, k -> new ArrayList<>());
        boolean duplicate = contacts.stream().anyMatch(c -> c.getFirstName().equalsIgnoreCase(contact.getFirstName()));
        if(duplicate){
            return "Duplicate contact cannot be added in " + bookName + " address book";
        }

        contacts.add(contact); 
        return "Contact added to " + bookName + " address book";
    }
    
    @GetMapping("/{bookName}")
    public List<Contact> getContacts(@PathVariable String bookName){
        return addressbooks.getOrDefault(bookName, new ArrayList<>());
    }

    @GetMapping("/all")
    public Map<String, List<Contact>> getAllAddressBooks() {
        return addressbooks;
    } 

    @GetMapping("/search/city/{city}")
    public List<Contact> searchByCity(@PathVariable String city) {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .filter(c -> c.getCity().equalsIgnoreCase(city))
                                    .toList();
    }
    
    @GetMapping("/search/state/{state}")
    public List<Contact> searchByState(@PathVariable String state) {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .filter(c -> c.getState().equalsIgnoreCase(state))
                                    .toList();
    }
    
}
