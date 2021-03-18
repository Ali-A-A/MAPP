package com.example.mapp;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UrlsController {
    Api.GetUrls getUrls;

    public UrlsController(Api.GetUrls getUrls) {
        this.getUrls = getUrls;
    }

    public void start() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL2).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<String> call = api.getUrls();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                
                if(response.isSuccessful()) getUrls.onResponse(true , response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("T" , t.getMessage());
                getUrls.onFailure(t.getMessage());
            }
        });
    }
}
