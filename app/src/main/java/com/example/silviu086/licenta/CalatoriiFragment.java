package com.example.silviu086.licenta;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalatoriiFragment extends Fragment {

    private Account account;

    public CalatoriiFragment() {
        // Required empty public constructor
    }

    public void setAccount(Account account){
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calatorii, container, false);

        return v;
    }

    public static CalatoriiFragment newInstance(Account account){
        CalatoriiFragment calatoriiFragment = new CalatoriiFragment();
        calatoriiFragment.setAccount(account);
        return calatoriiFragment;
    }

}
