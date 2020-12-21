package com.doordash.lite.repository;

import com.doordash.lite.model.FeedResponse;
import com.doordash.lite.model.Restaurant;
import com.doordash.lite.model.RestaurantV2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestaurantWebService {
    String BASE_URL = "https://api.doordash.com";

    @GET("/v1/store_feed")
    Call<FeedResponse> getRestaurants(@Query("lat") double lat,
                                      @Query("lng") double lng,
                                      @Query("offset") int offset,
                                      @Query("limit") int limit);
    @GET("/v2/restaurant/{id}/")
    Call<RestaurantV2> getRestaurantDetail(@Path("id") int id);
}
