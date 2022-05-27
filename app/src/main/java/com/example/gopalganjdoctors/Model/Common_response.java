package com.example.gopalganjdoctors.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;

public class Common_response {
    @SerializedName("message")
    @Expose
    public String message;
}
