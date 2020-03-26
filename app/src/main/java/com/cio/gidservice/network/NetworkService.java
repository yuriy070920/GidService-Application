package com.cio.gidservice.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String BASE_URL = "http://localhost:8080";

    Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
}
