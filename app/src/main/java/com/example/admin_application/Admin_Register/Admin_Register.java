package com.example.admin_application.Admin_Register;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin_application.Admin_Login.Admin_login;
import com.example.admin_application.Config;
import com.example.admin_application.Home_Page.MainActivity;
import com.example.admin_application.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Admin_Register extends AppCompatActivity {

    private EditText username, name, lastname, phone, password;
    private Button btn_submit;
    private TextView txt_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        username = findViewById(R.id.register_admin_username);
        name = findViewById(R.id.register_admin_name);
        lastname = findViewById(R.id.register_admin_lastname);
        phone = findViewById(R.id.register_admin_phone);
        password = findViewById(R.id.register_admin_password);

        btn_submit = findViewById(R.id.submit_button);
        txt_login = findViewById(R.id.txt_login);

        checkuserRegister();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAdmin();
            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Register.this, Admin_login.class));
            }
        });


    }



    private void insertAdmin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api uploadService = retrofit.create(api.class);

        String admin_username = username.getText().toString();
        String admin_name = name.getText().toString();
        String admin_lastname = lastname.getText().toString();
        String admin_phone = phone.getText().toString();
        String admin_password = password.getText().toString();

        Call<Model> call = uploadService.insert(admin_username, admin_name, admin_lastname, admin_phone, admin_password);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Admin registered successfully", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences("prefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username",username.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.apply();
                    startActivity(new Intent(Admin_Register.this, MainActivity.class));
                    finishAffinity();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error registering admin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkuserRegister() {
        SharedPreferences sp = getSharedPreferences("prefs",MODE_PRIVATE);
        if (sp.contains("username"))
            startActivity(new Intent(Admin_Register.this, Admin_login.class));
    }
}