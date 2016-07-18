package com.example.silviu086.licenta;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.zip.Inflater;

public class CalatorieAdaugataDetaliiActivity extends AppCompatActivity {
    private TextView textViewIdCalatorie;
    private TextView textViewDataAdaugare;
    private Button buttonEliminare;
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
    private LinearLayout linearLayoutCereri;
    private LinearLayout linearLayoutFaraCereri;
    private LinearLayout linearLayoutCerereBorder;
    private ImageView imageViewMap;
    private GoogleMap map;
    private LayoutInflater inflater;
    private CalatorieAdaugata calatorie;

    class HolderCerere{
        TextView textViewNume;
        TextView textViewEmail;
        TextView textViewDataCerere;
        Button buttonConfirma;

        public HolderCerere(View v){
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewEmail = (TextView) v.findViewById(R.id.textViewEmail);
            textViewDataCerere = (TextView) v.findViewById(R.id.textViewDataCerere);
            buttonConfirma = (Button) v.findViewById(R.id.buttonConfirma);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calatorie_adaugata_detalii);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalii calatorie");
        int position = getIntent().getExtras().getInt("position");
        calatorie = NavigationActivity.calatoriiHolder.calatoriiAdaugate.get(position);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        textViewIdCalatorie = (TextView) findViewById(R.id.textViewIdCalatorie);
        textViewDataAdaugare = (TextView) findViewById(R.id.textViewDataAdaugare);
        buttonEliminare = (Button) findViewById(R.id.buttonEliminare);
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
        linearLayoutCereri = (LinearLayout) findViewById(R.id.linearLayoutCereri);
        linearLayoutFaraCereri = (LinearLayout) findViewById(R.id.linearLayoutFaraCereri);
        linearLayoutCerereBorder = (LinearLayout) findViewById(R.id.linearLayoutCerereBorder);
        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(44.42677, 26.10254), 4));


        if(Internet.haveInternet(this)){
            imageViewMap.setVisibility(View.GONE);
            DirectionsMap directionsMap = new DirectionsMap(
                    map, this,
                    calatorie.getPunctPlecare(),
                    calatorie.getPunctSosire(),
                    new TaskCompleted() {@Override public void onTaskCompleted(String result) {}});
            directionsMap.execute();
        }else{
            Toast.makeText(this, "Nu s-a putut realiza traseul, este necesara conexiune la Internet!", Toast.LENGTH_LONG).show();
        }

        textViewIdCalatorie.setText("Calatoria " + String.valueOf(calatorie.getId()));
        textViewDataAdaugare.setText("Adaugata pe " + calatorie.getDataCreare());
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

        loadCereri();

        /*
        if(calatorie.getListaPasageriInAsteptare().size() > 0){
            linearLayoutFaraCereri.setVisibility(View.GONE);
            CalatoriiAdaugateCereriAdapter calatoriiAdaugateCereriAdapter = new CalatoriiAdaugateCereriAdapter(this, calatorie.getListaPasageriInAsteptare(), true);
            listViewInAsteptare.setAdapter(calatoriiAdaugateCereriAdapter);
        }
        if(calatorie.getListaPasageriConfirmati().size() > 0){
            linearLayoutFaraCereri.setVisibility(View.GONE);
            CalatoriiAdaugateCereriAdapter calatoriiAdaugateCereriAdapter = new CalatoriiAdaugateCereriAdapter(this, calatorie.getListaPasageriConfirmati(), false);
            listViewConfirmate.setAdapter(calatoriiAdaugateCereriAdapter);
        }
        */
    }

    private void loadCereri(){
        if(calatorie.getListaPasageriInAsteptare().size() > 0 || calatorie.getListaPasageriConfirmati().size() > 0){
            linearLayoutFaraCereri.setVisibility(View.GONE);
            linearLayoutCerereBorder.setVisibility(View.VISIBLE);
            linearLayoutCereri.removeAllViews();
            View border = inflater.inflate(R.layout.calatorii_adaugate_cereri_border, null);
            linearLayoutCereri.addView(border);
            for(int i=0;i<calatorie.getListaPasageriInAsteptare().size();i++) {
                View view = inflater.inflate(R.layout.calatorii_adaugate_cereri_layout, null);
                final HolderCerere hCerere = new HolderCerere(view);
                final HolderPasageri pasager = calatorie.getListaPasageriInAsteptare().get(i);
                hCerere.textViewNume.setText(pasager.getNume());
                hCerere.textViewEmail.setText("(" + pasager.getEmail() + ")");
                hCerere.textViewDataCerere.setText(pasager.getData());
                final int finalI = i;
                final int finalI1 = i;
                hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(calatorie.getLocuriDisponibile()>0){
                            hCerere.buttonConfirma.setEnabled(false);
                            hCerere.buttonConfirma.setText("Confirmare...");
                            //hCerere.buttonConfirma.setTextColor(context.getResources().getColor(R.color.colorGray));
                            CalatoriiAdaugateConfirmareTask confirmareTask = new CalatoriiAdaugateConfirmareTask(String.valueOf(calatorie.getId()), calatorie.getListaPasageriInAsteptare().get(finalI).getId(), new TaskCompleted() {
                                @Override
                                public void onTaskCompleted(String result) {
                                    if (result.equals("success")){
                                        calatorie.setLocuriDisponibile(calatorie.getLocuriDisponibile() - 1);
                                        textViewLocuri.setText(String.valueOf(calatorie.getLocuriDisponibile()));
                                        Toast.makeText(CalatorieAdaugataDetaliiActivity.this, "Cererea lui " + calatorie.getListaPasageriInAsteptare().get(finalI1).getNume() + " a fost confirmata!", Toast.LENGTH_LONG).show();
                                        hCerere.buttonConfirma.setText("Confirmata");
                                        hCerere.buttonConfirma.setTextColor(getResources().getColor(R.color.colorGrayDarker));
                                        calatorie.getListaPasageriInAsteptare().remove(pasager);
                                        calatorie.getListaPasageriConfirmati().add(pasager);
                                        loadCereri();
                                        /*
                                        hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(CalatorieAdaugataDetaliiActivity.this, "Cerere confirmata!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        */
                                        //NavigationActivity.setFragmentCalatorii(0);
                                    }else{
                                        hCerere.buttonConfirma.setEnabled(true);
                                        hCerere.buttonConfirma.setText("Confirma");
                                        hCerere.buttonConfirma.setTextColor(getResources().getColor(R.color.colorWhite));
                                        Toast.makeText(CalatorieAdaugataDetaliiActivity.this, "A aparut o problema la confirmare!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            confirmareTask.execute();
                        }else{
                            Toast.makeText(CalatorieAdaugataDetaliiActivity.this, "Numai sunt locuri libere!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                linearLayoutCereri.addView(view);
                //hCerere.buttonConfirma.setBackground(getResources().getDrawable(R.drawable.cauta_layout_shape_8));
            }

            for(int i=0;i<calatorie.getListaPasageriConfirmati().size();i++) {
                View view = inflater.inflate(R.layout.calatorii_adaugate_cereri_layout, null);
                final HolderCerere hCerere = new HolderCerere(view);
                HolderPasageri pasager = calatorie.getListaPasageriConfirmati().get(i);
                hCerere.textViewNume.setText(pasager.getNume());
                hCerere.textViewEmail.setText("(" + pasager.getEmail() + ")");
                hCerere.textViewDataCerere.setText(pasager.getData());
                hCerere.buttonConfirma.setText("Confirmata");
                hCerere.buttonConfirma.setTextColor(getResources().getColor(R.color.colorGrayDarker));
                hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CalatorieAdaugataDetaliiActivity.this, "Cerere confirmata!", Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayoutCereri.addView(view);
                //hCerere.buttonConfirma.setBackground(getResources().getDrawable(R.drawable.cauta_layout_shape_8));
            }
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
