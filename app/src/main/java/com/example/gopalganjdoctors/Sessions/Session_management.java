package com.example.gopalganjdoctors.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

public class Session_management {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String SHARED_PREF_NAME = "session";
    String SESSION_TOKEN = "PROMOTER_SESSION_TOKEN";
    String SESSION_SECRET_ID = "PROMOTER_SECRET_ID";

    String SESSION_ADMIN_ID = "SESSION_ADMIN_ID";
    String SESSION_PATIENT_ID = "SESSION_PATIENT_ID";
    String SESSION_DOCTOR_ID = "SESSION_DOCTOR_ID";


    public Session_management(Context con) {
        sharedPreferences = con.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAdmin(String adminID) {
        editor.putString(SESSION_ADMIN_ID, adminID).commit();

    }

    public void saveDoctor(String doctorID) {
        editor.putString(SESSION_DOCTOR_ID, doctorID).commit();

    }

    public String getDoctorID() {
        return sharedPreferences.getString(SESSION_DOCTOR_ID, "-1");
    }

    public void removeDoctorID() {
        editor.putString(SESSION_DOCTOR_ID, "-1").commit();
    }

    public String getAdminID() {
        return sharedPreferences.getString(SESSION_ADMIN_ID, "-1");
    }

    public void removeAdminID() {
        editor.putString(SESSION_ADMIN_ID, "-1").commit();
    }

    public void savePatient(String patientID) {
        editor.putString(SESSION_PATIENT_ID, patientID).commit();
    }

    public String getPatientID() {
        return sharedPreferences.getString(SESSION_PATIENT_ID, "-1");
    }

    public void removePatientID() {
        editor.putString(SESSION_PATIENT_ID, "-1").commit();
    }
}
