package com.example.simpleweather;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleweather.api.ApiClient;
import com.example.simpleweather.api.WeatherResponse;
import com.example.simpleweather.api.WeatherService;
import com.example.simpleweather.utils.PreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etCity;
    private Button btnSearch;
    private TextView tvCity, tvTemperature, tvWeather, tvHumidity, tvWind;

    // 需要到 https://openweathermap.org/ 注册获取免费API密钥
    private static final String API_KEY = "d79050fde5e711291df116924298dd96";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置全屏模式（隐藏状态栏和标题栏）
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // 隐藏ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);

        initView();
        loadWeather(PreferenceUtil.getLastCity(this));
    }

    private void initView() {
        etCity = findViewById(R.id.et_city);
        btnSearch = findViewById(R.id.btn_search);
        tvCity = findViewById(R.id.tv_city);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvWeather = findViewById(R.id.tv_weather);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWind = findViewById(R.id.tv_wind);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCity.getText().toString();
                if (!city.isEmpty()) {
                    loadWeather(city);
                    PreferenceUtil.saveLastCity(MainActivity.this, city);
                } else {
                    Toast.makeText(MainActivity.this, "请输入城市名称", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadWeather(String city) {
        WeatherService service = ApiClient.getWeatherService();
        Call<WeatherResponse> call = service.getWeather(city, API_KEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    updateUI(weatherResponse);
                } else {
                    Toast.makeText(MainActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "网络请求失败: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(WeatherResponse weatherResponse) {
        if (weatherResponse == null) return;

        try {
            // 城市名称
            tvCity.setText(weatherResponse.getCityName());

            // 温度
            tvTemperature.setText(String.format("%.0f°C", weatherResponse.getMain().getTemp()));

            // 天气状态和图标
            if (weatherResponse.getWeather() != null && weatherResponse.getWeather().length > 0) {
                String weatherDesc = weatherResponse.getWeather()[0].getDescription();
                String weatherIcon = getWeatherIcon(weatherDesc);
                tvWeather.setText(weatherIcon + " " + weatherDesc);
            }

            // 湿度和风速
            tvHumidity.setText(weatherResponse.getMain().getHumidity() + "%");
            tvWind.setText(String.format("%.1f m/s", weatherResponse.getWind().getSpeed()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据天气描述返回对应的图标
    private String getWeatherIcon(String weatherDesc) {
        if (weatherDesc == null) return "🌤️";

        weatherDesc = weatherDesc.toLowerCase();
        if (weatherDesc.contains("clear")) return "☀️";
        if (weatherDesc.contains("cloud")) return "☁️";
        if (weatherDesc.contains("rain")) return "🌧️";
        if (weatherDesc.contains("snow")) return "❄️";
        if (weatherDesc.contains("thunderstorm")) return "⛈️";
        if (weatherDesc.contains("drizzle")) return "🌦️";
        if (weatherDesc.contains("mist") || weatherDesc.contains("fog")) return "🌫️";
        return "🌤️";
    }
}