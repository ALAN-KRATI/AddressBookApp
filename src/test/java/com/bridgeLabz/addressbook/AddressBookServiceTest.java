package com.bridgeLabz.addressbook;

import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.service.AddressBookService;
import com.bridgeLabz.addressbook.service.AddressBookServiceImp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class AddressBookServiceTest {

    private static AddressBookService service;

    @BeforeAll
    static void setup(){
        service = new AddressBookServiceImp();
        service.addContact("family", new Contact("Alice", "Smith", "Street 1", "Agra", "UP", "112233", "9876543210", "Alice@gmail.com"));
        service.addContact("family", new Contact("Olive", "Harper", "Street 2", "Mathura", "UP", "334455", "9999999999", "olive@gmail.com"));
        service.addContact("office", new Contact("Jack", "Parker", "Street 3", "Agra", "UP", "445566", "8888888888", "jack@gmail.com"));
        service.addContact("office", new Contact("James", "Baker", "Street 4", "Mumbai", "Maharastra", "667788", "7777777777", "james@gmail.com"));
        service.addContact("friends", new Contact("Alankrati", "Saxena", "Street 5", "Bangalore", "Karnataka", "778899", "6666666666", "alankrati@gmail.com"));
    }

    @Test
    void addContactTest(){
        Contact  c = new Contact("Shruti", "Sharma", "Street 5", "Agra", "UP", "282007", "5555555555", "shruti@gmail.com");
        assertEquals("Contact added successfully", service.addContact("friends", c));
    }

    @Test
    void DuplicateContactTest(){
        Contact  c = new Contact("Alice", "Smith", "Street 1", "Agra", "UP", "112233", "9876543210", "Alice@gmail.com");
        assertEquals("Duplicate contact cannot be added in family address book", service.addContact("family", c));
    }

    @Test
    void getContactsForAddressBookTest(){
        int size = service.getContacts("family").size();
        assertEquals(2, size);
    }

    @Test
    void getAllAddressBooksTest(){
        int size = service.getAllAddressBooks().size();
        assertEquals(3, size);
    }

    @Test
    void searchByCityTest(){
        int size = service.searchByCity("Agra").size();
        assertEquals(2, size);
    }

    @Test
    void searchByStateTest(){
        int size = service.searchByState("UP").size();
        assertEquals(3, size);
    }

    @Test
    void viewByCityTest(){
        int size = service.viewByCity().size();
        assertEquals(4, size);
    }

    @Test
    void viewByStateTest(){
        int size = service.viewByState().size();
        assertEquals(3, size);
    }

    @Test
    void countByCityTest(){
        Map<String, Long> count = service.countByCity();
        assertEquals(3, count.get("Agra"));
    }

    @Test
    void countByStateTest(){
        Map<String, Long> count = service.countByState();
        assertEquals(4, count.get("UP"));
        assertEquals(1, count.get("Karnataka"));
    }

    @Test
    void sortByNameTest(){
        List<Contact> list = service.sortByName();
        assertEquals("Alankrati", list.get(0).getFirstName());
        assertEquals("Alice", list.get(1).getFirstName());
    }

    @Test
    void sortByCityTest(){
        List<Contact> list = service.sortByCity();
        assertEquals("Jack", list.get(0).getFirstName());
    }

    @Test
    void sortByStateTest(){
        List<Contact> list = service.sortByState();
        assertEquals("Alankrati", list.get(0).getFirstName());
        assertEquals("James", list.get(1).getFirstName());
    }

    @Test
    void givenContact_whenUpdated_shouldSyncWithDB() {
        Contact c = new Contact("Alice", "Smith", "Street 9", "Mumbai", "Maharastra", "222222", "987656789", "alicia@gmail.com");
        service.updateContact(c);
        Contact dbContact = service.getContactFromDB("Alice", "Smith");

        assertEquals(c, dbContact);
    }
}