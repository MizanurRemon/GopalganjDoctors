package com.example.gopalganjdoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopalganjdoctors.Adapter.Patient_doctors_list_adapter;
import com.example.gopalganjdoctors.Model.Doctors_response;
import com.example.gopalganjdoctors.Model.Patient_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;
import com.example.gopalganjdoctors.Sessions.Session_management;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Patient_activity extends AppCompatActivity implements Patient_doctors_list_adapter.OnItemClickListener, Patient_doctors_list_adapter.OnItemCallListener {

    ApiService apiService;
    Session_management session_management;
    String patientID;
    ImageView logOutButton;
    TextView nameText;
    RecyclerView doctorsListView;
    private List<Doctors_response.Doctor> doctorsList;
    private Patient_doctors_list_adapter doctors_list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        init_view();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                session_management.removePatientID();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        get_doctors();

        patient_profile();
    }

    private void get_doctors() {
        apiService.get_all_doctors().enqueue(new Callback<Doctors_response>() {
            @Override
            public void onResponse(Call<Doctors_response> call, Response<Doctors_response> response) {
                doctorsList = new ArrayList<>();
                doctorsList = response.body().doctors;
                doctors_list_adapter = new Patient_doctors_list_adapter(doctorsList);
                doctors_list_adapter.setOnClickListener(Patient_activity.this::OnItemClick, Patient_activity.this::OnItemCall);
                doctorsListView.setAdapter(doctors_list_adapter);
            }

            @Override
            public void onFailure(Call<Doctors_response> call, Throwable t) {

            }
        });
    }

    private void patient_profile() {
        apiService.patient_profile(patientID).enqueue(new Callback<Patient_response>() {
            @Override
            public void onResponse(Call<Patient_response> call, Response<Patient_response> response) {
                //Toast.makeText(Patient_activity.this, response.body().patientName, Toast.LENGTH_SHORT).show();

                nameText.setText(response.body().patientName);
            }

            @Override
            public void onFailure(Call<Patient_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(ApiService.class);
        session_management = new Session_management(getApplicationContext());
        patientID = session_management.getPatientID();
        //Toast.makeText(this, patientID, Toast.LENGTH_SHORT).show();

        logOutButton = findViewById(R.id.logOutButtonID);
        nameText = findViewById(R.id.nameTextID);

        doctorsListView = findViewById(R.id.doctorsListViewID);
        doctorsListView.setHasFixedSize(true);
        doctorsListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    public void OnItemClick(int position) {
        Doctors_response.Doctor response = doctorsList.get(position);

        Intent intent = new Intent(getApplicationContext(), Patient_serial_activity.class);
        intent.putExtra("doctor_ID", response.doctorID.toString());
        startActivity(intent);
    }

    @Override
    public void OnItemCall(int position) {
        Doctors_response.Doctor response = doctorsList.get(position);

        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + response.doctorPhone));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "something wrong", Toast.LENGTH_SHORT).show();
        }
    }
}