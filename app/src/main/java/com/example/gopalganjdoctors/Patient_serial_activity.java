package com.example.gopalganjdoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gopalganjdoctors.Adapter.Serial_adapter;
import com.example.gopalganjdoctors.Model.Common_response;
import com.example.gopalganjdoctors.Model.Serials_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;
import com.example.gopalganjdoctors.Sessions.Session_management;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Patient_serial_activity extends AppCompatActivity implements Serial_adapter.OnItemClickListener, Serial_adapter.OnItemViewListener{

    ImageView backButton;
    String doctorID;
    AppCompatButton submitButton;
    ApiService apiService;
    Session_management session_management;
    String patientID;
    EditText ageEditText, diseaseEditText;
    RecyclerView serialsView;
    private List<Serials_response.Serial> serialList;
    private Serial_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_serial);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String age = ageEditText.getText().toString().trim();
                String disease = diseaseEditText.getText().toString().trim();

                if (TextUtils.isEmpty(age) || TextUtils.isEmpty(disease)) {
                    FancyToast.makeText(getApplicationContext(), "empty field", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    apiService.serial_entry(doctorID, patientID, age, disease).enqueue(new Callback<Common_response>() {
                        @Override
                        public void onResponse(Call<Common_response> call, Response<Common_response> response) {
                            if (response.body().message.equals("successfully added")) {
                                FancyToast.makeText(getApplicationContext(), response.body().message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                                get_serials_list();
                            }
                        }

                        @Override
                        public void onFailure(Call<Common_response> call, Throwable t) {

                        }
                    });
                }
            }
        });

        get_serials_list();
    }

    private void get_serials_list() {
        apiService.get_doctor_serial_list(doctorID).enqueue(new Callback<Serials_response>() {
            @Override
            public void onResponse(Call<Serials_response> call, Response<Serials_response> response) {
                serialList = new ArrayList<>();
                serialList = response.body().serials;
                adapter = new Serial_adapter(serialList);
                adapter.setOnClickListener(Patient_serial_activity.this::OnItemClick, Patient_serial_activity.this::OnItemView);
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
        patientID = session_management.getPatientID();
        backButton = findViewById(R.id.backButtonID);
        doctorID = getIntent().getStringExtra("doctor_ID");
        submitButton = findViewById(R.id.submitButtonID);

        ageEditText = findViewById(R.id.ageEditTextID);
        diseaseEditText = findViewById(R.id.diseaseEditTextID);

        serialsView = findViewById(R.id.serialsViewID);
        serialsView.setHasFixedSize(true);
        serialsView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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