package models;

public abstract class WeatherSource {
    protected String cityName;
    protected String country;

    public WeatherSource(String cityName, String country) {
        this.cityName = cityName;
        this.country = country;
    }

    public abstract void fetchWeatherData();

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }
}
