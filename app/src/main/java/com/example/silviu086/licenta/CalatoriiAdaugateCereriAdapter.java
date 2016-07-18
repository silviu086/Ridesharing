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

import java.util.List;

/**
 * Created by Silviu086 on 12.07.2016.
 */
public class CalatoriiAdaugateCereriAdapter extends BaseAdapter {
    private Context context;
    private List<HolderPasageri> listaPasageri;
    private LayoutInflater inflater;
    private boolean isInAsteptare;

    public CalatoriiAdaugateCereriAdapter(Context context, List<HolderPasageri> listaPasageri, boolean isInAsteptare) {
        this.context = context;
        this.listaPasageri = listaPasageri;
        this.isInAsteptare = isInAsteptare;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class HolderCereri{
        TextView textViewNume;
        TextView textViewEmail;
        TextView textViewDataCerere;
        Button buttonConfirma;

        public HolderCereri(View v){
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewEmail = (TextView) v.findViewById(R.id.textViewEmail);
            textViewDataCerere = (TextView) v.findViewById(R.id.textViewDataCerere);
            buttonConfirma = (Button) v.findViewById(R.id.buttonConfirma);
        }
    }

    @Override
    public int getCount() {
        return listaPasageri.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPasageri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.valueOf(listaPasageri.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        HolderCereri hCerere = null;
        final HolderPasageri pasager = listaPasageri.get(position);

        if (convertView == null) {
            v = inflater.inflate(R.layout.calatorii_adaugate_cereri_layout, null);
            hCerere = new HolderCereri(v);
            v.setTag(hCerere);
        } else {
            hCerere = (HolderCereri)v.getTag();
        }

        hCerere.textViewNume.setText(pasager.getNume());
        hCerere.textViewEmail.setText(pasager.getEmail());
        hCerere.textViewDataCerere.setText(pasager.getData());
        if(isInAsteptare){
            hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, String.valueOf(pasager.getId()), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            hCerere.buttonConfirma.setTextColor(context.getResources().getColor(R.color.colorGrayDarker));
            hCerere.buttonConfirma.setText("Confirmata");
            hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Cererea este confirmata!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return v;
    }
}
