package com.bridgeLabz.addressbook.controller;

import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;


@RestController
@RequestMapping("/addressbook")
public class AddressBookController {
    
    @PostMapping("/create")
    public Contact createContact(@RequestBody Contact contact){
       return contact;
    }
}
