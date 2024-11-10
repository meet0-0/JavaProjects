package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherData extends WeatherSource {
    private double temperature;
    private String weatherDescription;
    private static final String API_KEY = "your api key";

    public WeatherData(String cityName, String country) {
        super(cityName, country);
    }

    @Override
    public void fetchWeatherData() {
        String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,%s&appid=%s&units=metric",
                cityName, country, API_KEY);
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseString = response.toString();
                temperature = extractTemp(responseString);
                weatherDescription = extractWeatherData(responseString);
            } else {
                System.out.println("Error: Unable to fetch data.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    private double extractTemp(String response) {
        int startIndex = response.indexOf("\"temp\":") + 7;
        int endIndex = response.indexOf(",", startIndex);
        return Double.parseDouble(response.substring(startIndex, endIndex));
    }

    private String extractWeatherData(String response) {
        int startIndex = response.indexOf("\"description\":\"") + 15;
        int endIndex = response.indexOf("\"", startIndex);
        return response.substring(startIndex, endIndex);
    }

    public void display() {
        System.out.println("Weather in " + cityName + ", " + country + ":");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Description: " + weatherDescription);
    }
}

