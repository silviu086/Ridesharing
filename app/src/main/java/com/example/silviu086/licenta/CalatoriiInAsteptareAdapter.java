package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

    class HolderInAsteptare{
        TextView textViewNumarCalatorie;
        TextView textViewNume;
        TextView textViewPunctPlecare;
        TextView textViewPunctSosire;
        TextView textViewPret;
        TextView textViewData;
        TextView textViewDataCerere;
        Button buttonDetalii;
        Button buttonProfil;
        LinearLayout linearLayoutTitle;
        View t;
        TextView textViewTitleData;
        TextView textViewTitlePunctPlecare;
        TextView textViewTitlePunctSosire;

        public HolderInAsteptare(View v){
            textViewNumarCalatorie = (TextView) v.findViewById(R.id.textViewNumarCalatorieInAsteptare);
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
            textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
            textViewPret = (TextView) v.findViewById(R.id.textViewPret);
            textViewData = (TextView) v.findViewById(R.id.textViewData);
            textViewDataCerere = (TextView) v.findViewById(R.id.textViewDataCerere);
            buttonDetalii = (Button) v.findViewById(R.id.buttonDetalii);
            buttonProfil = (Button) v.findViewById(R.id.buttonProfil);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
        }
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

    private boolean cautaId(int id, List<Integer> lista){
        for(int i=0;i<lista.size();i++){
            if(id == lista.get(i)){
                return true;
            }
        }
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        HolderInAsteptare h = null;
        final CalatorieInAsteptare calatorie = calatorii.get(position);

        if (convertView == null) {
            v = inflater.inflate(R.layout.calatorii_in_asteptare_list, null);
            h = new HolderInAsteptare(v);
            v.setTag(h);
        } else {
            h = (HolderInAsteptare)v.getTag();
        }

        h.linearLayoutTitle.removeAllViews();
        if(CalatoriiFragment.TIP_AFISARE == 2) {
            if (cautaId(calatorie.getId(), CalatoriiFragment.calatoriiTitleInAsteptareData)) {
                h.t = inflater.inflate(R.layout.calatorii_title_data, null);
                h.textViewTitleData = (TextView) h.t.findViewById(R.id.textViewTitleData);
                h.textViewTitleData.setText(calatorie.getDataPlecare());
                h.linearLayoutTitle.addView(h.t);
            }
        }else if(CalatoriiFragment.TIP_AFISARE == 1) {
            if(cautaId(calatorie.getId(), CalatoriiFragment.calatoriiTitleInAsteptareLocatii)) {
                h.t = inflater.inflate(R.layout.calatorii_title_locatii, null);
                h.textViewTitlePunctPlecare = (TextView) h.t.findViewById(R.id.textViewTitlePunctPlecare);
                h.textViewTitlePunctSosire = (TextView) h.t.findViewById(R.id.textViewTitlePunctSosire);
                h.textViewTitlePunctPlecare.setText(calatorie.getPunctPlecare());
                h.textViewTitlePunctSosire.setText(calatorie.getPunctSosire());
                h.linearLayoutTitle.addView(h.t);
            }
        }

        h.textViewNumarCalatorie.setText("Calatoria " + String.valueOf(calatorie.getId()));
        h.textViewNume.setText(calatorie.getNume());
        h.textViewPunctPlecare.setText(calatorie.getPunctPlecare());
        h.textViewPunctSosire.setText(calatorie.getPunctSosire());
        h.textViewPret.setText(String.valueOf(calatorie.getPret()) + " lei");
        h.textViewData.setText(calatorie.getDataPlecare() + ", " + calatorie.getOraPlecare());
        h.textViewDataCerere.setText(calatorie.getDataCerere());
        h.buttonDetalii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, CalatorieInAsteptareDetalii.class);
                it.putExtra("position", position);
                context.startActivity(it);
            }
        });
        h.buttonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, UtilizatorProfilActivity.class);
                it.putExtra("id_utilizator", calatorie.getIdUtilizator());
                it.putExtra("nume", calatorie.getNume());
                context.startActivity(it);
            }
        });
        return v;
    }
}
