package com.example.silviu086.licenta;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
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

public class ModifyPassword extends AppCompatActivity{


    EditText editTextCurrentPassword;
    EditText editTextNewPassword;
    EditText editTextNewPasswordAgain;
    Button buttonModifica;
    ProgressBar progressBarModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Modificare parola");

        editTextCurrentPassword = (EditText)findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextNewPasswordAgain = (EditText) findViewById(R.id.editTextNewPasswordAgain);
        buttonModifica = (Button) findViewById(R.id.buttonModifica);
        progressBarModify = (ProgressBar) findViewById(R.id.progressBarModify);

        final String email = getIntent().getExtras().getString("email");

        buttonModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificareCampuri()){
                    Bundle b = new Bundle();
                    b.putString("email", email);
                    b.putString("current_password", editTextCurrentPassword.getText().toString());
                    b.putString("new_password", editTextNewPassword.getText().toString());
                    ModifyPasswordTask modifyTask = new ModifyPasswordTask(b, progressBarModify, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            if(result.equals("updated")){
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("new_password", editTextNewPassword.getText().toString());
                                setResult(1, resultIntent);
                                finish();
                            }else if(result.equals("failed")){
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(" ");
                                builder.setSpan(new ImageSpan(ModifyPassword.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                                builder.append(" Parola incorecta!");
                                Snackbar.make(findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
                                editTextCurrentPassword.setError("Reintroduceti parola!");
                            }else if(result.equals("null")){
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(" ");
                                builder.setSpan(new ImageSpan(ModifyPassword.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                                builder.append(" Modificare esuata, reincercati!");
                                Snackbar.make(findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                    modifyTask.execute();
                }
            }
        });

    }

    private boolean verificareCampuri(){
        if(editTextCurrentPassword.getText().toString().equals("")) {
            editTextCurrentPassword.setError("Trebuie sa completati acest camp!");
            return false;
        } else if(editTextNewPassword.getText().toString().equals("")){
            editTextNewPassword.setError("Trebuie sa completati acest camp!");
            return false;
        } else if(editTextNewPasswordAgain.getText().toString().equals("")){
            editTextNewPasswordAgain.setError("Trebuie sa completati acest camp!");
            return false;
        }else if(!editTextNewPassword.getText().toString().equals(editTextNewPasswordAgain.getText().toString())){
            editTextNewPassword.setError("Reintroduceti parola");
            editTextNewPasswordAgain.setError("Reintroduceti parola");
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
        }
        return super.onOptionsItemSelected(item);
    }
}
