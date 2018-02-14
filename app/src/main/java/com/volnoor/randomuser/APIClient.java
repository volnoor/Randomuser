package com.volnoor.randomuser;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eugene on 13.02.2018.
 */

public class APIClient {
    private static Retrofit retrofit = null;

    private static final String BASE_URL = "https://randomuser.me";

    public static Retrofit getClient(final Context context) {
        // Setup Interceptor
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                cacheBuilder.maxStale(365, TimeUnit.DAYS);
                CacheControl cacheControl = cacheBuilder.build();

                Request request = chain.request();
                if (Utils.isNetworkAvailable(context)) {
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                okhttp3.Response originalResponse = chain.proceed(request);

                if (Utils.isNetworkAvailable(context)) {
                    int maxAge = 60; // Read from cache for 1 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // Tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };

        // Setup cache
        File httpCacheDirectory = new File(context.getCacheDir(), "results");
        int cacheSize = 20 * 1024 * 1024; // 20 mb
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit;
    }
}
