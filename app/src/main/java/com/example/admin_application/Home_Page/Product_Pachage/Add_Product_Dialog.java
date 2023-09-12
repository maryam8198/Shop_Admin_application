package com.example.admin_application.Home_Page.Product_Pachage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.admin_application.Config;
import com.example.admin_application.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Product_Dialog extends AppCompatActivity {

    private static final int RESULT_CODE_SUCCESS = 1;
    private static final int RESULT_CODE_CANCEL = 2;

    private Bitmap bitmap;
    private ImageView image_product;
    private EditText product_name, details_product, price_product, count_product;
    private static String username;
    private Button btn_submit_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_product);

        setTitle("اضافه کردن محصول جدید");


        product_name = findViewById(R.id.product_name);
        details_product = findViewById(R.id.details_product);
        price_product = findViewById(R.id.price_product);
        count_product = findViewById(R.id.count_product);
        image_product = findViewById(R.id.image_product);
        btn_submit_product = findViewById(R.id.btn_submit_product);

        btn_submit_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();

            }
        });

        image_product = findViewById(R.id.image_product);

        image_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

    }

    private void openImageChooser()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CAMERA}, Config.REQUEST_CAMERA_PERMISSION);
        }
        else
        {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Intent chooser = Intent.createChooser(intent, "انتخاب عکس");
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });

            if (chooser.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                startActivityForResult(chooser,Config.REQUEST_IMAGE_Camera);
            }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode ==Config.REQUEST_IMAGE_Camera) {
                // عکس انتخاب شد
                Uri imageUri = data.getData();
                // انجام عملیات مرتبط با عکس
                try {
                    //Getting the Bitmap from Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                    //Setting the Bitmap to ImageView
                    image_product.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == Config.REQUEST_IMAGE_GALLERY) {
                // عکس از گالری انتخاب شد
                Uri imageUri = data.getData();
                // انجام عملیات مرتبط با عکس از گالری
                try {
                    //Getting the Bitmap from Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);

                    //Setting the Bitmap to ImageView
                    image_product.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "nulll", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public String getStringImage (Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage =
                Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void InsertData()
    {

        if(validateInputs()) {
            // get address pick
            String image = getStringImage(bitmap);

            // request for server
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api_Product apiProduct = retrofit.create(Api_Product.class);
            Call<Model> call = apiProduct.Insert_data(username,
                    product_name.getText().toString(),
                    details_product.getText().toString(),
                    price_product.getText().toString(),
                    count_product.getText().toString(),
                    image
            );

            call.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {

                    Toast.makeText(getApplicationContext(), "با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CODE_SUCCESS);
                    finish();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (product_name.getText().toString().trim().isEmpty() || details_product.getText().toString().trim().isEmpty()
                || price_product.getText().toString().trim().isEmpty() || count_product.getText().toString().trim().isEmpty()
        ) {

            Toast.makeText(getApplicationContext(), "مقادیر وارد شده نامعتبر", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0)
        {
            Toast.makeText(getApplicationContext(), "مقادیر وارد شده نامعتبر", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_CODE_CANCEL);
        super.onBackPressed();
    }
}