package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TITLE_LOGIN_TAB = "LOGIN";
    private static final String TITLE_REGISTER_TAB = "REGISTER";
    public static SharedPreferences sharedPreferences;
    private static Context mContext;
    private static ViewPager sViewPager;
    public static boolean exit = false;
    public static View parentView;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SlidingTabLayout slidingTab;
    private LinearLayout linearLayoutAutoLogin;
    private ImageView imageViewProfil;
    private TextView textViewLogin;
    private ProgressBar progressBar;
    private ImageButton imageButtonReload;
    private LinearLayout linearLayoutLogin;
    private CharSequence [] tabTitle = {TITLE_LOGIN_TAB, TITLE_REGISTER_TAB};
    private int tabCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
                */
        setContentView(R.layout.activity_main);

        linearLayoutLogin = (LinearLayout) findViewById(R.id.linearLayoutLogin);
        linearLayoutAutoLogin = (LinearLayout) findViewById(R.id.linearLayoutAutoLogin);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageButtonReload = (ImageButton) findViewById(R.id.imageButtonReload);
        parentView = findViewById(R.id.parent_view);
        if(Internet.haveInternet(this)){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if(sharedPreferences != null){
                if(sharedPreferences.getString("username", null) != null){
                    linearLayoutAutoLogin.setVisibility(View.VISIBLE);
                    File mypath=new File(MainActivity.this.getFilesDir(),"photo_" + String.valueOf(sharedPreferences.getInt("id", 0)) + ".png");
                    if (mypath.exists()) {
                        Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
                        imageViewProfil.setImageDrawable(photo);
                    }else{
                        imageViewProfil.setImageDrawable(getResources().getDrawable(R.drawable.login_account));
                    }
                }
            }
        }else{
            linearLayoutAutoLogin.setVisibility(View.VISIBLE);
            linearLayoutLogin.setVisibility(View.GONE);
            textViewLogin.setText("Logare esuata!");
            progressBar.setVisibility(View.GONE);
            imageButtonReload.setVisibility(View.VISIBLE);
            imageButtonReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Internet.haveInternet(MainActivity.this)) {
                        finish();
                        startActivity(getIntent());
                    }
                }
            });
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(this, R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Nu exista conexiune la Internet!");
            Snackbar.make(parentView, builder, Snackbar.LENGTH_INDEFINITE).show();
        }
        mContext = this;
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabTitle, tabCount);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        sViewPager = viewPager;
        slidingTab = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setViewPager(viewPager);;
        slidingTab.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabSelectedBarColor);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(exit){
            exit = false;
            finish();
        }else{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if(sharedPreferences != null){
                if(sharedPreferences.getString("username", null) == null){
                    linearLayoutAutoLogin.setVisibility(View.GONE);
                    linearLayoutLogin.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static void setPage(int i){
        sViewPager.setCurrentItem(i);
    }
}
