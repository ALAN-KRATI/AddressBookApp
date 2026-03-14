package com.bridgeLabz.addressbook.controller;


import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.service.AddressBookService;
import com.bridgeLabz.addressbook.service.AddressBookServiceImp;


@RestController
@RequestMapping("/addressbook")
public class AddressBookController {
    AddressBookService service = new AddressBookServiceImp();

    @PostMapping("/addToDB")
    public String addContactToDB(@RequestBody Contact contact) {
        return service.addContactdb(contact);
    }
}
