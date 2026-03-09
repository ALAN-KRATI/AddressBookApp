package com.bridgeLabz.addressbook.service;

import java.util.List;
import java.util.Map;

import com.bridgeLabz.addressbook.model.Contact;

public interface AddressBookService {

    String addContact(String bookName, Contact contact);

    List<Contact> getContacts(String bookName);

    Map<String, List<Contact>> getAllAddressBooks();

    List<Contact> searchByCity(String city);

    List<Contact> searchByState(String state);

    Map<String, List<Contact>> viewByCity();

    Map<String, List<Contact>> viewByState();

    Map<String, Long> countByCity();

    Map<String, Long> countByState();

    List<Contact> sortByName();

    List<Contact> sortByCity();

    List<Contact> sortByState();

    List<Contact> sortByZip();

    String writeToFile();

    List<String> readFromFile();

    String writeCSV();

    List<String[]> readCSV();

    String writeJSON();

    List<Contact> readJSON();

    List<Contact> getAllContactsFromDB();

    boolean updateContact(Contact contact);

    Contact getContactFromDB(String firstName, String lastName);

    List<Contact> getContactsByRange(String startDate, String endDate);
}