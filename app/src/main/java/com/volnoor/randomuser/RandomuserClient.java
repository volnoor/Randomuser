package com.volnoor.randomuser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Eugene on 13.02.2018.
 */

public interface RandomuserClient {
    //https://randomuser.me/api/?page=1&results=10&seed=abc

    @GET("/api/")
    Call<RandomuserResponse> getUsers(@Query("page") int page,
                                      @Query("results") int results,
                                      @Query("seed") String seed);
}
