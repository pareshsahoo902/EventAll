package com.otta.eventall.Activities.SignUp.Methods;

import com.google.gson.annotations.SerializedName;

public class UserDetails_RESPONSE{


    @SerializedName("status")
    boolean Status;

    @SerializedName("message")
    String Message;

    public UserDetails_RESPONSE() {
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
