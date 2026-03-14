package com.bridgeLabz.addressbook;

import com.bridgeLabz.addressbook.model.Contact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class AddressBookJsonTest {

    List<Contact> addressBookMemory = new ArrayList<>();

    @Test
    public void readContactsFromJsonServer() {

        baseURI = "http://localhost:3000";

        Response response = given().when().get("/contacts");

        Contact[] contacts = response.as(Contact[].class);

        for (Contact c : contacts) {
            addressBookMemory.add(c);
        }

        System.out.println("Contacts loaded: " + addressBookMemory.size());
    }
}