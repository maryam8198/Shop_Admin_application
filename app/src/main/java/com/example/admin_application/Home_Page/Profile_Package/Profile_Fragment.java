package com.example.admin_application.Home_Page.Profile_Package;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_application.Config;
import com.example.admin_application.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile_Fragment extends Fragment implements View.OnClickListener{




    private Bitmap bitmap;

    private static String username;
    private RecyclerView rec;

    private ImageView image_user;
    private Button submit_for_upload_Image;
    TextView txt_username, txt_name,txt_phone;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        // cast
        rec = view.findViewById(R.id.rec);
        image_user = view.findViewById(R.id.image_user);
        submit_for_upload_Image = view.findViewById(R.id.submit_for_upload_Image);
        txt_username = view.findViewById(R.id.txt_username);
        txt_phone = view.findViewById(R.id.txt_phone);
        txt_name = view.findViewById(R.id.txt_name);

        // set click liserner
        image_user.setOnClickListener(this);
        submit_for_upload_Image.setOnClickListener(this);



        getData();
        return view;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.submit_for_upload_Image)
        {

//            Toast.makeText(getContext(),bitmap+"",Toast.LENGTH_SHORT).show();
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//            String imageFileName = "JPEG_" + timeStamp + "_";
            savaImage();
        }
        else if (view.getId() == R.id.image_user)
        {
            openImageChooser();
        }

    }

     //select image in galary or camera
    private void openImageChooser()
    {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA},Config.REQUEST_CAMERA_PERMISSION);
        }
        else
        {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Intent chooser = Intent.createChooser(intent, "انتخاب عکس");
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });

            if (chooser.resolveActivity(getContext().getPackageManager()) != null) {
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
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                    //Setting the Bitmap to ImageView
                    image_user.setImageBitmap(bitmap);
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
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

                    //Setting the Bitmap to ImageView
                    image_user.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getContext(), "nulll", Toast.LENGTH_SHORT).show();
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
    public void savaImage ()
    {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        String image = getStringImage(bitmap);


            if (image != null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Config.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api_Profile apiProfile = retrofit.create(Api_Profile.class);
                Call<Model> call = apiProfile.uploadImage(image,username);

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        try {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "عکس با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "خطایی رخ داده مجدد امتحان کنید", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "خطایی رخ داده مجدد امتحان کنید: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        try {
                            Toast.makeText(getContext(), "خطایی رخ داده مجدد امتحان کنید: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "خطایی رخ داده مجدد امتحان کنید: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
            }

        }

    private void getData ()
    {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("username", "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api_Profile apiProfile = retrofit.create(Api_Profile.class);
            Call<List<Model>> call = apiProfile.get_profile(username);
            call.enqueue(new Callback<List<Model>>() {
                @Override
                public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                    if (response.isSuccessful()) {
                        List<Model> model = response.body();
                        if (model != null)
                        {
                            // Process the data
                            txt_username.setText(" نام کاربری : "+ model.get(model.size() - 1).getUsername());
                            txt_name.setText("نام و نام خانوادگی : "+model.get(model.size() - 1).getName() +"  "+ model.get(model.size()-1).getLastname());
                            txt_phone.setText("شماره تلفن : " +model.get(model.size() - 1).getPhone());

                            String imageUrl = Config.url + model.get(model.size() - 1).getImage()+"";
                            //Toast.makeText(getContext(), imageUrl+"", Toast.LENGTH_SHORT).show();

                            if(imageUrl == "")
                            {

                                image_user.setImageDrawable(getResources().getDrawable(R.drawable.baseline_add_a_photo_24));
                            }
                            else
                            {
                              Picasso.get()
                                      .load(imageUrl)
                                      .placeholder(R.drawable.baseline_person_24)
                                      .error(R.drawable.baseline_person_24)
                                      .into(image_user);

                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Model>> call, Throwable t) {
                    Toast.makeText(getContext(), "خطا در دریافت اطلاعات", Toast.LENGTH_SHORT).show();
                }
            });
        }
}





