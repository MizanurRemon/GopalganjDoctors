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

import com.example.gopalganjdoctors.Adapter.Doctors_adapter;
import com.example.gopalganjdoctors.Model.Common_response;
import com.example.gopalganjdoctors.Model.Doctors_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;
import com.example.gopalganjdoctors.Sessions.Session_management;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_activity extends AppCompatActivity implements Doctors_adapter.OnItemClickListener {

    ExtendedFloatingActionButton addDoctorsButton;
    ImageView logOutButton;
    Session_management session_management;
    String adminID;
    RecyclerView doctorsListView;
    ApiService apiService;
    private List<Doctors_response.Doctor> doctorsList;
    private Doctors_adapter doctors_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init_view();

        addDoctorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Admin_activity.this);
                dialog.setContentView(R.layout.add_doctors_alert);
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
                EditText designationEditText = dialog.findViewById(R.id.designationEditTextID);
                EditText phoneEditText = dialog.findViewById(R.id.phoneEditTextID);
                EditText passwordEditText = dialog.findViewById(R.id.passwordEditTextID);
                AppCompatButton addButton = dialog.findViewById(R.id.addButtonID);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = nameEditText.getText().toString().trim();
                        String designation = designationEditText.getText().toString().trim();
                        String phone = phoneEditText.getText().toString().trim();
                        String password = passwordEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(designation) || TextUtils.isEmpty(phone)|| TextUtils.isEmpty(password)) {
                            FancyToast.makeText(getApplicationContext(), "empty field", FancyToast.LENGTH_LONG, FancyToast.ERROR, false);

                        } else {
                            apiService.add_doctors(name, designation, phone, password).enqueue(new Callback<Common_response>() {
                                @Override
                                public void onResponse(Call<Common_response> call, Response<Common_response> response) {
                                    if (response.body().message.equals("successfully added")) {
                                        FancyToast.makeText(getApplicationContext(), response.body().message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false);
                                        dialog.dismiss();
                                        get_doctors();
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
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                session_management.removeAdminID();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        get_doctors();
    }

    private void get_doctors() {
        apiService.get_all_doctors().enqueue(new Callback<Doctors_response>() {
            @Override
            public void onResponse(Call<Doctors_response> call, Response<Doctors_response> response) {
                doctorsList = new ArrayList<>();
                doctorsList = response.body().doctors;
                doctors_adapter = new Doctors_adapter(doctorsList);
                doctors_adapter.setOnClickListener(Admin_activity.this::OnItemClick);
                doctorsListView.setAdapter(doctors_adapter);

                //Log.d("dataxx", response.body().toString());
            }

            @Override
            public void onFailure(Call<Doctors_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(ApiService.class);
        session_management = new Session_management(getApplicationContext());
        adminID = session_management.getAdminID();
        //Toast.makeText(this, adminID, Toast.LENGTH_SHORT).show();
        addDoctorsButton = findViewById(R.id.addDoctorsButtonID);
        logOutButton = findViewById(R.id.logOutButtonID);

        doctorsListView = findViewById(R.id.doctorsListViewID);
        doctorsListView.setHasFixedSize(true);
        doctorsListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    @Override
    public void OnItemClick(int position) {
        Doctors_response.Doctor response = doctorsList.get(position);

        //Toast.makeText(this, String.valueOf(response.doctorID), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Admin_doctors_serial_activity.class);
        intent.putExtra("doctor_ID", response.doctorID.toString());
        startActivity(intent);
    }
}