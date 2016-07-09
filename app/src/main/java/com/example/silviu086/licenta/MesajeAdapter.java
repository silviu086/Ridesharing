package com.example.silviu086.licenta;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Silviu086 on 08.07.2016.
 */
public class MesajeAdapter extends BaseAdapter{
    private Context context;
    private List<MesajeTopic> listaMesajeTopics;
    private LayoutInflater inflater;

    public MesajeAdapter(Context context, List<MesajeTopic> listaTopics){
        this.context = context;
        this.listaMesajeTopics = listaTopics;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class HolderMesaje{
        TextView textViewNume;
        TextView textViewNumarCalatorie;
        TextView textViewNumarMesaje;
        TextView textViewData;
        TextView textViewUltimulMesaj;
        Button buttonDetaliiConversatie;

        public HolderMesaje(View v){
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewNumarCalatorie = (TextView) v.findViewById(R.id.textViewNumarCalatorieMesaje);
            textViewNumarMesaje = (TextView) v.findViewById(R.id.textViewNumarMesaje);
            textViewData = (TextView) v.findViewById(R.id.textViewData);
            textViewUltimulMesaj = (TextView) v.findViewById(R.id.textViewUltimulMesaj);
            buttonDetaliiConversatie = (Button) v.findViewById(R.id.buttonDetaliiConversatie);
        }
    }

    @Override
    public int getCount() {
        return listaMesajeTopics.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMesajeTopics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaMesajeTopics.get(position).getId();
    }

    public boolean haveUnreadedMessage(List<Mesaj> lista){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getCitit() == 0 && lista.get(i).getIdAccount() != NavigationActivity.account.getId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View v = convertView;
        final HolderMesaje h;
        final MesajeTopic topic = listaMesajeTopics.get(position);

        if (convertView == null) {
            v = inflater.inflate(R.layout.mesaje_list, null);
            h = new HolderMesaje(v);
            v.setTag(h);
        } else {
            h = (HolderMesaje) v.getTag();
        }

        if(topic.getIdDestinatar() != NavigationActivity.account.getId()){
            h.textViewNume.setText(topic.getNumeDestinatar());
        }else{
            h.textViewNume.setText(topic.getNumeExpeditor());
        }
        h.textViewNumarCalatorie.setText("Calatoria " + String.valueOf(topic.getIdCalatorie()));


        MesajeMarcareCititTask mesajeMarcareCititTask = null;
        if(haveUnreadedMessage(topic.getListaMesaje())){
            h.textViewNumarMesaje.setBackground(context.getResources().getDrawable(R.drawable.mesaj_nou_shape));
            h.textViewNumarMesaje.setText("mesaj nou");
            int idAccount;
            if (NavigationActivity.account.getId() == topic.getIdExpeditor()) {
                idAccount = topic.getIdDestinatar();
            } else {
                idAccount = topic.getIdExpeditor();
            }
            mesajeMarcareCititTask = new MesajeMarcareCititTask(idAccount, topic.getId(), new TaskCompleted() {
                @Override
                public void onTaskCompleted(String result) {
                    try {
                        JSONObject json = new JSONObject(result);
                        int success = json.getInt("success");
                        if (success == 1) {
                            Intent it = new Intent(context, MesajeConversatieActivity.class);
                            it.putExtra("conversatie", topic);
                            context.startActivity(it);
                        } else {
                            Toast.makeText(context, "Nu s-a putut citii conversatia!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Log.e("MesajeAdapter", ex.getMessage());
                    }
                }
            });
        }else if(topic.getListaMesaje().size() == 1){
            h.textViewNumarMesaje.setText("un mesaj");
            h.textViewNumarMesaje.setBackground(context.getResources().getDrawable(R.drawable.mesaje_numar_shape));
        }else{
            h.textViewNumarMesaje.setText(String.valueOf(topic.getListaMesaje().size() + " mesaje"));
            h.textViewNumarMesaje.setBackground(context.getResources().getDrawable(R.drawable.mesaje_numar_shape));
        }
        if(NavigationActivity.account.getId() == topic.getListaMesaje().get(topic.getListaMesaje().size()-1).getIdAccount()){
            h.textViewUltimulMesaj.setText("Eu: " + topic.getListaMesaje().get(topic.getListaMesaje().size() - 1).getMesaj());
        }
        else{
            h.textViewUltimulMesaj.setText(topic.getListaMesaje().get(topic.getListaMesaje().size()-1).getNume() + ": " + topic.getListaMesaje().get(topic.getListaMesaje().size() - 1).getMesaj());
        }
        h.textViewData.setText("Ultimul mesaj (" + topic.getListaMesaje().get(topic.getListaMesaje().size() - 1).getData() + ")");
        final MesajeMarcareCititTask finalMesajeMarcareCititTask = mesajeMarcareCititTask;
        h.buttonDetaliiConversatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalMesajeMarcareCititTask == null){
                    Intent it = new Intent(context, MesajeConversatieActivity.class);
                    it.putExtra("conversatie", topic);
                    context.startActivity(it);
                }
                else{
                    finalMesajeMarcareCititTask.execute();
                }
            }
        });
        return v;
    }
}
