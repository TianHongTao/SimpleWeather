package com.simpleweather.simpleweather.fragment;

import android.Manifest;
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
import com.simpleweather.simpleweather.Weather_Class.DailyWeatherInfo;
import com.simpleweather.simpleweather.Weather_Class.HoulyWeatherInfo;
import com.simpleweather.simpleweather.Weather_Class.Weather;
import com.simpleweather.simpleweather.Weather_Class.WeatherInfo;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private LocationManager locationManager;
    private double latitude = 39.913542;
    private double longitude = 116.379;
    private TextView info1 = null;
    HorizontalScrollView horizontalScrollView=null;
    ImageView icon = null;
    ImageView background = null;
    View F_view = null;
    Weather WInfo = new Weather();

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     *
     *Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                        latitude = location.getLatitude(); // 经度
                        longitude = location.getLongitude(); // 纬度
                        new Thread(networkTask).start();
                    }
                }
            };
            //TODO GPS FIRST LOGIN WILL EXIT(1);
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                Log.d("str", "== in android 6.0, getting permission");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1000, locationListener);
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            new Thread(networkTask).start();
        } else {
            Log.e("str","获取失败");
        }
    }

    public void set_Dinfo(int i){
        DailyWeatherInfo DWInfo = WInfo.getDailyWeatherInfo(i);
        StringBuilder D_infos = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0.00");
        D_infos.append("日期"+DWInfo.getDate()+"\n");
        D_infos.append("降水"+String.valueOf(df.format(DWInfo.getPrecipitation()[1]*100))+"%\n");
        D_infos.append("天气 "+DWInfo.getSkycon()+"\n");
        D_infos.append("AQI"+String.valueOf(DWInfo.getAqi()[1])+"°\n");
        D_infos.append("高温"+String.valueOf(DWInfo.getTemperature()[0])+"°\n");
        D_infos.append("低温"+String.valueOf(DWInfo.getTemperature()[1])+"°\n");
        D_infos.append("日出"+String.valueOf(DWInfo.getSunsetTime()[0])+"°\n");
        D_infos.append("日落"+String.valueOf(DWInfo.getSunsetTime()[1])+"°\n");
        info1.setText(D_infos.toString());
        return;
    }

    public void choos_Icon(String skycon){
        switch (skycon)
        {
            case "晴天" :icon.setImageResource(R.drawable.ic_sun); ; break;
            case "晴夜" :icon.setImageResource(R.drawable.ic_sun_night) ; break;
            case "多云天" :icon.setImageResource(R.drawable.ic_partlycloudy) ; break;
            case "多云夜" :icon.setImageResource(R.drawable.ic_partlycloudy_night) ; break;
            case "阴" :icon.setImageResource(R.drawable.ic_cloudy) ; break;
            case "雨" :icon.setImageResource(R.drawable.ic_rain) ; break;
            case "雪" :icon.setImageResource(R.drawable.ic_snow) ; break;
            case "风" :icon.setImageResource(R.drawable.ic_wind) ; break;
            case "雾霾沙尘" :icon.setImageResource(R.drawable.ic_smog) ; break;
            default:icon.setImageResource(R.drawable.ic_none) ;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("city");
            Log.i("mylog", "请求结果为-->" + val);
            val = val.replace(":\""," ");

            // UI界面的更新等相关操作
            info1 = F_view.findViewById(R.id.location);
            info1.setText(val);
            info1 = F_view.findViewById(R.id.time);
            WeatherInfo now = WInfo.getNowWeatherInfo();
            info1.setText(WInfo.getHourlyWeatherInfo(0).getDatetime());
            info1 = F_view.findViewById(R.id.temperate);
            info1.setText(String.valueOf(now.getTemperature()));
            info1 = F_view.findViewById(R.id.description);
            info1.setText(WInfo.getHourlyWeatherInfo(0).getDescription());
            info1 = F_view.findViewById(R.id.today);
            set_Dinfo(0);
            info1 = F_view.findViewById(R.id.first_day);
            set_Dinfo(1);
            info1 = F_view.findViewById(R.id.second_day);
            set_Dinfo(2);
            info1 = F_view.findViewById(R.id.third_day);
            set_Dinfo(3);
            info1 = F_view.findViewById(R.id.fourth_day);
            set_Dinfo(4);
            DecimalFormat df = new DecimalFormat("0.00");
            info1 = F_view.findViewById(R.id.pm25);
            info1.setText(String.valueOf(now.getPm25()));
            info1 = F_view.findViewById(R.id.humidity);
            info1.setText(String.valueOf(df.format(now.getHumidity()*100))+"%");
            info1 = F_view.findViewById(R.id.intensity);
            info1.setText(String.valueOf(df.format(now.getIntensity_l()*100))+"%");
            info1 = F_view.findViewById(R.id.direction);
            info1.setText(String.valueOf(now.getDirection()));
            info1 = F_view.findViewById(R.id.speed);
            info1.setText(String.valueOf(now.getSpeed()));
            horizontalScrollView = F_view.findViewById(R.id.hourly_info);
            LinearLayout linearLayout = horizontalScrollView.findViewById(R.id.hourly_info_layout);
            for(int i=0;i<48;i++)
            {
                HoulyWeatherInfo HWInfo = WInfo.getHourlyWeatherInfo(i);
                StringBuilder D_infos = new StringBuilder();
                D_infos.append("日期\n"+HWInfo.getDatetime()+"\n");
                D_infos.append("降水"+String.valueOf(df.format(HWInfo.getPrecipitation()*100))+"%\n");
                D_infos.append("天气 "+HWInfo.getSkycon()+"\n");
                D_infos.append("AQI"+String.valueOf(HWInfo.getAqi())+"°\n");
                D_infos.append("云量"+String.valueOf(HWInfo.getCloudrate())+"°\n");
                D_infos.append("温度"+String.valueOf(HWInfo.getTemperature())+"°\n");
                D_infos.append("湿度"+String.valueOf(HWInfo.getHumidity())+"°\n");
                D_infos.append("风向"+String.valueOf(HWInfo.getDirection())+"°\n");
                D_infos.append("风速"+String.valueOf(HWInfo.getSpeed())+"°\n");
                TextView T = new TextView(getActivity());
                T.setText(D_infos.toString());
                T.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                T.setTextColor(Color.parseColor("#FDFDFD"));
                T.setGravity(Gravity.CENTER);
                linearLayout.addView(T);
            }
            icon = F_view.findViewById(R.id.icon);
            choos_Icon(now.getSkycon());
        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {
        private String getCity(String latitude,String longitude) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                // 定义获取文件内容的URL
                URL myURL = new URL("http://api.map.baidu.com/geocoder?output=json&location="
                        +latitude+","+longitude+"&ak=esNPFDwwsXWtsQfw4NMNmur1");
                // 打开URL链接
                HttpURLConnection ucon = (HttpURLConnection)myURL.openConnection();
                System.out.println("http://api.map.baidu.com/geocoder?output=json&location="
                        +latitude+","+longitude+"&ak=esNPFDwwsXWtsQfw4NMNmur1");
                int c = ucon.getResponseCode();
                // 使用InputStream，从URLConnection读取数据
                if(ucon.getResponseCode() == 200)
                {
                    InputStream is = ucon.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                    String temp = null;
                    while((temp = br.readLine())!=null)
                    {
                        if(temp.contains("city"))
                        {
                            temp = temp.substring(temp.indexOf(":\""),temp.lastIndexOf('"'));
                            stringBuilder.append(temp+" ");
                        }else if(temp.contains("district"))
                        {
                            temp = temp.substring(temp.indexOf(":\""),temp.lastIndexOf('"'));
                            stringBuilder.append(temp);
                            break;
                        }
                    }
                }
                else
                {
                    Log.e("NetWork","Get City NetWork ERRO！");
                }
                // 用ByteArrayBuffer缓存
            }catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }


        private void getWeather(String latitude,String longitude){
            try {
                String temp;
                URL url = new URL("https://api.caiyunapp.com/v2/gAC7WmaahV0uV8FW/"+longitude+","+latitude+"/realtime.jsonp");
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                temp = br.readLine();
                JSONObject jsonObject = new JSONObject(temp);
                String result = jsonObject.optString("result");
                jsonObject = new JSONObject(result);
                //温度 天气 pm25 云量 相对湿度 降水 风
                double temperature = jsonObject.optDouble("temperature");
                String skycon = jsonObject.optString("skycon");
                double pm25 = jsonObject.optDouble("pm25");
                double cloudrate = jsonObject.optDouble("cloudrate");
                double humidity = jsonObject.optDouble("humidity");
                //需要解析
                String precipitation = jsonObject.optString("precipitation");
                //需要解析
                String wind = jsonObject.getString("wind");
                jsonObject = new JSONObject(precipitation);
                //需要解析
                String nearest = jsonObject.optString("nearest");
                //需要解析
                String local = jsonObject.optString("local");

                //解析precipitation
                //解析nearest
                jsonObject = new JSONObject(nearest);
                double distance = jsonObject.optDouble("distance");
                double intensity_n = jsonObject.optDouble("intensity");
                //解析local
                jsonObject = new JSONObject(local);
                double intensity_l = jsonObject.optDouble("intensity");

                //解析wind
                jsonObject = new JSONObject(wind);
                double direction = jsonObject.optDouble("direction");
                double speed = jsonObject.optDouble("speed");

                WInfo.setNowWeatherInfo(temperature,skycon,pm25,cloudrate,humidity,distance,
                        intensity_n,intensity_l, direction,speed);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void getWeatherforecast(String latitude,String longitude){
            try {
                String temp;
                URL url = new URL("https://api.caiyunapp.com/v2/gAC7WmaahV0uV8FW/"+longitude+","+latitude+"/forecast.jsonp");
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                temp = br.readLine();
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

                HoulyWeatherInfo[] HWInfo = new HoulyWeatherInfo[48];
                for(int i=0; i<HWInfo.length; i++)
                {
                    String datetime = pm25.getJSONObject(i).optString("datetime");
                    double s_pm25 = pm25.getJSONObject(i).optDouble("value");
                    String s_skycon = skycon.getJSONObject(i).optString("value");
                    double s_cloudrate = cloudrate.getJSONObject(i).optDouble("value");
                    double s_aqi = aqi.getJSONObject(i).optDouble("value");
                    double s_humidity = humidity.getJSONObject(i).optDouble("value");
                    double s_precipitation = precipitation.getJSONObject(i).optDouble("value");
                    double s_temperature = temperature.getJSONObject(i).optDouble("value");
                    double s_direction = wind.getJSONObject(i).optDouble("direction");
                    double s_speed = wind.getJSONObject(i).optDouble("speed");
                    HWInfo[i] = new HoulyWeatherInfo(description,datetime,s_pm25,s_skycon,s_cloudrate,s_aqi
                            ,s_humidity,s_precipitation,s_direction,s_speed,s_temperature);
                }
                WInfo.setHourlyWeatherInfo(HWInfo);

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

                DailyWeatherInfo[] DWInfo = new DailyWeatherInfo[5];
                for (int i=0; i<DWInfo.length; i++)
                {
                    String[] sunsetTime = new String[2];
                    sunsetTime[0] = astro.getJSONObject(i).optJSONObject("sunset").optString("time");
                    sunsetTime[1] = astro.getJSONObject(i).optJSONObject("sunrise").optString("time");
                    String date = temperature.getJSONObject(i).optString("date");
                    double[] s_temperature = new double[3];
                    s_temperature[0] = temperature.getJSONObject(i).optDouble("max");
                    s_temperature[1] = temperature.getJSONObject(i).optDouble("avg");
                    s_temperature[2] = temperature.getJSONObject(i).optDouble("min");
                    double[] s_pm25 = new double[3];
                    s_pm25[0] = pm25.getJSONObject(i).optDouble("max");
                    s_pm25[1] = pm25.getJSONObject(i).optDouble("avg");
                    s_pm25[2] = pm25.getJSONObject(i).optDouble("min");
                    String s_skycon = skycon.getJSONObject(i).optString("value");
                    double[] s_cloudrate = new double[3];
                    s_cloudrate[0] = cloudrate.getJSONObject(i).optDouble("max");
                    s_cloudrate[1] = cloudrate.getJSONObject(i).optDouble("avg");
                    s_cloudrate[2] = cloudrate.getJSONObject(i).optDouble("min");
                    double[] s_aqi = new double[3];
                    s_aqi[0] = aqi.getJSONObject(i).optDouble("max");
                    s_aqi[1] = aqi.getJSONObject(i).optDouble("avg");
                    s_aqi[2] = aqi.getJSONObject(i).optDouble("min");
                    double[] s_precipition = new double[3];
                    s_precipition[0] = precipitation.getJSONObject(i).optDouble("max");
                    s_precipition[1] = precipitation.getJSONObject(i).optDouble("avg");
                    s_precipition[2] = precipitation.getJSONObject(i).optDouble("min");
                    double direction[] = new double[3];
                    direction[0] = wind.getJSONObject(i).optJSONObject("max").optDouble("direction");
                    direction[1] = wind.getJSONObject(i).optJSONObject("avg").optDouble("direction");
                    direction[2] = wind.getJSONObject(i).optJSONObject("min").optDouble("direction");
                    double speed[] = new double[3];
                    speed[0] = wind.getJSONObject(i).optJSONObject("max").optDouble("speed");
                    speed[1] = wind.getJSONObject(i).optJSONObject("avg").optDouble("speed");
                    speed[2] = wind.getJSONObject(i).optJSONObject("min").optDouble("speed");
                    double[] s_humidity = new double[3];
                    s_humidity[0] = humidity.getJSONObject(i).optDouble("max");
                    s_humidity[1] = humidity.getJSONObject(i).optDouble("avg");
                    s_humidity[2] = humidity.getJSONObject(i).optDouble("min");
                    DWInfo[i] = new DailyWeatherInfo(date, sunsetTime, s_temperature, s_pm25, s_skycon, s_cloudrate,
                            s_aqi, s_precipition, direction, speed, s_humidity);
                }
                WInfo.setDailyWeatherInfo(DWInfo);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            Message msg = new Message();
            Bundle data = new Bundle();
            try{
                data.putString("city", getCity(String.valueOf(latitude),String.valueOf(longitude)));
                Log.e("str","wait");
                getWeather(String.valueOf(latitude),String.valueOf(longitude));getWeatherforecast(String.valueOf(latitude),String.valueOf(longitude));
                msg.setData(data);
                handler.sendMessage(msg);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    };

    /*
           //开始更新位置信息
           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

           //获取当前位置信息
           String locationProvider = LocationManager.GPS_PROVIDER;
           Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

           //决定何时停止更新位置信息
           locationManager.removeUpdates(locationListener);

   */public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
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
        this.F_view = view;
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
