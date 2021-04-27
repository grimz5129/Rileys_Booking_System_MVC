/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Yefri
 */
public class DatabaseConnection {
    public Connection databaseLink;
    
    public Connection getConnection(){
//        String databaseName = "rileysdb3";
//        String databaseName = "rileysdb";
        String databaseName = "sql4407938";
//        String databaseUser = "root";
        String databaseUser = "sql4407938";
//        String databasePassword = "";
        String databasePassword = "ujfQRKHxQE";
//        String url = "jdbc:mysql://localhost/" + databaseName;
        String url = "jdbc:mysql://sql4.freesqldatabase.com:3306/" + databaseName;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            
        } catch (ClassNotFoundException | SQLException e){
        }
        return databaseLink;
    }
}
