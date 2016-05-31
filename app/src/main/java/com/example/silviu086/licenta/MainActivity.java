package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TITLE_LOGIN_TAB = "LOGIN";
    private static final String TITLE_REGISTER_TAB = "REGISTER";
    private static RelativeLayout warningLayout;
    private static TextView warningTextView;
    private static ImageButton closeWarningButton;
    private static Context mContext;
    private static ViewPager sViewPager;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SlidingTabLayout slidingTab;
    private CharSequence [] tabTitle = {TITLE_LOGIN_TAB, TITLE_REGISTER_TAB};
    private int tabCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        warningLayout = (RelativeLayout)findViewById(R.id.warningLayout);
        warningTextView = (TextView)findViewById(R.id.textViewWarning);
        closeWarningButton = (ImageButton)findViewById(R.id.closeWarningButton);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabTitle, tabCount);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        sViewPager = viewPager;
        slidingTab = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setViewPager(viewPager);
        slidingTab.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabSelectedBarColor);
            }
        });
        closeWarningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static void setPage(int i){
        sViewPager.setCurrentItem(i);
    }

    public static void setWarning(String text, ColorsEnum culoare){
        warningLayout.setVisibility(View.VISIBLE);
        switch (culoare){
            case GREEN:
                warningTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                break;
            case RED:
                warningTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                break;
        }
        warningTextView.setText(text);
    }
}
