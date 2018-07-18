package com.purchase.sls.common.unit;

/**
 * Created by Administrator on 2016/3/18.
 */
public class CityManager {

    public static final String CITY = "City";

    public static String getCity(){

        return SPManager.getInstance().getCityData(CITY);
    }

    public static void saveCity(String city){
        SPManager.getInstance().putCityData(CITY,city);
    }

}
