package com.example.admin_application.Home_Page.Product_Pachage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin_application.Config;
import com.example.admin_application.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Product_Fragment extends Fragment
{

    private ImageView btn_add_product;
    private Dialog_Cost_add dialogCostAdd;
    private static String username;
    private RecyclerView rec_product;

    private List<Model> data;

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
        rec_product = view.findViewById(R.id.rcy_product);

        rec_product.setLayoutManager(new LinearLayoutManager(getContext()));

        //get_username
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");

        data = new ArrayList<>();


        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCostAdd = new Dialog_Cost_add(getContext());
                dialogCostAdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        get_Data_product();
                    }
                });
                dialogCostAdd.show();
            }
        });


        return view;
    }

    private void get_Data_product() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api_Product api_product = retrofit.create(Api_Product.class);
        Call<List<Model>> call = api_product.get_data(username);
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                List<Model> data = response.body();
                Adaptor adaptor = new Adaptor(data,getContext());
                rec_product.setAdapter(adaptor);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}