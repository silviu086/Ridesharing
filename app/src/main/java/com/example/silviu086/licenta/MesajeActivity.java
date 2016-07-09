package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MesajeActivity extends AppCompatActivity {
    private List<MesajeTopic> listaMesajeTopics;
    TextView textViewNumarConversatii;
    private ListView listView;
    LinearLayout linearLayoutFaraConversatii;
    LinearLayout linearLayoutNumarConversatii;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaje);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mesaje");
        listView = (ListView) findViewById(R.id.listView);
        textViewNumarConversatii = (TextView) findViewById(R.id.textViewNumarConversatii);
        linearLayoutFaraConversatii = (LinearLayout) findViewById(R.id.linearLayoutFaraConversatii);
        linearLayoutNumarConversatii = (LinearLayout) findViewById(R.id.linearLayoutNumarConversatii);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Se incarca mesajele!");
        MesajeTask mesajeTask = new MesajeTask(progressDialog, new TaskCompletedMesaje() {
            @Override
            public void onTaskCompleted(List<MesajeTopic> result) {
                listaMesajeTopics = result;
                if(listaMesajeTopics == null){
                    Toast.makeText(getApplicationContext(), "Nu s-au putut obtine mesajele!", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    if(listaMesajeTopics.size() == 1) {
                        textViewNumarConversatii.setText("1 conversatie");
                    }else{
                        textViewNumarConversatii.setText(String.valueOf(listaMesajeTopics.size() + " conversatii"));
                    }
                    if (listaMesajeTopics.size() == 0) {
                        linearLayoutFaraConversatii.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutNumarConversatii.setVisibility(View.VISIBLE);
                        linearLayoutFaraConversatii.setVisibility(View.GONE);
                        MesajeAdapter adapter = new MesajeAdapter(MesajeActivity.this, listaMesajeTopics);
                        listView.setAdapter(adapter);
                    }
                    progressDialog.dismiss();
                }
            }
        });
        mesajeTask.execute();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MesajeTask mesajeTask = new MesajeTask(progressDialog, new TaskCompletedMesaje() {
            @Override
            public void onTaskCompleted(List<MesajeTopic> result) {
                listaMesajeTopics = result;
                if(listaMesajeTopics == null){
                    Toast.makeText(getApplicationContext(), "Nu s-au putut obtine mesajele!", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    if(listaMesajeTopics.size() == 1) {
                        textViewNumarConversatii.setText("1 conversatie");
                    }else{
                        textViewNumarConversatii.setText(String.valueOf(listaMesajeTopics.size() + " conversatii"));
                    }
                    if (listaMesajeTopics.size() == 0) {
                        linearLayoutFaraConversatii.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutNumarConversatii.setVisibility(View.VISIBLE);
                        linearLayoutFaraConversatii.setVisibility(View.GONE);
                        MesajeAdapter adapter = new MesajeAdapter(MesajeActivity.this, listaMesajeTopics);
                        listView.setAdapter(adapter);
                    }
                    progressDialog.dismiss();
                }
            }
        });
        mesajeTask.execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
            case R.id.action_mesaje:
                Toast.makeText(this, "Trimite", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
