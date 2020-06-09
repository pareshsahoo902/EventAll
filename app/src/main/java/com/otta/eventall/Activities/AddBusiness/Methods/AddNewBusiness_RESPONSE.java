package com.otta.eventall.Activities.AddBusiness.Methods;

import com.google.gson.annotations.SerializedName;

public class AddNewBusiness_RESPONSE {

    @SerializedName("status")
    boolean Status;

    @SerializedName("message")
    String Message;

    public AddNewBusiness_RESPONSE() {
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
