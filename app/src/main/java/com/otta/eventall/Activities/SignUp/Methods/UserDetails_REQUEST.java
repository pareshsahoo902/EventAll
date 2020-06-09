package com.otta.eventall.Activities.SignUp.Methods;

import com.google.gson.annotations.SerializedName;

public class UserDetails_REQUEST {

    @SerializedName("name")
    String Name;

    @SerializedName("mobile_no")
    String PhoneNumber;

    @SerializedName("gender")
    int Gender;

    public UserDetails_REQUEST() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }
}

