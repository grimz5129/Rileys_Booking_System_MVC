/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.BCrypt;
import model.DatabaseConnection;
//import utils.BCrypt;

/**
 * FXML Controller class
 *
 * @author Yefri
 */
public class RegisterPageController implements Initializable {

    
    @FXML
    private ComboBox<String> comboTitle;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dateDOB;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtPostCode;
    @FXML
    private PasswordField txtPassword1;
    @FXML
    private PasswordField txtPassword2;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          comboTitle.getItems().addAll(FXCollections.observableArrayList("Mr","Mrs","Miss"));
    }    

    @FXML
    private void Register(ActionEvent event) throws SQLException {
        int x = isStringEmpty();
        if(x >= 1){
        alertBoxError();
        } else if(!emailExists(txtEmail.getText()) && isSecure(txtPassword1.getText(), txtPassword2.getText()) && isValid(txtEmail.getText()) && isOlder(dateDOB)){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        String title = comboTitle.getSelectionModel().getSelectedItem();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String birthDate = dateDOB.getValue().toString();
        String phoneNumber = txtPhoneNumber.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String postCode = txtPostCode.getText();
        String hashedPass = BCrypt.hashpw(txtPassword1.getText(), BCrypt.gensalt(12));
        
        Integer is_staff = 0;
        
        String insertFields = "INSERT INTO users(title, firstname, lastname, birthdate, email, password, phonenumber, address, city, postcode, is_staff) VALUES ('";
        String insertValues = title+"','"+firstName+"','"+lastName+"','"+birthDate+"','"+email+"','"+hashedPass+"','"+phoneNumber+"','"+address+"','"+city+"','"+postCode+"','"+is_staff+"')";
        String insertToRegister = insertFields+insertValues;
        try{
            Statement statement = connectDB.createStatement();
            statement.execute(insertToRegister);
            alertBox();
            goBack(event);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        } else {
            if(emailExists(txtEmail.getText())){
                alertBoxUnique();
            }
            if(!isValid(txtEmail.getText())){
                alertBoxEmail();
            } 
            if(!isOlder(dateDOB)){
                alertBoxDate();
            }
            if(!isSecure(txtPassword1.getText(), txtPassword2.getText())){
                alertBoxPass();
            }
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

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent login_page_parent = FXMLLoader.load(getClass().getResource("/loginPage/loginPage.fxml"));
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
   
    public void alertBox(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("User is now registered");
        alert.showAndWait();
    }
    
    public void alertBoxError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Fields cannot be empty");
        alert.setContentText("Please do not leave blank");
        alert.showAndWait();
    }
    
    public void alertBoxPass(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Password Error");
        alert.setContentText("Passwords must match");
        alert.showAndWait();
    }
    
    public void alertBoxEmail(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Email Error");
        alert.setContentText("Email must be valid");
        alert.showAndWait();
    }
    
    public void alertBoxDate(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Must be 18 or older");
        alert.setContentText("Please check date of birth");
        alert.showAndWait();
    }
    
    public void alertBoxUnique(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Email is already registered");
        alert.setContentText("Please use a different email or login");
        alert.showAndWait();
    }
    
    public int isStringEmpty(){
        int counter = 0;
        
        if (txtFirstName.getText().isEmpty()) {
            counter++;
        } else if (txtLastName.getText().isEmpty()) {
            counter++;
        } else if (txtEmail.getText().isEmpty()) {
            counter++;
        } else if (txtAddress.getText().isEmpty()) {
            counter++;
        } else if (txtCity.getText().isEmpty()) {
            counter++;
        } else if (txtPostCode.getText().isEmpty()) {
            counter++;
        } else if (txtPassword1.getText().isEmpty()) {
            counter++;
        } else if (txtPassword2.getText().isEmpty()) {
            counter++;
        } else if (dateDOB.toString().isEmpty()) {
            counter++;
        }
        return counter;
    }
    
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
    
    public static boolean isOlder(DatePicker date){
        LocalDate currentDate = LocalDate.now();
        LocalDate newDate = LocalDate.of(date.getValue().getYear(), date.getValue().getMonth(), date.getValue().getDayOfMonth());
        Period period = Period.between(currentDate, newDate);
        if(period.getYears() <= -18){
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isSecure(String password,String password2) {
        if(password.length() >= 8 && password.equals(password2)){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean emailExists(String email) throws SQLException {
        String query1 = "SELECT email FROM users WHERE email = '" + email + "'";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        Statement statement = connectDB.createStatement();
        ResultSet rs = statement.executeQuery(query1);
        Boolean exist = null;
        
        if(rs.next() && rs.getString("email").equals(email)){
            exist = true;
        } else {
            exist = false;
        }
        return exist;
    }
    
    
    
    
}
