package com.example.admin_application.Admin_Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("Login_Admin.php")
    Call<Model> login (@Field("username") String username,
                       @Field("password") String password);
                       //@Field("login_code") String login_code);

}
