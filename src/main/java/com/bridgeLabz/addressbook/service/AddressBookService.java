package com.bridgeLabz.addressbook.service;

import java.util.List;
import java.util.Map;

import com.bridgeLabz.addressbook.model.Contact;

public interface AddressBookService {
    List<Contact> getAllContactsFromDB();
    List<Contact> getContactsByRange(String startDate, String endDate);
}