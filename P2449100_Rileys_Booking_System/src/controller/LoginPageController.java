/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.BCrypt;
import model.User;
/**
 * FXML Controller class
 *
 * @author Yefri
 */
public class LoginPageController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label loginMessageLabel;

    private double xOffset = 0;
    private double yOffset = 0;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * login is called when the login button is pressed
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        if (txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()){
            loginMessageLabel.setText("Please enter email and password");
            return;
        } else if (isValid(txtEmail.getText())){
            String query = "SELECT * FROM users WHERE email = '" + txtEmail.getText() + "'";
        
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        
        while(queryResult.next()){
            if (BCrypt.checkpw(txtPassword.getText(), queryResult.getString(7))){
            switch (queryResult.getInt("is_staff")) {
                case 0:
                    loginMessageLabel.setText("");
                    alertBox();
                    homepage(event);
                    setUser();
                    break;
                case 1:
                    loginMessageLabel.setText("");
                    alertBox();
                    loadStaffPage(event);
                    setUser();
                    break;
                default:
                    loginMessageLabel.setText("Invalid Login");
                    txtEmail.clear();
                    txtPassword.clear();
                    break;
                }
            }
            loginMessageLabel.setText("Invalid Login");
            txtEmail.clear();
            txtPassword.clear();
//            setUser();
        }
            loginMessageLabel.setText("Invalid Login");
            txtEmail.clear();
            txtPassword.clear();
        } else {
            loginMessageLabel.setText("invalid email or password");
        } 
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

    private void loadStaffPage(ActionEvent event) throws IOException {
        Parent staff_page_parent = FXMLLoader.load(getClass().getResource("/view/staffViewPage.fxml"));
        Scene staff_page_scene = new Scene(staff_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        staff_page_scene.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        staff_page_scene.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(staff_page_scene);
        app_stage.show();
    }

    @FXML
    private void loadRegisterPage(ActionEvent event) throws IOException {
        
        Parent register_page_parent = FXMLLoader.load(getClass().getResource("/view/RegisterPage.fxml"));
        Scene register_page_scene = new Scene(register_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        register_page_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        register_page_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(register_page_scene);
        app_stage.show();
        alertBoxRules(); 
    }
    
    public void alertBox(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Login Successful");
        alert.setContentText("Email and Password Match");

        alert.showAndWait();
    }
    
    public void alertBoxRules(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Registration Form");
        alert.setContentText("1. Make sure all information is valid \n2. The password must be 8 characters or more \n3. You must be 18 or older.");
        alert.showAndWait();
    }
    
    public void homepage(ActionEvent event) throws IOException{
        Parent homepage_parent = FXMLLoader.load(getClass().getResource("/view/homePage.fxml"));
        Scene homepage_scene = new Scene(homepage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        homepage_parent.setOnMousePressed((MouseEvent event1) -> {
            xOffset = stage.getX() - event1.getScreenX();
            yOffset = stage.getY() - event1.getScreenY();
        });
        homepage_parent.setOnMouseDragged((MouseEvent event1) -> {
            stage.setX(event1.getScreenX() + xOffset);
            stage.setY(event1.getScreenY() + yOffset);
        });
        
        app_stage.setScene(homepage_scene);
        app_stage.show();
    }
    /**
     * This validates the string and checks if it follows the format of a valid email address.
     * @param email
     * @return 
     */
    public static boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) {
            return false;
        } 
        return pat.matcher(email).matches(); 
    } 
    /**
     * Sets the data for the user class
     * @throws SQLException 
     */
    public void setUser() throws SQLException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        String query = "SELECT * FROM users WHERE email = '" + txtEmail.getText() + "'";
        
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        User user = new User();
        
        if(queryResult.next()){
        user.setCust_ID(queryResult.getInt(1));
        user.setTitle(queryResult.getString(2));
        user.setFirstname(queryResult.getString(3));
        user.setLastname(queryResult.getString(4));
        user.setBirthDate(queryResult.getDate(5));
        user.setEmail(queryResult.getString(6));
        user.setPhoneNumber(queryResult.getString(8));
        user.setAddress(queryResult.getString(9));
        user.setCity(queryResult.getString(10));
        user.setPostCode(queryResult.getString(11));
        user.setPrivilege(queryResult.getInt(12));
        }
        //this actually sets the the user in the model package.
//        System.out.println("Login page controller second next" + user.toString(user));
    }
    
    
    
    
}
