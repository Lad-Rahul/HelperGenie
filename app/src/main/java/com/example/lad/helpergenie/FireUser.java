package com.example.lad.helpergenie;

import android.os.FileUriExposedException;

public class FireUser {
    private String address;
    private String address2;
    private String email;
    private String mobile;
    private String name;
    private String pincode;

    public FireUser(){
    }

    public FireUser(String address1,String address2,String email, String mobile,String name, String pincode){
        this.address = address1;
        this.address2 = address2;
        this.email = email;
        this.name = name;
        this.mobile = mobile ;
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address1) {
        this.address = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
