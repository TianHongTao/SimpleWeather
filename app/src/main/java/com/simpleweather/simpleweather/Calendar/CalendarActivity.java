package com.simpleweather.simpleweather.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import com.simpleweather.simpleweather.R;

public class CalendarActivity extends Activity implements OnClickListener, CalendarCard.OnCellClickListener {
    private ViewPager viewPager;
    private int currentIndex = 498;
    private CalendarViewAdapter<CalendarCard> adapter;
    private SildeDirection mDirection = SildeDirection.NO_SILDE;
    private TextView monthText;

    enum SildeDirection {
        RIGHT, LEFT, NO_SILDE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calendar);

        viewPager = (ViewPager) this.findViewById(R.id.vp_calendar);
        ImageButton homeImgBtn = (ImageButton) this.findViewById(R.id.home);
        ImageButton preImgBtn = (ImageButton) this.findViewById(R.id.btnPreMonth);
        ImageButton nextImgBtn = (ImageButton) this.findViewById(R.id.btnNextMonth);
        monthText = (TextView) this.findViewById(R.id.tvCurrentMonth);
        preImgBtn.setOnClickListener(this);
        nextImgBtn.setOnClickListener(this);
        homeImgBtn.setOnClickListener(this);

        CalendarCard[] views = new CalendarCard[3];
        for (int i = 0; i < 3; i++) {
            views[i] = new CalendarCard(this, this);
        }
        adapter = new CalendarViewAdapter<>(views);
        setViewPager();

    }

    private void setViewPager() {
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(498);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                finish();
                break;
            case R.id.btnPreMonth:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                break;
            case R.id.btnNextMonth:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                break;
            default:
                break;
        }
    }

    @Override
    public void clickDate(CustomDate date) {
    }

    @Override
    public void changeDate(CustomDate date) {
        monthText.setText(date.month + "月");
    }

    private void measureDirection(int arg0) {

        if (arg0 > currentIndex) {
            mDirection = SildeDirection.RIGHT;

        } else if (arg0 < currentIndex) {
            mDirection = SildeDirection.LEFT;
        }
        currentIndex = arg0;
    }

    // 更新日历视图
    private void updateCalendarView(int arg0) {
        CalendarCard[] showViews = adapter.getAllItems();
        if (mDirection == SildeDirection.RIGHT) {
            showViews[arg0 % showViews.length].rightSlide();
        } else if (mDirection == SildeDirection.LEFT) {
            showViews[arg0 % showViews.length].leftSlide();
        }
        mDirection = SildeDirection.NO_SILDE;
    }


}
