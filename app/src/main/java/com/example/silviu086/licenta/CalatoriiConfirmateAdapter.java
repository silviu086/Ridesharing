package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
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
 * Created by Silviu086 on 05.07.2016.
 */
public class CalatoriiConfirmateAdapter extends BaseAdapter {
    private Context context;
    private List<CalatorieConfirmata> calatorii;
    private LayoutInflater inflater;

    public CalatoriiConfirmateAdapter(Context context, List<CalatorieConfirmata> calatoriiConfirmate) {
        this.context = context;
        this.calatorii = calatoriiConfirmate;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class HolderConfirmate{
        TextView textViewNumarCalatorie;
        TextView textViewNume;
        TextView textViewPunctPlecare;
        TextView textViewPunctSosire;
        TextView textViewData;
        Button buttonDetalii;
        Button buttonProfil;
        LinearLayout linearLayoutFaraPasageri;
        LinearLayout linearLayoutPasageri;
        LinearLayout linearLayoutTitle;
        View t;
        TextView textViewTitleData;
        TextView textViewTitlePunctPlecare;
        TextView textViewTitlePunctSosire;

        public HolderConfirmate(View v){
            textViewNumarCalatorie = (TextView) v.findViewById(R.id.textViewNumarCalatorieConfirmate);
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
            textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
            textViewData = (TextView) v.findViewById(R.id.textViewData);
            buttonDetalii = (Button) v.findViewById(R.id.buttonDetalii);
            buttonProfil = (Button) v.findViewById(R.id.buttonProfil);
            linearLayoutFaraPasageri = (LinearLayout) v.findViewById(R.id.linearLayoutFaraPasageri);
            linearLayoutPasageri = (LinearLayout) v.findViewById(R.id.linearLayoutPasageri);
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
        HolderConfirmate h = null;
        final CalatorieConfirmata calatorie = calatorii.get(position);

        if (convertView == null) {
            v = inflater.inflate(R.layout.calatorii_confirmate_list, null);
            h = new HolderConfirmate(v);
            v.setTag(h);
        } else {
            h = (HolderConfirmate)v.getTag();
        }

        h.linearLayoutTitle.removeAllViews();
        if(CalatoriiFragment.TIP_AFISARE == 2) {
            if (cautaId(calatorie.getId(), CalatoriiFragment.calatoriiTitleConfirmateData)) {
                h.t = inflater.inflate(R.layout.calatorii_title_data, null);
                h.textViewTitleData = (TextView) h.t.findViewById(R.id.textViewTitleData);
                h.textViewTitleData.setText(calatorie.getDataPlecare());
                h.linearLayoutTitle.addView(h.t);
            }
        }else if(CalatoriiFragment.TIP_AFISARE == 1) {
            if(cautaId(calatorie.getId(), CalatoriiFragment.calatoriiTitleConfirmateLocatii)) {
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
        h.textViewData.setText(calatorie.getDataPlecare() + ", " + calatorie.getOraPlecare());
        h.buttonDetalii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, CalatorieConfirmataDetaliiActivity.class);
                it.putExtra("position", position);
                context.startActivity(it);
            }
        });
        h.buttonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, UtilizatorProfilActivity.class);
                it.putExtra("nume", calatorie.getNume());
                it.putExtra("id_utilizator", calatorie.getIdUtilizator());
                context.startActivity(it);
            }
        });
        if(calatorie.getListaAltiPasageri().size() > 0){
            if(calatorie.getListaAltiPasageri().size() == 1){
                if(!calatorie.getListaAltiPasageri().get(0).getId().equals(String.valueOf(NavigationActivity.account.getId()))){
                    h.linearLayoutPasageri.removeAllViews();
                    h.linearLayoutFaraPasageri.setVisibility(View.GONE);
                    View border = inflater.inflate(R.layout.calatorii_confirmate_pasageri_border, null);
                    h.linearLayoutPasageri.addView(border);
                    for(int i=0;i<calatorie.getListaAltiPasageri().size();i++){
                        View view = inflater.inflate(R.layout.calatorii_confirmate_pasageri_list, null);
                        TextView textViewPasagerNumar = (TextView) view.findViewById(R.id.textViewPasagerNumar);
                        final TextView textViewPasagerNume = (TextView) view.findViewById(R.id.textViewPasagerNume);
                        textViewPasagerNumar.setText(String.valueOf(i + 1));
                        textViewPasagerNume.setText(calatorie.getListaAltiPasageri().get(i).getNume());
                        h.linearLayoutPasageri.addView(view);
                        textViewPasagerNume.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "Profil " + textViewPasagerNume.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }else{
                h.linearLayoutPasageri.removeAllViews();
                h.linearLayoutFaraPasageri.setVisibility(View.GONE);
                View border = inflater.inflate(R.layout.calatorii_confirmate_pasageri_border, null);
                h.linearLayoutPasageri.addView(border);
                for(int i=0;i<calatorie.getListaAltiPasageri().size();i++){
                    if(!calatorie.getListaAltiPasageri().get(i).getId().equals(String.valueOf(NavigationActivity.account.getId()))) {
                        View view = inflater.inflate(R.layout.calatorii_confirmate_pasageri_list, null);
                        TextView textViewPasagerNumar = (TextView) view.findViewById(R.id.textViewPasagerNumar);
                        final TextView textViewPasagerNume = (TextView) view.findViewById(R.id.textViewPasagerNume);
                        textViewPasagerNumar.setText(String.valueOf(i+1));
                        textViewPasagerNume.setText(calatorie.getListaAltiPasageri().get(i).getNume());
                        h.linearLayoutPasageri.addView(view);
                        final HolderConfirmate finalH = h;
                        textViewPasagerNume.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "Profil " + textViewPasagerNume.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }
        return v;
    }
}
