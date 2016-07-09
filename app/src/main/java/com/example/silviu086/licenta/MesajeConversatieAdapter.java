package com.example.silviu086.licenta;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Silviu086 on 09.07.2016.
 */
public class MesajeConversatieAdapter extends BaseAdapter {
    private Context context;
    private List<Mesaj> listaMesaje;
    private LayoutInflater inflater;

    public MesajeConversatieAdapter(Context context, List<Mesaj> listaMesaje){
        this.context = context;
        this.listaMesaje = listaMesaje;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class HolderConversatie{
        TextView textViewNume;
        TextView textViewMesaj;
        TextView textViewData;
        LinearLayout linearLayoutNume;

        public HolderConversatie(View v){
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewMesaj = (TextView) v.findViewById(R.id.textViewMesaj);
            textViewData = (TextView) v.findViewById(R.id.textViewData);
            linearLayoutNume = (LinearLayout) v.findViewById(R.id.linearLayoutNume);
        }
    }

    @Override
    public int getCount() {
        return listaMesaje.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMesaje.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaMesaje.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        HolderConversatie h;
        final Mesaj mesaj = listaMesaje.get(position);

        if (convertView == null) {
            v = inflater.inflate(R.layout.mesaje_conversatie_list, null);
            h = new HolderConversatie(v);
            v.setTag(h);
        } else {
            h = (HolderConversatie) v.getTag();
        }
        if(NavigationActivity.account.getId() == mesaj.getIdAccount()){
            h.linearLayoutNume.setGravity(Gravity.RIGHT);
            h.textViewNume.setTextColor(context.getResources().getColor(R.color.colorWhite));
            h.textViewNume.setBackgroundColor(context.getResources().getColor(R.color.colorBlue));
            h.textViewMesaj.setGravity(Gravity.RIGHT);
            h.textViewData.setGravity(Gravity.RIGHT);
            h.textViewData.setText(mesaj.getData());
            h.textViewNume.setText("Eu");
        }else{
            h.linearLayoutNume.setGravity(Gravity.LEFT);
            h.textViewNume.setTextColor(context.getResources().getColor(R.color.colorGrayDarker));
            h.textViewNume.setBackgroundColor(context.getResources().getColor(R.color.colorSilverDarker));
            h.textViewMesaj.setGravity(Gravity.LEFT);
            h.textViewData.setGravity(Gravity.LEFT);
            h.textViewData.setText(mesaj.getData());
            h.textViewNume.setText(mesaj.getNume());
        }
        h.textViewMesaj.setText(mesaj.getMesaj());
        return v;
    }
}
