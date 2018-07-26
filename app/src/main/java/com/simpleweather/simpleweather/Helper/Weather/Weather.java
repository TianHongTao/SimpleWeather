package com.simpleweather.simpleweather.Helper.Weather;

public class Weather {
    private WeatherInfo nowWeatherInfo;
    private HoulyWeatherInfo[] hourlyWeatherInfo;
    private DailyWeatherInfo[] dailyWeatherInfo;

    public void setNowWeatherInfo(WeatherInfo nowWeatherInfo) {
        this.nowWeatherInfo = nowWeatherInfo;
    }

    public Weather() {
        nowWeatherInfo = new WeatherInfo();
        hourlyWeatherInfo = new HoulyWeatherInfo[48];
        for (int i = 0; i < hourlyWeatherInfo.length; i++) {
            hourlyWeatherInfo[i] = new HoulyWeatherInfo();
        }
        dailyWeatherInfo = new DailyWeatherInfo[5];
        for (int i = 0; i < dailyWeatherInfo.length; i++) {
            dailyWeatherInfo[i] = new DailyWeatherInfo();
        }
    }

    public void setNowWeatherInfo(double temperature, String skycon, double pm25, double cloudrate, double humidity
            , double distance, double intensity_n, double intensity_l, double direction, double speed) {
        this.nowWeatherInfo = new WeatherInfo(temperature, skycon, pm25, cloudrate, humidity,
                distance, intensity_n, intensity_l, direction, speed);
    }

    public void setDailyWeatherInfo(DailyWeatherInfo[] dailyWeatherInfo) {
        this.dailyWeatherInfo = dailyWeatherInfo;
    }

    public void setHourlyWeatherInfo(HoulyWeatherInfo[] hourlyWeatherInfo) {
        this.hourlyWeatherInfo = hourlyWeatherInfo;
    }

    public WeatherInfo getNowWeatherInfo() {
        return nowWeatherInfo;
    }

    public DailyWeatherInfo getDailyWeatherInfo(int i) {
        return dailyWeatherInfo[i];
    }

    public DailyWeatherInfo[] getDailyWeatherInfos() {
        return dailyWeatherInfo;
    }

    public HoulyWeatherInfo getHourlyWeatherInfo(int i) {
        return hourlyWeatherInfo[i];
    }

    public HoulyWeatherInfo[] getHourlyWeatherInfos() {
        return hourlyWeatherInfo;
    }
}
