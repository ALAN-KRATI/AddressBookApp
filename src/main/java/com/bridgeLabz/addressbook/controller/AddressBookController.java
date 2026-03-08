package com.bridgeLabz.addressbook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;


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

    @PutMapping("/edit/{firstName}")
    public String editContact(@PathVariable String firstName, @RequestBody Contact contact) {
        for(Contact c : contacts){
            if(c.getFirstName().equalsIgnoreCase(firstName)){
                c.setLastName(contact.getLastName());
                c.setAddress(contact.getAddress());
                c.setCity(contact.getCity());
                c.setState(contact.getState());
                c.setZip(contact.getZip());
                c.setPhoneNumber(contact.getPhoneNumber());
                c.setEmail(contact.getEmail());

                return "Contact updated successfully";
            }
        }
        
        return "Contact not found";
    }
    
}
