package com.example.gopalganjdoctors.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doctors_response {
    @SerializedName("doctors")
    @Expose
    public List<Doctor> doctors = null;

    public class Doctor {

        @SerializedName("doctorID")
        @Expose
        public Integer doctorID;
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
