package com.purchase.sls.common.location;

import java.io.Serializable;

/**
 * Created by JWC on 2018/5/29.
 */

public class LocationBean implements Serializable {

    private double lon;
    private double lat;
    private String title;
    private String content;

    public LocationBean(double lon, double lat, String title, String content) {
        this.lon = lon;
        this.lat = lat;
        this.title = title;
        this.content = content;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
