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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BCrypt;
import model.Booking;
import model.DatabaseConnection;
import model.User;

/**
 * P2449100 Final Year Project - Riley's Booking System
 * This is my booking system that has been written in Java with the use of JavaFXML
 * Tools: Netbeans IDE 12.0, XAMPP, Github, SceneBuilder.
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
    private DatePicker bookingDate = new DatePicker();
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9 = new Button();
    //update password page
    @FXML
    private PasswordField txtCurrentPass;
    @FXML
    private PasswordField txtNewPass;
    @FXML
    private PasswordField txtNewPass2;
    //manage Booking Page
    @FXML
    private TableView<Booking> bookingTableView = new TableView<>();
    @FXML
    private TableColumn<Booking, String> activityColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> dateColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> periodofdayColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> timeColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> durationColumn = new TableColumn<>();
    //staffViewPage
    @FXML
    private TableView<Booking> staffTableView = new TableView<>();
    @FXML
    private TableColumn<Booking, String> staffActivityColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> staffDateColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> staffPeriodofdayColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> staffTimeColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> staffDurationColumn = new TableColumn<>();
    @FXML
    private TableColumn<Booking, String> staffCustColumn = new TableColumn<>();
    
    String bookingTime;
    private double xOffset = 0;
    private double yOffset = 0;
    
    static final User user;
    static{ 
        user = new User();
    }
    
    /**
     * This is where methods and data are being called/set.
     * Adds all data when a new scene is initialized
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboTitle.setItems(FXCollections.observableArrayList("Mr","Mrs","Miss"));
        comboActivity.setItems(FXCollections.observableArrayList("Snooker","American Pool","Darts","Table Tennis"));
        comboTime.setItems(FXCollections.observableArrayList("Afternoon","Evening"));
        comboDuration.setItems(FXCollections.observableArrayList("30 Minutes","60 Minutes"));
        updateComboTitle.setItems(FXCollections.observableArrayList("Mr","Mrs", "Miss"));
        loadUserData();
        
        bookingDate.setDayCellFactory(picker -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };  });
        
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        periodofdayColumn.setCellValueFactory(new PropertyValueFactory<>("periodofday"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        staffActivityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        staffPeriodofdayColumn.setCellValueFactory(new PropertyValueFactory<>("periodofday"));
        staffDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        staffTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        staffDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        staffCustColumn.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        
        try {
            bookingTableView.setItems(getBookings());
        } catch (SQLException ex) {
            Logger.getLogger(P2449100_Rileys_Booking_SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            staffTableView.setItems(getAllBookings());
        } catch (SQLException ex) {
            Logger.getLogger(P2449100_Rileys_Booking_SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
     * The back button from register page back to login
     * @param event
     * @throws IOException 
     */
    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
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
     * The user selects a time and confirms the booking.
     * @param event 
     */
    @FXML
    private void confirm(ActionEvent event) throws SQLException, ParseException, IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        String activity = comboActivity.getSelectionModel().getSelectedItem();
        LocalDate date = bookingDate.getValue();
        String time = bookingTime;
        String periodofday = comboTime.getSelectionModel().getSelectedItem();
        int duration = 0;
        int cust_id = user.getCust_ID();
        
        if(comboDuration.getSelectionModel().getSelectedItem().contains("30")){
            duration = 30;
        } else {
            duration = 60;
        }
        
        String insertFields = "INSERT INTO booking(activity, date, periodofday, duration, time, cust_id) VALUES ('";
        String insertValues = activity+"','"+date+"','"+periodofday+"','"+duration+"','"+time+"','"+cust_id+"')";
        String insertToBooking = insertFields+insertValues;
        
        if(!bookingTime.isEmpty()){
            try{
                Statement statement = connectDB.createStatement();
                statement.execute(insertToBooking);
                alertBoxBookingComplete();
                statement.close();
                loadBookings(event);
            } catch (SQLException e){
            } 
        }
        
    }
    
    /**
     * After entering search parameters, the user will be shown available bookings.
     * @param event 
     */
    @FXML
    private void search(ActionEvent event) throws SQLException {
        reset();
        disableButton();
        boolean activity1 = comboActivity.getSelectionModel().getSelectedIndex() < 0;
        LocalDate date1 = bookingDate.getValue();
        boolean timeofDay1 = comboTime.getSelectionModel().getSelectedIndex() < 0;
        boolean duration1 = comboDuration.getSelectionModel().getSelectedIndex() <0;
        String timeofDay2 = comboTime.getSelectionModel().getSelectedItem();
        
        if(activity1 || date1 == null || timeofDay1 || duration1){
            alertBoxBookingSearch();
        } else if(!activity1 || !date1.isEqual(null) || !timeofDay1 || !duration1){
            if(timeofDay2.equals("Afternoon")){
            btn1.setText("12:00");
            btn2.setText("12:30");
            btn3.setText("13:00");
            btn4.setText("13:30");
            btn5.setText("14:00");
            btn6.setText("14:30");
            btn7.setText("15:00");
            btn8.setText("15:30");
            btn9.setText("16:00");
        } else if(timeofDay2.equals("Evening")){
            btn1.setText("16:30");
            btn2.setText("17:00");
            btn3.setText("17:30");
            btn4.setText("18:00");
            btn5.setText("18:30");
            btn6.setText("19:00");
            btn7.setText("19:30");
            btn8.setText("20:00");
            btn9.setText("20:30");
        }
        }
        disableButton();
        
    }
    
    /**
     * Booking page button handler
     * Sets the time in the variable booking
     * @param event 
     */
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if(event.getSource() == btn1) {
            bookingTime = btn1.getText();
        } else if(event.getSource() == btn2) {
            bookingTime = btn2.getText();
        } else if(event.getSource() == btn3) {
            bookingTime = btn3.getText();
        } else if(event.getSource() == btn4) {
            bookingTime = btn4.getText();
        } else if(event.getSource() == btn5) {
            bookingTime = btn5.getText();
        } else if(event.getSource() == btn6) {
            bookingTime = btn6.getText();
        } else if(event.getSource() == btn7) {
            bookingTime = btn7.getText();
        } else if(event.getSource() == btn8) {
            bookingTime = btn8.getText();
        } else if(event.getSource() == btn9) {
            bookingTime = btn9.getText();
        }
    }
    
    // manage Booking Page
    /**
     * This is to cancel a previously made booking.
     * @param event 
     */
    @FXML
    private void cancelBooking(ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        int selectedIndex = bookingTableView.getSelectionModel().getSelectedIndex();
        String activity = activityColumn.getCellData(selectedIndex);
        String periodofday = periodofdayColumn.getCellData(selectedIndex);
        String duration = durationColumn.getCellData(selectedIndex);
        String time = timeColumn.getCellData(selectedIndex);
        String date = dateColumn.getCellData(selectedIndex);
        
        if(selectedIndex >= 0){
            String query = "DELETE FROM booking WHERE activity = ? AND periodofday = ? AND duration = ? AND time = ? AND date = ? AND cust_id = ?";
            
            PreparedStatement pst = connectDB.prepareStatement(query);
            pst.setString(1, activity);
            pst.setString(2, periodofday);
            pst.setString(3, duration);
            pst.setString(4, time);
            pst.setString(5, date);
            pst.setInt(6, user.getCust_ID());
            pst.execute();
            bookingTableView.getItems().remove(selectedIndex);
            
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("No selection was made.");
            alert.setContentText("You have not selected an item to delete. Please try again.");
            alert.showAndWait();
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
    private void cancelBookingStaff(ActionEvent event) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        int selectedIndex = staffTableView.getSelectionModel().getSelectedIndex();
        String activity = staffActivityColumn.getCellData(selectedIndex);
        String periodofday = staffPeriodofdayColumn.getCellData(selectedIndex);
        String duration = staffDurationColumn.getCellData(selectedIndex);
        String time = staffTimeColumn.getCellData(selectedIndex);
        String date = staffDateColumn.getCellData(selectedIndex);
        String cust_id = staffCustColumn.getCellData(selectedIndex);
        
        if(selectedIndex >= 0){
            String query = "DELETE FROM booking WHERE activity = ? AND periodofday = ? AND duration = ? AND time = ? AND date = ? AND cust_id = ?";
            
            PreparedStatement pst = connectDB.prepareStatement(query);
            pst.setString(1, activity);
            pst.setString(2, periodofday);
            pst.setString(3, duration);
            pst.setString(4, time);
            pst.setString(5, date);
            pst.setInt(6, Integer.parseInt(cust_id));
            pst.execute();
            staffTableView.getItems().remove(selectedIndex);
            
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("No selection was made.");
            alert.setContentText("You have not selected an item to delete. Please try again.");
            alert.showAndWait();
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
    
    public void alertBoxBookingComplete(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Booking Successful");
        alert.setContentText("Payment is required in-store");
        alert.showAndWait();
    }
    
    public void alertBoxBookingSearch(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Search parameters are empty");
        alert.setContentText("Please try again");
        alert.showAndWait();
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
        alert.setContentText("1. Make sure all information is valid \n2. The password must be 8 characters or more \n3. You must be 18 or older");
        alert.showAndWait();
    }
    
    /**
     * This method checks if all text field are empty on registration page.
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
     * This method validates the age and only allows users who are 18+
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
     * This method verifies that the passwords match and that the password is over 8 characters long
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
     * This method validates the string and checks if it follows the format of a valid email address.
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
     * This method sets the data for the user class
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
    }
    
    /**
     * This method loads the user data on the profile page.
     */
    public void loadUserData(){
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
    
    /**
     * This method updates the profile Page user data
     */
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
    
    /**
     * This method retrieves bookings for a user and stores it in the observeableList
     * @return
     * @throws SQLException 
     */
    public ObservableList<Booking> getBookings() throws SQLException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String query = "SELECT * FROM booking WHERE cust_id = '" + user.getCust_ID() + "'";
        
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        
        ObservableList<Booking> booking = FXCollections.observableArrayList();
        
        while (queryResult.next()){
                booking.add(new Booking(queryResult.getString("activity"), queryResult.getString("periodofday"), 
                        queryResult.getString("duration"), queryResult.getString("time"), queryResult.getString("date")));
            } 
        return booking;
        }
    
    /**
     * This method retrieves all bookings for staff View table and stores it in the observeableList
     * @return
     * @throws SQLException 
     */
    public ObservableList<Booking> getAllBookings() throws SQLException{
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String query = "SELECT * FROM booking";
        
        Statement statement = connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(query);
        
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        
        while (queryResult.next()){
                bookings.add(new Booking(queryResult.getString("activity"), queryResult.getString("periodofday"), 
                        queryResult.getString("duration"), queryResult.getString("time"), queryResult.getString("date"), queryResult.getString("cust_id")));
            } 
        return bookings;
        }
    
    /**
     * This method will check if a booking already exists
     * This is a helper method
     * @param activity
     * @param date
     * @param periodofday
     * @param duration
     * @param time
     * @return
     * @throws SQLException 
     */
    public boolean bookingExists(String activity, String date, String periodofday, String duration, String time) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        
        PreparedStatement preparedStatement = connectDB.prepareCall("SELECT * FROM booking WHERE activity = ? AND date = ? AND periodofday = ? AND duration = ? AND time = ?");
        preparedStatement.setString(1, activity);
        preparedStatement.setString(2, date);
        preparedStatement.setString(3, periodofday);
        preparedStatement.setString(4, duration);
        preparedStatement.setString(5, time);
        ResultSet rs = preparedStatement.executeQuery();

        Boolean exist = null;
        
        if(rs.next() && rs.getString("activity").equals(activity) && rs.getString("date").equals(date) && 
                rs.getString("periodofday").equals(periodofday) && rs.getString("duration").equals(duration) && rs.getString("date").equals(date)){
            exist = true;
        } else {
            exist = false;
        }
        return exist;
    }
    
    /**
     * This method disables buttons on the booking page
     * The button is disabled to imitate the time slot being booked.
     * @throws SQLException 
     */
    public void disableButton() throws SQLException{
        String activity = "";
        String time = "";
        String duration = "";
        String date = "";
        String periodofday = "";
        
        ObservableList<Booking> bookings;
        bookings = getAllBookings();
        
        for(int i = 0; i < bookings.size(); i++){
            activity = bookings.get(i).getActivity();
            date = bookings.get(i).getDate();
            periodofday = bookings.get(i).getPeriodofday();
            duration = bookings.get(i).getDuration();
            time = bookings.get(i).getTime();
            
            if(bookingExists(activity, date, periodofday, duration, time)){
                
                if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn1.getText().equals(time)){
                    btn1.setDisable(true);
                    btn1.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn2.getText().equals(time)){
                    btn2.setDisable(true);
                    btn2.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn3.getText().equals(time)){
                    btn3.setDisable(true);
                    btn3.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn4.getText().equals(time)){
                    btn4.setDisable(true);
                    btn4.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn5.getText().equals(time)){
                    btn5.setDisable(true);
                    btn5.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn6.getText().equals(time)){
                    btn6.setDisable(true);
                    btn6.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn7.getText().equals(time)){
                    btn7.setDisable(true);
                    btn7.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn8.getText().equals(time)){
                    btn8.setDisable(true);
                    btn8.setText("Booked");
                } else if(true && comboActivity.getSelectionModel().getSelectedItem().contains(bookings.get(i).getActivity()) && bookingDate.getValue().toString().contains(bookings.get(i).getDate()) 
                        && comboTime.getSelectionModel().getSelectedItem().contains(bookings.get(i).getPeriodofday()) 
                        && comboDuration.getSelectionModel().getSelectedItem().contains(bookings.get(i).getDuration()) && btn9.getText().equals(time)){
                    btn9.setDisable(true);
                    btn9.setText("Booked");
                }
                //end of bookingexist
            }
            //end of for loop
        }
        //end of method
    }
    
    /**
     * This method enables all buttons on booking page
     */
    public void reset(){
        btn1.setDisable(false);
        btn2.setDisable(false);
        btn3.setDisable(false);
        btn4.setDisable(false);
        btn5.setDisable(false);
        btn6.setDisable(false);
        btn7.setDisable(false);
        btn8.setDisable(false);
        btn9.setDisable(false);
    }
    
        
        
        
}
    

