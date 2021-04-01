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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BCrypt;
import model.DatabaseConnection;
import model.User;

/**
 * P2449100 Final Year Project - Riley's Booking System
 * @author Yefri
 */
public class P2449100_Rileys_Booking_SystemController implements Initializable {
    //Login Page
    @FXML
    private TextField txtEmailLogin;
    @FXML
    private PasswordField txtPasswordLogin;
    @FXML
    private Label loginMessageLabel;
    //Register Page
    @FXML
    private ComboBox<String> comboTitle = new ComboBox<String>();
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
    //UpdateProfilePage
    @FXML
    private ComboBox<String> updateComboTitle = new ComboBox<String>();
    @FXML
    private TextField txtUpdateFirstName = new TextField();
    @FXML
    private TextField txtUpdateLastName = new TextField();
    @FXML
    private TextField txtUpdateEmail = new TextField();
    @FXML
    private DatePicker dateUpdateDOB = new DatePicker();
    @FXML
    private TextField txtUpdatePhoneNumber = new TextField();
    @FXML
    private TextField txtUpdateAddress = new TextField();
    @FXML
    private TextField txtUpdateCity = new TextField();
    @FXML
    private TextField txtUpdatePostCode = new TextField();
    //booking page
    @FXML
    private ComboBox<String> comboActivity = new ComboBox<String>();
    @FXML
    private ComboBox<String> comboTime = new ComboBox<String>();
    @FXML
    private ComboBox<String> comboDuration = new ComboBox<String>();
    @FXML
    private DatePicker bookingDate;
    //update password page
    @FXML
    private PasswordField txtCurrentPass;
    @FXML
    private PasswordField txtNewPass;
    @FXML
    private PasswordField txtNewPass2;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    static final User user;
    static{ 
        user = new User();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboTitle.setItems(FXCollections.observableArrayList("Mr","Mrs","Miss"));
        comboActivity.setItems(FXCollections.observableArrayList("Snooker","American Pool","Darts","Table Tennis"));
        comboTime.setItems(FXCollections.observableArrayList("Morning","Afternoon","Evening"));
        comboDuration.setItems(FXCollections.observableArrayList("30 Minutes","60 Minutes"));
        updateComboTitle.setItems(FXCollections.observableArrayList("Mr","Mrs", "Miss"));
        loadUserData();
        
    }
    
    /**
     * login connects to the database and verifies if the user is valid.
     * This method checks if the user is a customer or a member of staff
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        if (txtEmailLogin.getText().isEmpty() || txtPasswordLogin.getText().isEmpty()){
            loginMessageLabel.setText("Please enter email and password");
        } else if (isValid(txtEmailLogin.getText())){
        String query = "SELECT * FROM users WHERE email = '" + txtEmailLogin.getText() + "'";
        
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        
        while(queryResult.next()){
            if (BCrypt.checkpw(txtPasswordLogin.getText(), queryResult.getString(7))){
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
                    txtEmailLogin.clear();
                    txtPasswordLogin.clear();
                    break;
                }
            }
            loginMessageLabel.setText("Invalid Login");
            txtEmailLogin.clear();
            txtPasswordLogin.clear();
        }
            loginMessageLabel.setText("Invalid Login");
            txtEmailLogin.clear();
            txtPasswordLogin.clear();
        } else {
            loginMessageLabel.setText("invalid email or password");
        } 
    }
    /**
     * A button event that closes the application
     * @param event 
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        System.exit(0);
    }
    /**
     * A button event that minimises the application to taskbar
     * @param event 
     */
    @FXML
    private void minimiseWindow(ActionEvent event) {
        Stage s = (Stage) ((Node)event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }
    /**
     * If the user is a member of staff they will be redirected to the staff view page
     * @param event
     * @throws IOException 
     */
    @FXML
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
    /**
     * The user is redirected to the register page.
     * @param event
     * @throws IOException 
     */
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
    /**
     * The user can confirm or cancel logging out of the application.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void goBackToLogin(ActionEvent event) throws IOException {
        Parent login_page_parent = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
        Scene login_page_scene = new Scene(login_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText("You will now be logged out");
        alert.getDialogPane().setHeaderText("LOGOUT");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
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
        } else if (result.get() == ButtonType.CANCEL){
            
        }
        
    }
    /**
     * On the homepage this will redirect the user to the profile page.
     * The user will be able to view and change their information.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void loadProfile(ActionEvent event) throws IOException {
        //Made it so that it loads the updateProfilePage over the profilepag egoing to remove that page.
        Parent profile_page_parent = FXMLLoader.load(getClass().getResource("/view/updateProfilePage.fxml"));
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
    /**
     * This will open a new window with the food and drink menu.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void loadMenu(ActionEvent event) throws IOException {
        Parent menu_page_parent = FXMLLoader.load(getClass().getResource("/view/menuPage.fxml"));
        Scene menu_page_scene = new Scene(menu_page_parent);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/images/RileyLogo.png")));
        
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
    /**
     * This will redirect the user to the page where they can see their bookings.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void loadBookings(ActionEvent event) throws IOException {
        Parent profile_page_parent = FXMLLoader.load(getClass().getResource("/view/manageBookingPage.fxml"));
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
    /**
     * This redirects the user to the page where they can search for available bookings.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void loadBookingSearch(ActionEvent event) throws IOException {
        Parent booking_page_parent = FXMLLoader.load(getClass().getResource("/view/bookingPage.fxml"));
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
    /**
     * This opens a link to the Twitter page on your default browser.
     * @param event 
     */
    @FXML
    private void openTwitter(ActionEvent event) {
        try{
            Desktop.getDesktop().browse(new URI("https://twitter.com/clubrileys"));
        } catch (IOException | URISyntaxException e) {
        }
    }
    /**
     * This opens a link to the Facebook page on your default browser.
     * @param event 
     */
    @FXML
    private void openFacebook(ActionEvent event) {
        try{
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/clubrileys"));
        } catch (IOException | URISyntaxException e) {
        }
    }
    /**
     * When registering a user multiple checks are in place before the user is registered and the data is sent to the database.
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    private void Register(ActionEvent event) throws SQLException, IOException {
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
            alertBoxRegister();
            goBackToLogin(event);
        } catch (SQLException e) {
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
    /**
     * goBack will redirect the user back to the homepage.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/view/homePage.fxml"));
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
    //this is Booking Page Controller
    /**
     * Books the desired booking.
     * @param event 
     */
    @FXML
    private void confirm(ActionEvent event) {
        //do some stuff
        if(null == null){
            
        }
    }
    /**
     * After entering search parameters, the user will be shown available bookings.
     * @param event 
     */
    @FXML
    private void search(ActionEvent event) {
        
        comboActivity.getSelectionModel().getSelectedItem();
        bookingDate.getValue();
        comboTime.getSelectionModel().getSelectedItem();
        comboDuration.getSelectionModel().getSelectedItem();
        
        
        
        
        
        
        
        
//        if(null == null){
//            
//        }
    }
    // manage Booking Page
    /**
     * This is to cancel a previously made booking.
     * @param event 
     */
    @FXML
    private void cancelBooking(ActionEvent event) {
        if (null == null){
            
        }
    }
    //profile page
    /**
     * The user is redirected to the change password page.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void UpdatePasswordPage(ActionEvent event) throws IOException {
        Parent password_page_parent = FXMLLoader.load(getClass().getResource("/view/updatePasswordPage.fxml"));
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
    /**
     * This redirects the user from the change password page back to the profile page.
     * @param event
     * @throws IOException 
     */
    @FXML
    void goBackProfile(ActionEvent event) throws IOException {
        Parent password_page_parent = FXMLLoader.load(getClass().getResource("/view/updateProfilePage.fxml"));
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
    //staff view page
    /**
     * This allows a member of staff to cancel a booking
     * @param event 
     */
    @FXML
    private void cancelBookingStaff(ActionEvent event) {
        if(null == null){
            
        }
    }
    //update password page
    /**
     * This updates the users password.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void saveChanges(ActionEvent event) throws SQLException, IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        String hashedPass2 = BCrypt.hashpw(txtNewPass.getText(), BCrypt.gensalt(12));
        
        String query = "SELECT * FROM users WHERE email = '" + user.getEmail() + "'";
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        
        String storedPass = "";
        while(queryResult.next()){
            storedPass = queryResult.getString(7);
        }
        
        if(BCrypt.checkpw(txtCurrentPass.getText(), storedPass) == true && txtNewPass.getText().equals(txtNewPass2.getText())){
            try {
                String updateQuery = "Update users set password='"+hashedPass2+"' where email='"+user.getEmail()+"' ";
                PreparedStatement pst = connectDB.prepareStatement(updateQuery);
                pst.execute();
                alertBoxPassChange();
                pst.close();
                loadProfile(event);
            } catch(SQLException e) {
                System.out.println("Error occured while updating record!");
            }
        } else {
            alertBoxPassError();
        }
        
    }
    /**
     * The cancel button on update password page.
     * @param event 
     */
    @FXML
    private void Cancel2(ActionEvent event) throws IOException {
        loadProfile(event);
    }
    //update profile page
    /**
     * The cancel button on update profile page.
     * @param event 
     */
    @FXML
    private void Cancel(ActionEvent event) throws IOException {
        homepage(event);
    }
    /**
     * This checks if the user has changed any details and updates the database.
     * @param event 
     */
    @FXML
    private void UpdateUserDetails(ActionEvent event) throws IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        String title = updateComboTitle.getSelectionModel().getSelectedItem();
        String firstName = txtUpdateFirstName.getText();
        String lastName = txtUpdateLastName.getText();
        String email = txtUpdateEmail.getText();
        String birthDate = dateUpdateDOB.getValue().toString();
        String phoneNumber = txtUpdatePhoneNumber.getText();
        String address = txtUpdateAddress.getText();
        String city = txtUpdateCity.getText();
        String postCode = txtUpdatePostCode.getText();
        
        try {
            String query = "UPDATE users SET title=?, firstname=?, lastname=?, birthdate=?, email=?, phonenumber=?, address=?, city=?, postcode=? where cust_id="+user.getCust_ID();
            PreparedStatement pst = connectDB.prepareStatement(query);
            pst.setString(1, title);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, birthDate);
            pst.setString(5, email);
            pst.setString(6, phoneNumber);
            pst.setString(7, address);
            pst.setString(8, city);
            pst.setString(9, postCode);

            pst.execute();
            alertBoxUserUpdate();
            updateUserData();
            pst.close();
        } catch(SQLException e) {
            System.out.println("Error occured while updating record!");
        } 
    }
    /**
     * The homepage method redirects a customer to the homepage after a successful login
     * @param event
     * @throws IOException 
     */
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
     * This validates the email during registration to ensure each email entered is linked to only one account.
     * @param email
     * @return
     * @throws SQLException 
     */
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
    
    public void alertBoxUserUpdate(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Update Successful");
        alert.setContentText("You have updated your profile");
        alert.showAndWait();
    }
    
    public void alertBoxPassError(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Password Change Unsuccessful");
        alert.setContentText("Password does not match");
        alert.showAndWait();
    }
    
    public void alertBoxPassChange(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Password Change Successful");
        alert.setContentText("next time log in with new password");
        alert.showAndWait();
    }
    
    public void alertBoxRegister(){
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
    /**
     * This checks if all text field are empty on registration page.
     * @return 
     */
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
    /**
     * This validates the age and only allows users who are 18+
     * @param date
     * @return 
     */
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
    /**
     * This verifies that the passwords match and that the password is over 8 characters long
     * @param password
     * @param password2
     * @return 
     */
    public static boolean isSecure(String password,String password2) {
        if(password.length() >= 8 && password.equals(password2)){
            return true;
        } else {
            return false;
        }
    }
    /**
     * alertBox for successful login
     */
    public void alertBox(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Login Successful");
        alert.setContentText("Email and Password Match");

        alert.showAndWait();
    }
    /**
     * Registration form alertBox informing the user what they must do 
     */
    public void alertBoxRules(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Registration Form");
        alert.setContentText("1. Make sure all information is valid \n2. The password must be 8 characters or more \n3. You must be 18 or older.");
        alert.showAndWait();
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
        
        String query = "SELECT * FROM users WHERE email = '" + txtEmailLogin.getText() + "'";
        
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        //going to move this to top of class
        if(queryResult.next()){
        user.setCust_ID(queryResult.getInt(1));
        user.setTitle(queryResult.getString(2));
        user.setFirstname(queryResult.getString(3));
        user.setLastname(queryResult.getString(4));
        user.setBirthDate(queryResult.getDate(5).toLocalDate());
        user.setEmail(queryResult.getString(6));
        user.setPhoneNumber(queryResult.getString(8));
        user.setAddress(queryResult.getString(9));
        user.setCity(queryResult.getString(10));
        user.setPostCode(queryResult.getString(11));
        user.setPrivilege(queryResult.getInt(12));
        
        }
//        System.out.println("setUser " + user.toString(user));
    }
    /**
     * This loads the user data on the profile page.
     */
    public void loadUserData(){
//        System.out.println("loadUserData" + user.toString(user));
        updateComboTitle.setValue(user.getTitle());
        txtUpdateFirstName.appendText(user.getFirstname());
        txtUpdateLastName.appendText(user.getLastname());
        txtUpdateEmail.setText(user.getEmail());
        dateUpdateDOB.setValue(user.getBirthDate());
        txtUpdatePhoneNumber.setText(user.getPhoneNumber());
        txtUpdateAddress.setText(user.getAddress());
        txtUpdateCity.setText(user.getCity());
        txtUpdatePostCode.setText(user.getPostCode());
        
    }
    
    public void updateUserData(){
        user.setTitle(updateComboTitle.getSelectionModel().getSelectedItem());
        user.setFirstname(txtUpdateFirstName.getText());
        user.setLastname(txtUpdateLastName.getText());
        user.setBirthDate(dateUpdateDOB.getValue());
        user.setEmail(txtUpdateEmail.getText());
        user.setPhoneNumber(txtUpdatePhoneNumber.getText());
        user.setAddress(txtUpdateAddress.getText());
        user.setCity(txtUpdateCity.getText());
        user.setPostCode(txtUpdatePostCode.getText());
    }
    
    
}
