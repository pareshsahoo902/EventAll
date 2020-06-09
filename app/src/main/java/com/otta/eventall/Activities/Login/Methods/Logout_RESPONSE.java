package com.otta.eventall.Activities.Login.Methods;

import com.google.gson.annotations.SerializedName;

public class Logout_RESPONSE {

    @SerializedName("message")
    String Message;

    public Logout_RESPONSE() {
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
