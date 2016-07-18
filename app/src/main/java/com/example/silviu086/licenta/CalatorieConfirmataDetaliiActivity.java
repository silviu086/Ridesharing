package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;

public class CalatorieConfirmataDetaliiActivity extends AppCompatActivity {
    private LinearLayout parentView;
    private LinearLayout linearLayoutProfil;
    private ImageView imageViewProfil;
    private TextView textViewEmail;
    private TextView textViewIdCalatorie;
    private TextView textViewDataAdaugare;
    private TextView textViewPunctPlecare;
    private TextView textViewPunctSosire;
    private TextView textViewDurata;
    private TextView textViewDistanta;
    private TextView textViewDataCalatorie;
    private TextView textViewLocuri;
    private TextView textViewMarca;
    private TextView textViewModel;
    private TextView textViewAnFabricatie;
    private TextView textViewExperientaAuto;
    private TextView textViewNivelConfort;
    private TextView textViewCapacitateBagaj;
    private TextView textViewFaraPasageri;
    private LinearLayout linearLayoutPasageri;
    private LinearLayout linearLayoutPasageriBorder;
    private Button buttonRecenzie;
    private Button buttonMesaj;
    private Button buttonChat;
    private ImageView imageViewMap;
    private GoogleMap map;
    private LayoutInflater inflater;
    private CalatorieConfirmata calatorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calatorie_confirmata_detalii);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalii calatorie");
        int position = getIntent().getExtras().getInt("position");

        parentView = (LinearLayout) findViewById(R.id.parent_view);
        parentView.setVisibility(View.GONE);
        calatorie = NavigationActivity.calatoriiHolder.calatoriiConfirmate.get(position);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayoutProfil = (LinearLayout) findViewById(R.id.linearLayoutProfil);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewIdCalatorie = (TextView) findViewById(R.id.textViewIdCalatorie);
        textViewDataAdaugare = (TextView) findViewById(R.id.textViewDataAdaugare);
        textViewPunctPlecare = (TextView) findViewById(R.id.textViewPunctPlecare);
        textViewPunctSosire = (TextView) findViewById(R.id.textViewPunctSosire);
        textViewDurata = (TextView) findViewById(R.id.textViewDurata);
        textViewDistanta = (TextView) findViewById(R.id.textViewDistanta);
        textViewDataCalatorie = (TextView) findViewById(R.id.textViewDataCalatorie);
        textViewLocuri = (TextView) findViewById(R.id.textViewLocuri);
        textViewMarca = (TextView) findViewById(R.id.textViewMarca);
        textViewModel = (TextView) findViewById(R.id.textViewModel);
        textViewAnFabricatie = (TextView) findViewById(R.id.textViewAnFabricatie);
        textViewExperientaAuto = (TextView) findViewById(R.id.textViewExperientaAuto);
        textViewNivelConfort = (TextView) findViewById(R.id.textViewNivelConfort);
        textViewCapacitateBagaj = (TextView) findViewById(R.id.textViewCapacitateBagaj);
        textViewFaraPasageri = (TextView) findViewById(R.id.textViewFaraPasageri);
        linearLayoutPasageri = (LinearLayout) findViewById(R.id.linearLayoutPasageri);
        linearLayoutPasageriBorder = (LinearLayout) findViewById(R.id.linearLayoutPasageriBorder);
        buttonRecenzie = (Button) findViewById(R.id.buttonRecenzie);
        buttonMesaj = (Button) findViewById(R.id.buttonMesaj);
        buttonChat = (Button) findViewById(R.id.buttonChat);
        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(44.42677, 26.10254), 4));

        textViewEmail.setText(calatorie.getEmail());
        textViewIdCalatorie.setText("Calatoria " + String.valueOf(calatorie.getId()));
        textViewDataAdaugare.setText(calatorie.getDataCreare());
        textViewPunctPlecare.setText(calatorie.getPunctPlecare());
        textViewPunctSosire.setText(calatorie.getPunctSosire());
        textViewDurata.setText(calatorie.getDurataCalatorie());
        textViewDistanta.setText(calatorie.getDistantaCalatorie());
        textViewDataCalatorie.setText(calatorie.getDataPlecare());
        textViewLocuri.setText(String.valueOf(calatorie.getLocuriDisponibile()) + " locuri");
        textViewMarca.setText(calatorie.getMarcaMasina());
        textViewModel.setText(calatorie.getModelMasina());
        textViewAnFabricatie.setText(String.valueOf(calatorie.getAnFabricatie()));
        textViewExperientaAuto.setText(calatorie.getExperientaAuto());
        textViewNivelConfort.setText(calatorie.getNivelConfort());
        textViewCapacitateBagaj.setText(calatorie.getMarimeBagaj());
        linearLayoutProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CalatorieConfirmataDetaliiActivity.this, UtilizatorProfilActivity.class);
                it.putExtra("nume", calatorie.getNume());
                it.putExtra("id_utilizator", calatorie.getIdUtilizator());
                startActivity(it);
            }
        });
        buttonRecenzie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CalatorieConfirmataDetaliiActivity.this, AdaugaRecenzieActivity.class);
                it.putExtra("id_calatorie", calatorie.getId());
                it.putExtra("id_utilizator_vizat", calatorie.getIdUtilizator());
                it.putExtra("nume", calatorie.getNume());
                it.putExtra("email", calatorie.getEmail());
                startActivity(it);
            }
        });
        buttonMesaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CalatorieConfirmataDetaliiActivity.this, TrimiteMesajActivity.class);
                it.putExtra("id_calatorie", calatorie.getId());
                it.putExtra("id_destinatar", calatorie.getIdUtilizator());
                it.putExtra("nume", calatorie.getNume());
                it.putExtra("email", calatorie.getEmail());
                startActivity(it);
            }
        });
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final File mypath = new File(getFilesDir(), "photo_" + String.valueOf(calatorie.getIdUtilizator()) + ".png");
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading...");
        progress.setMessage("Se preiau datele!");
        progress.show();
        ContDownloadPhotoTask contDownloadPhotoTask = new ContDownloadPhotoTask(CalatorieConfirmataDetaliiActivity.this, calatorie.getIdUtilizator(), new TaskCompletedUpload() {
            @Override
            public void onTaskCompleted(Bitmap bitmap) {
                if (bitmap != null) {
                    try {
                        File mypath = new File(getFilesDir(), "photo_" + String.valueOf(calatorie.getIdUtilizator()) + ".png");
                        if (!mypath.exists()) {
                            FileOutputStream out = new FileOutputStream(mypath);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                            out.flush();
                            out.close();
                        }
                    } catch (Exception ex) {
                        Log.e("LoginTabFragment", ex.getMessage());
                    }
                    Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
                    imageViewProfil.setImageDrawable(photo);
                }
                if(calatorie.getListaAltiPasageri().size() > 0){
                }
                for(int i=0;i<calatorie.getListaAltiPasageri().size();i++){
                    final HolderPasageri pasager = calatorie.getListaAltiPasageri().get(i);
                    textViewFaraPasageri.setVisibility(View.GONE);
                    View view = inflater.inflate(R.layout.calatorie_confirmata_detalii_pasageri_list, null);
                    final ImageView imageViewProfilPasager = (ImageView) view.findViewById(R.id.imageViewProfilPasager);
                    TextView textViewNume = (TextView) view.findViewById(R.id.textViewNume);
                    Button buttonProfil = (Button) view.findViewById(R.id.buttonProfil);
                    textViewNume.setText(pasager.getNume());
                    buttonProfil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(CalatorieConfirmataDetaliiActivity.this, UtilizatorProfilActivity.class);
                            it.putExtra("nume", pasager.getNume());
                            it.putExtra("id_utilizator", Integer.valueOf(pasager.getId()));
                            startActivity(it);
                        }
                    });
                    final File mypath = new File(getFilesDir(), "photo_" + String.valueOf(pasager.getId()) + ".png");
                    ContDownloadPhotoTask pasagerDownloadPhotoTask = new ContDownloadPhotoTask(CalatorieConfirmataDetaliiActivity.this, Integer.valueOf(pasager.getId()), new TaskCompletedUpload() {
                        @Override
                        public void onTaskCompleted(Bitmap bitmap) {
                            if (bitmap != null) {
                                try {
                                    File mypath = new File(getFilesDir(), "photo_" + String.valueOf(pasager.getId()) + ".png");
                                    if (!mypath.exists()) {
                                        FileOutputStream out = new FileOutputStream(mypath);
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                                        out.flush();
                                        out.close();
                                    }
                                } catch (Exception ex) {
                                    Log.e("LoginTabFragment", ex.getMessage());
                                }
                                Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
                                imageViewProfilPasager.setImageDrawable(photo);
                            }
                        }});
                    pasagerDownloadPhotoTask.execute();
                    linearLayoutPasageri.addView(view);
                }
                progress.dismiss();
                if(Internet.haveInternet(CalatorieConfirmataDetaliiActivity.this)){
                    imageViewMap.setVisibility(View.GONE);
                    DirectionsMap directionsMap = new DirectionsMap(
                            map, CalatorieConfirmataDetaliiActivity.this,
                            calatorie.getPunctPlecare(),
                            calatorie.getPunctSosire(),
                            new TaskCompleted() {@Override public void onTaskCompleted(String result) {}});
                    directionsMap.execute();
                }else{
                    Toast.makeText(CalatorieConfirmataDetaliiActivity.this, "Nu s-a putut realiza traseul, este necesara conexiune la Internet!", Toast.LENGTH_LONG).show();
                }
                parentView.setVisibility(View.VISIBLE);
            }
        });
        contDownloadPhotoTask.execute();
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
