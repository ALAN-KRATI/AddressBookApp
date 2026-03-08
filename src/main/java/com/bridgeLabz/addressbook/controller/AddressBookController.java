package com.bridgeLabz.addressbook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {
    private List<Contact> contacts = new ArrayList<>();
    
    @PostMapping("/add")
    public String createContact(@RequestBody Contact contact){
        contacts.add(contact);
        return "Contact added to Address Book successfully";
    }

    @GetMapping("/all")
    public List<Contact> getAllContacts() {
        return contacts;
    }
    
}
