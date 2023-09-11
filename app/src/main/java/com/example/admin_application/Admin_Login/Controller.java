package com.example.admin_application.Admin_Login;

import com.example.admin_application.Config;
import com.example.admin_application.Home_Page.Profile_Package.Api_Profile;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {


    private static Controller client_object;
    private static Retrofit retrofit;



    Controller(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static synchronized Controller getInstance(){
        if (client_object == null)
            client_object = new Controller();

        return client_object;
    }


    Api api(){
        return  retrofit.create(Api.class);
    }

}
