package com.otta.eventall.Model;

import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @SerializedName("id")
    String ID;
    @SerializedName("name")
    String Name;
    @SerializedName("email")
    String Email;
    @SerializedName("email_verified_at")
    String EmailVerifiedAt;
    @SerializedName("mobile_no")
    String MobileNo;
    @SerializedName("gender")
    String Gender;
    @SerializedName("photo")
    String PhotoURL;
    @SerializedName("is_business_available")
    String IsBusinessAvailable;
    @SerializedName("status")
    String Status;
    @SerializedName("created_at")
    String CreatedAt;
    @SerializedName("updated_at")
    String UpdatedAt;

    public UserInfoModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmailVerifiedAt() {
        return EmailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        EmailVerifiedAt = emailVerifiedAt;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getIsBusinessAvailable() {
        return IsBusinessAvailable;
    }

    public void setIsBusinessAvailable(String isBusinessAvailable) {
        IsBusinessAvailable = isBusinessAvailable;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }
}
