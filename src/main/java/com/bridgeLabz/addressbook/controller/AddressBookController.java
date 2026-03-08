package com.bridgeLabz.addressbook.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




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

    @GetMapping("/view/city")
    public Map<String, List<Contact>> viewContactsByCity() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy(Contact::getCity));
    }

    @GetMapping("/view/state")
    public Map<String, List<Contact>> viewContactsByState() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy(Contact::getState));
    }

    @GetMapping("/count/city")
    public Map<String, Long> countByCity() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy
                                        (Contact::getCity, Collectors.counting()
                                    ));
    }
    
    @GetMapping("/count/state")
    public Map<String, Long> countByState() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy
                                        (Contact::getState, Collectors.counting()
                                    ));
    }

    @GetMapping("/sort/name")
    public List<Contact> sortByName() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted((c1, c2) -> c1.getFirstName().compareToIgnoreCase(c2.getFirstName()))
                                    .toList();
    }
    
    @GetMapping("/sort/city")
    public List<Contact> sortByCity() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted((Comparator.comparing(Contact::getCity, String.CASE_INSENSITIVE_ORDER)))
                                    .toList();
    }

    @GetMapping("/sort/state")
    public List<Contact> sortByState() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted((Comparator.comparing(Contact::getState, String.CASE_INSENSITIVE_ORDER)))
                                    .toList();
    }

    @GetMapping("/sort/zip")
    public List<Contact> sortByZip() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted(Comparator.comparing(Contact::getZip))
                                    .toList();
    }

}
