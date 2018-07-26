package com.simpleweather.simpleweather.Helper.Weather;

public class DailyWeatherInfo {
    private String date;
    private String[] sunsetTime;
    private double[] temperature;
    private double[] pm25;
    private String skycon;
    private double[] cloudrate;
    private double[] aqi;
    private double[] precipitation;
    private double[] direction;
    private double[] speed;
    private double[] humidity;

    public String get_skycon_CN(String info) {
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

    public DailyWeatherInfo() {
        this.date = "";
        this.sunsetTime = new String[2];
        for (int i = 0; i < 2; i++) {
            sunsetTime[i] = "";
        }
        this.temperature = new double[3];
        this.pm25 = new double[3];
        this.skycon = "";
        this.cloudrate = new double[3];
        this.aqi = new double[3];
        this.precipitation = new double[3];
        this.direction = new double[3];
        this.speed = new double[3];
        this.humidity = new double[3];
        for (int i = 0; i < 3; i++) {
            speed[i] = 0.0;
            direction[i] = 0.0;
            temperature[i] = 0.0;
            pm25[i] = 0.0;
            cloudrate[i] = 0.0;
            aqi[i] = 0.0;
            precipitation[i] = 0.0;
            humidity[i] = 0.0;
        }
    }

    public DailyWeatherInfo(String date, String[] sunsetTime, double[] temperature, double[] pm25,
                            String skycon, double[] cloudrate, double[] aqi, double[] precipitation,
                            double[] direction, double[] speed, double[] humidity) {
        this.date = date;
        this.sunsetTime = sunsetTime;
        this.temperature = temperature;
        this.pm25 = pm25;
        this.skycon = get_skycon_CN(skycon);
        this.cloudrate = cloudrate;
        this.aqi = aqi;
        this.precipitation = precipitation;
        this.direction = direction;
        this.speed = speed;
        this.humidity = humidity;
    }

    public double[] getSpeed() {
        return speed;
    }

    public double[] getDirection() {
        return direction;
    }

    public String getSkycon() {
        return skycon;
    }

    public String getDate() {
        return date;
    }

    public String[] getSunsetTime() {
        return sunsetTime;
    }

    public double[] getAqi() {
        return aqi;
    }

    public double[] getCloudrate() {
        return cloudrate;
    }

    public double[] getHumidity() {
        return humidity;
    }

    public double[] getPm25() {
        return pm25;
    }

    public double[] getPrecipitation() {
        return precipitation;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public void setAqi(double[] aqi) {
        this.aqi = aqi;
    }

    public void setCloudrate(double[] cloudrate) {
        this.cloudrate = cloudrate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDirection(double direction[]) {
        this.direction = direction;
    }

    public void setHumidity(double[] humidity) {
        this.humidity = humidity;
    }

    public void setPm25(double[] pm25) {
        this.pm25 = pm25;
    }

    public void setPrecipitation(double[] precipitation) {
        this.precipitation = precipitation;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public void setSpeed(double speed[]) {
        this.speed = speed;
    }

    public void setSunsetTime(String[] sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date + " ");
        for (int i = 0; i < sunsetTime.length; i++) {
            stringBuilder.append(sunsetTime[i]);
            stringBuilder.append(" ");
        }

        stringBuilder.append("\n");
        for (int i = 0; i < temperature.length; i++) {
            stringBuilder.append(temperature[i]);
            stringBuilder.append(" ");
        }

        stringBuilder.append("\n");
        for (int i = 0; i < pm25.length; i++) {
            stringBuilder.append(pm25[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append(skycon);
        stringBuilder.append("\n");

        for (int i = 0; i < cloudrate.length; i++) {
            stringBuilder.append(cloudrate[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < aqi.length; i++) {
            stringBuilder.append(aqi[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < precipitation.length; i++) {
            stringBuilder.append(precipitation[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < direction.length; i++) {
            stringBuilder.append(direction[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < speed.length; i++) {
            stringBuilder.append(speed[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < humidity.length; i++) {
            stringBuilder.append(humidity[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}

