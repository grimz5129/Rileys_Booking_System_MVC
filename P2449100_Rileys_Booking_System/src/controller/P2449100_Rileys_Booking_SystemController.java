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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.BCrypt;
import model.DatabaseConnection;
import model.User;

/**
 *
 * @author Yefri
 */
public class P2449100_Rileys_Booking_SystemController implements Initializable {
    //Login Page
    @FXML
    private TextField txtEmailLogin;
    @FXML
    private TextField txtPasswordLogin;
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
    //Update profile page
    @FXML
    private ComboBox<String> updatecomboTitle = new ComboBox<String>();
    @FXML
    private TextField txtUpdateFirstName;
    @FXML
    private TextField txtUpdateLastName;
    @FXML
    private TextField txtUpdateEmail;
    @FXML
    private DatePicker dateUpdateDOB;
    @FXML
    private TextField txtUpdatePhoneNumber;
    @FXML
    private TextField txtUpdateAddress;
    @FXML
    private TextField txtUpdateCity;
    @FXML
    private TextField txtUpdatePostCode;
    //Update password
    @FXML
    private TextField txtCurrentPass;
    @FXML
    private TextField txtNewPass;
    @FXML
    private TextField txtNewPass2;
    //booking page
    @FXML
    private ComboBox<String> comboActivity = new ComboBox<String>();
    @FXML
    private DatePicker bookingDate;
    @FXML
    private ComboBox<String> comboTime = new ComboBox<String>();
    @FXML
    private ComboBox<String> comboDuration = new ComboBox<String>();
    
//    String SQLUser = "";
    
    private double xOffset = 0;
    private double yOffset = 0;
    
//    private final User user = new User();
    User user = new User();
//    User user;
    
    @Override
    public void initialize(URL url, ResourceBundle rb ) {
        comboTitle.setItems(FXCollections.observableArrayList("Mr","Mrs","Miss"));
        comboActivity.setItems(FXCollections.observableArrayList("Snooker","American Pool","Darts","Table Tennis"));
        comboTime.setItems(FXCollections.observableArrayList("Morning","Afternoon","Evening"));
        comboDuration.setItems(FXCollections.observableArrayList("30 Minutes","60 Minutes"));
        
        
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
        
        if (txtEmailLogin.getText().isEmpty() || txtPasswordLogin.getText().isEmpty()){
            loginMessageLabel.setText("Please enter email and password");
        } else if (isValid(txtEmailLogin.getText())){
        String query = "SELECT * FROM users WHERE email = '" + txtEmailLogin.getText() + "'";
        
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        
        while(queryResult.next()){
            //setting the SQLUSER string
//            SQLUser = txtEmailLogin.getText();
            if (BCrypt.checkpw(txtPasswordLogin.getText(), queryResult.getString(7))){
            switch (queryResult.getInt("is_staff")) {
                case 0:
                    loginMessageLabel.setText("");
                    alertBox();
                    homepage(event);
                    setUser();
//                    System.out.println("loginPart " + user.toString(user));
                    loadUserData();
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

    @FXML
    private void goBackToLogin(ActionEvent event) throws IOException {
        Parent login_page_parent = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
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
    
    @FXML
    private void loadMenu(ActionEvent event) throws IOException {
        Parent menu_page_parent = FXMLLoader.load(getClass().getResource("/view/menuPage.fxml"));
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
    
    @FXML
    private void Register(ActionEvent event) throws SQLException, IOException {
        int x = isStringEmpty();
        if(x >= 1){
        alertBoxError();
        } else if(!emailExists(txtEmail.getText()) && isSecure(txtPassword1.getText(), txtPassword2.getText()) && isValid(txtEmail.getText()) && isOlder(dateDOB)){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
//        String title = comboTitle.getSelectionModel().getSelectedItem();
//        String title = (String) comboTitle.getSelectionModel().getSelectedItem();
        //im not sure why i cast to string before
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
    @FXML
    private void confirm(ActionEvent event) {
        //do some stuff
        if(null == null){
            
        }
    }
    @FXML
    private void search(ActionEvent event) {
        //do some stuff
        if(null == null){
            
        }
    }
    // manage Booking Page
    @FXML
    private void cancelBooking(ActionEvent event) {
        if (null == null){
            
        }
    }
    //profile page
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
    @FXML
    private void cancelBookingStaff(ActionEvent event) {
        if(null == null){
            
        }
    }
    @FXML
    private void saveChanges(ActionEvent event) {
        if (null == null){
            
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        if (null == null){
            
        }
    }
    //update profile page
    @FXML
    private void UpdateUserDetails(ActionEvent event) {
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
    
    public void alertBox(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
//        String fname = "";
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
//        fname = queryResult.getString(3);
        user.setLastname(queryResult.getString(4));
        user.setBirthDate(queryResult.getDate(5));
        user.setEmail(queryResult.getString(6));
        user.setPhoneNumber(queryResult.getString(8));
        user.setAddress(queryResult.getString(9));
        user.setCity(queryResult.getString(10));
        user.setPostCode(queryResult.getString(11));
        user.setPrivilege(queryResult.getInt(12));
        }
//        this.user.setFirstname(fname);
        //this actually sets the the user in the model package.
//        user.setFirstname("Jeff");
        System.out.println("setUser " + user.toString(user));
    }
//    @FXML
//    public void loadUserData() throws SQLException{
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//        
//        String query = "SELECT * FROM users WHERE email = '" + txtEmailLogin + "'";
////        
//        Statement statement = connectDB.createStatement();
//        ResultSet queryResult = statement.executeQuery(query);
//        
//        if(queryResult.next()){
////        updatecomboTitle.setItems(FXCollections.observableArrayList("Mr","Mrs","Miss"));
////        updatecomboTitle.getSelectionModel().isSelected(0);
//        txtUpdateAddress.setText(user.getAddress());
//        txtUpdateFirstName.setText(queryResult.getString(3));
//        txtUpdateLastName.setText(queryResult.getString(4));
//        txtUpdateEmail.setText(queryResult.getString(6));
////        dateUpdateDOB.setValue(user.getBirthDate());
//        txtUpdatePhoneNumber.setText(queryResult.getString(8));
//        txtUpdateAddress.setText(queryResult.getString(9));
//        txtUpdateCity.setText(queryResult.getString(10));
//        }
//        
//    }
    
    
    @FXML
    public void loadUserData() {
//        User user2 = this.user;
//        User localUser = user;
//        txtUpdateAddress.setText(localUser.getAddress());
//        System.out.print(txtUpdateAddress.getText());
        System.out.println("loadUserData" + user.toString(user));
//        System.out.println("loadUserData" + user.getFirstname());
    }
    
    
}
