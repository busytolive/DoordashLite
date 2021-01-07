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
 * DataSource used to fetch nearby restaurant list to feed the paged list when needed.
 *
 * Currently the current location is fixed. We may want to create a custom constructor to support
 * fetch restaurant list near a specified location(latitude, longitude)
 */
public class RestaurantDataSource extends PositionalDataSource<Restaurant> {
    private static final String TAG = RestaurantRepository.class.getSimpleName();

    public static final double LAT = 37.422740;
    public static final double LNG = -122.139956;

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Restaurant> callback) {
        try {
            Response<FeedResponse> response = RestaurantRepository.getInstance().getRestaurantWebService()
                    .getRestaurants(LAT, LNG, params.requestedStartPosition, params.requestedLoadSize).execute();
            if (response.isSuccessful()) {
                // TODO: this line of log should not go to production build (same as other similar places)
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
            Response<FeedResponse> response = RestaurantRepository.getInstance().getRestaurantWebService().
                    getRestaurants(LAT, LNG, params.startPosition, params.loadSize).execute();
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
