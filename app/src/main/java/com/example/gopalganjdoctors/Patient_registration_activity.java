package com.example.gopalganjdoctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gopalganjdoctors.Model.Registration_response;
import com.example.gopalganjdoctors.Retrofit.ApiService;
import com.example.gopalganjdoctors.Retrofit.AppConfig;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Patient_registration_activity extends AppCompatActivity {

    ImageView backButton;
    EditText nameEditText, phoneEditText, passwordEditText;
    AppCompatButton createButton;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    FancyToast.makeText(getApplicationContext(), "empty field", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                } else {
                    if (password.length() < 6) {
                        FancyToast.makeText(getApplicationContext(), "password length must be 6", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                    } else {
                        apiService.patient_registration(name, phone, password).enqueue(new Callback<Registration_response>() {
                            @Override
                            public void onResponse(Call<Registration_response> call, Response<Registration_response> response) {
                                if (response.body().status == 1) {
                                    FancyToast.makeText(getApplicationContext(), response.body().message, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                                } else if (response.body().status == 0) {
                                    FancyToast.makeText(getApplicationContext(), response.body().message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Registration_response> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });
    }

    private void init_view() {
        apiService = AppConfig.getRetrofit().create(ApiService.class);
        backButton = findViewById(R.id.backButtonID);
        nameEditText = findViewById(R.id.nameEditTextID);
        phoneEditText = findViewById(R.id.phoneEditTextID);
        passwordEditText = findViewById(R.id.passwordEditTextID);

        createButton = findViewById(R.id.createButtonID);
    }
}