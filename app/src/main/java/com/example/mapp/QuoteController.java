package com.example.mapp;

import android.util.Log;

import com.example.mapp.models.Quot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteController {
    Api.GetQuot getQuot;

    public QuoteController(Api.GetQuot getQuot) {
        this.getQuot = getQuot;
    }

    public void star() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<Quot> call = api.getQuot();
        call.enqueue(new Callback<Quot>() {
            @Override
            public void onResponse(Call<Quot> call, Response<Quot> response) {
                if(response.isSuccessful()) getQuot.onResponse(true , response.body());
                else {
                    Log.d("DD" , "DSD");
                }
            }

            @Override
            public void onFailure(Call<Quot> call, Throwable t) {
                getQuot.onFailure(t.getMessage());
            }
        });
    }
}
