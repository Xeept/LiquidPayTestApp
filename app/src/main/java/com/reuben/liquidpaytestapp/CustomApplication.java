package com.reuben.liquidpaytestapp;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.reuben.liquidpaytestapp.retroInterface.LiquidClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomApplication extends Application {
    private Retrofit retrofit;
    private LiquidClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder.baseUrl(ConstantsManager.LIQUID_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        client = retrofit.create(LiquidClient.class);
    }

    public LiquidClient getLiquidClient(){
        return client;
    }
}
