/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author Yefri
 */
public class ProfilePageController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private TextArea UserData;
    @FXML
    private TextArea AddressData;
    
//    private User user;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        User user = getUser();
//        UserData.appendText(user.getFirstname().toString());
//        UserData.setText(user.getAddress());
//        System.out.println("profilePage getUser" + getUser());
//        System.out.println("profilePage address to string" + user.getAddress().toString());
//        User.class.get
//        getUser().toString();
//        System.out.println("profile page" + );
//        UserData.setText(user.getFirstname());
//        System.out.println("profile page " + user.getFirstname());
//        UserData.appendText(user.getUser().toString(user));
        AddressData.setText("test");
        UserData.appendText("testing");
        
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
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/homePage/homePage.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        home_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        home_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    private void UpdatePasswordPage(ActionEvent event) throws IOException {
        Parent password_page_parent = FXMLLoader.load(getClass().getResource("/updatePasswordPage/updatePasswordPage.fxml"));
        Scene password_page_scene = new Scene(password_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        password_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        password_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(password_page_scene);
        app_stage.show();
    }

    @FXML
    private void updateProfilePage(ActionEvent event) throws IOException {
        Parent update_profile_page_parent = FXMLLoader.load(getClass().getResource("/updateProfilePage/updateProfilePage.fxml"));
        Scene update_profile_page_scene = new Scene(update_profile_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        update_profile_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        update_profile_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(update_profile_page_scene);
        app_stage.show();
    }
    
//    public User getUser(){
////        User user = getUser();
//        String firstname = user.getFirstname();
//        String lastname = user.getLastname();
//        Date birthDate = user.getBirthDate();
//        String email = user.getEmail();
//        String phonenumber = user.getPhoneNumber();
//        String address = user.getAddress();
//        String city = user.getCity();
//        String postcode = user.getPostCode();
//        int privilege = user.getPrivilege();
////        System.out.println("getUser profile page" + user);
//        return user;
//    }
    
    
    
    
    
}
