package com.umermajeed.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherAPI {
    private static final String API_KEY = "847a91e8f77b63060840e61a238409f1";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

    public String getForecast(String city) throws IOException {
        String apiURL = BASE_URL + "?q=" + URLEncoder.encode(city , StandardCharsets.UTF_8.toString()) + "&appid=" + API_KEY + "&units=metric";
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
    }
}
