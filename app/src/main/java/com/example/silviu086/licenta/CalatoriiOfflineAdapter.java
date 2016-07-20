package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Silviu086 on 26.05.2016.
 */
public class CalatoriiOfflineAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Calatorie> listaCalatorii;
    private ArrayList<Account> listaConturi;


    public CalatoriiOfflineAdapter(Context context, ArrayList<Calatorie> listaCalatorii, ArrayList<Account> listaConturi) {
        this.context = context;
        this.listaCalatorii = listaCalatorii;
        this.listaConturi = listaConturi;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaCalatorii.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCalatorii.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaCalatorii.get(position).getId();
    }

    static class Holder {
        TextView textViewNumar;
        TextView textViewDataAdaugare;
        ImageView imageViewProfil;
        TextView textViewNume;
        Button buttonDetalii;
        TextView textViewDataPlecare;
        TextView textViewOraPlecare;
        TextView textViewPret;
        TextView textViewLocuriDisponibile;

        public Holder(View v){
            textViewNumar = (TextView) v.findViewById(R.id.textViewNumar);
            textViewDataAdaugare = (TextView) v.findViewById(R.id.textViewDataAdaugare);
            imageViewProfil = (ImageView) v.findViewById(R.id.imageViewProfil);
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            buttonDetalii = (Button) v.findViewById(R.id.buttonDetalii);
            textViewDataPlecare = (TextView) v.findViewById(R.id.textViewDataPlecare);
            textViewOraPlecare = (TextView) v.findViewById(R.id.textViewOraPlecare);
            textViewPret = (TextView) v.findViewById(R.id.textViewPret);
            textViewLocuriDisponibile = (TextView) v.findViewById(R.id.textViewLocuriDisponibile);
        }
    }

    private boolean searchPasager(String pasager, String[] listaPasageri){
        for(int i=0;i<listaPasageri.length;i++){
            if(listaPasageri[i].equals(pasager)){
                return true;
            }
        }
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Holder h = null;
        final Calatorie calatorie = listaCalatorii.get(position);
        final Account account = listaConturi.get(position);

        if (convertView == null) {
            v = inflater.inflate(R.layout.calatorii_offline_list, null);
            h = new Holder(v);
            v.setTag(h);
        } else {
            h = (Holder) v.getTag();
        }

        final File mypath = new File(context.getFilesDir(), "photo_" + String.valueOf(account.getId()) + ".png");
        if(mypath.exists()){
            Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
            h.imageViewProfil.setBackground(photo);
        }
        h.textViewNumar.setText(String.valueOf(position + 1));
        h.textViewDataAdaugare.setText(calatorie.getDataCreare());
        h.textViewNume.setText(account.getNume());
        h.textViewDataPlecare.setText(calatorie.getDataPlecare());
        h.textViewOraPlecare.setText(calatorie.getOraPlecare());
        h.textViewPret.setText(String.valueOf(calatorie.getPret()) + " lei");
        h.textViewLocuriDisponibile.setText(String.valueOf(calatorie.getLocuriDisponibile()) + " locuri");

        h.buttonDetalii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CalatorieDetaliiActivity.class);
                intent.putExtra("calatorie", calatorie);
                intent.putExtra("account", account);
                context.startActivity(intent);
            }
        });
        return v;
    }
}
