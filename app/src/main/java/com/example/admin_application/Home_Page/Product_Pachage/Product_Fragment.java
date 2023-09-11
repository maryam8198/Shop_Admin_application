package com.example.admin_application.Home_Page;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin_application.Home_Page.Product_Pachage.Dialog_Cost_add;
import com.example.admin_application.R;


public class Product_Fragment extends Fragment
{

    private RecyclerView rcy_product;
    private ImageView btn_add_product;
    private Dialog_Cost_add dialogCostAdd;

    public Product_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product_, container, false);

        btn_add_product = view.findViewById(R.id.btn_add_product);

        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCostAdd = new Dialog_Cost_add(getContext());
                dialogCostAdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                dialogCostAdd.show();
            }
        });


        return view;
    }
}