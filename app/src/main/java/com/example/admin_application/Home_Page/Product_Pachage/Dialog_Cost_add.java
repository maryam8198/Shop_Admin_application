    package com.example.admin_application.Home_Page.Product_Pachage;

    import android.app.Dialog;
    import android.content.Context;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.View;
    import android.view.Window;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;

    import com.example.admin_application.Config;
    import com.example.admin_application.R;

    import java.text.DecimalFormat;
    import java.text.NumberFormat;
    import java.util.Locale;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class Dialog_Cost_add extends Dialog {

        private EditText product_name,details_product,price_product,count_product;
        private ImageView image_product;
        private Button btn_submit_product;
        private static String username;


        public Dialog_Cost_add(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_product);

            product_name = findViewById(R.id.product_name);
            details_product = findViewById(R.id.details_product);
            price_product = findViewById(R.id.price_product);
            count_product = findViewById(R.id.count_product);
            image_product = findViewById(R.id.image_product);
            btn_submit_product = findViewById(R.id.btn_submit_product);

            //get_username
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("prefs",Context.MODE_PRIVATE);
            username = sharedPreferences.getString("username","");


            btn_submit_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InsertData();

                    dismiss();
                }
            });

        }

        private void InsertData() {



            Retrofit retrofit  = new Retrofit.Builder()
                    .baseUrl(Config.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api_Product apiProduct = retrofit.create(Api_Product.class);
            Call<Model> call = apiProduct.Insert_data(username,product_name.getText().toString(),details_product.getText().toString(),price_product.getText().toString(),count_product.getText().toString());
            call.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    Toast.makeText(getContext(), "با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
