package com.example.gopalganjdoctors.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Prescription_response {
    @SerializedName("doctor")
    @Expose
    public Doctor doctor;
    @SerializedName("patient")
    @Expose
    public Patient patient;
    @SerializedName("medicines")
    @Expose
    public List<Medicine> medicines = null;

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

    public class Patient {

        @SerializedName("patientID")
        @Expose
        public Integer patientID;
        @SerializedName("patient_name")
        @Expose
        public String patientName;
        @SerializedName("patient_phone")
        @Expose
        public String patientPhone;
        @SerializedName("age")
        @Expose
        public String age;
        @SerializedName("disease")
        @Expose
        public String disease;

    }

    public class Medicine {

        @SerializedName("prescriptionID")
        @Expose
        public Integer prescriptionID;
        @SerializedName("medicine")
        @Expose
        public String medicine;
        @SerializedName("days")
        @Expose
        public String days;
        @SerializedName("rules")
        @Expose
        public String rules;

    }
}
