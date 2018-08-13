package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

public class CoordinateCityRequest {
    @SerializedName("coordinate")
    private String coordinate;
    @SerializedName("city")
    private String city;

    public CoordinateCityRequest(String coordinate, String city) {
        this.coordinate = coordinate;
        this.city = city;
    }
}
