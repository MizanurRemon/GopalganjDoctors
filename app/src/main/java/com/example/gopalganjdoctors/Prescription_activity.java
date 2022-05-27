package com.example.gopalganjdoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopalganjdoctors.Adapter.Medicine_adapter;
import com.example.gopalganjdoctors.Model.Prescription_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prescription_activity extends AppCompatActivity {

    String serialID;
    ApiService apiService;
    TextView patientNameText, patientAgeText, patientPhoneText;
    TextView doctorNameText, designationText,doctorPhoneText;
    TextView diseaseText;
    RecyclerView medicineListView;

    private List<Prescription_response.Medicine> medicineList;
    private Medicine_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        init_view();

        //Toast.makeText(this, serialID, Toast.LENGTH_SHORT).show();
        get_prescription();
    }

    private void get_prescription() {
        apiService.get_prescription(serialID).enqueue(new Callback<Prescription_response>() {
            @Override
            public void onResponse(Call<Prescription_response> call, Response<Prescription_response> response) {
                patientNameText.setText(response.body().patient.patientName);
                patientPhoneText.setText("Phone: "+response.body().patient.patientPhone);
                patientAgeText.setText("Age: "+response.body().patient.age);

                doctorNameText.setText(response.body().doctor.doctorName);
                designationText.setText(response.body().doctor.doctorDesignation);
                doctorPhoneText.setText(response.body().doctor.doctorPhone);
                diseaseText.setText(response.body().patient.disease);

                medicineList = new ArrayList<>();
                medicineList = response.body().medicines;
                adapter = new Medicine_adapter(medicineList);
                medicineListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Prescription_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(ApiService.class);
        serialID = getIntent().getStringExtra("serial_id");

        patientNameText = findViewById(R.id.patientNameTextID);
        patientPhoneText = findViewById(R.id.patientPhoneTextID);
        patientAgeText = findViewById(R.id.patientAgeTextID);
        doctorNameText = findViewById(R.id.doctorNameTextID);
        designationText = findViewById(R.id.designationTextID);
        doctorPhoneText = findViewById(R.id.doctorPhoneTextID);
        diseaseText = findViewById(R.id.diseaseTextID);

        medicineListView = findViewById(R.id.medicineListViewID);
        medicineListView.setHasFixedSize(true);
        medicineListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}