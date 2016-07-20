package com.example.silviu086.licenta;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetariFragment extends Fragment {

    private Account account;
    private TextView textViewEmail;
    private Switch aSwitchNotificari;
    private Button buttonDeconectare;

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
        View v = inflater.inflate(R.layout.fragment_setari, container, false);
        textViewEmail = (TextView) v.findViewById(R.id.textViewEmail);
        aSwitchNotificari = (Switch) v.findViewById(R.id.switchNotificari);
        buttonDeconectare = (Button) v.findViewById(R.id.buttonDeconectare);
        textViewEmail.setText(NavigationActivity.account.getEmail());
        aSwitchNotificari.setChecked(Setari.NOTIFICARI);
        aSwitchNotificari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setari.NOTIFICARI = isChecked;
            }
        });
        buttonDeconectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
                edit.remove("username");
                edit.remove("password");
                edit.remove("id");
                edit.apply();
                Setari.LOGAT = false;
                getActivity().finish();
            }
        });

        return v;
    }

    public static SetariFragment newInstance(Account account){
        SetariFragment setariFragment = new SetariFragment();
        setariFragment.setAccount(account);
        return setariFragment;
    }

}
