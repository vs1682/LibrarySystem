package com.inatreo.testing.librarysystem.models;

/**
 * Created by vishal on 2/2/2016.
 */
public class Admin {
    String firstName;
    String lastName;
    String mobile;
    String username;
    String password;
    String masterOrAdmin;

    public String getMasterOrAdmin() {
        return masterOrAdmin;
    }

    public void setMasterOrAdmin(String masterOrAdmin) {
        this.masterOrAdmin = masterOrAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
