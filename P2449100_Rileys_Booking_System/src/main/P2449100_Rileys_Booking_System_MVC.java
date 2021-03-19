/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Yefri
 * 
 * so basically the new reg page works so you need to transfer over and make new classes no copy and pasting no work
 */
public class P2449100_Rileys_Booking_System_MVC extends Application {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
        //loads the login page
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));

        //staff page
//        Parent root = FXMLLoader.load(getClass().getResource("/StaffPage/staffPage.fxml"));

        //register Page
//        Parent root = FXMLLoader.load(getClass().getResource("/registerPage/RegisterPage.fxml"));
        
        //booking page
//        Parent root = FXMLLoader.load(getClass().getResource("/bookingPage/bookingPage.fxml"));
        
        //homepage
//        Parent root = FXMLLoader.load(getClass().getResource("/homePage/homePage.fxml"));
        
        //menu
//        Parent root = FXMLLoader.load(getClass().getResource("/menuPage/menuPage.fxml"));
        
        //manageBookingPage
//        Parent root = FXMLLoader.load(getClass().getResource("/manageBookingPage/manageBookingPage.fxml"));
        
        //profilePage
//        Parent root = FXMLLoader.load(getClass().getResource("/profilePage/profilePage.fxml"));
        
        //updateProfilePage
//        Parent root = FXMLLoader.load(getClass().getResource("/updateProfilePage/updateProfilePage.fxml"));
        
        //updatePasswordPage
//        Parent root = FXMLLoader.load(getClass().getResource("/updatePasswordPage/updatePasswordPage.fxml"));
        
        
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
    
    
}
