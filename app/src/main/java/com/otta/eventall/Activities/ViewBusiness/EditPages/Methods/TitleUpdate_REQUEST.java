package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class TitleUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessID;

    @SerializedName("title")
    String Title;

    public TitleUpdate_REQUEST() {
    }

    public String getBusinessID() {
        return BusinessID;
    }

    public void setBusinessID(String businessID) {
        BusinessID = businessID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
