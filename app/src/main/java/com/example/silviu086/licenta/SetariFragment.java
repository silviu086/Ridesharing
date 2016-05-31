package com.example.silviu086.licenta;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetariFragment extends Fragment {

    private Account account;

    public SetariFragment() {
        // Required empty public constructor
    }

    public void setAccount(Account account){
        this.account = account;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setari, container, false);
    }

    public static SetariFragment newInstance(Account account){
        SetariFragment setariFragment = new SetariFragment();
        setariFragment.setAccount(account);
        return setariFragment;
    }

}
