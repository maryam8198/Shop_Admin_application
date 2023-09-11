package com.example.admin_application.Home_Page.Product_Pachage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api_Product {


    @FormUrlEncoded
    @POST("Insert_product.php")
    Call<Model> Insert_data (@Field("username") String username,
                             @Field("product_name") String product_name,
                             @Field("details_product") String details_product,
                             @Field("price_product") String price_product,
                             @Field("count_product") String count_product);
//                            @Field("image") String image);

}
