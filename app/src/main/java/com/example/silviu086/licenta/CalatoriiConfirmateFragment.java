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

public class CalatoriiConfirmateFragment extends Fragment {
    private ListView listView;
    private LinearLayout linearLayoutFaraCalatorii;

    public CalatoriiConfirmateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calatorii_confirmate, container, false);
        List<CalatorieConfirmata> calatorii = NavigationActivity.calatoriiHolder.calatoriiConfirmate;
        listView = (ListView) v.findViewById(R.id.listView);
        linearLayoutFaraCalatorii = (LinearLayout) v.findViewById(R.id.linearLayoutFaraCalatorii);
        if(calatorii.size()>0){
            linearLayoutFaraCalatorii.setVisibility(View.GONE);
            CalatoriiConfirmateAdapter adapter = new CalatoriiConfirmateAdapter(getContext(), calatorii);
            listView.setAdapter(adapter);
        }
        return v;
    }
}
