package com.example.silviu086.licenta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CalatoriiAdaugateFragment extends Fragment {
    public static CalatoriiAdaugateAdapter adapter;
    private LinearLayout linearLayoutFaraCalatorii;
    private ListView listView;

    public CalatoriiAdaugateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calatorii_adaugate, container, false);
        List<CalatorieAdaugata> calatorii = NavigationActivity.calatoriiHolder.calatoriiAdaugate;
        linearLayoutFaraCalatorii = (LinearLayout) v.findViewById(R.id.linearLayoutFaraCalatorii);
        listView = (ListView) v.findViewById(R.id.listView);
        if(calatorii.size()>0){
            linearLayoutFaraCalatorii.setVisibility(View.GONE);
            adapter = new CalatoriiAdaugateAdapter(getContext(), calatorii);
            listView.setAdapter(adapter);
        }else{
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<CalatorieAdaugata> calatorii = NavigationActivity.calatoriiHolder.calatoriiAdaugate;
        if(calatorii.size()>0) {
            linearLayoutFaraCalatorii.setVisibility(View.GONE);
            CalatoriiAdaugateAdapter adapter = new CalatoriiAdaugateAdapter(getContext(), calatorii);
            listView.setAdapter(adapter);
        }
    }
}
