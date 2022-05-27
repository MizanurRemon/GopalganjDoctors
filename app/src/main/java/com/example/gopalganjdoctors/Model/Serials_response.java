package com.example.gopalganjdoctors.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Serials_response {
    @SerializedName("serials")
    @Expose
    public List<Serial> serials = null;

    public class Serial {

        @SerializedName("serialID")
        @Expose
        public Integer serialID;
        @SerializedName("patientID")
        @Expose
        public Integer patientID;
        @SerializedName("patient_name")
        @Expose
        public String patientName;
        @SerializedName("patient_phone")
        @Expose
        public String patientPhone;
        @SerializedName("patient_age")
        @Expose
        public String patientAge;
        @SerializedName("disease")
        @Expose
        public String disease;

    }
}
