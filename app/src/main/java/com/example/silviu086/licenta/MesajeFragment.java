package com.example.silviu086.licenta;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MesajeFragment extends Fragment {

    private Account account;

    public MesajeFragment() {
        // Required empty public constructor
    }

    public void setAccount(Account account){
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mesaje, container, false);
    }

    public static MesajeFragment newInstance(Account account){
        MesajeFragment mesajeFragment = new MesajeFragment();
        mesajeFragment.setAccount(account);
        return mesajeFragment;
    }

}
