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

    @PostMapping("/add-multiple")
    public String addMulitpleContactToDB(@RequestBody List<Contact> contact) {
        return service.addMulitpleContactdb(contact);
    }
}
