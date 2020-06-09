package com.otta.eventall;

import com.google.gson.annotations.SerializedName;
import com.otta.eventall.Model.UserInfoModel;

public class UserInfo_RESONSE {

    @SerializedName("status")
    boolean Status;

    @SerializedName("data")
    UserInfoModel userInfo;

    public UserInfo_RESONSE() {
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public UserInfoModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoModel userInfo) {
        this.userInfo = userInfo;
    }
}
