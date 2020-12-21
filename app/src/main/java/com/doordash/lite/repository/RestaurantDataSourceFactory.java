package com.doordash.lite.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.doordash.lite.model.Restaurant;

class RestaurantDataSourceFactory extends DataSource.Factory<Integer, Restaurant> {
    @NonNull
    @Override
    public DataSource<Integer, Restaurant> create() {
        return new RestaurantDataSource();
    }
}
