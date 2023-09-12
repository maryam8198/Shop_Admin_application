package com.example.admin_application.Home_Page.Product_Pachage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.admin_application.R;


public class Product_Fragment extends Fragment
{
    private static final int DIALOG_REQUEST_CODE = 1;
    private RecyclerView rcy_product;
    private ImageView btn_add_product;

    public Product_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_, container, false);

        btn_add_product = view.findViewById(R.id.btn_add_product);


        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });


        return view;
    }
    private void showCustomDialog() {
        Intent intent = new Intent(getActivity(), Add_Product_Dialog.class);
        startActivityForResult(intent, DIALOG_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                // عملیات بازگشت از دیالوگ با موفقیت انجام شده است
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                // عملیات بازگشت از دیالوگ کنسل شده است
            }
        }
    }

}