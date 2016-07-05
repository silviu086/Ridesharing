package com.example.silviu086.licenta;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalatoriiFragment extends Fragment {

    private static final String TITLE_LOGIN_ADAUGATE = "Adaugate";
    private static final String TITLE_IN_ASTEPTARE = "In asteptare";
    private static final String TITLE_CONFIRMATE = "Confirmate";
    //private static Context mContext;

    private ViewPager viewPager;
    private static ViewPager sViewPager;
    private ViewPagerAdapterCalatorii viewPagerAdapter;
    private SlidingTabLayoutCalatorii slidingTab;
    private CharSequence[] tabTitle = {TITLE_LOGIN_ADAUGATE, TITLE_IN_ASTEPTARE, TITLE_CONFIRMATE};
    private int tabCount = 3;
    private CalatoriiHolder calatorii;
    public static int pageIndex;

    public CalatoriiFragment() {
        // Required empty public constructor
    }

    public void setCalatorii(CalatoriiHolder calatorii) {
        this.calatorii = calatorii;
    }

    public void setIndexPage(int index) {
        pageIndex = index;
    }

    public static void setPage(){
        sViewPager.setCurrentItem(pageIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calatorii, container, false);
        viewPagerAdapter = new ViewPagerAdapterCalatorii(getActivity().getSupportFragmentManager(), tabTitle, tabCount);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        sViewPager = viewPager;
        slidingTab = (SlidingTabLayoutCalatorii) v.findViewById(R.id.tabs);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setViewPager(viewPager);
        slidingTab.setCustomTabColorizer(new SlidingTabLayoutCalatorii.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabSelectedBarColorCalatorii);
            }
        });
        slidingTab.scrollToTab(pageIndex, 1);
        return v;
    }

    public static CalatoriiFragment newInstance(CalatoriiHolder calatoriiHolder) {
        CalatoriiFragment calatoriiFragment = new CalatoriiFragment();
        calatoriiFragment.setCalatorii(calatoriiHolder);
        return calatoriiFragment;
    }
}
