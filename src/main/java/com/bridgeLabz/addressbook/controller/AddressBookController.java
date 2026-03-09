package com.bridgeLabz.addressbook.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.service.AddressBookService;
import com.bridgeLabz.addressbook.service.AddressBookServiceImp;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/addressbook")
public class AddressBookController {
    AddressBookService service = new AddressBookServiceImp();

    @PostMapping("/add/{bookName}")
    public String addContact(@PathVariable String bookName, @RequestBody Contact contact) {
        return service.addContact(bookName, contact);
    }
    
    @GetMapping("/{bookName}")
    public List<Contact> getContacts(@PathVariable String bookName){
        return service.getContacts(bookName);
    }

    @GetMapping("/all")
    public List<Contact> getAllContacts() {
        return service.getAllContactsFromDB();
    } 

    @GetMapping("/search/city/{city}")
    public List<Contact> searchByCity(@PathVariable String city) {
        return service.searchByCity(city);
    }
    
    @GetMapping("/search/state/{state}")
    public List<Contact> searchByState(@PathVariable String state) {
        return service.searchByState(state);
    }    

    @GetMapping("/view/city")
    public Map<String, List<Contact>> viewByCity() {
        return service.viewByCity();
    }

    @GetMapping("/view/state")
    public Map<String, List<Contact>> viewByState() {
        return service.viewByState();
    }

    @GetMapping("/count/city")
    public Map<String, Long> countByCity() {
        return service.countByCity();
    }
    
    @GetMapping("/count/state")
    public Map<String, Long> countByState() {
        return service.countByState();
    }

    @GetMapping("/sort/name")
    public List<Contact> sortByName() {
        return service.sortByName();
    }
    
    @GetMapping("/sort/city")
    public List<Contact> sortByCity() {
        return service.sortByCity();
    }

    @GetMapping("/sort/state")
    public List<Contact> sortByState() {
        return service.sortByState();
    }

    @GetMapping("/sort/zip")
    public List<Contact> sortByZip() {
        return service.sortByZip();
    }

    @GetMapping("/write")
    public String writeToFile(){
        return service.writeToFile();
    }

    @GetMapping("/read")
    public List<String> readFromFile() {
        return service.readFromFile();
    }

    @GetMapping("/writeCSV")
    public String writeToCSV() {
        return service.writeCSV();
    }

    @GetMapping("/readCSV")
    public List<String[]> readFromCSV() {
        return service.readCSV();
    }

    @GetMapping("/writeJSON")
    public String writeToJSON() {
        return service.writeJSON();
    }

    @GetMapping("/readJSON")
    public List<Contact> readFromJSON() {
        return service.readJSON();
    }
    
    @PutMapping("/update")
    public String updateContact(@RequestBody Contact contact){
        boolean b = service.updateContact(contact);
        if(b) return "Contact updated successfully";

        return "Contact not found";
    }

    @GetMapping("/contacts/date")
    public List<Contact> getContactsByDate(@RequestParam String startDate, @RequestParam String endDate) {
        return service.getContactsByRange(startDate, endDate);
    }
}
