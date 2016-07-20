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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
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

    class HolderAdaugate{
        TextView textViewNumarCalatorie;
        TextView textViewData;
        TextView textViewPunctPlecare;
        TextView textViewPunctSosire;
        TextView textViewDataCalatorie;
        TextView textViewLocuri;
        TextView textViewCereriInAsteptare;
        TextView textViewCereriConfirmate;
        LinearLayout linearLayoutDetalii;
        LinearLayout linearLayoutTitle;
        View t;
        TextView textViewTitleData;
        TextView textViewTitleLocatii;

        public HolderAdaugate(View v){
            textViewNumarCalatorie = (TextView) v.findViewById(R.id.textviewNumarCalatorie);
            textViewData = (TextView) v.findViewById(R.id.textViewData);
            textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
            textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
            textViewDataCalatorie = (TextView) v.findViewById(R.id.textViewDataCalatorie);
            textViewLocuri = (TextView) v.findViewById(R.id.textViewLocuri);
            textViewCereriInAsteptare = (TextView) v.findViewById(R.id.textViewCereriInAsteptare);
            textViewCereriConfirmate = (TextView) v.findViewById(R.id.textViewCereriConfirmate);
            linearLayoutDetalii = (LinearLayout) v.findViewById(R.id.linearLayoutDetalii);
            linearLayoutTitle = (LinearLayout) v.findViewById(R.id.linearLayoutTitle);
        }
    }

    @Override
    public int getCount() {
        return calatorii.size();
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
        HolderAdaugate hCalatorie = null;
        final CalatorieAdaugata calatorie = calatorii.get(position);

        if (convertView == null) {
            v = inflater.inflate(R.layout.calatorii_adaugate_list, null);
            hCalatorie = new HolderAdaugate(v);
            v.setTag(hCalatorie);
        } else {
            hCalatorie = (HolderAdaugate)v.getTag();
        }

        hCalatorie.linearLayoutTitle.removeAllViews();
        if(CalatoriiFragment.TIP_AFISARE == 2) {
            if (cautaId(calatorie.getId(), CalatoriiFragment.calatoriiTitleAdaugateData)) {
                hCalatorie.t = inflater.inflate(R.layout.calatorii_title_data, null);
                hCalatorie.textViewTitleData = (TextView) hCalatorie.t.findViewById(R.id.textViewTitleData);
                hCalatorie.textViewTitleData.setText(calatorie.getDataPlecare());
                hCalatorie.linearLayoutTitle.addView(hCalatorie.t);
            }
        }else if(CalatoriiFragment.TIP_AFISARE == 1) {
            if(cautaId(calatorie.getId(), CalatoriiFragment.calatoriiTitleAdaugateLocatii)) {
                hCalatorie.t = inflater.inflate(R.layout.calatorii_title_locatii, null);
                hCalatorie.textViewTitleLocatii = (TextView) hCalatorie.t.findViewById(R.id.textViewTitleLocatii);
                hCalatorie.textViewTitleLocatii.setText(calatorie.getPunctPlecare().split(",")[0] + " - " + calatorie.getPunctSosire().split(",")[0]);
                hCalatorie.linearLayoutTitle.addView(hCalatorie.t);
            }
        }
        hCalatorie.textViewNumarCalatorie.setText("Calatoria " + calatorie.getId());
        hCalatorie.textViewData.setText(calatorie.getDataCreare());
        hCalatorie.textViewPunctPlecare.setText(calatorie.getPunctPlecare());
        hCalatorie.textViewPunctSosire.setText(calatorie.getPunctSosire());
        hCalatorie.textViewDataCalatorie.setText(calatorie.getDataPlecare() + ", " + calatorie.getOraPlecare());
        hCalatorie.textViewLocuri.setText(String.valueOf(calatorie.getLocuriDisponibile()) + " locuri");
        hCalatorie.textViewCereriInAsteptare.setText(String.valueOf(calatorie.getListaPasageriInAsteptare().size()));
        hCalatorie.textViewCereriConfirmate.setText(String.valueOf(calatorie.getListaPasageriConfirmati().size()));


        hCalatorie.linearLayoutDetalii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, CalatorieAdaugataDetaliiActivity.class);
                it.putExtra("position", position);
                context.startActivity(it);
            }
        });

        /*
        if(pasageriInAsteptare.size() != 0 || pasageriConfirmati.size() != 0){
            hCalatorie.linearLayoutFaraCereri.setVisibility(View.GONE);
            for(int i=0;i<pasageriInAsteptare.size();i++){
                View view = inflater.inflate(R.layout.calatorii_adaugate_cereri_layout, null);
                final HolderCerere hCerere = new HolderCerere();
                hCerere.textViewNume = (TextView) view.findViewById(R.id.textViewNume);
                hCerere.textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
                hCerere.textViewDataCerere = (TextView) view.findViewById(R.id.textViewDataCerere);
                hCerere.buttonConfirma = (Button) view.findViewById(R.id.buttonConfirma);
                hCerere.textViewNume.setText(pasageriInAsteptare.get(i).getNume());
                hCerere.textViewEmail.setText("(" + pasageriInAsteptare.get(i).getEmail() + ")");
                hCerere.textViewDataCerere.setText(pasageriInAsteptare.get(i).getData());
                hCerere.buttonConfirma.setBackground(context.getResources().getDrawable(R.drawable.cauta_layout_shape_8));
                hCerere.buttonConfirma.setTextColor(context.getResources().getColor(R.color.colorWhite));
                hCalatorie.linearLayoutCereri.addView(view);
                final int finalI = i;
                hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(calatorie.getLocuriDisponibile()>0){
                            hCerere.buttonConfirma.setEnabled(false);
                            hCerere.buttonConfirma.setText("Confirmare...");
                            //hCerere.buttonConfirma.setTextColor(context.getResources().getColor(R.color.colorGray));
                            CalatoriiAdaugateConfirmareTask confirmareTask = new CalatoriiAdaugateConfirmareTask(String.valueOf(calatorie.getId()), pasageriInAsteptare.get(finalI).getId(), new TaskCompleted() {
                                @Override
                                public void onTaskCompleted(String result) {
                                    if (result.equals("success")){
                                        calatorie.setLocuriDisponibile(calatorie.getLocuriDisponibile() - 1);
                                        hCalatorie.textViewLocuri.setText(String.valueOf(calatorie.getLocuriDisponibile()));
                                        Toast.makeText(context, "Cererea lui " + pasageriInAsteptare.get(finalI).getNume() + " a fost confirmata!", Toast.LENGTH_LONG).show();
                                        NavigationActivity.setFragmentCalatorii(0);
                                    }else{
                                        hCerere.buttonConfirma.setEnabled(true);
                                        hCerere.buttonConfirma.setText("Confirma");
                                        hCerere.buttonConfirma.setTextColor(context.getResources().getColor(R.color.colorWhite));
                                        Toast.makeText(context, "A aparut o problema la confirmare!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            confirmareTask.execute();
                        }else{
                            Toast.makeText(context, "Numai sunt locuri libere!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            for(int i=0;i<pasageriConfirmati.size();i++){
                View view = inflater.inflate(R.layout.calatorii_adaugate_cereri_layout, null);
                final HolderCerere hCerere = new HolderCerere();
                hCerere.textViewNume = (TextView) view.findViewById(R.id.textViewNume);
                hCerere.textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
                hCerere.textViewDataCerere = (TextView) view.findViewById(R.id.textViewDataCerere);
                hCerere.buttonConfirma = (Button) view.findViewById(R.id.buttonConfirma);
                hCerere.textViewNume.setText(pasageriConfirmati.get(i).getNume());
                hCerere.textViewEmail.setText("(" + pasageriConfirmati.get(i).getEmail() + ")");
                hCerere.textViewDataCerere.setText(pasageriConfirmati.get(i).getData());
                hCerere.buttonConfirma.setBackground(context.getResources().getDrawable(R.drawable.calatorii_adaugate_shape));
                hCerere.buttonConfirma.setTextColor(context.getResources().getColor(R.color.colorGray));
                hCerere.buttonConfirma.setText("Confirmat");
                hCalatorie.linearLayoutCereri.addView(view);
                hCerere.buttonConfirma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Deja confirmat!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }*/
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
