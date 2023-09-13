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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_Product extends AppCompatActivity
{
    private Bitmap bitmap;
    private ImageView image_product;
    private EditText product_name, details_product, price_product, count_product;
    private static String username;
    private Button btn_submit_product;
    private int id_edit;
    private List<Model_product> modelProducts;
    private String flag_image = "";
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_product);

        product_name = findViewById(R.id.product_name);
        details_product = findViewById(R.id.details_product);
        price_product = findViewById(R.id.price_product);
        count_product = findViewById(R.id.count_product);
        image_product = findViewById(R.id.image_product);
        btn_submit_product = findViewById(R.id.btn_submit_product);

        setTitle("");

        //get Id
        Intent intent = getIntent();
         id_edit = intent.getIntExtra("id_edit",1);
        getDate_Product(id_edit);

        btn_submit_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                update_Product();
            }
        });
        image_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

    }

    private void getDate_Product(int id_edit)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api_Product apiProduct = retrofit.create(Api_Product.class);
        Call<List<Model_product>> call = apiProduct.get_data(id_edit);
        call.enqueue(new Callback<List<Model_product>>() {
            @Override
            public void onResponse(Call<List<Model_product>> call, Response<List<Model_product>> response) {
                 modelProducts = response.body();
                if(modelProducts != null)
                {
                    product_name.setText(modelProducts.get(0).getProduct_name());
                    details_product.setText(modelProducts.get(0).getDetails_product());
                    price_product.setText(modelProducts.get(0).getPrice_product());
                    count_product.setText(modelProducts.get(0).getCount_product());

                    String image_url = Config.url + modelProducts.get(0).getImage_product();

                    if(image_url == "")
                    {
                        image_product.setImageDrawable(getResources().getDrawable(R.drawable.baseline_add_a_photo_24));
                    }
                    else
                    {
                        Picasso.get()
                                .load(image_url)
                                .placeholder(R.drawable.baseline_person_24)
                                .error(R.drawable.baseline_person_24)
                                .into(image_product);

                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void update_Product()
    {
        String image ="";
        String image_old = modelProducts.get(0).getImage_product();

        if(bitmap == null)
        {
            image = image_old;
            flag_image = "old";
            //Toast.makeText(getApplicationContext(), image, Toast.LENGTH_SHORT).show();
        }
        else
        {
            flag_image = "new";
           image = getStringImage(bitmap);
            //Toast.makeText(getApplicationContext(), image, Toast.LENGTH_SHORT).show();
        }
        if(validateInputs())
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api_Product apiProduct = retrofit.create(Api_Product.class);
            Call<Model_product> call = apiProduct.edit_product(id_edit,
                    product_name.getText().toString(),
                    details_product.getText().toString(),
                    price_product.getText().toString(),
                    count_product.getText().toString(),
                    image , flag_image);

            call.enqueue(new Callback<Model_product>() {
                @Override
                public void onResponse(Call<Model_product> call, Response<Model_product> response) {
                    Toast.makeText(getApplicationContext(), "با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    setResult(Config.RESULT_CODE_SUCCESS);
                    finish();
                }

                @Override
                public void onFailure(Call<Model_product> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                    Toast.makeText(getApplicationContext(), bitmap+"", Toast.LENGTH_SHORT).show();
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
    private boolean validateInputs() {
        boolean isValid = true;

        if (product_name.getText().toString().trim().isEmpty() || details_product.getText().toString().trim().isEmpty()
                || price_product.getText().toString().trim().isEmpty() || count_product.getText().toString().trim().isEmpty()
        ) {

            Toast.makeText(getApplicationContext(), "مقادیر وارد شده نامعتبرh", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }
    @Override
    public void onBackPressed() {
        setResult(Config.RESULT_CODE_CANCEL);
        super.onBackPressed();
    }
}
