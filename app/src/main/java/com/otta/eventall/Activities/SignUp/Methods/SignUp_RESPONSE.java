package com.otta.eventall.Activities.SignUp.Methods;

import com.google.gson.annotations.SerializedName;

public class SignUp_RESPONSE {

    @SerializedName("status")
    Boolean Status;

    @SerializedName("access_token")
    String Token;

    @SerializedName("expires_at")
    String ExpiresAt;

    @SerializedName("token_type")
    String TokenType;


    public SignUp_RESPONSE() {
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getExpiresAt() {
        return ExpiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        ExpiresAt = expiresAt;
    }

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }
}
