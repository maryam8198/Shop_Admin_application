package com.example.admin_application.Home_Page.Product_Pachage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api_Product {


    @FormUrlEncoded
    @POST("Insert_product.php")
    Call<Model_product> Insert_data (@Field("username") String username,
                                     @Field("product_name") String product_name,
                                     @Field("details_product") String details_product,
                                     @Field("price_product") String price_product,
                                     @Field("count_product") String count_product,
                                     @Field("image") String image);


    @GET("Get_product.php")
    Call<List<Model_product>> get_data (@Query("id") int id);

    @FormUrlEncoded
    @POST("Delete_product.php")
    Call<Model_product> delete_product(@Field("id") int id);

    @FormUrlEncoded
    @POST("Edit_product.php")
    Call<Model_product> edit_product(@Field("id") int id,
                                     @Field("product_name") String product_name,
                                     @Field("details_product") String details_product,
                                     @Field("price_product") String price_product,
                                     @Field("count_product") String count_product,
                                     @Field("image") String image ,
                                     @Field("flag_image") String flag_image);


}
