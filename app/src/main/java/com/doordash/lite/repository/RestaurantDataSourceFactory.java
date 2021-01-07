package com.doordash.lite.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.doordash.lite.model.Restaurant;

class RestaurantDataSourceFactory extends DataSource.Factory<Integer, Restaurant> {
    @NonNull
    @Override
    public DataSource<Integer, Restaurant> create() {
        // always return a new instance of RestaurantDataSource, for the case that
        // we need to call invalidate() to refresh the page list data.
        return new RestaurantDataSource();
    }
}
