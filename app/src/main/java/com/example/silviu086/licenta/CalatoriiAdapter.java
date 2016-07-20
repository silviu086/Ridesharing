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
public class CalatoriiAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Calatorie> listaCalatorii;
    private ArrayList<Account> listaConturi;


    public CalatoriiAdapter(Context context, ArrayList<Calatorie> listaCalatorii, ArrayList<Account> listaConturi) {
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
        Button buttonMerg;

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
            buttonMerg = (Button) v.findViewById(R.id.buttonMerg);
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
            v = inflater.inflate(R.layout.calatorii_list, null);
            h = new Holder(v);
            v.setTag(h);
        } else {
            h = (Holder)v.getTag();
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

        final String[] accountInAsteptare = calatorie.getPasageriInAsteptare().split(",");
        final String[] accountConfirmat = calatorie.getPasageriConfirmati().split(",");
        if(searchPasager(String.valueOf(NavigationActivity.account.getId()), accountInAsteptare)){
            h.buttonMerg.setText("In asteptare");
            h.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
            h.buttonMerg.setEnabled(false);
        }

        if(searchPasager(String.valueOf(NavigationActivity.account.getId()), accountConfirmat)){
            h.buttonMerg.setText("Confirmata");
            h.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
            h.buttonMerg.setEnabled(false);
        }

        if(calatorie.getLocuriDisponibile() == 0){
            h.buttonMerg.setText("Locuri ocupate");
            h.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
            h.buttonMerg.setEnabled(false);
        }

        if(NavigationActivity.account.getId() == account.getId()){
            h.buttonMerg.setText("Calatoria ta");
            h.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
            h.buttonMerg.setEnabled(false);
        }

        final Holder finalH1 = h;
        h.buttonMerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalH1.buttonMerg.setEnabled(false);
                finalH1.buttonMerg.setText("Trimitere cerere...");
                finalH1.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
                CalatorieTask calatorieTask = new CalatorieTask(account.getId(), listaCalatorii.get(position), new TaskCompleted() {
                    @Override
                    public void onTaskCompleted(String result) {
                        if(result.equals("sent|adaugat")){
                            Toast.makeText(context, "O cerere a fost trimisa catre " + account.getNume() + "!", Toast.LENGTH_LONG).show();
                            finalH1.buttonMerg.setText("In asteptare");
                            Handler mainHandler = new Handler(context.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SystemClock.sleep(1000);
                                    NavigationActivity.setFragmentCalatorii(1);
                                }
                            });
                        }
                    }
                });
                calatorieTask.execute();
            }
        });

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
