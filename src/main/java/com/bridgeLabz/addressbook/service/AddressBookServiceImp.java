package com.bridgeLabz.addressbook.service;


import com.bridgeLabz.addressbook.model.Contact;
import com.bridgeLabz.addressbook.util.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;

public class AddressBookServiceImp implements AddressBookService{
    private Map<String, List<Contact>> addressbooks = new HashMap<>();
    
    public String addContact(String bookName, Contact contact) {
        List<Contact> contacts = addressbooks.computeIfAbsent(bookName, k -> new ArrayList<>());
        boolean duplicate = contacts.stream().anyMatch(c -> c.getFirstName().equalsIgnoreCase(contact.getFirstName()));
        if(duplicate){
            return "Duplicate contact cannot be added in " + bookName + " address book";
        }

        contacts.add(contact); 
        return "Contact added successfully";
    }

    @Override
    public List<Contact> getContacts(String bookName){
        return addressbooks.getOrDefault(bookName, new ArrayList<>());
    }

    @Override
    public Map<String, List<Contact>> getAllAddressBooks() {
        return addressbooks;
    } 

    @Override
    public List<Contact> searchByCity(String city) {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .filter(c -> c.getCity().equalsIgnoreCase(city))
                                    .toList();
    }

    @Override
    public List<Contact> searchByState(String state) {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .filter(c -> c.getState().equalsIgnoreCase(state))
                                    .toList();
    }   
    
    @Override
    public Map<String, List<Contact>> viewByCity() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy(Contact::getCity));
    }

    @Override
    public Map<String, List<Contact>> viewByState() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy(Contact::getState));
    }

    @Override
    public Map<String, Long> countByCity() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy
                                        (Contact::getCity, Collectors.counting()
                                    ));
    }

    @Override
    public Map<String, Long> countByState() {
        return addressbooks.values().stream()
                                    .flatMap(List::stream)
                                    .collect(Collectors.groupingBy
                                        (Contact::getState, Collectors.counting()
                                    ));
    }

    @Override
    public List<Contact> sortByName() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted((c1, c2) -> c1.getFirstName().compareToIgnoreCase(c2.getFirstName()))
                                    .toList();
    }

    @Override
    public List<Contact> sortByCity() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted((Comparator.comparing(Contact::getCity, String.CASE_INSENSITIVE_ORDER)))
                                    .toList();
    }

    @Override
     public List<Contact> sortByState() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted((Comparator.comparing(Contact::getState, String.CASE_INSENSITIVE_ORDER)))
                                    .toList();
    }

    @Override
     public List<Contact> sortByZip() {
        return addressbooks.values().stream()   
                                    .flatMap(List::stream)
                                    .sorted(Comparator.comparing(Contact::getZip))
                                    .toList();
    }

    @Override
     public String writeToFile(){
        try{
            FileWriter writer = new FileWriter("addressbooks.txt");

            for(List<Contact> contacts : addressbooks.values()){
                for(Contact c : contacts){
                    writer.write(
                        c.getFirstName() + " | " +
                        c.getLastName() + " | " + 
                        c.getAddress() + " | " + 
                        c.getCity() + " | " +
                        c.getState() +  " | " +
                        c.getZip() + " | " + 
                        c.getPhoneNumber() + " | " +
                        c.getEmail() + "\n"
                    );
                }
            }

            writer.close();
            return "Contacts written to file successfully";
        } 
        catch(IOException e){
            return "Error writing file";
        }    
    }

    @Override
    public List<String> readFromFile() {
        List<String> lines = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("addressbooks.txt"));

            String line;
            while((line = reader.readLine()) != null){
                lines.add(line);
            }

            reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return lines;
    }

    @Override
    public String writeCSV() {
        try(CSVWriter writer = new CSVWriter(new FileWriter("addressbook.csv"))){
            for(List<Contact> contacts: addressbooks.values()){
                for(Contact c : contacts){
                    String[] lines = {
                        c.getFirstName(),
                        c.getLastName(),
                        c.getAddress(),
                        c.getCity(),
                        c.getState(),
                        c.getZip(),
                        c.getPhoneNumber(),
                        c.getEmail()
                    };

                    writer.writeNext(lines);
                }
            }
            return "Contacts written to CSV File";
        }
        catch(IOException e){
            return "Error writing in CSV";
        }
    }

    @Override
    public List<String[]> readCSV() {
        List<String[]> lines = new ArrayList<>();
        try(CSVReader reader = new CSVReader(new FileReader("addressbook.csv"))){
            lines = reader.readAll();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return lines;
    }

    @Override
    public String writeJSON() {
        try{
            Gson gson = new Gson();

            List<Contact> contacts = addressbooks.values().stream()
                                                          .flatMap(List::stream)
                                                          .toList();
            
            FileWriter writer = new FileWriter("addressbook.json");
            gson.toJson(contacts, writer);
            writer.close();

            return "Contacts written in JSON file";
        }
        catch(Exception e){
            return "Error writing in JSON";
        }
    }

    @Override
    public List<Contact> readJSON() {
        try{
            Gson gson = new Gson();
            FileReader reader = new FileReader("addressbook.json");
            Type type = new TypeToken<List<Contact>>(){}.getType();
            List<Contact> contacts = gson.fromJson(reader, type);
            reader.close();
            return contacts;
        }
        catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public List<Contact> getAllContactsFromDB(){
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
