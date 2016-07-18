package com.example.silviu086.licenta;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CalatorieDetaliiActivity extends AppCompatActivity {

    private Calatorie calatorie;
    private Account account;

    //CONTROALE
    private TextView textViewPunctPlecare;
    private TextView textViewPunctSosire;
    private TextView textViewDurataCalatorie;
    private TextView textViewDistantaCalatorie;
    private TextView textViewDataPlecare;
    private TextView textViewOraPlecare;
    private TextView textViewPret;
    private TextView textViewLocuriDisponibile;
    private TextView textViewMarcaMasina;
    private TextView textViewModelMasina;
    private TextView textViewAnFabricatie;
    private TextView textViewExperientaAuto;
    private TextView textViewConfort;
    private TextView textViewBagaj;
    private TextView textViewNume;
    private TextView textViewEmail;
    private TextView textViewTelefon;
    private TextView textViewVarsta;
    private LinearLayout linearLayoutEmail;
    private LinearLayout linearLayoutTelefon;
    private Button buttonInchide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calatorie_detalii);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalii calatorie");
        calatorie = (Calatorie) getIntent().getExtras().getSerializable("calatorie");
        account = (Account) getIntent().getExtras().getSerializable("account");
        getSupportActionBar().setSubtitle("Calatoria " + String.valueOf(calatorie.getId()));
        textViewPunctPlecare = (TextView) findViewById(R.id.textViewPunctPlecare);
        textViewPunctSosire = (TextView) findViewById(R.id.textViewPunctSosire);
        textViewDurataCalatorie = (TextView) findViewById(R.id.textViewDurataCalatorie);
        textViewDistantaCalatorie = (TextView) findViewById(R.id.textViewDistantaCalatorie);
        textViewDataPlecare = (TextView) findViewById(R.id.textViewDataPlecare);
        textViewOraPlecare = (TextView) findViewById(R.id.textViewOraPlecare);
        textViewPret = (TextView) findViewById(R.id.textViewPret);
        textViewLocuriDisponibile = (TextView) findViewById(R.id.textViewLocuriDisponibile);
        textViewMarcaMasina = (TextView) findViewById(R.id.textViewMarcaMasina);
        textViewModelMasina = (TextView) findViewById(R.id.textViewModelMasina);
        textViewAnFabricatie = (TextView) findViewById(R.id.textViewAnFabricatie);
        textViewExperientaAuto = (TextView) findViewById(R.id.textViewExperientaAuto);
        textViewConfort = (TextView) findViewById(R.id.textViewConfort);
        textViewBagaj = (TextView) findViewById(R.id.textViewBagaj);
        textViewNume = (TextView) findViewById(R.id.textViewNume);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewTelefon = (TextView) findViewById(R.id.textViewTelefon);
        textViewVarsta = (TextView) findViewById(R.id.textViewVarsta);
        linearLayoutEmail = (LinearLayout) findViewById(R.id.linearLayoutEmail);
        linearLayoutTelefon = (LinearLayout) findViewById(R.id.linearLayoutTelefon);
        buttonInchide = (Button) findViewById(R.id.buttonInchide);

        textViewPunctPlecare.setText(calatorie.getPunctPlecare());
        textViewPunctSosire.setText(calatorie.getPunctSosire());
        textViewDurataCalatorie.setText(calatorie.getDurataCalatorie());
        textViewDistantaCalatorie.setText(calatorie.getDistantaCalatorie());
        textViewDataPlecare.setText(calatorie.getDataPlecare());
        textViewOraPlecare.setText(calatorie.getOraPlecare());
        textViewPret.setText(String.valueOf(calatorie.getPret()) + " lei");
        textViewLocuriDisponibile.setText(String.valueOf(calatorie.getLocuriDisponibile()) + " locuri");
        textViewMarcaMasina.setText(calatorie.getMarcaMasina());
        textViewModelMasina.setText(calatorie.getModelMasina());
        textViewAnFabricatie.setText(String.valueOf(calatorie.getAnFabricatie()));
        textViewExperientaAuto.setText(calatorie.getExperientaAuto());
        textViewConfort.setText(calatorie.getNivelConfort());
        textViewBagaj.setText(calatorie.getMarimeBagaj());
        textViewNume.setText("Informatii despre " + account.getNume());
        textViewEmail.setText(account.getEmail());
        textViewTelefon.setText(account.getTelefon());
        textViewVarsta.setText(String.valueOf(account.getVarsta()) + " ani");

        linearLayoutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(account.getEmail());
                Toast.makeText(CalatorieDetaliiActivity.this, "Email copiat in clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

        linearLayoutTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(account.getTelefon());
                Toast.makeText(CalatorieDetaliiActivity.this, "Telefon copiat in clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonInchide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setClipboard(String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
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
