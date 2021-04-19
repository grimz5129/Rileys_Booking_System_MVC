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
        String databaseName = "rileysdb3";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            
        } catch (ClassNotFoundException | SQLException e){
        }
        return databaseLink;
    }
}
