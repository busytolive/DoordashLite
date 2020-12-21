package com.doordash.lite.model;

import java.util.List;

/**
 * Data(partial) responded by API: https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=500
 */
public class FeedResponse {
    private int numResults;
    private int nextOffset;
    private List<Restaurant> stores;

    public int getNumResults() {
        return numResults;
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public List<Restaurant> getStores() {
        return stores;
    }
}
