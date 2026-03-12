package com.bridgeLabz.addressbook.controller;


import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.service.AddressBookService;
import com.bridgeLabz.addressbook.service.AddressBookServiceImp;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/addressbook")
public class AddressBookController {
    AddressBookService service = new AddressBookServiceImp();

    @GetMapping("/contacts/date")
    public List<Contact> getContactsByDate(@RequestParam String startDate, @RequestParam String endDate) {
        return service.getContactsByRange(startDate, endDate);
    }
}
