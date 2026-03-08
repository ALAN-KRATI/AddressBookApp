package com.bridgeLabz.addressbook;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.bridgeLabz.addressbook.service.AddressBookServiceImp;

public class AddressBookServiceTest {
    @Test
    void getAllContactsFromDBTest(){
        AddressBookServiceImp service = new AddressBookServiceImp();
        int size = service.getAllContacts().size();
        assertTrue(size > 0);
    }
}
