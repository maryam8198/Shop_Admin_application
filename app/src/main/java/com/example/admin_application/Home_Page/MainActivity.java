package com.example.admin_application.Home_Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.admin_application.Home_Page.Add_Product.Add_Fragment;
import com.example.admin_application.Home_Page.Product_Pachage.Product_Fragment;
import com.example.admin_application.Home_Page.Profile_Package.Profile_Fragment;
import com.example.admin_application.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btn_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_navigation = findViewById(R.id.btn_navigation);


        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.item_home) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home_Page_Fragment()).commit();
                    btn_navigation.getMenu().getItem(0).setChecked(true);

                } else if (id == R.id.item_add) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Add_Fragment()).commit();
                    btn_navigation.getMenu().getItem(1).setChecked(true);

                } else if (id == R.id.item_product) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Product_Fragment()).commit();
                    btn_navigation.getMenu().getItem(2).setChecked(true);

                } else if (id == R.id.item_profile) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Profile_Fragment()).commit();
                    btn_navigation.getMenu().getItem(3).setChecked(true);

                }
                return false;
            }
        });


    }
}