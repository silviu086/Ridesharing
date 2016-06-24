package com.example.silviu086.licenta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CalatoriiAdaugateFragment extends Fragment {
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
        listView = (ListView) v.findViewById(R.id.listView);
        CalatoriiAdaugateAdapter adapter = new CalatoriiAdaugateAdapter(getContext(), calatorii);
        listView.setAdapter(adapter);
        return v;
    }
}
