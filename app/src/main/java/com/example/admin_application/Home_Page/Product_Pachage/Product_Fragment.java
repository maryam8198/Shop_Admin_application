package com.example.admin_application.Home_Page.Product_Pachage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Product_Fragment extends Fragment
{
    private static final int DIALOG_REQUEST_CODE = 1;
    private RecyclerView rcy_product;
    private ImageView btn_add_product;

    private Adaptor_Product adaptorProduct;
    public static List<Model_product> modelProducts;

    private int position;
    public static int id_edit;
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
        rcy_product = view.findViewById(R.id.rcy_product);

        rcy_product.setLayoutManager(new LinearLayoutManager(getContext()));

        //adaptor = new Habit_Adaptor(getModelList);
        modelProducts = new ArrayList<>();

        getDataProduct();

        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Add_Product();
            }
        });

        // ایجاد ItemTouchHelper با itemTouchHelperCallback
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rcy_product);

        return view;
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback
            (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
    {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
             position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                {

                    int id2 = modelProducts.get(position).getId();
                    Toast.makeText(getContext(),id2+"",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("حذف")
                            .setMessage("آیا از حذف آیتم انتخاب شده مطمئن هستید؟")
                            .setIcon(R.drawable.baseline_delete_outline_24)
                            .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteProduct(id2);
                                }
                            })
                            .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    adaptorProduct.notifyDataSetChanged();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                case ItemTouchHelper.RIGHT:
                {
                     id_edit = modelProducts.get(position).getId();
                    Toast.makeText(getContext(),id_edit+"",Toast.LENGTH_SHORT).show();

                    show_Edit_product();
                }
            }
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (dX < 0) {
                    // Swipe به سمت چپ
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeLeftBackgroundColor(Color.RED)
                            .addSwipeLeftActionIcon(R.drawable.baseline_delete_outline_24)
                            .addSwipeLeftLabel(" حذف ")
                            .create()
                            .decorate();

                }
                if (dX > 0) {
                    // Swipe به سمت راست
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeRightBackgroundColor(Color.LTGRAY)
                            .addSwipeRightActionIcon(R.drawable.baseline_edit_24)
                            .addSwipeRightLabel(" ویرایش ")
                            .create()
                            .decorate();
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    private void show_Add_Product() {
        Intent intent = new Intent(getActivity(), Add_Product_Dialog.class);

        startActivityForResult(intent, DIALOG_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_REQUEST_CODE)
        {
            if (resultCode == Config.RESULT_CODE_SUCCESS)
            {
                // عملیات بازگشت از دیالوگ با موفقیت انجام شده است
                getDataProduct();

            }
            else if (resultCode == Config.RESULT_CODE_CANCEL)
            {
                // عملیات بازگشت از دیالوگ کنسل شده است
            }
        }
    }

    private void show_Edit_product()
    {
        Intent intent1 = new Intent(getActivity(), Edit_Product.class);
        intent1.putExtra("id_edit",id_edit);
        startActivityForResult(intent1, DIALOG_REQUEST_CODE);
    }

    private void getDataProduct()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int id = 1;
        Api_Product apiProduct = retrofit.create(Api_Product.class);
        Call<List<Model_product>> call = apiProduct.get_data(id);

        call.enqueue(new Callback<List<Model_product>>() {
            @Override
            public void onResponse(Call<List<Model_product>> call, Response<List<Model_product>> response)
            {
                modelProducts = response.body();

                adaptorProduct = new Adaptor_Product(modelProducts, getContext());
                rcy_product.setAdapter(adaptorProduct);
            }

            @Override
            public void onFailure(Call<List<Model_product>> call, Throwable t)
            {
                Toast.makeText(getContext(), "مشکل برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteProduct(int id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api_Product apiProduct = retrofit.create(Api_Product.class);
        Call<Model_product> call = apiProduct.delete_product(id);

        call.enqueue(new Callback<Model_product>() {
            @Override
            public void onResponse(Call<Model_product> call, Response<Model_product> response) {

                if (response.isSuccessful())
                {
                    adaptorProduct.removeItem(position);
                    adaptorProduct.notifyDataSetChanged();
                    Toast.makeText(getContext(), "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "خطایی رخ داده مجدد امتحان کنید", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Model_product> call, Throwable t) {
                Toast.makeText(getContext(), "مشکل برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        });
    }



}