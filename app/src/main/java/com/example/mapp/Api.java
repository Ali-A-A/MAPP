package com.example.mapp;

import com.example.mapp.models.Quot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Api {
    String BASE_URL = "https://api.quotable.io/";
    String BASE_URL2 = "http://www.splashbase.co/";
//    String BASE_URL2 = "http://10.0.2.2:8000/";

    @GET("random/")
    Call<Quot> getQuot();

    @Headers("Content-Type: application/json")
//    @GET("photos/random/?client_id=tSbDbd3PeGvMXjDvaicQHgwjFc4xY_NVyXY1OpLJ4ow&query=flowers")
    @GET("api/v1/images/random")
    Call<String> getUrls();

    interface GetQuot {
        void onResponse(boolean successful , Quot s);
        void onFailure(String c);
    }

    interface GetUrls {
        void onResponse(boolean successful , String u);
        void onFailure(String c);
    }
}
