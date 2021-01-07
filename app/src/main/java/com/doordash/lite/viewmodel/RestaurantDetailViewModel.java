package com.doordash.lite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doordash.lite.model.RestaurantV2;
import com.doordash.lite.repository.RestaurantRepository;

/**
 * Hold Restaurant detail data to display.
 * Always fetch data from repository simply.
 */
public class RestaurantDetailViewModel extends ViewModel {
    public LiveData<RestaurantV2> getRestaurantDetail(int id) {
        return RestaurantRepository.getInstance().getRestaurantDetail(id);
    }
}
