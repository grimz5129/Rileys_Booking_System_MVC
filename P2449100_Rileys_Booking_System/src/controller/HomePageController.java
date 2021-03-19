/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yefri
 */
public class HomePageController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimise;
    @FXML
    private Button btnBack;

    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void closeWindow(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void minimiseWindow(ActionEvent event) {
        Stage s = (Stage) ((Node)event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent login_page_parent = FXMLLoader.load(getClass().getResource("/View/loginPage.fxml"));
        Scene login_page_scene = new Scene(login_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        login_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        login_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(login_page_scene);
        app_stage.show();
    }

    @FXML
    private void loadProfile(ActionEvent event) throws IOException {
        Parent profile_page_parent = FXMLLoader.load(getClass().getResource("/view/profilePage.fxml"));
        Scene profile_page_scene = new Scene(profile_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        profile_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        profile_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(profile_page_scene);
        app_stage.show();
    }

    @FXML
    private void loadMenu(ActionEvent event) throws IOException {
        Parent menu_page_parent = FXMLLoader.load(getClass().getResource("/View/menuPage.fxml"));
        Scene menu_page_scene = new Scene(menu_page_parent);
        Stage stage = new Stage();
        
        menu_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        menu_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        stage.setResizable(false);
        stage.setScene(menu_page_scene);
        stage.show();
        
    }

    @FXML
    private void loadBookings(ActionEvent event) throws IOException {
        Parent profile_page_parent = FXMLLoader.load(getClass().getResource("/View/manageBookingPage.fxml"));
        Scene profile_page_scene = new Scene(profile_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        profile_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        profile_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(profile_page_scene);
        app_stage.show();
    }

    @FXML
    private void loadBookingSearch(ActionEvent event) throws IOException {
        Parent booking_page_parent = FXMLLoader.load(getClass().getResource("/View/bookingPage.fxml"));
        Scene booking_page_scene = new Scene(booking_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        booking_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        booking_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(booking_page_scene);
        app_stage.show();
    }

    @FXML
    private void openTwitter(ActionEvent event) {
        try{
            Desktop.getDesktop().browse(new URI("https://twitter.com/clubrileys"));
        } catch (IOException | URISyntaxException e) {
        }
    }
    
    @FXML
    private void openFacebook(ActionEvent event) {
        try{
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/clubrileys"));
        } catch (IOException | URISyntaxException e) {
        }
    }
    
}
