package com.ict.tablayoutviewpager16;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;
    private String username;

    public String getToken() {
        return token;
    }

    public String getUsername() { return username; }
}