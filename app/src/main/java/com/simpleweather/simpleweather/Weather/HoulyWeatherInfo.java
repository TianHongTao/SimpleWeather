package com.simpleweather.simpleweather.Weather;

public class HoulyWeatherInfo {
    private String datetime; //时间
    private double pm25; //PM2.5
    /*
    skycon取值:

    */
    private String skycon; //天气状况
    private double cloudrate; //云量
    private double aqi; // AQI
    private double humidity;// 相对湿度
    private double precipitation;// 降水强度，单位 mm/h，0.05 ~ 0.9 小雨 0.9 ~ 2.87 中雨 >2.87大雨
    private double direction;// 风向
    private double speed;// 风速
    private double temperature; // 温度;
    private String description;

    public HoulyWeatherInfo() {
        this.datetime = "";
        this.description = "";
        this.pm25 = 0.0;
        this.skycon = "";
        this.cloudrate = 0.0;
        this.aqi = 0.0;
        this.humidity = 0.0;
        this.precipitation = 0.0;
        this.direction = 0.0;
        this.speed = 0.0;
        this.temperature = 0.0;
    }

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

    public HoulyWeatherInfo(String description, String datetime, double pm25, String skycon, double cloudrate, double aqi
            , double humidity, double precipitation, double direction, double speed, double temperature) {
        this.description = description;
        this.datetime = datetime;
        this.pm25 = pm25;
        this.skycon = get_skycon_CN(skycon);
        this.cloudrate = cloudrate;
        this.aqi = aqi;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.direction = direction;
        this.speed = speed;
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getCloudrate() {
        return cloudrate;
    }

    public double getPm25() {
        return pm25;
    }

    public String getSkycon() {
        return skycon;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getAqi() {
        return aqi;
    }

    public double getDirection() {
        return direction;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getSpeed() {
        return speed;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(description);
        stringBuilder.append(" ");
        stringBuilder.append(getDatetime());
        stringBuilder.append(" ");
        stringBuilder.append(getTemperature());
        stringBuilder.append(" ");
        stringBuilder.append(getSkycon());
        stringBuilder.append("\n");
        stringBuilder.append(getPm25());
        stringBuilder.append(" ");
        stringBuilder.append(getAqi());
        stringBuilder.append(" ");
        stringBuilder.append(getCloudrate());
        stringBuilder.append(" ");
        stringBuilder.append(getHumidity());
        stringBuilder.append("\n");
        stringBuilder.append(getPrecipitation());
        stringBuilder.append(" ");
        stringBuilder.append(getDirection());
        stringBuilder.append(" ");
        stringBuilder.append(getSpeed());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}