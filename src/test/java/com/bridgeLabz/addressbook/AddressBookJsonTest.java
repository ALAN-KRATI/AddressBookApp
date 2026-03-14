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
    public void deleteContactFromJsonServer() {

        baseURI = "http://localhost:3000";

        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;

        int id = 1;

        Response response =
                given()
                .when()
                .delete("/contacts/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("Deleted Contact Response: " + response.asPrettyString());

        
        addressBookMemory.removeIf(c -> c.getId() == id);

        System.out.println("Contact removed from AddressBook memory.");
    }
}