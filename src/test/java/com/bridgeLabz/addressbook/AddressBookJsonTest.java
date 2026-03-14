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
    public void addMultipleContactsToJsonServer() {

        baseURI = "http://localhost:3000";

        Contact contact1 = new Contact(
                "Aman","Verma","Street 10","Delhi","Delhi",
                "110001","9999991111","aman@gmail.com"
        );

        Contact contact2 = new Contact(
                "Riya","Sharma","Street 20","Mumbai","Maharashtra",
                "400001","8888882222","riya@gmail.com"
        );

        List<Contact> contacts = List.of(contact1, contact2);

        for(Contact contact : contacts){

            Response response =
                    given()
                            .contentType("application/json")
                            .body(contact)
                            .when()
                            .post("/contacts");

            Contact newContact = response.as(Contact.class);

            addressBookMemory.add(newContact);
        }

        System.out.println("Contacts added to memory: " + addressBookMemory.size());
    }
}