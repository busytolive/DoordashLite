package com.doordash.lite.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.doordash.lite.model.Restaurant;
import com.doordash.lite.model.RestaurantV2;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.doordash.lite.repository.RestaurantWebService.BASE_URL;

/**
 * Provide a clean API so that the rest of the app can retrieve Restaurant data easily,
 * without having to care where the data comes from, like remote APIs or local caches.
 */
public class RestaurantRepository {
    private static RestaurantRepository instance;

    private static final String TAG = RestaurantRepository.class.getSimpleName();
    private final RestaurantWebService restaurantWebService;
    private LiveData<PagedList<Restaurant>> restaurantList;

    private RestaurantRepository() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        restaurantWebService = retrofit.create(RestaurantWebService.class);
    }

    public static synchronized RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    public LiveData<PagedList<Restaurant>> getRestaurantList() {
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(50)
                .setEnablePlaceholders(false)
                .build();
        RestaurantDataSourceFactory restaurantDataSourceFactory = new RestaurantDataSourceFactory();
        if (restaurantList == null) {
            restaurantList = new LivePagedListBuilder(restaurantDataSourceFactory, config).build();
        }
        return restaurantList;
    }

    public LiveData<RestaurantV2> getRestaurantDetail(int id) {
        MutableLiveData<RestaurantV2> restaurantLiveData = new MutableLiveData<>();
        restaurantWebService.getRestaurantDetail(id).enqueue(new Callback<RestaurantV2>() {
            @Override
            public void onResponse(Call<RestaurantV2> call, Response<RestaurantV2> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Retrofit call to get restaurant detail success with response: " + response.body().toString());
                    restaurantLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "Retrofit call to get restaurant detail fail with response: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RestaurantV2> call, Throwable t) {
                Log.d(TAG, "Retrofit call to get restaurant detail gets Exception: " + t.getMessage());
            }
        });
        return restaurantLiveData;
    }

    public RestaurantWebService getRestaurantWebService() {
        return restaurantWebService;
    }
}
