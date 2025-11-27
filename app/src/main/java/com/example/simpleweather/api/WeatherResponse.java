package com.example.simpleweather.api;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("name")
    private String cityName;

    @SerializedName("main")
    private MainData main;

    @SerializedName("weather")
    private Weather[] weather;

    @SerializedName("wind")
    private Wind wind;

    // 内部类
    public static class MainData {
        @SerializedName("temp")
        private double temp;

        @SerializedName("humidity")
        private int humidity;

        public double getTemp() { return temp; }
        public int getHumidity() { return humidity; }
    }

    public static class Weather {
        @SerializedName("main")
        private String main;

        @SerializedName("description")
        private String description;

        public String getMain() { return main; }
        public String getDescription() { return description; }
    }

    public static class Wind {
        @SerializedName("speed")
        private double speed;

        public double getSpeed() { return speed; }
    }

    // Getter方法
    public String getCityName() { return cityName; }
    public MainData getMain() { return main; }
    public Weather[] getWeather() { return weather; }
    public Wind getWind() { return wind; }
}