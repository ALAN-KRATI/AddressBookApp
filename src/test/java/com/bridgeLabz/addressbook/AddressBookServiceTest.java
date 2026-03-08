package com.bridgeLabz.addressbook;

import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.service.AddressBookService;
import com.bridgeLabz.addressbook.service.AddressBookServiceImp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddressBookServiceTest {

    AddressBookService service = new AddressBookServiceImp();

    @Test
    void givenContact_whenAdded_shouldReturnSuccess() {

        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setCity("Delhi");

        String result = service.addContact("family", contact);

        assertEquals("Contact added successfully", result);
    }

    @Test
    void givenDuplicateContact_whenAdded_shouldReturnDuplicateMessage() {

        Contact contact = new Contact();
        contact.setFirstName("John");

        service.addContact("friends", contact);

        String result =
                service.addContact("friends", contact);

        assertEquals("Duplicate contact cannot be added", result);
    }

    @Test
    void givenCity_whenSearch_shouldReturnContacts() {

        Contact contact = new Contact();
        contact.setFirstName("Alice");
        contact.setCity("Mumbai");

        service.addContact("office", contact);

        List<Contact> contacts =
                service.searchByCity("Mumbai");

        assertTrue(contacts.size() > 0);
    }

    @Test
    void givenContacts_whenSorted_shouldReturnList() {

        List<Contact> contacts =
                service.sortByName();

        assertNotNull(contacts);
    }
}