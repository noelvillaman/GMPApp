package com.software.leonwebmedia.gmpapp.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommitsApi {
    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit retrofit;
    private static CommitsService commitsService;

    public static CommitsService getInstance() {
        if (commitsService != null) {
            return commitsService;
        }
        if (retrofit == null) {
            initializeRetrofit();
        }
        commitsService = retrofit.create(CommitsService.class);
        return commitsService;
    }

    private static void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
