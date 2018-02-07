package com.example.lad.helpergenie;

/**
 * Created by LAD on 26-Jan-18.
 */

public class FireSP {

    private String name;
    private String mobile;
    private String email;
    private String proffesion;
    private String rating;
    private int ordercomplete;


    public FireSP(){

    }

    public FireSP(String email,String mobile,String name,String proffesion,String rating,int ordercomplete){
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        this.proffesion = proffesion;
        this.rating = rating;
        this.ordercomplete = ordercomplete;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getOrdercomplete() {
        return ordercomplete;
    }

    public void setOrdercomplete(int ordercomplete) {
        this.ordercomplete = ordercomplete;
    }
}
