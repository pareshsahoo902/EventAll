package com.otta.eventall.Activities.SignUp.Methods;

import com.google.gson.annotations.SerializedName;

public class SignUp_REQUEST {

    @SerializedName("email")
    String Email;

    @SerializedName("password")
    String Password;

    public SignUp_REQUEST() {
    }

    public SignUp_REQUEST(String email, String password) {
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
