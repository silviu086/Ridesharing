package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MesajeConversatieActivity extends AppCompatActivity {
    private ListView listView;
    private EditText editTextMesaj;
    private ImageButton imageButtonSend;
    private MesajeTopic conversatie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaje_conversatie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        conversatie = (MesajeTopic) getIntent().getExtras().getSerializable("conversatie");
        getSupportActionBar().setTitle("Conversatie");
        getSupportActionBar().setSubtitle("Calatoria " + String.valueOf(conversatie.getIdCalatorie()));

        listView = (ListView) findViewById(R.id.listView);
        editTextMesaj = (EditText) findViewById(R.id.editTextMesaj);
        imageButtonSend = (ImageButton) findViewById(R.id.imageButtonSend);

        final MesajeConversatieAdapter mesajeConversatieAdapter = new MesajeConversatieAdapter(this, conversatie.getListaMesaje());
        listView.setAdapter(mesajeConversatieAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(MesajeConversatieActivity.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Se trimite mesajul!");
                MesajeConversatieTask mesajeConversatieTask = new MesajeConversatieTask(
                        editTextMesaj.getText().toString(),
                        conversatie.getId(),
                        progressDialog,
                        new TaskCompleted() {
                            @Override
                            public void onTaskCompleted(String result) {
                                try{
                                    JSONObject json = new JSONObject(result);
                                    int success = json.getInt("success");
                                    if (success == 1) {
                                        Date data = new Date();
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm");
                                        int id = Integer.valueOf(json.getString("id"));
                                        Mesaj mesaj = new Mesaj(
                                                id,
                                                editTextMesaj.getText().toString(),
                                                NavigationActivity.account.getId(),
                                                NavigationActivity.account.getNume(),
                                                dateFormat.format(data),
                                                0);
                                        conversatie.addMesaj(mesaj);
                                        MesajeConversatieAdapter newMesajeConversatieAdapter = new MesajeConversatieAdapter(
                                                MesajeConversatieActivity.this,
                                                conversatie.getListaMesaje());
                                        editTextMesaj.setText("");
                                        View view = MesajeConversatieActivity.this.getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        listView.setAdapter(newMesajeConversatieAdapter);
                                    }else{

                                    }
                                }catch (Exception ex){
                                    Log.e("MesajeConversatieActivi", ex.getMessage());
                                }
                            }
                        }
                );
                mesajeConversatieTask.execute();
            }
        });

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
