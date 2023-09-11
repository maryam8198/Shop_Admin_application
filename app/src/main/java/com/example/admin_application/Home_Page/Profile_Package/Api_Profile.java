package com.example.admin_application.Home_Page.Profile_Package;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api_Profile {


    @GET("Get_profile.php")
    Call<List<Model>> get_profile (@Query("username") String username);



    @FormUrlEncoded
    @POST("upload_image.php")
    Call<Model> uploadImage(@Field("image") String image , @Field("username") String username);


}
