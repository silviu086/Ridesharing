package com.example.silviu086.licenta;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalatoriiFragment extends Fragment {

    private static final String TITLE_LOGIN_ADAUGATE = "Adaugate";
    private static final String TITLE_IN_ASTEPTARE = "In asteptare";
    private static final String TITLE_CONFIRMATE = "Confirmate";
    public static int TIP_AFISARE = 1;
    public static List<Integer> calatoriiTitleAdaugateData;
    public static List<Integer> calatoriiTitleAdaugateLocatii;
    public static List<Integer> calatoriiTitleInAsteptareData;
    public static List<Integer> calatoriiTitleInAsteptareLocatii;
    public static List<Integer> calatoriiTitleConfirmateData;
    public static List<Integer> calatoriiTitleConfirmateLocatii;
    public static CalatoriiHolder CALATORII;
    //private static Context mContext;

    private ViewPager viewPager;
    private static ViewPager sViewPager;
    private ViewPagerAdapterCalatorii viewPagerAdapter;
    private SlidingTabLayoutCalatorii slidingTab;
    private RadioGroup radioGroupAfisare;
    private android.support.v7.widget.AppCompatRadioButton radioButtonLocatii;
    private android.support.v7.widget.AppCompatRadioButton radioButtonData;
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

    /*
    public static void resetElementeSortare(){
        if(TIP_AFISARE == 1){
            if(CALATORII.calatoriiAdaugate.size() > 0){
                ELEMENT_SOARTARE_ADAUGARE = CALATORII.calatoriiAdaugate.get(0).getPunctPlecare() +
                        CALATORII.calatoriiAdaugate.get(0).getPunctSosire();
            }
            if(CALATORII.calatoriiInAsteptare.size() > 0){
                ELEMENT_SOARTARE_IN_ASTEPTARE = CALATORII.calatoriiInAsteptare.get(0).getPunctPlecare() +
                        CALATORII.calatoriiInAsteptare.get(0).getPunctSosire();
            }
            if(CALATORII.calatoriiConfirmate.size() > 0){
                ELEMENT_SOARTARE_CONFIRMATE = CALATORII.calatoriiConfirmate.get(0).getPunctPlecare() +
                        CALATORII.calatoriiConfirmate.get(0).getPunctSosire();
            }
        }
        if(TIP_AFISARE == 2){
            if(CALATORII.calatoriiAdaugate.size() > 0){
                ELEMENT_SOARTARE_ADAUGARE = CALATORII.calatoriiAdaugate.get(0).getDataPlecare();
            }
            if(CALATORII.calatoriiInAsteptare.size() > 0){
                ELEMENT_SOARTARE_IN_ASTEPTARE = CALATORII.calatoriiInAsteptare.get(0).getDataPlecare();
            }
            if(CALATORII.calatoriiConfirmate.size() > 0){
                ELEMENT_SOARTARE_CONFIRMATE = CALATORII.calatoriiConfirmate.get(0).getDataPlecare();
            }
        }
    }
    */

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
        radioGroupAfisare = (RadioGroup) v.findViewById(R.id.radioGroupAfisare);
        radioButtonLocatii = (android.support.v7.widget.AppCompatRadioButton) radioGroupAfisare.findViewById(R.id.radioButtonLocatii);
        radioButtonData = (android.support.v7.widget.AppCompatRadioButton) radioGroupAfisare.findViewById(R.id.radioButtonData);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{

                        getResources().getColor(R.color.colorSilver)
                        , getResources().getColor(R.color.colorSilver),
                }
        );
        radioButtonLocatii.setSupportButtonTintList(colorStateList);
        radioButtonData.setSupportButtonTintList(colorStateList);

        calatoriiTitleAdaugateData = new ArrayList<>();
        calatoriiTitleAdaugateLocatii = new ArrayList<>();
        calatoriiTitleInAsteptareData = new ArrayList<>();
        calatoriiTitleInAsteptareLocatii = new ArrayList<>();
        calatoriiTitleConfirmateData = new ArrayList<>();
        calatoriiTitleConfirmateLocatii = new ArrayList<>();

        if(TIP_AFISARE == 1){
            radioButtonLocatii.setChecked(true);
            TIP_AFISARE = 1;

            List<CalatorieAdaugata> listaCalatoriiAdaugate = NavigationActivity.calatoriiHolder.calatoriiAdaugate;
            if(listaCalatoriiAdaugate.size()>0){
                Collections.sort(listaCalatoriiAdaugate, new Comparator<CalatorieAdaugata>() {
                    @Override
                    public int compare(CalatorieAdaugata lhs, CalatorieAdaugata rhs) {
                        if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) < 0) {
                            return -1;
                        } else if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                calatoriiTitleAdaugateLocatii.add(listaCalatoriiAdaugate.get(0).getId());
                String locatii = listaCalatoriiAdaugate.get(0).getPunctPlecare() + listaCalatoriiAdaugate.get(0).getPunctSosire();
                for(int i=1;i<listaCalatoriiAdaugate.size();i++){
                    if(!locatii.equals((listaCalatoriiAdaugate.get(i).getPunctPlecare() + listaCalatoriiAdaugate.get(i).getPunctSosire()))){
                        calatoriiTitleAdaugateLocatii.add(listaCalatoriiAdaugate.get(i).getId());
                        locatii = listaCalatoriiAdaugate.get(i).getPunctPlecare() + listaCalatoriiAdaugate.get(i).getPunctSosire();
                    }
                }
            }

            List<CalatorieInAsteptare> listaCalatoriiInAsteptare = NavigationActivity.calatoriiHolder.calatoriiInAsteptare;
            if(listaCalatoriiInAsteptare.size()>0){
                Collections.sort(listaCalatoriiInAsteptare, new Comparator<CalatorieInAsteptare>() {
                    @Override
                    public int compare(CalatorieInAsteptare lhs, CalatorieInAsteptare rhs) {
                        if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) < 0) {
                            return -1;
                        } else if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                calatoriiTitleInAsteptareLocatii.add(listaCalatoriiInAsteptare.get(0).getId());
                String locatii = listaCalatoriiInAsteptare.get(0).getPunctPlecare() + listaCalatoriiInAsteptare.get(0).getPunctSosire();
                for(int i=1;i<listaCalatoriiInAsteptare.size();i++){
                    if(!locatii.equals((listaCalatoriiInAsteptare.get(i).getPunctPlecare() + listaCalatoriiInAsteptare.get(i).getPunctSosire()))){
                        calatoriiTitleInAsteptareLocatii.add(listaCalatoriiInAsteptare.get(i).getId());
                        locatii = listaCalatoriiInAsteptare.get(i).getPunctPlecare() + listaCalatoriiInAsteptare.get(i).getPunctSosire();
                    }
                }
            }

            List<CalatorieConfirmata> listaCalatoriiConfirmate = NavigationActivity.calatoriiHolder.calatoriiConfirmate;
            if(listaCalatoriiConfirmate.size()>0){
                Collections.sort(listaCalatoriiConfirmate, new Comparator<CalatorieConfirmata>() {
                    @Override
                    public int compare(CalatorieConfirmata lhs, CalatorieConfirmata rhs) {
                        if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) < 0) {
                            return -1;
                        } else if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                calatoriiTitleConfirmateLocatii.add(listaCalatoriiConfirmate.get(0).getId());
                String locatii = listaCalatoriiConfirmate.get(0).getPunctPlecare() + listaCalatoriiConfirmate.get(0).getPunctSosire();
                for(int i=1;i<listaCalatoriiConfirmate.size();i++){
                    if(!locatii.equals((listaCalatoriiConfirmate.get(i).getPunctPlecare() + listaCalatoriiConfirmate.get(i).getPunctSosire()))){
                        calatoriiTitleConfirmateLocatii.add(listaCalatoriiConfirmate.get(i).getId());
                        locatii = listaCalatoriiConfirmate.get(i).getPunctPlecare() + listaCalatoriiConfirmate.get(i).getPunctSosire();
                    }
                }
            }
        } else if(TIP_AFISARE == 2){
            radioButtonData.setChecked(true);
            TIP_AFISARE = 2;

            List<CalatorieAdaugata> listaCalatoriiAdaugate = NavigationActivity.calatoriiHolder.calatoriiAdaugate;
            if(listaCalatoriiAdaugate.size()>0){
                Collections.sort(listaCalatoriiAdaugate, new Comparator<CalatorieAdaugata>() {
                    @Override
                    public int compare(CalatorieAdaugata lhs, CalatorieAdaugata rhs) {
                        if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) < 0) {
                            return -1;
                        } else if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                calatoriiTitleAdaugateData.add(listaCalatoriiAdaugate.get(0).getId());
                String data = listaCalatoriiAdaugate.get(0).getDataPlecare();
                for(int i=1;i<listaCalatoriiAdaugate.size();i++){
                    if(!data.equals(listaCalatoriiAdaugate.get(i).getDataPlecare())){
                        calatoriiTitleAdaugateData.add(listaCalatoriiAdaugate.get(i).getId());
                        data = listaCalatoriiAdaugate.get(i).getDataPlecare();
                    }
                }
            }

            List<CalatorieInAsteptare> listaCalatoriiInAsteptare = NavigationActivity.calatoriiHolder.calatoriiInAsteptare;
            if(listaCalatoriiInAsteptare.size()>0){
                Collections.sort(listaCalatoriiInAsteptare, new Comparator<CalatorieInAsteptare>() {
                    @Override
                    public int compare(CalatorieInAsteptare lhs, CalatorieInAsteptare rhs) {
                        if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) < 0) {
                            return -1;
                        } else if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                calatoriiTitleInAsteptareData.add(listaCalatoriiInAsteptare.get(0).getId());
                String data = listaCalatoriiInAsteptare.get(0).getDataPlecare();
                for(int i=1;i<listaCalatoriiInAsteptare.size();i++){
                    if(!data.equals(listaCalatoriiInAsteptare.get(i).getDataPlecare())){
                        calatoriiTitleInAsteptareData.add(listaCalatoriiInAsteptare.get(i).getId());
                        data = listaCalatoriiInAsteptare.get(i).getDataPlecare();
                    }
                }
            }

            List<CalatorieConfirmata> listaCalatoriiConfirmate = NavigationActivity.calatoriiHolder.calatoriiConfirmate;
            if(listaCalatoriiConfirmate.size()>0){
                Collections.sort(listaCalatoriiConfirmate, new Comparator<CalatorieConfirmata>() {
                    @Override
                    public int compare(CalatorieConfirmata lhs, CalatorieConfirmata rhs) {
                        if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) < 0) {
                            return -1;
                        } else if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) > 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                calatoriiTitleConfirmateData.add(listaCalatoriiConfirmate.get(0).getId());
                String data = listaCalatoriiConfirmate.get(0).getDataPlecare();
                for(int i=1;i<listaCalatoriiConfirmate.size();i++){
                    if(!data.equals(listaCalatoriiConfirmate.get(i).getDataPlecare())){
                        calatoriiTitleConfirmateData.add(listaCalatoriiConfirmate.get(i).getId());
                        data = listaCalatoriiConfirmate.get(i).getDataPlecare();
                    }
                }
            }
        }

        radioButtonLocatii.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    TIP_AFISARE = 1;
                    List<CalatorieAdaugata> listaCalatoriiAdaugate = NavigationActivity.calatoriiHolder.calatoriiAdaugate;
                    if (listaCalatoriiAdaugate.size() > 0) {
                        Collections.sort(listaCalatoriiAdaugate, new Comparator<CalatorieAdaugata>() {
                            @Override
                            public int compare(CalatorieAdaugata lhs, CalatorieAdaugata rhs) {
                                if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) < 0) {
                                    return -1;
                                } else if ((lhs.getPunctPlecare()+ lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) > 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        NavigationActivity.setFragmentCalatoriiSortare(viewPager.getCurrentItem());
                    }

                    List<CalatorieInAsteptare> listaCalatoriiInAsteptare = NavigationActivity.calatoriiHolder.calatoriiInAsteptare;
                    if (listaCalatoriiInAsteptare.size() > 0) {
                        Collections.sort(listaCalatoriiInAsteptare, new Comparator<CalatorieInAsteptare>() {
                            @Override
                            public int compare(CalatorieInAsteptare lhs, CalatorieInAsteptare rhs) {
                                if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) < 0) {
                                    return -1;
                                } else if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) > 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        NavigationActivity.setFragmentCalatoriiSortare(viewPager.getCurrentItem());
                    }

                    List<CalatorieConfirmata> listaCalatoriiConfirmate = NavigationActivity.calatoriiHolder.calatoriiConfirmate;
                    if (listaCalatoriiConfirmate.size() > 0) {
                        Collections.sort(listaCalatoriiConfirmate, new Comparator<CalatorieConfirmata>() {
                            @Override
                            public int compare(CalatorieConfirmata lhs, CalatorieConfirmata rhs) {
                                if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) < 0) {
                                    return -1;
                                } else if ((lhs.getPunctPlecare() + lhs.getPunctSosire()).compareTo((rhs.getPunctPlecare() + rhs.getPunctSosire())) > 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        NavigationActivity.setFragmentCalatoriiSortare(viewPager.getCurrentItem());
                    }
                }
            }});

        radioButtonData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    TIP_AFISARE = 2;
                    List<CalatorieAdaugata> listaCalatoriiAdaugate = NavigationActivity.calatoriiHolder.calatoriiAdaugate;
                    if(listaCalatoriiAdaugate.size()>0){
                        Collections.sort(listaCalatoriiAdaugate, new Comparator<CalatorieAdaugata>() {
                            @Override
                            public int compare(CalatorieAdaugata lhs, CalatorieAdaugata rhs){
                                if(lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) < 0){
                                    return -1;
                                } else if(lhs.getDataPlecare().compareTo(rhs.getDataPlecare())>0){
                                    return 1;
                                }else{
                                    return 0;
                                }
                            }
                        });
                        NavigationActivity.setFragmentCalatoriiSortare(viewPager.getCurrentItem());
                    }

                    List<CalatorieInAsteptare> listaCalatoriiInAsteptare = NavigationActivity.calatoriiHolder.calatoriiInAsteptare;
                    if(listaCalatoriiInAsteptare.size()>0){
                        Collections.sort(listaCalatoriiInAsteptare, new Comparator<CalatorieInAsteptare>() {
                            @Override
                            public int compare(CalatorieInAsteptare lhs, CalatorieInAsteptare rhs){
                                if(lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) < 0){
                                    return -1;
                                } else if(lhs.getDataPlecare().compareTo(rhs.getDataPlecare())>0){
                                    return 1;
                                }else{
                                    return 0;
                                }
                            }
                        });
                        NavigationActivity.setFragmentCalatoriiSortare(viewPager.getCurrentItem());
                    }

                    List<CalatorieConfirmata> listaCalatoriiConfirmate = NavigationActivity.calatoriiHolder.calatoriiConfirmate;
                    if(listaCalatoriiConfirmate.size()>0){
                        Collections.sort(listaCalatoriiConfirmate, new Comparator<CalatorieConfirmata>() {
                            @Override
                            public int compare(CalatorieConfirmata lhs, CalatorieConfirmata rhs) {
                                if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) < 0) {
                                    return -1;
                                } else if (lhs.getDataPlecare().compareTo(rhs.getDataPlecare()) > 0) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        NavigationActivity.setFragmentCalatoriiSortare(viewPager.getCurrentItem());
                    }
                }
            }
        });
        return v;
    }

    public static CalatoriiFragment newInstance(CalatoriiHolder calatoriiHolder) {
        CalatoriiFragment calatoriiFragment = new CalatoriiFragment();
        calatoriiFragment.setCalatorii(calatoriiHolder);
        CALATORII = calatoriiHolder;
        return calatoriiFragment;
    }
}
