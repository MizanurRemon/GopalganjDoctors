package com.example.gopalganjdoctors.Retrofit;

import com.example.gopalganjdoctors.Model.Admin_response;
import com.example.gopalganjdoctors.Model.Common_response;
import com.example.gopalganjdoctors.Model.Doctor_response;
import com.example.gopalganjdoctors.Model.Doctors_profile_response;
import com.example.gopalganjdoctors.Model.Doctors_response;
import com.example.gopalganjdoctors.Model.Patient_response;
import com.example.gopalganjdoctors.Model.Prescription_response;
import com.example.gopalganjdoctors.Model.Registration_response;
import com.example.gopalganjdoctors.Model.Serials_response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/admin_login")
    Call<Admin_response> adminLogin(@Query("email") String email, @Query("password") String password);

    @GET("/patient_login")
    Call<Patient_response> patientLogin(@Query("patient_phone") String patient_phone, @Query("patient_password") String patient_password);

    @GET("/doctor_login")
    Call<Doctor_response> doctorLogin(@Query("doctor_phone") String phone, @Query("password") String password);

    @FormUrlEncoded
    @POST("/add_doctors")
    Call<Common_response> add_doctors(@Field("doctor_name") String doctor_name,
                                      @Field("doctor_designation") String doctor_designation,
                                      @Field("doctor_phone") String doctor_phone,
                                      @Field("password") String password);

    @POST("/get_doctors")
    Call<Doctors_response> get_all_doctors();

    @FormUrlEncoded
    @POST("/patient_profile")
    Call<Patient_response> patient_profile(@Field("patientID") String patientID);

    @FormUrlEncoded
    @POST("/serial_entry")
    Call<Common_response> serial_entry(@Field("doctorID") String doctorID,
                                       @Field("patientID") String patientID,
                                       @Field("age") String age,
                                       @Field("disease") String disease);

    //get_doctor_serial_list
    @FormUrlEncoded
    @POST("/get_doctor_serial_list")
    Call<Serials_response> get_doctor_serial_list(@Field("doctorID") String doctorID);

    @FormUrlEncoded
    @POST("/get_doctors_profile")
    Call<Doctors_profile_response> get_doctors_profile(@Field("doctorID") String doctorID);

    @FormUrlEncoded
    @POST("/patient_registration")
    Call<Registration_response> patient_registration(@Field("patient_name") String patient_name,
                                                     @Field("patient_phone") String patient_phone,
                                                     @Field("patient_password") String patient_password);

    //suggest_prescription
    @FormUrlEncoded
    @POST("/suggest_prescription")
    Call<Common_response> suggest_prescription(@Field("serialID") String serialID,
                                               @Field("doctorID") String doctorID,
                                               @Field("patientID") String patientID,
                                               @Field("medicine") String medicine,
                                               @Field("days") String days,
                                               @Field("rules") String rules,
                                               @Field("date") String date);

    @FormUrlEncoded
    @POST("/get_prescription")
    Call<Prescription_response> get_prescription(@Field("serialID") String serialID);

}
