package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Silviu086 on 24.06.2016.
 */
public class CalatoriiInAsteptareAdapter extends BaseAdapter {
    private Context context;
    private List<CalatorieInAsteptare> calatorii;
    private LayoutInflater inflater;

    public CalatoriiInAsteptareAdapter(Context context, List<CalatorieInAsteptare> calatoriiInAsteptare) {
        this.context = context;
        this.calatorii = calatoriiInAsteptare;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class Holder{
        TextView textViewNume;
        TextView textViewPunctPlecare;
        TextView textViewPunctSosire;
        TextView textViewPret;
        TextView textViewData;
        TextView textViewDataCerere;
        Button buttonDetalii;
    }

    @Override
    public int getCount() {
        return calatorii.size();
    }

    @Override
    public Object getItem(int position) {
        return calatorii.get(position);
    }

    @Override
    public long getItemId(int position) {
        return calatorii.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        v = inflater.inflate(R.layout.calatorii_in_asteptare_list, null);

        Holder h = new Holder();
        h.textViewNume = (TextView) v.findViewById(R.id.textViewNume);
        h.textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
        h.textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
        h.textViewPret = (TextView) v.findViewById(R.id.textViewPret);
        h.textViewData = (TextView) v.findViewById(R.id.textViewData);
        h.textViewDataCerere = (TextView) v.findViewById(R.id.textViewDataCerere);
        h.buttonDetalii = (Button) v.findViewById(R.id.buttonDetalii);

        final CalatorieInAsteptare calatorie = calatorii.get(position);
        h.textViewNume.setText(calatorie.getNume());
        h.textViewPunctPlecare.setText(calatorie.getPunctPlecare());
        h.textViewPunctSosire.setText(calatorie.getPunctSosire());
        h.textViewPret.setText(String.valueOf(calatorie.getPret()) + " lei");
        h.textViewData.setText(calatorie.getDataPlecare() + ", " + calatorie.getOraPlecare());
        h.textViewDataCerere.setText(calatorie.getDataCerere());
        h.buttonDetalii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, CalatorieDetaliiActivity.class);
                it.putExtra("calatorie", calatorie.getCalatorie());
                AccountBuilder builder = new AccountBuilder();
                Account ac = builder
                        .setNume(calatorie.getNume())
                        .setEmail(calatorie.getEmail())
                        .setTelefon(calatorie.getTelefon())
                        .setVarsta(Integer.valueOf(calatorie.getVarsta()))
                        .build();
                it.putExtra("account", ac);
                context.startActivity(it);
            }
        });
        return v;
    }
}
