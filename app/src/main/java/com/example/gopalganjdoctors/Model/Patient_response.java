package com.example.gopalganjdoctors.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient_response {
    @SerializedName("patientID")
    @Expose
    public Integer patientID;
    @SerializedName("patient_name")
    @Expose
    public String patientName;
    @SerializedName("patient_phone")
    @Expose
    public String patientPhone;
}
