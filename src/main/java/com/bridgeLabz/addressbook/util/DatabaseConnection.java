package com.bridgeLabz.addressbook.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getConnection() throws Exception{
        String url = "jdbc:mysql://localhost:3306/addressbook";
        String user = "root";
        String password = "Alan@2004";

        return DriverManager.getConnection(url, user, password);
        
    }
}
