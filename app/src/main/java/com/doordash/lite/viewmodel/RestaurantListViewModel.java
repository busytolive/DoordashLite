package com.doordash.lite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.doordash.lite.model.Restaurant;
import com.doordash.lite.repository.RestaurantRepository;

public class RestaurantListViewModel extends ViewModel {
    public LiveData<PagedList<Restaurant>> getRestaurantList() {
        return RestaurantRepository.getInstance().getRestaurantList();
    }
}
