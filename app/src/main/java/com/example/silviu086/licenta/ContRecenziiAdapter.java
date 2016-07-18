package com.example.silviu086.licenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Silviu086 on 11.07.2016.
 */
public class ContRecenziiAdapter extends BaseAdapter {
    private Context context;
    private List<Recenzie> listaRecenzii;
    private LayoutInflater inflater;

    public ContRecenziiAdapter(Context context, List<Recenzie> recenzii) {
        this.context = context;
        this.listaRecenzii = recenzii;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class HolderRecenzii{
        TextView textViewNume;
        TextView textViewData;
        TextView textViewDescriere;
        RatingBar ratingBar;
        View view;

        public HolderRecenzii(View v){
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewData = (TextView) v.findViewById(R.id.textViewData);
            textViewDescriere = (TextView) v.findViewById(R.id.textViewDescriere);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        }
    }

    @Override
    public int getCount() {
        return listaRecenzii.size();
    }

    @Override
    public Object getItem(int position) {
        return listaRecenzii.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaRecenzii.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        HolderRecenzii h = null;
        Recenzie recenzie = listaRecenzii.get(position);
        if (convertView == null) {
            v = inflater.inflate(R.layout.recenzii_list, null);
            h = new HolderRecenzii(v);
            v.setTag(h);
        } else {
            h = (HolderRecenzii)v.getTag();
        }

        h.textViewNume.setText(recenzie.getNume());
        h.textViewData.setText(recenzie.getData());
        h.textViewDescriere.setText(recenzie.getDescriere());
        h.ratingBar.setRating(recenzie.getScor());
        return v;
    }
}
