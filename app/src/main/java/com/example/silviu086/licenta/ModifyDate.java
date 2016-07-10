package com.example.silviu086.licenta;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ModifyDate extends AppCompatActivity {

    private EditText editTextNume;
    private EditText editTextTelefon;
    private EditText editTextVarsta;
    private ProgressBar progressBarModify;
    private Button buttonModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Modificare date personale");

        editTextNume = (EditText) findViewById(R.id.editTextNume);
        editTextTelefon = (EditText) findViewById(R.id.editTextTelefon);
        editTextVarsta = (EditText) findViewById(R.id.editTextVarsta);
        progressBarModify = (ProgressBar) findViewById(R.id.progressBarModify);
        buttonModify = (Button) findViewById(R.id.buttonModify);
        final Account account = (Account) getIntent().getExtras().getSerializable("account");
        editTextNume.setText(account.getNume().toString());
        editTextTelefon.setText(account.getTelefon().toString());
        editTextVarsta.setText(String.valueOf(account.getVarsta()).toString());

        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampuri()){
                    Bundle b = new Bundle();
                    b.putString("email", account.getEmail().toString());
                    b.putString("nume", editTextNume.getText().toString());
                    b.putString("telefon", editTextTelefon.getText().toString());
                    b.putString("varsta", editTextVarsta.getText().toString());
                    ModifyDateTask modifyDateTask = new ModifyDateTask(b, progressBarModify, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            if(result.equals("updated")){
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("nume", editTextNume.getText().toString());
                                resultIntent.putExtra("telefon", editTextTelefon.getText().toString());
                                resultIntent.putExtra("varsta", editTextVarsta.getText().toString());
                                setResult(2, resultIntent);
                                finish();
                            }else if(result.equals("failed")){
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(" ");
                                builder.setSpan(new ImageSpan(ModifyDate.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                                builder.append(" Nu s-a modificat, reincercati!");
                                Snackbar.make(findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                    modifyDateTask.execute();
                }
            }
        });
    }

    private boolean verificaCampuri(){
        if(editTextNume.getText().toString().equals("")){
            editTextNume.setError("Trebuie sa completati acest camp!");
            return false;
        }else if(editTextTelefon.getText().toString().equals("")){
            editTextTelefon.setError("Trebuie sa completati acest camp!");
            return false;
        } else if(editTextVarsta.getText().toString().equals("")){
            editTextVarsta.setError("Trebuie sa completati acest camp!");
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


