package com.example.admin_application.Admin_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin_application.Admin_Register.Admin_Register;
import com.example.admin_application.Home_Page.MainActivity;
import com.example.admin_application.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_login extends AppCompatActivity {

    private EditText username,password,login_code;
    private Button btn_submit;

    TextView txt_regester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        //caste_View
        username = findViewById(R.id.login_admin_username);
        password = findViewById(R.id.login_admin_password);
        login_code = findViewById(R.id.login_code);
        btn_submit = findViewById(R.id.btn_login_submit);
        txt_regester = findViewById(R.id.txt_regester);

        checkuserlogin();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        txt_regester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_login.this, Admin_Register.class));
            }
        });

    }

    private void login() {

        Call<Model> call = Controller.getInstance().api().login(username.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                String output = model.getMessage();
                if (output.equals("ok")){
                    Toast.makeText(Admin_login.this, "با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences("prefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username",username.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.apply();
                    //SharedPreferences
                    checkuserlogin();
                    finishAffinity();
                }

                if (output.equals("error")){
                    Toast.makeText(Admin_login.this, "همچین کاربری وجود ندارد لطفا ثبت نام کنید", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"مشکل برقراری ارتباط",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkuserlogin() {
        SharedPreferences sp = getSharedPreferences("prefs",MODE_PRIVATE);
        if (sp.contains("username")){
            startActivity(new Intent(Admin_login.this, MainActivity.class));
        }

    }

    @Override
    public void onBackPressed() {
        //  this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
