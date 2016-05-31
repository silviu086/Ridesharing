package com.example.silviu086.licenta;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                                Toast.makeText(ModifyPassword.this, "Parola incorecta!", Toast.LENGTH_SHORT).show();
                                editTextCurrentPassword.setError("Reintroduceti parola!");
                            }else if(result.equals("null")){
                                Toast.makeText(ModifyPassword.this, "Modificare esuata, reincercati!", Toast.LENGTH_SHORT).show();
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
