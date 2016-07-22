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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


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


        /*
        try {
            File file = new File(Setari.FILE_CONFIG);
            FileInputStream  input = new FileInputStream(file);
            Properties p = new Properties();
            p.load(input);
            input.close();
            String value = p.getProperty("NOTIFICARI");
            if(value.equals("true")){
                Setari.NOTIFICARI = true;
            }else{
                Setari.NOTIFICARI = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        textViewEmail = (TextView) v.findViewById(R.id.textViewEmail);
        aSwitchNotificari = (Switch) v.findViewById(R.id.switchNotificari);
        buttonDeconectare = (Button) v.findViewById(R.id.buttonDeconectare);
        textViewEmail.setText(NavigationActivity.account.getEmail());
        aSwitchNotificari.setChecked(Setari.NOTIFICARI);
        aSwitchNotificari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    File file = new File(getContext().getFilesDir(), Setari.FILE_CONFIG);
                    FileOutputStream  output = new FileOutputStream(file);
                    Properties p = new Properties();
                    if(isChecked){
                        p.setProperty("NOTIFICARI", "true");
                    }else{
                        p.setProperty("NOTIFICARI", "false");
                    }
                    p.store(output, null);
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                edit.remove("logat");
                edit.apply();
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
