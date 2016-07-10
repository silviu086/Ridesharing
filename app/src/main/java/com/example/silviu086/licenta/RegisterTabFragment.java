package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Silviu086 on 28.02.2016.
 */
public class RegisterTabFragment extends Fragment implements TaskCompleted {

    private View v;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordAgain;
    private EditText editTextNume;
    private EditText editTextTelefon;
    private EditText editTextVarsta;
    private AutoCompleteTextView autoCompleteMarca;
    private AutoCompleteTextView autoCompleteModel;
    private EditText editTextAnFabricatie;
    private Spinner spinnerExperienta;
    private CheckBox checkBoxMasina;
    private CheckBox checkBoxTermeni;
    private Button createButton;
    private ProgressBar progress;
    private LinearLayout linearLayoutMasina;
    private String messageServer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.register_tab, container, false);

        //PRELUARE CONTROALE
        editTextEmail = (EditText) v.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) v.findViewById(R.id.editTextPassword);
        editTextPasswordAgain = (EditText) v.findViewById(R.id.editTextPasswordAgain);
        editTextNume = (EditText) v.findViewById(R.id.editTextNume);
        editTextTelefon = (EditText) v.findViewById(R.id.editTextTelefon);
        editTextVarsta = (EditText) v.findViewById(R.id.editTextVarsta);
        autoCompleteMarca = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteMarca);
        autoCompleteModel = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteModel);
        editTextAnFabricatie = (EditText) v.findViewById(R.id.editTextAn);
        spinnerExperienta = (Spinner) v.findViewById(R.id.spinner);
        checkBoxMasina = (CheckBox) v.findViewById(R.id.checkBoxMasina);
        checkBoxTermeni = (CheckBox) v.findViewById(R.id.checkBoxTermeni);
        progress = (ProgressBar) v.findViewById(R.id.progressBar);
        createButton = (Button) v.findViewById(R.id.createButton);
        linearLayoutMasina = (LinearLayout) v.findViewById(R.id.linearLayoutMasina);

        //SETARE ADAPTER PENTRU MARCA,MODEL,EXPERIENTA

        //MARCA
        ArrayAdapter<String> arrayAdapterMarca = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.lista_marci));
        autoCompleteMarca.setThreshold(1);
        autoCompleteMarca.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        autoCompleteMarca.setAdapter(arrayAdapterMarca);

        //MODEL
        autoCompleteModel.setThreshold(1);
        autoCompleteModel.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        autoCompleteMarca.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteModel.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayAdapter<String> arrayAdapterModel;
                String marca = autoCompleteMarca.getText().toString();
                switch (marca.toLowerCase()) {
                    case "opel":
                        arrayAdapterModel = new ArrayAdapter<>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.lista_model_opel));
                        autoCompleteModel.setAdapter(arrayAdapterModel);
                        break;
                    case "bmw":
                        arrayAdapterModel = new ArrayAdapter<>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.lista_model_bmw));
                        autoCompleteModel.setAdapter(arrayAdapterModel);
                }
            }
        });

        //EXPERIENTA
        ArrayAdapter<String> arrayAdapterExperienta = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.lista_nivel_experienta)) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        arrayAdapterExperienta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExperienta.setAdapter(arrayAdapterExperienta);
        spinnerExperienta.setSelection(arrayAdapterExperienta.getCount());

        //FUNCTIONALITATE CHECKBOX

        //CHECKBOX MASINA
        checkBoxMasina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout.LayoutParams layoutParamsInvisible = new LinearLayout.LayoutParams(0, 0);
                LinearLayout.LayoutParams layoutParamsVisible = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                if (!isChecked) {
                    linearLayoutMasina.setVisibility(View.INVISIBLE);
                    linearLayoutMasina.setLayoutParams(layoutParamsInvisible);
                } else {
                    linearLayoutMasina.setVisibility(View.VISIBLE);
                    linearLayoutMasina.setLayoutParams(layoutParamsVisible);
                    resetCampuriMasina();
                }
            }
        });

        //FUNCTIONALITATE APASARE BUTON CREATE
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificaCampuri()){
                    //CREARE OBIECT ACCOUNT
                    Account account;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                    Date date = new Date();
                    String dateCreated = dateFormat.format(date);
                    if(checkBoxMasina.isChecked()){
                        account = new AccountBuilder()
                                .setEmail(editTextEmail.getText().toString())
                                .setParola(editTextPassword.getText().toString())
                                .setDateCreated(dateCreated)
                                .setNume(editTextNume.getText().toString())
                                .setTelefon(editTextTelefon.getText().toString())
                                .setVarsta(Integer.valueOf(editTextVarsta.getText().toString()))
                                .setMarcaMasina(autoCompleteMarca.getText().toString())
                                .setModelMasina(autoCompleteModel.getText().toString())
                                .setAnFabricatie(Integer.valueOf(editTextAnFabricatie.getText().toString()))
                                .setExperientaAuto(spinnerExperienta.getSelectedItem().toString())
                                .build();
                    }else{
                        account = new AccountBuilder()
                                .setEmail(editTextEmail.getText().toString())
                                .setParola(editTextPassword.getText().toString())
                                .setDateCreated(dateCreated)
                                .setNume(editTextNume.getText().toString())
                                .setTelefon(editTextTelefon.getText().toString())
                                .setVarsta(Integer.valueOf(editTextVarsta.getText().toString()))
                                .build();
                    }
                    RegisterTask register = new RegisterTask(getContext(), createButton, progress, account, RegisterTabFragment.this);
                    register.execute();
                }
            }
        });
        return v;
    }

    @Override
    public void onTaskCompleted(String result) {
        if(result.equals("success")){
            resetCampuriDateLogare();
            resetCompuriDatePersonale();
            resetCampuriMasina();
        } else{
            Toast.makeText(getContext(), "Nu s-a putut inregistra!", Toast.LENGTH_LONG).show();
        }
    }

    private void resetCampuriDateLogare(){
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextPasswordAgain.setText("");
    }

    private void resetCompuriDatePersonale(){
        editTextNume.setText("");
        editTextTelefon.setText("");
        editTextVarsta.setText("");
    }

    private void resetCampuriMasina(){
        autoCompleteMarca.setText("");
        autoCompleteModel.setText("");
        editTextAnFabricatie.setText("");
        spinnerExperienta.setSelection(5);
    }

    private Boolean verificaCampuri(){
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
        if (editTextPasswordAgain.getText().toString().equals("")) {
            editTextPasswordAgain.setError("Trebuie sa introduceti parola!");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Trebuie sa introduceti parola!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (!editTextPassword.getText().toString().equals(editTextPasswordAgain.getText().toString())) {
            editTextPassword.setError("Reintroduceti parola!");
            editTextPasswordAgain.setError("Reintroduceti parola!");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
            builder.append(" Parola nu coincide!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (editTextNume.getText().toString().equals("")) {
            editTextNume.setError("Trebuie sa completezi acest camp!");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Trebuie sa introduceti numele!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (editTextTelefon.getText().toString().equals("")) {
            editTextTelefon.setError("Trebuie sa completezi acest camp!");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Trebuie sa introduceti telefonul!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (editTextVarsta.getText().toString().equals("")) {
            editTextVarsta.setError("Trebuie sa completezi acest camp!");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Trebuie sa introduceti varsta!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if(checkBoxMasina.isChecked()){
            if (autoCompleteMarca.getText().toString().equals("")) {
                autoCompleteMarca.setError("Trebuie sa completezi acest camp!");
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(" ");
                builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
                builder.append(" Trebuie sa introduceti marca masinii!");
                Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
                return false;
            }
            if (autoCompleteModel.getText().toString().equals("")) {
                autoCompleteModel.setError("Trebuie sa completezi acest camp!");
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(" ");
                builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
                builder.append(" Trebuie sa introduceti modelul masinii!");
                Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
                return false;
            }
            if (editTextAnFabricatie.getText().toString().equals("")) {
                editTextAnFabricatie.setError("Trebuie sa completezi acest camp!");
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(" ");
                builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
                builder.append(" Trebuie sa introduceti anul de fabricatie!");
                Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
                return false;
            }
            if(spinnerExperienta.getSelectedItemPosition()==5){
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(" ");
                builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
                builder.append(" Trebuie sa selectati experienta auto!");
                Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!checkBoxTermeni.isChecked()){
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
            builder.append(" Acceptati termenii de utilizare!");
            Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
