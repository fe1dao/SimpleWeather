package com.example.simpleweather.model;

public class WeatherData {
    private String city;
    private String temperature;
    private String weather;
    private String humidity;
    private String wind;

    // 构造函数
    public WeatherData(String city, String temperature, String weather, String humidity, String wind) {
        this.city = city;
        this.temperature = temperature;
        this.weather = weather;
        this.humidity = humidity;
        this.wind = wind;
    }

    // Getter 和 Setter 方法
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public String getHumidity() { return humidity; }
    public void setHumidity(String humidity) { this.humidity = humidity; }

    public String getWind() { return wind; }
    public void setWind(String wind) { this.wind = wind; }
}