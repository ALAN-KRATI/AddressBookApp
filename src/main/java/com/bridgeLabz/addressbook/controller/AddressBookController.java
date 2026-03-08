package com.bridgeLabz.addressbook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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

    @DeleteMapping("/delete/{firstName}")
    public String deleteContact(@PathVariable String firstName){
        for(Contact c : contacts){
            if(c.getFirstName().equalsIgnoreCase(firstName)){
                contacts.remove(c);
                return "Contact deleted successfully";
            }
        }

        return "Contact not found";
    }

    @PostMapping("/addMultiple")
    public String addMultipleContacts(@RequestBody List<Contact> newContacts) {
        contacts.addAll(newContacts);        
        return "Multiple contacts added successfully";
    }
    
}
