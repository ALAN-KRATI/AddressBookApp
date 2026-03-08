package com.bridgeLabz.addressbook.service;


import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.util.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class AddressBookServiceImp implements AddressBookService{
    @Override
    public List<Contact> getAllContacts(){
        List<Contact> contacts = new ArrayList<>();
        try{
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM contacts");

            while(set.next()){
                Contact contact = new Contact();

                contact.setFirstName(set.getString("firstName"));
                contact.setLastName(set.getString("lastName"));
                contact.setAddress(set.getString("address"));
                contact.setCity(set.getString("city"));
                contact.setState(set.getString("state"));
                contact.setZip(set.getString("zip"));
                contact.setPhoneNumber(set.getString("phoneNumber"));
                contact.setEmail(set.getString("email"));

                contacts.add(contact);
            }
        }
        catch(Exception e){
                e.printStackTrace();
            }

        return contacts;
    }
}
