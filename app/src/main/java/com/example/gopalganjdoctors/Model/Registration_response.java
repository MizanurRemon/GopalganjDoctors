package com.example.gopalganjdoctors.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registration_response {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
}
