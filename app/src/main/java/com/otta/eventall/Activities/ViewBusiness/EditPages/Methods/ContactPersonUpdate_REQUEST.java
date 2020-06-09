package com.otta.eventall.Activities.ViewBusiness.EditPages.Methods;

import com.google.gson.annotations.SerializedName;

public class ContactPersonUpdate_REQUEST {

    @SerializedName("business_id")
    String BusinessId;
    @SerializedName("contact_person")
    String ContactPerson;

    public ContactPersonUpdate_REQUEST() {
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }
}
