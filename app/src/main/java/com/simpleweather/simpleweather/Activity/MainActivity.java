package com.simpleweather.simpleweather.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.simpleweather.simpleweather.R;
import com.simpleweather.simpleweather.Fragment.AboutFragment;
import com.simpleweather.simpleweather.Fragment.ForecastFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ForecastFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {

    private static int navItemIndex = 0;

    private static final String TAG_HOME = "forecast";
    private static final String TAG_CALENDAR = "calendar";
    private static final String TAG_SETTING = "setting";
    private static final String TAG_ABOUT_US = "about us";
    private static String CURRENT_TAG = TAG_HOME;
    private String[] activityTitles;

    private Handler mHandler;

    public static final String CURRENT_CITY = "com.simpleweather.simpleweather.CURRENT_CITY";
    public static final String DEFAULT_CITY_VALUE = "北京";
    public static final String CURRENT_CITY_KEY = "current_city";
    public SharedPreferences preferences;
    public SharedPreferences.Editor preferenceEditor;
    private String currentCity = null;
    public DrawerLayout drawer;
    public NavigationView navigationView;
    public static final boolean SHOULD_BACK_HOME_ON_BACK_PRESS = true;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceEditor = preferences.edit();

        currentCity = preferences.getString(CURRENT_CITY_KEY, DEFAULT_CITY_VALUE);

        mHandler = new Handler();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCity();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            load();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        // flag to load home fragment when user presses back key
        if (SHOULD_BACK_HOME_ON_BACK_PRESS) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                load();
                return;
            }

        }
        super.onBackPressed();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void load() {
        // selecting appropriate nav menu item
//        selectNavMenu();
        switch (navItemIndex) {
            case 0:
            case 4:
                loadFragment();
                break;
            case 1:
                Intent intentCalendar = new Intent(this, CalendarActivity.class);
                startActivity(intentCalendar);
                break;
            case 3:
                Intent intentSetting = new Intent(this, SettingsActivity.class);
                startActivity(intentSetting);
                break;
            default:
                break;
        }
        ;
        drawer.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void loadFragment() {
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        mHandler.post(mPendingRunnable);
        toggleFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_forecast:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_calendar:
                navItemIndex = 1;
                CURRENT_TAG = TAG_CALENDAR;
                break;
            case R.id.nav_setting:
                navItemIndex = 3;
                CURRENT_TAG = TAG_SETTING;
                break;
            case R.id.nav_about_us:
                navItemIndex = 4;
                CURRENT_TAG = TAG_ABOUT_US;
                break;
            default:
                navItemIndex = 0;
                break;
        }

        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        item.setChecked(true);

        load();
        return true;
    }

    private void toggleFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return ForecastFragment.newInstance(currentCity);
//            case 1:
//                return new CalendarFragment();
//            case 2:
//                return new DiaryFragment();
            case 4:
                return new AboutFragment();
            default:
                return new ForecastFragment();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void selectCity() {
        Intent intent = new Intent(this, CityActivity.class);
        intent.putExtra(CURRENT_CITY, currentCity);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("cur city: " + currentCity);
        currentCity = data.getStringExtra(CityActivity.RESPONSE_CITY);
        System.out.println("cur city: " + currentCity);

        switch (requestCode) {
            case 0:
                System.out.println("Got city: " + currentCity);

                ForecastFragment forecastFragment = (ForecastFragment) this.getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                forecastFragment.chooseCity(currentCity);
                preferenceEditor.putString(CURRENT_CITY_KEY, currentCity);
                preferenceEditor.commit();
                break;
            case 2:
                break;
            default:
                break;
        }
    }

}
