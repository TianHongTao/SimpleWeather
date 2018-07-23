package com.simpleweather.simpleweather.activity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import com.simpleweather.simpleweather.R;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.city_activity_city_select);
        Intent intent = getIntent();
        String currentCity = intent.getStringExtra(MainActivity.CURRENT_CITY);


    }

}
