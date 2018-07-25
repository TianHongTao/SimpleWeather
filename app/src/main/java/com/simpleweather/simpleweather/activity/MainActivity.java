package com.simpleweather.simpleweather.activity;

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
import com.simpleweather.simpleweather.Calendar.CalendarActivity;
import com.simpleweather.simpleweather.R;
import com.simpleweather.simpleweather.fragment.ForecastFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ForecastFragment.OnFragmentInteractionListener {

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "forecast";
    private static final String TAG_CALENDAR = "calendar";
    private static final String TAG_SETTING = "setting";
    private static final String TAG_ABOUT_US = "about us";

    public static String CURRENT_TAG = TAG_HOME;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    public static final String CURRENT_CITY = "com.simpleweather.simpleweather.CURRENT_CITY";
    public static final String CURRENT_CITY_KEY = "current_city";
    public SharedPreferences prefs;
    public SharedPreferences.Editor prefsEditor;
    private String currentCity = null;
    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = prefs.edit();

        currentCity = prefs.getString(CURRENT_CITY_KEY, "北京");

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            load();
        }
//        forecastFragment.ChooseCity(currentCity);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
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
            case 4:
                Intent intentAbout = new Intent(this, AboutUsActivity.class);
                startActivity(intentAbout);
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
        // set toolbar title
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            // show or hide the fab button
            toggleFab();
            return;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.post(mPendingRunnable);
        // show or hide the fab button
        toggleFab();
        //Closing drawer on item click
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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
        if (id == R.id.nav_forecast) {
            item.setChecked(true);
        } else {
            item.setChecked(false);
        }
//        item.setChecked(true);

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

            default:
                return new ForecastFragment();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void selectCity() {
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
                forecastFragment.ChooseCity(currentCity);
                prefsEditor.putString(CURRENT_CITY_KEY, currentCity);
                prefsEditor.commit();
                break;
            case 2:
                break;
            default:
                break;
        }
    }

}
