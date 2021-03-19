/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Yefri
 */
public class User {
    private int cust_id;
    private String title;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String email;
    private String phonenumber;
    private String address;
    private String city;
    private String postcode;
    private int is_staff;
    
    //this causes issues
//    User user = new User();
    
//    public User User() {
//        User user = new User();
//            cust_id = -1;
//            title = "";
//            firstname = "";
//            lastname = "";
//            birthdate = null;
//            email = "";
//            phonenumber = "";
//            address = "";
//            city = "";
//            postcode = "";
//            is_staff = -1;
//            return user;
//    }
    
    public User() {
            cust_id = -1;
            title = "";
            firstname = "";
            lastname = "";
            birthdate = null;
            email = "";
            phonenumber = "";
            address = "";
            city = "";
            postcode = "";
            is_staff = -1;
    }
    
//    public User(String title, String firstname, String lastname, Date birDate, String email, String phonenumber, String address, String city, String postcode){
//        this.title = title;
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.birthdate = birDate;
//        this.email = email;
//        this.phonenumber = phonenumber;
//        this.address = address;
//        this.city = city;
//        this.postcode = postcode;
//        
//    }
    
    //this is to return a user.
//    public User getUser(){
//        return userSession;
//    }
    
//    public User(User user){
//        this.user = user;
//    }
    
        public int getCust_ID() {
		return cust_id;
	}
	
	public void setCust_ID(int cust_id) {
		this.cust_id = cust_id;
	}
    
        public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
        public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
        
        public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getBirthDate() {
		return birthdate;
	}
	
	public void setBirthDate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
        public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
        
        public String getPhoneNumber() {
		return phonenumber;
	}
	
	public void setPhoneNumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
        
        public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
        
        public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
        
        public String getPostCode() {
		return firstname;
	}
	
	public void setPostCode(String postcode) {
		this.postcode = postcode;
	}
        
        public int getPrivilege() {
		return is_staff;
	}
	
	public void setPrivilege(int is_staff) {
		this.is_staff = is_staff;
	}
        
	
//	@Override
	public String toString(User user) {
		return "User Details:[Title=" + title + ", FirstName="
				+ firstname + ", LastName=" + lastname + ", BirthDate="
				+ birthdate + ", email=" + email + ", PhoneNumber=" + phonenumber 
				+ ", Address=" + address + ", City=" + city 
				+ ", PostCode=" + postcode + "]";
	}
        
        
}

