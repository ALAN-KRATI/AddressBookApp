package com.bridgeLabz.addressbook.service;

import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.util.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class AddressBookServiceImp implements AddressBookService {
    
    @Override
    public List<Contact> getAllContactsFromDB() {
        List<Contact> contacts = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM contacts");

            while (set.next()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    
    }

    @Override
    public List<Contact> getContactsByRange(String startDate, String endDate) {
        List<Contact> contacts = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM contacts WHERE DATE(date_added) BETWEEN ? AND ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);

            ResultSet set = ps.executeQuery();
            while (set.next()) {
                Contact c = new Contact();

                c.setFirstName(set.getString("firstName"));
                c.setLastName(set.getString("lastName"));
                c.setAddress(set.getString("address"));
                c.setCity(set.getString("city"));
                c.setState(set.getString("state"));
                c.setZip(set.getString("zip"));
                c.setPhoneNumber(set.getString("phoneNumber"));
                c.setEmail(set.getString("email"));

                contacts.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }
}
