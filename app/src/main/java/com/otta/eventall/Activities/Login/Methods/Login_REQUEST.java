package com.otta.eventall.Activities.Login.Methods;

import com.google.gson.annotations.SerializedName;

public class Login_REQUEST {

    @SerializedName("email")
    String Email;

    @SerializedName("password")
    String Password;

    public Login_REQUEST() {
    }

    public Login_REQUEST(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
