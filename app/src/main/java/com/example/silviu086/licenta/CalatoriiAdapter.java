package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
        TextView textViewNume;
        Button buttonDetalii;
        TextView textViewDataPlecare;
        TextView textViewOraPlecare;
        TextView textViewPret;
        TextView textViewLocuriDisponibile;
        TextView buttonMerg;
    }

    private boolean searchPasageriInAsteptare(String pasager, String[] listaPasageri){
        for(int i=0;i<listaPasageri.length;i++){
            if(listaPasageri[i].equals(pasager)){
                return true;
            }
        }
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        v = inflater.inflate(R.layout.calatorii_list, null);
        final Calatorie calatorie = listaCalatorii.get(position);
        final Account account = listaConturi.get(position);
        final Holder h = new Holder();
        h.textViewNumar = (TextView) v.findViewById(R.id.textViewNumar);
        h.textViewDataAdaugare = (TextView) v.findViewById(R.id.textViewDataAdaugare);
        h.textViewNume = (TextView) v.findViewById(R.id.textViewNume);
        h.buttonDetalii = (Button) v.findViewById(R.id.buttonDetalii);
        h.textViewDataPlecare = (TextView) v.findViewById(R.id.textViewDataPlecare);
        h.textViewOraPlecare = (TextView) v.findViewById(R.id.textViewOraPlecare);
        h.textViewPret = (TextView) v.findViewById(R.id.textViewPret);
        h.textViewLocuriDisponibile = (TextView) v.findViewById(R.id.textViewLocuriDisponibile);
        h.buttonMerg = (Button) v.findViewById(R.id.buttonMerg);

        h.textViewNumar.setText(String.valueOf(position + 1));
        h.textViewDataAdaugare.setText(calatorie.getDataCreare());
        h.textViewNume.setText(account.getNume());
        h.textViewDataPlecare.setText(calatorie.getDataPlecare());
        h.textViewOraPlecare.setText(calatorie.getOraPlecare());
        h.textViewPret.setText(String.valueOf(calatorie.getPret()) + " lei");
        h.textViewLocuriDisponibile.setText(String.valueOf(calatorie.getLocuriDisponibile()) + " locuri");

        final String[] accountInAsteptare = calatorie.getPasageriInAsteptare().split(",");
        if(searchPasageriInAsteptare(String.valueOf(NavigationActivity.account.getId()), accountInAsteptare)){
            h.buttonMerg.setText("In asteptare");
            h.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
            h.buttonMerg.setEnabled(false);
        }

        if(NavigationActivity.account.getId() == account.getId()){
            h.buttonMerg.setText("Calatoria ta");
            h.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
            h.buttonMerg.setEnabled(false);
        }

        h.buttonMerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalatorieTask calatorieTask = new CalatorieTask(listaCalatorii.get(position), new TaskCompleted() {
                    @Override
                    public void onTaskCompleted(String result) {
                        if(result.equals("sent|adaugat")){
                            Toast.makeText(context, "O cerere a fost trimisa catre " + account.getNume() + "!", Toast.LENGTH_LONG).show();
                            h.buttonMerg.setText("In asteptare");
                            h.buttonMerg.setTextColor(context.getResources().getColor(R.color.colorGray));
                            h.buttonMerg.setEnabled(false);
                            Handler mainHandler = new Handler(context.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SystemClock.sleep(1000);
                                    NavigationActivity.setFragment(4);
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
