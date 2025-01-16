package com.umermajeed.weather;

import java.io.IOException;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your city name: ");
        String city = scanner.nextLine();

        WeatherAPI weatherAPI = new WeatherAPI();
        String response = weatherAPI.getForecast(city);
        System.out.println(response);

        WeatherResponseParser weatherResponseParser = new WeatherResponseParser();
        weatherResponseParser.parseResponse(response);
    }
}
