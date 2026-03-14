package com.bridgeLabz.addressbook;

import com.bridgeLabz.addressbook.model.Contact;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class AddressBookJsonTest {

    List<Contact> addressBookMemory = new ArrayList<>();

    @Test
    public void updateContactInJsonServer() {

        baseURI = "http://localhost:3000";

        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;

        Contact updatedContact = new Contact(
                "Rahul",
                "Sharma",
                "Street 999",
                "Delhi",
                "Delhi",
                "110001",
                "9999999999",
                "rahul@gmail.com"
        );

        Response response =
                given()
                        .contentType("application/json")
                        .body(updatedContact)
                .when()
                        .put("/contacts/1");

        //System.out.println(response.asPrettyString());

        Contact contactResponse = response.as(Contact.class);

        addressBookMemory.add(contactResponse);

        System.out.println("Updated Contact: " + contactResponse);
    }
}