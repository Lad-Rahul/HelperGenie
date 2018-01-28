package com.example.lad.helpergenie;

/**
 * Created by LAD on 26-Jan-18.
 */

public class FireSP {

    private String name;
    private String mobile;
    private String email;
    private String proffesion;


    public FireSP(){

    }

    public FireSP(String email,String mobile,String name,String proffesion){
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        this.proffesion = proffesion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProffesion() {
        return proffesion;
    }

    public void setProffesion(String proffesion) {
        this.proffesion = proffesion;
    }





}
