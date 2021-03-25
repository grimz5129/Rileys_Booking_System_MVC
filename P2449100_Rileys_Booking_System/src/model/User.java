/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Yefri
 */
public class User{
    private int cust_id;
    private String title;
    private String firstname = "";
    private String lastname;
    private Date birthdate;
    private String email;
    private String phonenumber;
    private String address;
    private String city;
    private String postcode;
    private int is_staff;
    
    public User() {
//            cust_id = -1;
            title = "";
            firstname = "";
            lastname = "";
            birthdate = null;
            email = "";
            phonenumber = "";
            address = "";
            city = "";
            postcode = "";
//            is_staff = -1;
    }
    
//    public User(String title, String firstname){
//        super();
////        setTitle(title);
////        setFirstname(firstname);
//        this.title = title;
//        this.firstname = firstname;
//    }
    
        public void setCust_ID(int cust_id) {
            this.cust_id = cust_id;
	}
        
        public int getCust_ID() {
            return cust_id;
	}
	
        public void setTitle(String title) {
            this.title = title;
	}
        
        public String getTitle() {
            return this.title;
	}
	
	public void setFirstname(String fname) {
            this.firstname = fname;
	}
        
        public String getFirstname() {
            return this.firstname;
	}
        
	public void setLastname(String lastname) {
            this.lastname = lastname;
	}
        
        public String getLastname() {
            return lastname;
	}
	
        public void setBirthDate(Date birthdate) {
            this.birthdate = birthdate;
	}
        
	public Date getBirthDate() {
            return birthdate;
	}
	
	public void setEmail(String email) {
            this.email = email;
	}
	
        public String getEmail() {
            return email;
	}
	
	public void setPhoneNumber(String phonenumber) {
            this.phonenumber = phonenumber;
	}
        
        public String getPhoneNumber() {
            return phonenumber;
	}
	
	public void setAddress(String address) {
            this.address = address;
	}
        
        public String getAddress() {
            return address;
	}
	
	public void setCity(String city) {
            this.city = city;
	}
        
        public String getCity() {
            return city;
	}
	
	public void setPostCode(String postcode) {
            this.postcode = postcode;
	}
        
        public String getPostCode() {
            return firstname;
	}
	
	public void setPrivilege(int is_staff) {
            this.is_staff = is_staff;
	}
        
        public int getPrivilege() {
            return is_staff;
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

