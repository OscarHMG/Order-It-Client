package com.oscarhmg.orderit.model;

/**
 * Created by OscarHMG on 07/11/2017.
 */

public class User {
    private String name;
    private String password;
    private String phone;
    private String isStaffMember;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.isStaffMember = "false";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsStaffMember() {
        return isStaffMember;
    }

    public void setIsStaffMember(String isStaffMember) {
        this.isStaffMember = isStaffMember;
    }
}
