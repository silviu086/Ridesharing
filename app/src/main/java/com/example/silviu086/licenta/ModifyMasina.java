package com.example.silviu086.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyMasina extends AppCompatActivity {


    private AutoCompleteTextView autoCompleteTextViewMarca;
    private AutoCompleteTextView autoCompleteTextViewModel;
    private EditText editTextAnFabricatie;
    private Spinner spinnerExperienta;
    private Button buttonModifica;
    private ProgressBar progressBarModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_masina);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final String button = getIntent().getExtras().getString("button");
        if(button.equals("adauga")){
            getSupportActionBar().setTitle("Adauga masina");
        }else{
            getSupportActionBar().setTitle("Modifica masina");
        }

        autoCompleteTextViewMarca = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewMarcaCont);
        autoCompleteTextViewModel = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewModelCont);
        editTextAnFabricatie = (EditText) findViewById(R.id.editTextAnFabricatieCont);
        spinnerExperienta = (Spinner) findViewById(R.id.spinnerExperientaCont);
        buttonModifica = (Button) findViewById(R.id.buttonModifyMasina);
        progressBarModify = (ProgressBar) findViewById(R.id.progressBarModify);

        if(button.equals("adauga")){
            buttonModifica.setText("Adauga");
        }else{
            buttonModifica.setText("Modifica");
        }

        final Account account = (Account) getIntent().getExtras().getSerializable("account");
        autoCompleteTextViewMarca.setText(account.getMarcaMasina().toString());
        autoCompleteTextViewModel.setText(account.getModelMasina().toString());
        editTextAnFabricatie.setText(account.getAnFabricatie() == 0 ? "" : String.valueOf(account.getAnFabricatie()));


        ArrayAdapter<String> arrayAdapterMarca = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.custom_list_dropdown,
                getResources().getStringArray(R.array.lista_marci)
        );

        autoCompleteTextViewMarca.setThreshold(1);
        autoCompleteTextViewMarca.setAdapter(arrayAdapterMarca);

        autoCompleteTextViewModel.setThreshold(1);
        autoCompleteTextViewMarca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteTextViewModel.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayAdapter<String> arrayAdapterModel;
                String marca = autoCompleteTextViewMarca.getText().toString();
                switch (marca.toLowerCase()) {
                    case "bmw":
                        arrayAdapterModel = new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.custom_list_dropdown,
                                getResources().getStringArray(R.array.lista_model_bmw)
                        );
                        autoCompleteTextViewModel.setAdapter(arrayAdapterModel);
                        break;
                    case "opel":
                        arrayAdapterModel = new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.custom_list_dropdown,
                                getResources().getStringArray(R.array.lista_model_opel)
                        );
                        autoCompleteTextViewModel.setAdapter(arrayAdapterModel);
                        break;
                }
            }
        });

        ArrayAdapter<String> arrayAdapterExperienta = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.lista_nivel_experienta)){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if(position == getCount()){
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        arrayAdapterExperienta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExperienta.setAdapter(arrayAdapterExperienta);
        spinnerExperienta.setSelection(arrayAdapterExperienta.getCount());

        int poz = -1;
        switch (account.getExperientaAuto().toString().toLowerCase()){
            case "mai putin de 1 an":
                poz = 0;
                break;
            case "1 an":
                poz = 1;
                break;
            case "2 ani":
                poz = 2;
                break;
            case "3 ani":
                poz = 3;
                break;
            case "4 ani":
                poz = 4;
                break;
            case  "mai mult de 4 ani":
                poz = 5;
                break;
        }
        if(poz != -1){
            spinnerExperienta.setSelection(poz);
        }

        buttonModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampuri()){
                    Bundle b = new Bundle();
                    b.putSerializable("email", account.getEmail().toString());
                    b.putString("marca", autoCompleteTextViewMarca.getText().toString());
                    b.putString("model", autoCompleteTextViewModel.getText().toString());
                    b.putString("an", editTextAnFabricatie.getText().toString());
                    b.putString("experienta", spinnerExperienta.getSelectedItem().toString());

                    ModifyMasinaTask modifyMasinaTask = new ModifyMasinaTask(b, progressBarModify, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            if(result.equals("updated")){
                                Intent it = new Intent();
                                it.putExtra("marca", autoCompleteTextViewMarca.getText().toString());
                                it.putExtra("model", autoCompleteTextViewModel.getText().toString());
                                it.putExtra("an", editTextAnFabricatie.getText().toString());
                                it.putExtra("experienta", spinnerExperienta.getSelectedItem().toString());

                                if(button.equals("adauga")){
                                    setResult(3, it);
                                    finish();
                                } else if(button.equals("modifica")){
                                    setResult(4, it);
                                    finish();
                                }
                            } else if(result.equals("failed")){
                                Toast.makeText(getApplicationContext(), "Nu s-a modificat, reincercati!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    modifyMasinaTask.execute();
                }
            }
        });
    }

    private boolean verificaCampuri(){
        if(autoCompleteTextViewMarca.getText().toString().equals("")){
            autoCompleteTextViewMarca.setError("Trebuie sa completati acest camp!");
            return false;
        } else if(autoCompleteTextViewModel.getText().toString().equals("")){
            autoCompleteTextViewModel.setError("Trebuie sa completati acest camp!");
            return false;
        } else if(editTextAnFabricatie.getText().toString().equals("")){
            editTextAnFabricatie.setError("Trebuie sa complecati acest camp!");
            return false;
        } else if(spinnerExperienta.getSelectedItem().toString().equals("selecteaza din lista")){
            Toast.makeText(getApplicationContext(), "Selectati experienta auto!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
