package com.example.gopalganjdoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopalganjdoctors.Model.Admin_response;
import com.example.gopalganjdoctors.Model.Doctor_response;
import com.example.gopalganjdoctors.Model.Patient_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;
import com.example.gopalganjdoctors.Sessions.Session_management;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button homebtn, doctorLoginButton;

    ImageView adminButton;
    ApiService apiService;
    Session_management session_management;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        session_management = new Session_management(getApplicationContext());

        if (session_management.getAdminID().equals("-1") && session_management.getPatientID().equals("-1")&& session_management.getDoctorID().equals("-1")) {

        } else {

            if (!session_management.getAdminID().equals("-1")) {
                startActivity(new Intent(getApplicationContext(), Admin_activity.class));
                finish();
            } else if (!session_management.getPatientID().equals("-1")) {
                startActivity(new Intent(getApplicationContext(), Patient_activity.class));
                finish();
            }else if (!session_management.getDoctorID().equals("-1")) {
                startActivity(new Intent(getApplicationContext(), Doctors_activity.class));
                finish();
            }
        }

        setContentView(R.layout.activity_main);

        apiService = AppConfig.getRetrofit().create(ApiService.class);
        homebtn = findViewById(R.id.homebtn);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, Details.class);
                //startActivity(intent);
                patient_login();

            }
        });

        adminButton = findViewById(R.id.adminButtonID);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.admin_login_alert);
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

                EditText emailEditText = dialog.findViewById(R.id.emailEditTextID);
                EditText passwordEditText = dialog.findViewById(R.id.passwordEditTextID);
                AppCompatButton adminLoginButton = dialog.findViewById(R.id.adminLoginButtonID);

                adminLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = emailEditText.getText().toString().trim();
                        String password = passwordEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                            FancyToast.makeText(getApplicationContext(), "empty field", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        } else {
                            apiService.adminLogin(email, password).enqueue(new Callback<Admin_response>() {
                                @Override
                                public void onResponse(Call<Admin_response> call, Response<Admin_response> response) {
                                    //Toast.makeText(MainActivity.this, response.body().adminID.toString(), Toast.LENGTH_SHORT).show();
                                    if (response.body().adminID.equals("-1")) {
                                        FancyToast.makeText(getApplicationContext(), "authentication failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                                    } else {
                                        FancyToast.makeText(getApplicationContext(), "Login Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                        session_management.saveAdmin(response.body().adminID.toString());

                                        startActivity(new Intent(getApplicationContext(), Admin_activity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Admin_response> call, Throwable t) {

                                    Log.d("errorxx", t.getMessage());

                                }
                            });
                        }
                    }
                });

            }
        });

        doctorLoginButton = findViewById(R.id.doctorLoginButtonID);
        doctorLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor_login();
            }
        });
    }

    private void doctor_login() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.doctor_login_alert);
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

        EditText phoneEditText = dialog.findViewById(R.id.phoneEditTextID);
        EditText passwordEditText = dialog.findViewById(R.id.passwordEditTextID);
        AppCompatButton loginButton = dialog.findViewById(R.id.doctorLoginButtonID);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    FancyToast.makeText(getApplicationContext(), "empty field", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    apiService.doctorLogin(phone, password).enqueue(new Callback<Doctor_response>() {
                        @Override
                        public void onResponse(Call<Doctor_response> call, Response<Doctor_response> response) {
                            if (response.body().doctorID == -1) {
                                FancyToast.makeText(getApplicationContext(), "authentication failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                            } else {
                                FancyToast.makeText(getApplicationContext(), "Login Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                session_management.saveDoctor(response.body().doctorID.toString());

                                //Toast.makeText(MainActivity.this, response.body().doctorID.toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Doctors_activity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Doctor_response> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void patient_login() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.patient_login_alert);
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

        EditText phoneEditText = dialog.findViewById(R.id.phoneEditTextID);
        EditText passwordEditText = dialog.findViewById(R.id.passwordEditTextID);
        AppCompatButton loginButton = dialog.findViewById(R.id.loginButtonID);

        TextView registerButton = dialog.findViewById(R.id.registerButtonID);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Patient_registration_activity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    FancyToast.makeText(getApplicationContext(), "empty field", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    apiService.patientLogin(phone, password).enqueue(new Callback<Patient_response>() {
                        @Override
                        public void onResponse(Call<Patient_response> call, Response<Patient_response> response) {
                            if (response.body().patientID == -1) {
                                FancyToast.makeText(getApplicationContext(), "authentication failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                            } else {
                                FancyToast.makeText(getApplicationContext(), "Login Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                session_management.savePatient(response.body().patientID.toString());

                                //Toast.makeText(MainActivity.this, response.body().patientID.toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Patient_activity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Patient_response> call, Throwable t) {
                            Log.d("errorxx", t.getMessage());
                        }
                    });
                }
            }
        });
    }
}