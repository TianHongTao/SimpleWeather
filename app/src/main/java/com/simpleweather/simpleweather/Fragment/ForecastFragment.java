package com.simpleweather.simpleweather.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simpleweather.simpleweather.R;
import com.simpleweather.simpleweather.Helper.Weather.DailyWeatherInfo;
import com.simpleweather.simpleweather.Helper.Weather.HoulyWeatherInfo;
import com.simpleweather.simpleweather.Helper.Weather.Weather;
import com.simpleweather.simpleweather.Helper.Weather.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForecastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {
    private static final String ARG_PARAM1 = "CURRENT_CITY";
    public static final int MIN_TIME = 5000;
    public static final int MIN_DISTANCE = 5000;
    public static final String HTTPS_API_GET_COORODINATE = "https://api.map.baidu.com/geocoder?address=%s&output=json&key=37492c0ee6f924cb5e934fa08c6b167";
    public static final String HTTPS_API_GET_CITY = "https://api.map.baidu.com/geocoder?output=json&location=%s,%s&ak=esNPFDwwsXWtsQfw4NMNmur1";
    public static final String HTTPS_API_GET_WEATHERINFO = "https://api.caiyunapp.com/v2/gAC7WmaahV0uV8FW/%s,%s/realtime.jsonp";
    public static final String HTTPS_API_GET_FORECAST_WEATHER = "https://api.caiyunapp.com/v2/gAC7WmaahV0uV8FW/%s,%s/forecast.jsonp";

    private OnFragmentInteractionListener mListener;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private LocationManager locationManager;
    private boolean isChooseCity = false;
    private String city = "";
    private double latitude = 39.913542;
    private double longitude = 116.379;
    private TextView info = null;
    HorizontalScrollView horizontalScrollView = null;
    ImageView icon = null;
    View fragmentView = null;
    Weather weatherInfo = new Weather();

    public void chooseCity(String city) {
        this.city = city;
        isChooseCity = true;
        new Thread(networkTask).start();
    }

    public ForecastFragment() {
        // Required empty public constructor
    }


    public static ForecastFragment newInstance(String city) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getString(ARG_PARAM1);
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationListener locationListener = new LocationListener() {
                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {
                    Log.e("str", " provider");
                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {
                    Log.e("str", provider);
                }

                // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null && !isChooseCity) {
                        Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                        latitude = location.getLatitude(); // 经度
                        longitude = location.getLongitude(); // 纬度
                        new Thread(networkTask).start();
                    }
                }
            };
            if (!city.equals("")) {
                chooseCity(city);

            } else {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("str", "== in android 6.0, getting permission");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
                String locationProvider = LocationManager.GPS_PROVIDER;
                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                new Thread(networkTask).start();
            }
        }
    }

    private void setDailyInfo(int i) {
        DailyWeatherInfo dailyWeatherInfo = weatherInfo.getDailyWeatherInfo(i);
        DecimalFormat df = new DecimalFormat("0.00");
        String D_infos = ("日期\n" + dailyWeatherInfo.getDate() + "\n") +
                "降水 " + String.valueOf(df.format(dailyWeatherInfo.getPrecipitation()[1] * 100)) + "%\n" +
                "天气 " + dailyWeatherInfo.getSkycon() + "\n" +
                "AQI " + String.valueOf(dailyWeatherInfo.getAqi()[1]) + "\n" +
                "高温 " + String.valueOf(dailyWeatherInfo.getTemperature()[0]) + "°\n" +
                "低温 " + String.valueOf(dailyWeatherInfo.getTemperature()[1]) + "°\n" +
                "日出 " + String.valueOf(dailyWeatherInfo.getSunsetTime()[0]) + "\n" +
                "日落 " + String.valueOf(dailyWeatherInfo.getSunsetTime()[1]) + "\n";
        info.setText(D_infos);
    }

    private void chooseIcon(String skycon) {
        switch (skycon) {
            case "晴天":
                icon.setImageResource(R.drawable.ic_sun);
                ;
                break;
            case "晴夜":
                icon.setImageResource(R.drawable.ic_sun_night);
                break;
            case "多云天":
                icon.setImageResource(R.drawable.ic_partlycloudy);
                break;
            case "多云夜":
                icon.setImageResource(R.drawable.ic_partlycloudy_night);
                break;
            case "阴":
                icon.setImageResource(R.drawable.ic_cloudy);
                break;
            case "雨":
                icon.setImageResource(R.drawable.ic_rain);
                break;
            case "雪":
                icon.setImageResource(R.drawable.ic_snow);
                break;
            case "风":
                icon.setImageResource(R.drawable.ic_wind);
                break;
            case "雾霾沙尘":
                icon.setImageResource(R.drawable.ic_smog);
                break;
            default:
                icon.setImageResource(R.drawable.ic_none);
        }
    }

    private void chooseBackgroud(String skycon) {
        switch (skycon) {
            case "晴天":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.clear_day);
                break;
            case "晴夜":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.clear_night);
                break;
            case "多云天":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.partly_cloudy);
                break;
            case "多云夜":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.partly_clear_night);
                break;
            case "阴":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.cloudy);
                break;
            case "雨":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.rain);
                break;
            case "雪":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.snow);
                break;
            case "风":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.wind);
                break;
            case "雾霾沙尘":
                fragmentView.findViewById(R.id.r_layout).setBackgroundResource(R.drawable.haze);
                break;
            default:
                ;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("city");
            Log.i("mylog", "请求结果为-->" + val);
            assert val != null;
            val = val.replace(":\"", " ");

            // UI界面的更新等相关操作
            info = fragmentView.findViewById(R.id.location);
            info.setText(val);
            info = fragmentView.findViewById(R.id.time);
            WeatherInfo now = weatherInfo.getNowWeatherInfo();
            info.setText(weatherInfo.getHourlyWeatherInfo(0).getDatetime());
            info = fragmentView.findViewById(R.id.temperate);
            info.setText(String.valueOf(now.getTemperature()));
            info = fragmentView.findViewById(R.id.description);
            info.setText(weatherInfo.getHourlyWeatherInfo(0).getDescription());
            info = fragmentView.findViewById(R.id.aqi);
            info.setText(String.valueOf(weatherInfo.getHourlyWeatherInfo(0).getAqi()));
            info = fragmentView.findViewById(R.id.sun_rise);
            info.setText(String.valueOf(weatherInfo.getDailyWeatherInfo(0).getSunsetTime()[0]));
            info = fragmentView.findViewById(R.id.sun_set);
            info.setText(String.valueOf(weatherInfo.getDailyWeatherInfo(0).getSunsetTime()[1]));
            info = fragmentView.findViewById(R.id.today);
            setDailyInfo(0);
            info = fragmentView.findViewById(R.id.first_day);
            setDailyInfo(1);
            info = fragmentView.findViewById(R.id.second_day);
            setDailyInfo(2);
            info = fragmentView.findViewById(R.id.third_day);
            setDailyInfo(3);
            info = fragmentView.findViewById(R.id.fourth_day);
            setDailyInfo(4);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            info = fragmentView.findViewById(R.id.pm25);
            info.setText(String.valueOf(now.getPm25()));
            info = fragmentView.findViewById(R.id.humidity);
            info.setText(String.valueOf(decimalFormat.format(now.getHumidity() * 100)) + "%");
            info = fragmentView.findViewById(R.id.intensity);
            info.setText(String.valueOf(decimalFormat.format(now.getIntensity_l() * 100)) + "%");
            info = fragmentView.findViewById(R.id.direction);
            info.setText(String.valueOf(now.getDirection()));
            info = fragmentView.findViewById(R.id.speed);
            info.setText(String.valueOf(now.getSpeed()));
            horizontalScrollView = fragmentView.findViewById(R.id.hourly_info);
            LinearLayout linearLayout = horizontalScrollView.findViewById(R.id.hourly_info_layout);
            for (int i = 0; i < 48; i++) {
                HoulyWeatherInfo hourlyWeatherInfo = weatherInfo.getHourlyWeatherInfo(i);
                StringBuilder infos = new StringBuilder();
                String string = hourlyWeatherInfo.getDatetime();
                infos.append("\n").append(string.substring(0, string.indexOf(" "))).append("\n");
                infos.append(string.substring(string.indexOf(" "))).append("\n");
                infos.append("降水 ").append(String.valueOf(decimalFormat.format(hourlyWeatherInfo.getPrecipitation() * 100))).append("%\n");
                infos.append("天气 ").append(hourlyWeatherInfo.getSkycon()).append("\n");
                infos.append("AQI ").append(String.valueOf(hourlyWeatherInfo.getAqi())).append("\n");
                infos.append("云量 ").append(String.valueOf(hourlyWeatherInfo.getCloudrate())).append("\n");
                infos.append("温度 ").append(String.valueOf(hourlyWeatherInfo.getTemperature())).append("°\n");
                infos.append("湿度 ").append(String.valueOf(hourlyWeatherInfo.getHumidity())).append("\n");
                infos.append("风向 ").append(String.valueOf(hourlyWeatherInfo.getDirection())).append("\n");
                infos.append("风速 ").append(String.valueOf(hourlyWeatherInfo.getSpeed())).append("\n");
                TextView textView = new TextView(linearLayout.getContext());
                textView.setText(infos.toString());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                textView.setTextColor(Color.parseColor("#FDFDFD"));
                textView.setGravity(Gravity.CENTER);
                linearLayout.addView(textView);
            }
            icon = fragmentView.findViewById(R.id.icon);
            chooseIcon(now.getSkycon());
            chooseBackgroud(now.getSkycon());

        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        private void getCoordinate(String city) {
            try {
                // 定义获取文件内容的URL
                URL myURL = new URL(String.format(HTTPS_API_GET_COORODINATE, city));
                // 打开URL链接
                HttpURLConnection httpURLConnection = (HttpURLConnection) myURL.openConnection();
                System.out.println(String.format(HTTPS_API_GET_COORODINATE, city));
                // 使用InputStream，从URLConnection读取数据
                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String temp = null;
                    while ((temp = bufferedReader.readLine()) != null) {
                        if (temp.contains("lng")) {
                            longitude = Double.valueOf(temp.substring(temp.indexOf(":") + 1, temp.length() - 2));
                        }
                        if (temp.contains("lat")) {
                            latitude = Double.valueOf(temp.substring(temp.indexOf(":") + 1, temp.length() - 2));
                        }
                    }
                } else {
                    Log.e("NetWork", "Get Coordinate NetWork ERRO！");
                }
                // 用ByteArrayBuffer缓存
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getCity(String latitude, String longitude) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                // 定义获取文件内容的URL
                URL myURL = new URL(String.format(HTTPS_API_GET_CITY, latitude, longitude));
                // 打开URL链接
                HttpURLConnection httpURLConnection = (HttpURLConnection) myURL.openConnection();
                System.out.println(String.format(HTTPS_API_GET_CITY, latitude, longitude));
                // 使用InputStream，从URLConnection读取数据
                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String temp = null;
                    while ((temp = bufferedReader.readLine()) != null) {
                        if (temp.contains("city")) {
                            temp = temp.substring(temp.indexOf(":\""), temp.lastIndexOf('"'));
                            stringBuilder.append(temp).append(" ");
                        } else if (temp.contains("district")) {
                            temp = temp.substring(temp.indexOf(":\""), temp.lastIndexOf('"'));
                            stringBuilder.append(temp);
                            break;
                        }
                    }
                } else {
                    Log.e("NetWork", "Get City NetWork ERRO！");
                }
                // 用ByteArrayBuffer缓存
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private void getWeather(String latitude, String longitude) {
            try {
                String temp;
                URL url = new URL(String.format(HTTPS_API_GET_WEATHERINFO, longitude, latitude));
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                temp = bufferedReader.readLine();
                JSONObject jsonObject = new JSONObject(temp);
                String result = jsonObject.optString("result");
                jsonObject = new JSONObject(result);
                double temperature = jsonObject.optDouble("temperature");
                String skycon = jsonObject.optString("skycon");
                double pm25 = jsonObject.optDouble("pm25");
                double cloudrate = jsonObject.optDouble("cloudrate");
                double humidity = jsonObject.optDouble("humidity");
                String precipitation = jsonObject.optString("precipitation");
                String wind = jsonObject.getString("wind");
                jsonObject = new JSONObject(precipitation);
                String nearest = jsonObject.optString("nearest");
                String local = jsonObject.optString("local");
                jsonObject = new JSONObject(nearest);
                double distance = jsonObject.optDouble("distance");
                double nearlyIntensity = jsonObject.optDouble("intensity");
                jsonObject = new JSONObject(local);
                double localIntensity = jsonObject.optDouble("intensity");
                jsonObject = new JSONObject(wind);
                double direction = jsonObject.optDouble("direction");
                double speed = jsonObject.optDouble("speed");
                weatherInfo.setNowWeatherInfo(temperature, skycon, pm25, cloudrate, humidity, distance,
                        nearlyIntensity, localIntensity, direction, speed);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        private void getWeatherForecast(String latitude, String longitude) {
            try {
                String temp;
                URL url = new URL(String.format(HTTPS_API_GET_FORECAST_WEATHER, longitude, latitude));
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                temp = bufferedReader.readLine();
                JSONObject jsonObject = new JSONObject(temp);
                String result = jsonObject.optString("result");
                jsonObject = new JSONObject(result);

                String hourly = jsonObject.optString("hourly");
                String daily = jsonObject.optString("daily");
                /*-------------------------hourly-----------------------------*/
                jsonObject = new JSONObject(hourly);
                String description = jsonObject.optString("description");
                JSONArray pm25 = new JSONArray(jsonObject.optString("pm25"));
                JSONArray skycon = new JSONArray(jsonObject.optString("skycon"));
                JSONArray cloudrate = new JSONArray(jsonObject.optString("cloudrate"));
                JSONArray aqi = new JSONArray(jsonObject.optString("aqi"));
                JSONArray humidity = new JSONArray(jsonObject.optString("humidity"));
                JSONArray precipitation = new JSONArray(jsonObject.optString("precipitation"));
                JSONArray wind = new JSONArray(jsonObject.optString("wind"));
                JSONArray temperature = new JSONArray(jsonObject.optString("temperature"));

                HoulyWeatherInfo[] hourlyWeatherInfos = new HoulyWeatherInfo[48];
                for (int i = 0; i < hourlyWeatherInfos.length; i++) {
                    String datetime = pm25.getJSONObject(i).optString("datetime");
                    double hourlyPm25 = pm25.getJSONObject(i).optDouble("value");
                    String hourlySkycon = skycon.getJSONObject(i).optString("value");
                    double hourlyCloudrate = cloudrate.getJSONObject(i).optDouble("value");
                    double hourlyAqi = aqi.getJSONObject(i).optDouble("value");
                    double hourlyHumidity = humidity.getJSONObject(i).optDouble("value");
                    double hourlyPrecipitation = precipitation.getJSONObject(i).optDouble("value");
                    double hourlyTemperature = temperature.getJSONObject(i).optDouble("value");
                    double hourlyDirection = wind.getJSONObject(i).optDouble("direction");
                    double hourlySpeed = wind.getJSONObject(i).optDouble("speed");
                    hourlyWeatherInfos[i] = new HoulyWeatherInfo(description, datetime, hourlyPm25, hourlySkycon, hourlyCloudrate, hourlyAqi
                            , hourlyHumidity, hourlyPrecipitation, hourlyDirection, hourlySpeed, hourlyTemperature);
                }
                weatherInfo.setHourlyWeatherInfo(hourlyWeatherInfos);

                /*-------------------------daily-----------------------------*/
                jsonObject = new JSONObject(daily);
                JSONArray astro = new JSONArray(jsonObject.optString("astro"));
                temperature = new JSONArray(jsonObject.optString("temperature"));
                pm25 = new JSONArray(jsonObject.optString("pm25"));
                skycon = new JSONArray(jsonObject.optString("skycon"));
                cloudrate = new JSONArray(jsonObject.optString("cloudrate"));
                aqi = new JSONArray(jsonObject.optString("aqi"));
                precipitation = new JSONArray(jsonObject.optString("precipitation"));
                wind = new JSONArray(jsonObject.optString("wind"));
                humidity = new JSONArray(jsonObject.optString("humidity"));

                DailyWeatherInfo[] dailyWeatherInfos = new DailyWeatherInfo[5];
                for (int i = 0; i < dailyWeatherInfos.length; i++) {
                    String[] dailySunTime = new String[2];
                    dailySunTime[0] = astro.getJSONObject(i).optJSONObject("sunset").optString("time");
                    dailySunTime[1] = astro.getJSONObject(i).optJSONObject("sunrise").optString("time");
                    String date = temperature.getJSONObject(i).optString("date");
                    double[] dailyTemperature = new double[3];
                    dailyTemperature[0] = temperature.getJSONObject(i).optDouble("max");
                    dailyTemperature[1] = temperature.getJSONObject(i).optDouble("avg");
                    dailyTemperature[2] = temperature.getJSONObject(i).optDouble("min");
                    double[] dailyPm25 = new double[3];
                    dailyPm25[0] = pm25.getJSONObject(i).optDouble("max");
                    dailyPm25[1] = pm25.getJSONObject(i).optDouble("avg");
                    dailyPm25[2] = pm25.getJSONObject(i).optDouble("min");
                    String dailySkycon = skycon.getJSONObject(i).optString("value");
                    double[] dailyCloudrate = new double[3];
                    dailyCloudrate[0] = cloudrate.getJSONObject(i).optDouble("max");
                    dailyCloudrate[1] = cloudrate.getJSONObject(i).optDouble("avg");
                    dailyCloudrate[2] = cloudrate.getJSONObject(i).optDouble("min");
                    double[] dailyAqi = new double[3];
                    dailyAqi[0] = aqi.getJSONObject(i).optDouble("max");
                    dailyAqi[1] = aqi.getJSONObject(i).optDouble("avg");
                    dailyAqi[2] = aqi.getJSONObject(i).optDouble("min");
                    double[] dailyPrecipition = new double[3];
                    dailyPrecipition[0] = precipitation.getJSONObject(i).optDouble("max");
                    dailyPrecipition[1] = precipitation.getJSONObject(i).optDouble("avg");
                    dailyPrecipition[2] = precipitation.getJSONObject(i).optDouble("min");
                    double dailyDirection[] = new double[3];
                    dailyDirection[0] = wind.getJSONObject(i).optJSONObject("max").optDouble("direction");
                    dailyDirection[1] = wind.getJSONObject(i).optJSONObject("avg").optDouble("direction");
                    dailyDirection[2] = wind.getJSONObject(i).optJSONObject("min").optDouble("direction");
                    double dailySpeed[] = new double[3];
                    dailySpeed[0] = wind.getJSONObject(i).optJSONObject("max").optDouble("speed");
                    dailySpeed[1] = wind.getJSONObject(i).optJSONObject("avg").optDouble("speed");
                    dailySpeed[2] = wind.getJSONObject(i).optJSONObject("min").optDouble("speed");
                    double[] dailyHumidity = new double[3];
                    dailyHumidity[0] = humidity.getJSONObject(i).optDouble("max");
                    dailyHumidity[1] = humidity.getJSONObject(i).optDouble("avg");
                    dailyHumidity[2] = humidity.getJSONObject(i).optDouble("min");
                    dailyWeatherInfos[i] = new DailyWeatherInfo(date, dailySunTime, dailyTemperature, dailyPm25, dailySkycon, dailyCloudrate,
                            dailyAqi, dailyPrecipition, dailyDirection, dailySpeed, dailyHumidity);
                }
                weatherInfo.setDailyWeatherInfo(dailyWeatherInfos);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            Message msg = new Message();
            Bundle data = new Bundle();
            try {
                if (!isChooseCity) {
                    data.putString("city", getCity(String.valueOf(latitude), String.valueOf(longitude)));
                    msg.setData(data);
                } else {
                    getCoordinate(city);
                    data.putString("city", city);
                    msg.setData(data);
                }
                Log.e("str", "wait");
                getWeather(String.valueOf(latitude), String.valueOf(longitude));
                getWeatherForecast(String.valueOf(latitude), String.valueOf(longitude));
                msg.setData(data);
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
            }
        }
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        this.fragmentView = view;
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
