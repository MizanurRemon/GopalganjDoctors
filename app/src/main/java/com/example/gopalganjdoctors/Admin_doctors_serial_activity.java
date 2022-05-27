package com.example.gopalganjdoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gopalganjdoctors.Adapter.Serial_adapter;
import com.example.gopalganjdoctors.Model.Doctors_profile_response;
import com.example.gopalganjdoctors.Model.Serials_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;
import com.example.gopalganjdoctors.Sessions.Session_management;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_doctors_serial_activity extends AppCompatActivity implements Serial_adapter.OnItemClickListener, Serial_adapter.OnItemViewListener{

    ImageView backButton;
    RecyclerView serialsView;
    ApiService apiService;
    Session_management session_management;
    String doctorID;
    private List<Serials_response.Serial> serialList;
    private Serial_adapter adapter;

    TextView nameText, designationText, phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_doctors_serial);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        get_serials_list();

        get_doctor_profile();
    }

    private void get_doctor_profile() {
        apiService.get_doctors_profile(doctorID).enqueue(new Callback<Doctors_profile_response>() {
            @Override
            public void onResponse(Call<Doctors_profile_response> call, Response<Doctors_profile_response> response) {
                nameText.setText(response.body().profile.doctorName);
                designationText.setText(response.body().profile.doctorDesignation);
                phoneText.setText("Phone: "+response.body().profile.doctorPhone);
            }

            @Override
            public void onFailure(Call<Doctors_profile_response> call, Throwable t) {

            }
        });
    }

    private void get_serials_list() {
        apiService.get_doctor_serial_list(doctorID).enqueue(new Callback<Serials_response>() {
            @Override
            public void onResponse(Call<Serials_response> call, Response<Serials_response> response) {
                serialList = new ArrayList<>();
                serialList = response.body().serials;
                adapter = new Serial_adapter(serialList);
                adapter.setOnClickListener(Admin_doctors_serial_activity.this::OnItemClick, Admin_doctors_serial_activity.this::OnItemView);
                serialsView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Serials_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(ApiService.class);
        session_management = new Session_management(getApplicationContext());
        //patientID = session_management.getPatientID();
        backButton = findViewById(R.id.backButtonID);

        doctorID = getIntent().getStringExtra("doctor_ID");

        serialsView = findViewById(R.id.serialsViewID);
        serialsView.setHasFixedSize(true);
        serialsView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        nameText = findViewById(R.id.nameTextID);
        designationText = findViewById(R.id.designationTextID);
        phoneText = findViewById(R.id.phoneTextID);

        //Toast.makeText(this, doctorID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemView(int position) {
        Serials_response.Serial response = serialList.get(position);

        String serialID = String.valueOf(response.serialID);

        Intent intent = new Intent(getApplicationContext(), Prescription_activity.class);
        intent.putExtra("serial_id", serialID);
        startActivity(intent);
    }

    @Override
    public void OnItemClick(int position) {

    }
}