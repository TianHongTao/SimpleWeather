package com.simpleweather.simpleweather.Weather;

public class CurrentWeatherInfo {
    private double temperature;
    private String skycon;
    private double pm25;

    public void setCloudrate(double cloudrate) {
        this.cloudrate = cloudrate;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setIntensity_n(double intensity_n) {
        this.intensity_n = intensity_n;
    }

    public void setIntensity_l(double intensity_l) {
        this.intensity_l = intensity_l;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double cloudrate;
    private double humidity;
    private double distance;
    private double intensity_n;
    private double intensity_l;
    private double direction;
    private double speed;

    public CurrentWeatherInfo() {
        this.temperature = 0.0;
        this.skycon = "";
        this.pm25 = 0.0;
        this.cloudrate = 0.0;
        this.humidity = 0.0;
        this.distance = 0.0;
        this.intensity_n = 0.0;
        this.intensity_l = 0.0;
        this.direction = 0.0;
        this.speed = 0.0;
    }


    public String getSkyconText(String info) {
        info = getSkyconString(info);
        return info;
    }

    static String getSkyconString(String info) {
        switch (info) {
            case "CLEAR_DAY":
                info = "晴天";
                break;
            case "CLEAR_NIGHT":
                info = "晴夜";
                break;
            case "PARTLY_CLOUDY_DAY":
                info = "多云天";
                break;
            case "PARTLY_CLOUDY_NIGHT":
                info = "多云夜";
                break;
            case "CLOUDY":
                info = "阴";
                break;
            case "RAIN":
                info = "雨";
                break;
            case "SNOW":
                info = "雪";
                break;
            case "WIND":
                info = "风";
                break;
            case "HAZE":
                info = "雾霾沙尘";
                break;
            default:
                info = "NONE";
        }
        return info;
    }

    public CurrentWeatherInfo(double temperature, String skycon, double pm25, double cloudrate, double humidity
            , double distance, double intensity_n, double intensity_l, double direction, double speed) {
        this.temperature = temperature;
        this.skycon = getSkyconText(skycon);
        this.pm25 = pm25;
        this.cloudrate = cloudrate;
        this.humidity = humidity;
        this.distance = distance;
        this.intensity_n = intensity_n;
        this.intensity_l = intensity_l;
        this.direction = direction;
        this.speed = speed;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getSkycon() {
        return skycon;
    }

    public double getPm25() {
        return pm25;
    }

    public double getCloudrate() {
        return cloudrate;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getDistance() {
        return distance;
    }

    public double getIntensity_n() {
        return intensity_n;
    }

    public double getIntensity_l() {
        return intensity_l;
    }

    public double getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTemperature());
        stringBuilder.append(" ");
        stringBuilder.append(getSkycon());
        stringBuilder.append(" ");
        stringBuilder.append(getPm25());
        stringBuilder.append(" ");
        stringBuilder.append(getCloudrate());
        stringBuilder.append("\n");
        stringBuilder.append(getHumidity());
        stringBuilder.append(" ");
        stringBuilder.append(getDistance());
        stringBuilder.append(" ");
        stringBuilder.append(getIntensity_n());
        stringBuilder.append("\n");
        stringBuilder.append(getIntensity_l());
        stringBuilder.append(" ");
        stringBuilder.append(getDirection());
        stringBuilder.append(" ");
        stringBuilder.append(getSpeed());
        return stringBuilder.toString();
    }
}