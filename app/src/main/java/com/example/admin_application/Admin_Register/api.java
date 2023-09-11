package com.example.admin_application.Admin_Register;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface  api {


    @FormUrlEncoded
    @POST("Insert_Admin.php")
    Call<Model> insert (@Field("username") String username,
                        @Field("name") String name,
                        @Field("lastname") String lastname,
                        @Field("phone") String phone,
                        @Field("password") String password);

}
