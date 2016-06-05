package com.example.silviu086.licenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Silviu086 on 03.06.2016.
 */
public class CalatoriiAdaugateAdapter extends BaseAdapter {

    private Context context;
    private List<CalatorieAdaugata> calatorii;
    private LayoutInflater inflater;

    public CalatoriiAdaugateAdapter(Context context, List<CalatorieAdaugata> calatoriiAdaugate) {
        this.context = context;
        this.calatorii = calatoriiAdaugate;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class Holder{

    }

    @Override
    public int getCount() {
        return calatorii.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        v = inflater.inflate(R.layout.calatorii_list, null);
        final CalatorieAdaugata calatorie = calatorii.get(position);
        final List<HolderPasageri> pasageriInAsteptare = calatorie.getListaPasageriInAsteptare();
        final List<HolderPasageri> pasageriConfirmati = calatorie.getListaPasageriConfirmati();

        return v;
    }

    @Override
    public CalatorieAdaugata getItem(int position) {
        return calatorii.get(position);
    }

    @Override
    public long getItemId(int position) {
        return calatorii.get(position).getId();
    }
}
