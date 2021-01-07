package com.doordash.lite.model;

import java.util.List;

/**
 * Restaurant meta data, usually to be shown in a restaurant list.
 *
 * Fetched from API: https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=500
 */
public class Restaurant {

    private class Status {
        private List<Integer> asapMinutesRange;
    }

    private int id;
    private String name;
    private String description;
    private String coverImgUrl;
    private Status status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public String getDeliveryTime() {
        return !status.asapMinutesRange.isEmpty() ? String.valueOf(status.asapMinutesRange.get(0)) : "";
    }

    public boolean equals(Object another) {
        if (this == another) return true;

        if (!(another instanceof Restaurant)) {
            return false;
        }

        Restaurant anotherRestaurant = (Restaurant)another;
        return anotherRestaurant.id == id &&
                anotherRestaurant.name.equals(name) &&
                anotherRestaurant.description.equals(description) &&
                anotherRestaurant.coverImgUrl.equals(coverImgUrl) &&
                anotherRestaurant.status == status;
    }
}
