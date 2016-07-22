package com.example.silviu086.licenta;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

/**
 * Created by Silviu086 on 28.02.2016.
 */
public class LoginTabFragment extends Fragment implements TaskCompleted {

    View v;
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
        v = inflater.inflate(R.layout.login_tab, container, false);

        //PRELUARE CONTROALE
        editTextEmail = (EditText) v.findViewById(R.id.editTextEmailLogin);
        editTextPassword = (EditText) v.findViewById(R.id.editTextPasswordLogin);
        checkboxRemember = (CheckBox) v.findViewById(R.id.checkBoxRemember);
        buttonLogin = (Button) v.findViewById(R.id.buttonLogin);
        buttonLoginSkip = (Button) v.findViewById(R.id.buttonLoginSkip);
        progress = (ProgressBar) v.findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
        if(sharedPreferences != null){
            if(sharedPreferences.getString("username", null) != null){
                editTextEmail.setText(sharedPreferences.getString("username", null));
                editTextPassword.setText(sharedPreferences.getString("password", null));
                checkboxRemember.setChecked(true);
                if(Internet.haveInternet(getContext())) {
                    account = new AccountBuilder()
                            .setEmail(sharedPreferences.getString("username", null))
                            .setParola(sharedPreferences.getString("password", null))
                            .build();
                    LoginTask login = new LoginTask(getContext(), buttonLogin, progress, account, LoginTabFragment.this);
                    login.execute();
                }
            }
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificaCampuri()) {
                    if(Internet.haveInternet(getContext())) {
                        account = new AccountBuilder()
                                .setEmail(editTextEmail.getText().toString())
                                .setParola(editTextPassword.getText().toString())
                                .build();
                        LoginTask login = new LoginTask(getContext(), buttonLogin, progress, account, LoginTabFragment.this);
                        login.execute();
                    }else{
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append(" ");
                        builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
                        builder.append(" Nu exista conexiune la Internet!");
                        Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_LONG).show();
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

                /*
                SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
                if(sharedPreferences != null) {
                    if (sharedPreferences.getString("username", null) == null) {
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append(" ");
                        builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
                        builder.append(" Autentificare cu succes!");
                        Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
                    }
                }
                */
                JSONObject json = new JSONObject(result);
                JSONObject jsonAccount = json.getJSONObject("account");
                final Account account = new AccountBuilder()
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
                JSONArray jsonRecenzii = jsonAccount.getJSONArray("recenzii");
                for(int i=0;i<jsonRecenzii.length();i++){
                    JSONObject jsonRecenzie = jsonRecenzii.getJSONObject(i);
                    Recenzie recenzie = new Recenzie(
                            Integer.valueOf(jsonRecenzie.getString("id")),
                            jsonRecenzie.getString("nume"),
                            jsonRecenzie.getString("data"),
                            Float.valueOf(jsonRecenzie.getString("scor")),
                            jsonRecenzie.getString("descriere")
                    );
                    account.addRecenzie(recenzie);
                }
                if(account.getListaRecenzii().size() > 0) {
                    Collections.sort(account.getListaRecenzii(), new Comparator<Recenzie>() {
                        @Override
                        public int compare(Recenzie lhs, Recenzie rhs) {
                            if ((lhs.getData()).compareTo((rhs.getData())) > 0) {
                                return -1;
                            } else if (((lhs.getData()).compareTo((rhs.getData())) < 0)) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                int profilePhoto = jsonAccount.getInt("profile_photo");
                if(profilePhoto == 1){
                    account.setHaveProfilPhoto(true);
                    File mypath=new File(getContext().getFilesDir(),"photo_" + String.valueOf(account.getId()) + ".png");
                    if(!mypath.exists()){
                        ContDownloadPhotoTask contDownloadPhotoTask = new ContDownloadPhotoTask(getContext(), account.getId(), new TaskCompletedUpload() {
                            @Override
                            public void onTaskCompleted(Bitmap bitmap) {
                                if(bitmap != null){
                                    try {
                                        File mypath=new File(getContext().getFilesDir(),"photo_" + String.valueOf(account.getId()) + ".png");
                                        if (!mypath.exists()) {
                                            FileOutputStream out = new FileOutputStream(mypath);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                                            out.flush();
                                            out.close();
                                        }
                                    }catch (Exception ex){
                                        Log.e("LoginTabFragment", ex.getMessage());
                                    }
                                }
                            }
                        });
                        contDownloadPhotoTask.execute();
                    }
                }else{
                    account.setHaveProfilPhoto(false);
                }
                CalatoriiFragmentTask task = new CalatoriiFragmentTask(account.getId(), new TaskCompletedCalatorii() {
                    @Override
                    public void onTaskCompleted(CalatoriiHolder result) {
                        NavigationActivity.calatoriiHolder = result;
                        Intent it = new Intent(getActivity(), NavigationActivity.class);
                        it.putExtra("account", account);
                        buttonLogin.setText("LOGIN");
                        buttonLogin.setBackgroundColor(getContext().getResources().getColor(R.color.colorBlue));
                        progress.setVisibility(View.INVISIBLE);
                        try {
                            File file = new File(getContext().getFilesDir(), Setari.FILE_CONFIG);
                            if(!file.exists()){
                                file.createNewFile();
                            }
                            FileInputStream input = new FileInputStream(file);
                            Properties p = new Properties();
                            p.load(input);
                            input.close();
                            String value = p.getProperty("NOTIFICARI", null);
                            if(value == null){
                                File f = new File(getContext().getFilesDir(), Setari.FILE_CONFIG);
                                FileOutputStream output = new FileOutputStream(f);
                                p.setProperty("NOTIFICARI", "true");
                                p.store(output, null);
                                output.close();
                            }else if(value.equals("true")){
                                Setari.NOTIFICARI = true;
                            }else{
                                Setari.NOTIFICARI = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        SharedPreferences.Editor ed = MainActivity.sharedPreferences.edit();
                        ed.putBoolean("logat", true);
                        ed.apply();
                        if(checkboxRemember.isChecked()){
                            SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                            editor.putString("username", account.getEmail());
                            editor.putString("password", account.getParola());
                            editor.putInt("id", account.getId());
                            editor.apply();
                        }else{
                            SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
                            edit.remove("username");
                            edit.remove("password");
                            edit.remove("id");
                            edit.apply();
                        }
                        startActivity(it);
                    }
                });
                task.execute();
            } catch (Exception ex) {
                Log.e("JSONException", ex.toString());
            }
        }else{
            buttonLogin.setText("LOGIN");
            buttonLogin.setBackgroundColor(getContext().getResources().getColor(R.color.colorBlue));
            progress.setVisibility(View.INVISIBLE);
        }
    }

    public Boolean verificaCampuri(){

        if (editTextEmail.getText().toString().equals("")) {
            editTextEmail.setError("Trebuie sa completezi acest camp!");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Trebuie sa introduceti emailul!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (editTextPassword.getText().toString().equals("")) {
            editTextPassword.setError("Trebuie sa completezi acest camp!");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Trebuie sa introduceti parola!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
