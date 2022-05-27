package com.example.gopalganjdoctors.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctors_profile_response {

    @SerializedName("profile")
    @Expose
    public Profile profile;

    public class Profile {

        @SerializedName("doctor_name")
        @Expose
        public String doctorName;
        @SerializedName("doctor_designation")
        @Expose
        public String doctorDesignation;
        @SerializedName("doctor_phone")
        @Expose
        public String doctorPhone;

    }
}
