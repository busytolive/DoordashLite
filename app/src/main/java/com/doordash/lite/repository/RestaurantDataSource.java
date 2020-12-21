package com.doordash.lite.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.doordash.lite.model.FeedResponse;
import com.doordash.lite.model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Android DataSource fetching restaurant list data with offset and limit.
 */
public class RestaurantDataSource extends PositionalDataSource<Restaurant> {
    public static final double LAT = 37.422740;
    public static final double LNG = -122.139956;
    private static final String TAG = RestaurantRepository.class.getSimpleName();

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Restaurant> callback) {
        try {
            Response<FeedResponse> response = RestaurantRepository.getInstance().getRestaurantWebService().getRestaurants(LAT, LNG, params.requestedStartPosition, params.requestedLoadSize).execute();
            if (response.isSuccessful()) {
                Log.d(TAG, "Retrofit call to get restaurant list success with response: " + response.body());
                List<Restaurant> restaurantList = new ArrayList<>();
                if (response.body() != null) {
                    restaurantList = response.body().getStores();
                    if (restaurantList == null) {
                        restaurantList = new ArrayList<>();
                    }
                }
                callback.onResult(restaurantList, response.body().getNextOffset());
            } else {
                Log.e(TAG, "Retrofit call to get restaurant list failed with response: " + response.body());
            }
        } catch (IOException e) {
            Log.e(TAG, "Retrofit call to get restaurant list got IO Exception: " + e.toString());
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Restaurant> callback) {
        try {
            Response<FeedResponse> response = RestaurantRepository.getInstance().getRestaurantWebService().getRestaurants(LAT, LNG, params.startPosition, params.loadSize).execute();
            if (response.isSuccessful()) {
                Log.d(TAG, "Retrofit call to get restaurant list success with response: " + response.body());
                List<Restaurant> restaurantList = new ArrayList<>();
                if (response.body() != null) {
                    restaurantList = response.body().getStores();
                    if (restaurantList == null) {
                        restaurantList = new ArrayList<>();
                    }
                }
                callback.onResult(restaurantList);
            } else {
                Log.e(TAG, "Retrofit call to get restaurant list failed with response: " + response.body());
            }
        } catch (IOException e) {
            Log.e(TAG, "Retrofit call to get restaurant list got IO Exception: " + e.toString());
        }
    }
}
