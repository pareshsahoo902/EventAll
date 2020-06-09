package com.otta.eventall.Activities.SignUp.Methods;

import com.google.gson.annotations.SerializedName;

public class UserProfilePicture_REQUEST{

    @SerializedName("photo")
    String photo;


    public UserProfilePicture_REQUEST() {
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
