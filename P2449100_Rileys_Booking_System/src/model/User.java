/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Yefri
 */
public class User{
    private int cust_id;
    private String title;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String email;
    private String phonenumber;
    private String address;
    private String city;
    private String postcode;
    private int is_staff;
    
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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public void setBirthDate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getBirthDate() {
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
        return postcode;
    }

    public void setPrivilege(int is_staff) {
        this.is_staff = is_staff;
    }

    public int getPrivilege() {
        return is_staff;
    }

//    @Override
    public String toString(User user) {
        return "User Details:[CustomerID=" + cust_id + ", Title=" + title + ", FirstName="
            + firstname + ", LastName=" + lastname + ", BirthDate="
            + birthdate + ", email=" + email + ", PhoneNumber=" + phonenumber 
            + ", Address=" + address + ", City=" + city 
            + ", PostCode=" + postcode + ", Staff=" + is_staff + "]";
    }

    public User(int cust_id, String firstname, String lastname, LocalDate birthdate, String email, String phonenumber, String address, String city, String postcode, int is_staff) {
        this.cust_id = cust_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.is_staff = is_staff;
    }
        
}

