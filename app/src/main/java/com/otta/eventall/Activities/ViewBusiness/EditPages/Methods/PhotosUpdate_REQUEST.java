package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotosUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessID;

    @SerializedName("photos")
    String PhotosListInString;

    public PhotosUpdate_REQUEST() {
    }

    public String getBusinessID() {
        return BusinessID;
    }

    public void setBusinessID(String businessID) {
        BusinessID = businessID;
    }

    public String getPhotosListInString() {
        return PhotosListInString;
    }

    public void setPhotosListInString(String photosListInString) {
        PhotosListInString = photosListInString;
    }
}
