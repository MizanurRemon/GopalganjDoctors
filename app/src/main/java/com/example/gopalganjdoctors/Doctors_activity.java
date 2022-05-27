package com.example.gopalganjdoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopalganjdoctors.Adapter.Serial_adapter;
import com.example.gopalganjdoctors.Model.Common_response;
import com.example.gopalganjdoctors.Model.Doctors_profile_response;
import com.example.gopalganjdoctors.Model.Serials_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;
import com.example.gopalganjdoctors.Sessions.Session_management;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Doctors_activity extends AppCompatActivity implements Serial_adapter.OnItemClickListener, Serial_adapter.OnItemViewListener {

    ApiService apiService;
    String doctorID;
    Session_management session_management;
    ImageView logOutButton;
    TextView nameText;
    RecyclerView serialListView;

    private List<Serials_response.Serial> serialList;
    private Serial_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        init_view();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                session_management.removeDoctorID();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        get_doctor_profile();

        get_serials_list();
    }

    private void get_serials_list() {
        apiService.get_doctor_serial_list(doctorID).enqueue(new Callback<Serials_response>() {
            @Override
            public void onResponse(Call<Serials_response> call, Response<Serials_response> response) {
                serialList = new ArrayList<>();
                serialList = response.body().serials;
                adapter = new Serial_adapter(serialList);
                adapter.setOnClickListener(Doctors_activity.this::OnItemClick, Doctors_activity.this::OnItemView);
                serialListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Serials_response> call, Throwable t) {

            }
        });
    }

    private void get_doctor_profile() {
        apiService.get_doctors_profile(doctorID).enqueue(new Callback<Doctors_profile_response>() {
            @Override
            public void onResponse(Call<Doctors_profile_response> call, Response<Doctors_profile_response> response) {
                nameText.setText(response.body().profile.doctorName);
                //designationText.setText(response.body().profile.doctorDesignation);
                //phoneText.setText("Phone: "+response.body().profile.doctorPhone);
            }

            @Override
            public void onFailure(Call<Doctors_profile_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(ApiService.class);
        session_management = new Session_management(getApplicationContext());
        doctorID = session_management.getDoctorID();
        //Toast.makeText(this, doctorID, Toast.LENGTH_SHORT).show();

        logOutButton = findViewById(R.id.logOutButtonID);
        nameText = findViewById(R.id.nameTextID);

        serialListView = findViewById(R.id.serialListViewID);
        serialListView.setHasFixedSize(true);
        serialListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void OnItemClick(int position) {
        Serials_response.Serial response = serialList.get(position);

        String serialID = String.valueOf(response.serialID);
        String patientID = String.valueOf(response.patientID);

        Dialog dialog = new Dialog(Doctors_activity.this);
        dialog.setContentView(R.layout.add_medicine_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = dialog.findViewById(R.id.closeButtonID);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        EditText nameEditText = dialog.findViewById(R.id.nameEditTextID);
        EditText rulesEditText = dialog.findViewById(R.id.rulesEditTextID);
        EditText daysEditText = dialog.findViewById(R.id.daysEditTextID);
        TextView dateText = dialog.findViewById(R.id.dateTextID);
        AppCompatButton addMedicineButton = dialog.findViewById(R.id.addMedicineButtonID);

        dateText.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medicineName = nameEditText.getText().toString().trim();
                String days = daysEditText.getText().toString().trim();
                String rules = rulesEditText.getText().toString().trim();

                if (TextUtils.isEmpty(medicineName) || TextUtils.isEmpty(rules) || TextUtils.isEmpty(days)) {
                    FancyToast.makeText(getApplicationContext(), "empty field", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {

                    apiService.suggest_prescription(serialID, doctorID, patientID, medicineName, days, rules, dateText.getText().toString()).enqueue(new Callback<Common_response>() {
                        @Override
                        public void onResponse(Call<Common_response> call, Response<Common_response> response) {
                            if (response.body().message.equals("successfully added")) {
                                FancyToast.makeText(getApplicationContext(), response.body().message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Common_response> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void OnItemView(int position) {
        Serials_response.Serial response = serialList.get(position);

        String serialID = String.valueOf(response.serialID);

        Intent intent = new Intent(getApplicationContext(), Prescription_activity.class);
        intent.putExtra("serial_id", serialID);
        startActivity(intent);
    }
}