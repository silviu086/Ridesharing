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

public class CalatoriiConfirmateFragment extends Fragment {
    private ListView listView;

    public CalatoriiConfirmateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calatorii_confirmate, container, false);

        List<CalatorieConfirmata> calatorii = NavigationActivity.calatoriiHolder.calatoriiConfirmate;

        return v;
    }
}
