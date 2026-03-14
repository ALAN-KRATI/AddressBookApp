package com.bridgeLabz.addressbook.service;

import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.util.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    public String addMulitpleContactdb(List<Contact> contact){
        contact.forEach(c ->{
            Thread thread = new Thread(() -> {
                addContactdb(c);
            });
            thread.start();
        });

        return "All contacts added successfully!";
    }

    @Override
    public String addContactdb(Contact contact){
        try{
            Connection connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO contacts(firstName, lastName, address, city, state, zip, phoneNumber, email, date_added) VALUES(?, ?, ?, ?, ?, ?, ? ,?, CURDATE()) ";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getAddress());
            ps.setString(4, contact.getCity());
            ps.setString(5, contact.getState());
            ps.setString(6, contact.getZip());
            ps.setString(7, contact.getPhoneNumber());
            ps.setString(8, contact.getEmail());

            ps.executeUpdate();
            connection.commit();
            return "Contact successfully to Database";
        }
        catch(Exception e){
            return "Error adding contact";
        }
    }

    //private AddressBookRepository repository = new AddressBookRepository();

    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public void addContactAsync(Contact contact) {

        executor.submit(() -> {
            addContactdb(contact);
            System.out.println("Contact added to DB in background thread");
        });

    }
}
