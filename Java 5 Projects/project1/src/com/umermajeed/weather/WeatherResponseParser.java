package com.umermajeed.weather;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherResponseParser {
    public void parseResponse(String jsonResponse) {
        JSONObject response = new JSONObject(jsonResponse);
        if (response.getInt("cod") == 200) {
            System.out.println("Weather Response: ");
            JSONArray weatherArray = response.getJSONArray("list");
            for (int i =0 ; i < weatherArray.length(); i++) {
                JSONObject weatherObject = weatherArray.getJSONObject(i);
                long timestamp = weatherObject.getLong("dt");
                String date = new SimpleDateFormat("yyyy-MM-dd").format( new Date(timestamp * 1000));
                double temperature = weatherObject.getJSONObject("main").getDouble("temp");
                String description = weatherObject.getJSONArray("weather").getJSONObject(0).getString("description");
                String icon = weatherObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                int humidity = weatherObject.getJSONObject("main").getInt("humidity");

                System.out.println(date+" "+temperature+" "+description +" "+icon+" "+humidity);

            }
        }
        else {
            System.out.println("Error: " + response.getInt("status"));
        }
    }
}
