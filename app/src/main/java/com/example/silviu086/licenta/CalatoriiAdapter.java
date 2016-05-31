package com.example.silviu086.licenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Silviu086 on 26.05.2016.
 */
public class CalatoriiAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Calatorie> listaCalatorii;


    public CalatoriiAdapter(Context context, ArrayList<Calatorie> listaCalatorii) {
        this.context = context;
        this.listaCalatorii = listaCalatorii;
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

    public class Holder {
        TextView textViewAccount;
        TextView textViewTelefon;
        TextView textViewPunctPlecare;
        TextView textViewPunctSosire;
        TextView textViewPret;
        TextView textViewLocuriDisponibile;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewMarca;
        TextView textViewModel;
        TextView textViewAn;
        TextView textViewExperienta;
        TextView textViewConfort;
        TextView textViewBagaj;
        TextView textViewDurataCalatorie;
        TextView textViewDistantaCalatorie;
        Button buttonMerg;
    }

    private void setClipboard(String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        v = convertView;
        if(v == null) {
            v = inflater.inflate(R.layout.calatorii_list, null);
        }
        final Holder h = new Holder();
        h.textViewAccount = (TextView) v.findViewById(R.id.textViewAccount);
        h.textViewTelefon = (TextView) v.findViewById(R.id.textViewTelefon);
        h.textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
        h.textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
        h.textViewPret = (TextView) v.findViewById(R.id.textViewPret);
        h.textViewLocuriDisponibile = (TextView) v.findViewById(R.id.textViewLocuriDisponibile);
        h.textViewDate = (TextView) v.findViewById(R.id.textViewDate);
        h.textViewTime = (TextView) v.findViewById(R.id.textViewTime);
        h.textViewMarca = (TextView) v.findViewById(R.id.textViewMarcaMasina);
        h.textViewModel = (TextView) v.findViewById(R.id.textViewModelMasina);
        h.textViewAn = (TextView) v.findViewById(R.id.textViewAnFabricatie);
        h.textViewExperienta = (TextView) v.findViewById(R.id.textViewExperientaAuto);
        h.textViewConfort = (TextView) v.findViewById(R.id.textViewConfort);
        h.textViewBagaj = (TextView) v.findViewById(R.id.textViewBagaj);
        h.textViewDurataCalatorie = (TextView) v.findViewById(R.id.textViewDurataCalatorie);
        h.textViewDistantaCalatorie = (TextView) v.findViewById(R.id.textViewDistantaCalatorie);
        h.buttonMerg = (Button) v.findViewById(R.id.buttonMerg);


        h.textViewAccount.setText(listaCalatorii.get(position).getNume());
        h.textViewTelefon.setText(listaCalatorii.get(position).getTelefon());
        h.textViewPunctPlecare.setText(listaCalatorii.get(position).getPunctPlecare());
        h.textViewPunctSosire.setText(listaCalatorii.get(position).getPunctSosire());
        h.textViewPret.setText(String.valueOf(listaCalatorii.get(position).getPret()) + " lei");
        h.textViewLocuriDisponibile.setText(String.valueOf(listaCalatorii.get(position).getLocuriDisponibile()) + " locuri");
        h.textViewDate.setText(listaCalatorii.get(position).getDataPlecare());
        h.textViewTime.setText(listaCalatorii.get(position).getOraPlecare());
        h.textViewMarca.setText(listaCalatorii.get(position).getMarcaMasina());
        h.textViewModel.setText(listaCalatorii.get(position).getModelMasina());
        h.textViewAn.setText(String.valueOf(listaCalatorii.get(position).getAnFabricatie()));
        h.textViewExperienta.setText(listaCalatorii.get(position).getExperientaAuto());
        h.textViewConfort.setText("Confort " + listaCalatorii.get(position).getNivelConfort());
        h.textViewBagaj.setText(listaCalatorii.get(position).getMarimeBagaj());
        h.textViewDurataCalatorie.setText(listaCalatorii.get(position).getDurataCalatorie());
        h.textViewDistantaCalatorie.setText(listaCalatorii.get(position).getDistantaCalatorie());

        h.textViewTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(h.textViewTelefon.getText().toString());
                Toast.makeText(context, "Telefon copiat in Clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

        h.buttonMerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ati selectat calatoria " + String.valueOf(listaCalatorii.get(position).getId()), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
