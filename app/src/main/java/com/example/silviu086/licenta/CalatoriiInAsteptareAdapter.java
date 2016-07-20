package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
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
        ImageView imageViewProfil;
        TextView textViewNume;
        TextView textViewPunctPlecare;
        TextView textViewPunctSosire;
        TextView textViewPret;
        TextView textViewData;
        TextView textViewDataCerere;
        LinearLayout linearLayoutDetalii;
        LinearLayout linearLayoutTitle;
        View t;
        TextView textViewTitleData;
        TextView textViewTitleLocatii;

        public HolderInAsteptare(View v){
            textViewNumarCalatorie = (TextView) v.findViewById(R.id.textViewNumarCalatorieInAsteptare);
            imageViewProfil = (ImageView) v.findViewById(R.id.imageViewProfil);
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
            textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
            textViewPret = (TextView) v.findViewById(R.id.textViewPret);
            textViewData = (TextView) v.findViewById(R.id.textViewData);
            textViewDataCerere = (TextView) v.findViewById(R.id.textViewDataCerere);
            linearLayoutDetalii = (LinearLayout) v.findViewById(R.id.linearLayoutDetalii);
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

        final File mypath = new File(context.getFilesDir(), "photo_" + String.valueOf(calatorie.getIdUtilizator()) + ".png");
        if (mypath.exists()) {
            Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
            h.imageViewProfil.setBackground(photo);
        }else{
            final HolderInAsteptare finalH = h;
            ContDownloadPhotoTask pasagerDownloadPhotoTask = new ContDownloadPhotoTask(context, calatorie.getIdUtilizator(), new TaskCompletedUpload() {
                @Override
                public void onTaskCompleted(Bitmap bitmap) {
                    if (bitmap != null) {
                        try {
                            FileOutputStream out = new FileOutputStream(mypath);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                            out.flush();
                            out.close();
                        } catch (Exception ex) {
                            Log.e("CalatoriiInAsteptare", ex.getMessage());
                        }
                        Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
                        finalH.imageViewProfil.setBackground(photo);
                    }
                }});
            pasagerDownloadPhotoTask.execute();
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
                h.textViewTitleLocatii = (TextView) h.t.findViewById(R.id.textViewTitleLocatii);
                h.textViewTitleLocatii.setText(calatorie.getPunctPlecare().split(",")[0] + " - " + calatorie.getPunctSosire().split(",")[0]);
                h.linearLayoutTitle.addView(h.t);
            }
        }

        h.textViewNumarCalatorie.setText("Calatoria " + String.valueOf(calatorie.getId()));
        h.textViewNume.setText(calatorie.getNume());
        h.textViewPunctPlecare.setText(calatorie.getPunctPlecare());
        h.textViewPunctSosire.setText(calatorie.getPunctSosire());
        h.textViewPret.setText(String.valueOf(calatorie.getPret()) + " lei");
        h.textViewData.setText(calatorie.getDataPlecare() + ", " + calatorie.getOraPlecare());
        h.textViewDataCerere.setText("Cerere trimisa pe " + calatorie.getDataCerere());
        h.linearLayoutDetalii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, CalatorieInAsteptareDetalii.class);
                it.putExtra("position", position);
                context.startActivity(it);
            }
        });

        return v;
    }
}
