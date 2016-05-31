package com.example.silviu086.licenta;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Silviu086 on 28.02.2016.
 */
public class LoginTabFragment extends Fragment implements TaskCompleted {


    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkboxRemember;
    private Button buttonLogin;
    private Button buttonLoginSkip;
    private ProgressBar progress;
    private Account account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_tab, container, false);

        //PRELUARE CONTROALE
        editTextEmail = (EditText) v.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) v.findViewById(R.id.editTextPassword);
        checkboxRemember = (CheckBox) v.findViewById(R.id.checkBoxRemember);
        buttonLogin = (Button) v.findViewById(R.id.buttonLogin);
        buttonLoginSkip = (Button) v.findViewById(R.id.buttonLoginSkip);
        progress = (ProgressBar) v.findViewById(R.id.progressBar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaCampuri()) {
                    if(Internet.haveInternet(getContext())) {
                        account = new AccountBuilder()
                                .setEmail(editTextEmail.getText().toString())
                                .setParola(editTextPassword.getText().toString())
                                .build();
                        LoginTask login = new LoginTask(getContext(), buttonLogin, progress, account, LoginTabFragment.this);
                        login.execute();
                    }else{
                        MainActivity.setWarning("Conexiune la internet lipsa!", ColorsEnum.RED);
                    }
                }
            }
        });

        buttonLoginSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), NavigationActivity2.class);
                startActivity(it);
            }
        });
        return v;
    }

    @Override
    public void onTaskCompleted(String result) {
        if (!result.equals("failed")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONObject jsonAccount = json.getJSONObject("account");
                Account account = new AccountBuilder()
                        .setId(Integer.valueOf(jsonAccount.getString("id")))
                        .setEmail(jsonAccount.getString("email"))
                        .setParola(jsonAccount.getString("parola"))
                        .setDateCreated(jsonAccount.getString("date_created"))
                        .setNume(jsonAccount.getString("nume"))
                        .setTelefon(jsonAccount.getString("telefon"))
                        .setVarsta(Integer.valueOf(jsonAccount.getString("varsta")))
                        .setMarcaMasina(jsonAccount.getString("marca_masina").equals("null") ? "" : jsonAccount.getString("marca_masina"))
                        .setModelMasina(jsonAccount.getString("model_masina").equals("null") ? "" : jsonAccount.getString("model_masina"))
                        .setAnFabricatie(Integer.valueOf(jsonAccount.getString("an_fabricatie").equals("null")? "0" : jsonAccount.getString("an_fabricatie")))
                        .setExperientaAuto(jsonAccount.getString("experienta_auto").equals("null")? "" : jsonAccount.getString("experienta_auto"))
                        .build();
                Intent it = new Intent(getActivity(), NavigationActivity.class);
                it.putExtra("account", account);
                startActivity(it);
            } catch (Exception ex) {
                Log.e("JSONException", ex.toString());
            }
        }
    }

    public Boolean verificaCampuri(){

        if (editTextEmail.getText().toString().equals("")) {
            editTextEmail.setError("Trebuie sa completezi acest camp!");
            MainActivity.setWarning("Trebuie sa introduceti emailul!", ColorsEnum.RED);
            return false;
        }
        if (editTextPassword.getText().toString().equals("")) {
            editTextPassword.setError("Trebuie sa completezi acest camp!");
            MainActivity.setWarning("Trebuie sa introduceti parola!", ColorsEnum.RED);
            return false;
        }

        return true;
    }
}
