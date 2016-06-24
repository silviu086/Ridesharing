package com.example.silviu086.licenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silviu086 on 03.06.2016.
 */
public class CalatoriiAdaugateAdapter extends BaseAdapter {

    private Context context;
    private List<CalatorieAdaugata> calatorii;
    private LayoutInflater inflater;

    public CalatoriiAdaugateAdapter(Context context, List<CalatorieAdaugata> calatoriiAdaugate) {
        this.context = context;
        this.calatorii = calatoriiAdaugate;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class HolderCalatorie{
        TextView textViewCalatorieNumar;
        Button buttonDetalii;
        TextView textViewPunctPlecare;
        TextView textViewPunctSosire;
        TextView textViewDataCalatorie;
        TextView textViewLocuri;
        LinearLayout linearLayoutCereri;
        LinearLayout linearLayoutFaraCereri;
    }

    class HolderCerere{
        TextView textViewNume;
        TextView textViewEmail;
        TextView textViewDataCerere;
        Button buttonConfirma;
    }

    @Override
    public int getCount() {
        return calatorii.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        v = inflater.inflate(R.layout.calatorii_adaugate_list, null);
        final CalatorieAdaugata calatorie = calatorii.get(position);
        final List<HolderPasageri> pasageriInAsteptare = calatorie.getListaPasageriInAsteptare();
        final List<HolderPasageri> pasageriConfirmati = calatorie.getListaPasageriConfirmati();

        HolderCalatorie hCalatorie = new HolderCalatorie();
        hCalatorie.textViewCalatorieNumar = (TextView) v.findViewById(R.id.textViewCalatorieNumar);
        hCalatorie.buttonDetalii = (Button) v.findViewById(R.id.buttonDetalii);
        hCalatorie.textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
        hCalatorie.textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
        hCalatorie.textViewDataCalatorie = (TextView) v.findViewById(R.id.textViewDataCalatorie);
        hCalatorie.textViewLocuri = (TextView) v.findViewById(R.id.textViewLocuri);
        hCalatorie.linearLayoutCereri = (LinearLayout) v.findViewById(R.id.linearLayoutCereri);
        hCalatorie.linearLayoutFaraCereri = (LinearLayout) v.findViewById(R.id.linearLayoutFaraCereri);

        hCalatorie.textViewCalatorieNumar.setText(String.valueOf(calatorie.getId()));
        hCalatorie.textViewPunctPlecare.setText(calatorie.getPunctPlecare());
        hCalatorie.textViewPunctSosire.setText(calatorie.getPunctSosire());
        hCalatorie.textViewDataCalatorie.setText(calatorie.getDataPlecare() + ", " + calatorie.getOraPlecare());
        hCalatorie.textViewLocuri.setText(String.valueOf(calatorie.getLocuriDisponibile()));
        if(pasageriInAsteptare.size() != 0 || pasageriConfirmati.size() != 0){
            hCalatorie.linearLayoutFaraCereri.setVisibility(View.GONE);
            for(int i=0;i<pasageriInAsteptare.size();i++){
                View view = inflater.inflate(R.layout.calatorii_adaugate_cereri_layout, null);
                HolderCerere hCerere = new HolderCerere();
                hCerere.textViewNume = (TextView) view.findViewById(R.id.textViewNume);
                hCerere.textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
                hCerere.textViewDataCerere = (TextView) view.findViewById(R.id.textViewDataCerere);
                hCerere.buttonConfirma = (Button) view.findViewById(R.id.buttonConfirma);

                hCerere.textViewNume.setText(pasageriInAsteptare.get(i).getNume());
                hCerere.textViewEmail.setText("(" + pasageriInAsteptare.get(i).getEmail() + ")");
                hCerere.textViewDataCerere.setText(pasageriInAsteptare.get(i).getData());
                hCalatorie.linearLayoutCereri.addView(view);
                final int finalI = i;
                hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Cerere nr " + String.valueOf(finalI), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        return v;
    }

    @Override
    public CalatorieAdaugata getItem(int position) {
        return calatorii.get(position);
    }

    @Override
    public long getItemId(int position) {
        return calatorii.get(position).getId();
    }
}
