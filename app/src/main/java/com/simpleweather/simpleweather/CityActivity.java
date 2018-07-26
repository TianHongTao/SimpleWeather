package com.simpleweather.simpleweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;

public class CityActivity extends AppCompatActivity {
    public static final String RESPONSE_CITY_MESSAGE = "com.simpleweather.simpleweather.RESPONSE_CITY_MESSAGE";

    private static List<String> list = new ArrayList<String>();
    private static List<String> listTag = new ArrayList<String>();

    private void getData() {
        String city_name_list[] = CityActivity.this.getResources()
                .getStringArray(R.array.city_description_list);
        String city_list_tag[] = CityActivity.this.getResources()
                .getStringArray(R.array.city_group_list);

        String cityTag[] = {"热门", "A", "B", "C", "D", "E", "F", "G", "H", "J",
                "K", "L", "M", "N", "Q", "S", "T", "W", "X", "Y", "Z"};
        int listsize[] = {0, 18, 5, 5, 9, 7, 1, 3, 6, 13, 13, 5, 7, 5, 7, 7,
                9, 6, 10, 7, 11, 9};

        for (int j = 1; j < listsize.length; j++) {
            list.add(cityTag[j - 1]);
            listTag.add(cityTag[j - 1]);
            listsize[j] = listsize[j - 1] + listsize[j];
            list.addAll(Arrays.asList(city_name_list).subList(listsize[j - 1], listsize[j]));
        }
    }

    public class CityListAdapter extends ArrayAdapter<String> {

        private List<String> listTag = null;

        public CityListAdapter(Context context, List<String> objects, List<String> tags) {
            super(context, 0, objects);
            this.listTag = tags;
        }

        @Override
        public boolean isEnabled(int position) {
            if (listTag.contains(getItem(position))) {
                return false;
            }
            return super.isEnabled(position);
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (listTag.contains(getItem(position))) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.city_list_item_tag, null);
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.city_list_item, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.group_list_item_text);
            textView.setText(getItem(position));
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.city_activity_city_select);
        Intent intent = getIntent();
        String currentCity = intent.getStringExtra(MainActivity.CURRENT_CITY_MESSAGE);

        if (list.isEmpty()) {
            getData();
        }
        CityListAdapter adapter = new CityListAdapter(this, list, listTag);
        final ListView listView = findViewById(R.id.group_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra(RESPONSE_CITY_MESSAGE, CityActivity.list.get(i));
                CityActivity.this.setResult(0, intent);
                CityActivity.this.finish();
            }
        });
        listView.setAdapter(adapter);

    }
}
