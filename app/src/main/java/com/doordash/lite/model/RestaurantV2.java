package com.doordash.lite.model;

/**
 * Data(partial) of a restaurant returned by API: https://api.doordash.com/v2/restaurant/{id}/
 */
public class RestaurantV2 {
    private int id;
    private String name;
    private String description;
    private String coverImgUrl;
    private String status;
    private int deliveryFee;
    private double averageRating;
    private int numberOfRatings;

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

    public String getStatus() {
        return status;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }
}
