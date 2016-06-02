package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Silviu086 on 02.06.2016.
 */
public class CalatoriiFragmentTask extends AsyncTask<String, Integer, CalatoriiHolder> {
    private TaskCompletedCalatorii taskCompleted;
    private CalatoriiHolder calatorii;
    private int id;

    public CalatoriiFragmentTask(int id, TaskCompletedCalatorii taskCompleted) {
        this.id = id;
        this.taskCompleted = taskCompleted;
        calatorii = new CalatoriiHolder();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(CalatoriiHolder calatoriiHolder) {
        super.onPostExecute(calatoriiHolder);
        //progressDialog.dismiss();
        taskCompleted.onTaskCompleted(calatoriiHolder);
    }

    @Override
    protected CalatoriiHolder doInBackground(String... params) {
        try{
            URL url = new URL(UrlLinks.URL_CAUTA_CALATORIILE_MELE);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id_account", String.valueOf(id))
                    .build().getEncodedQuery();

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            is.close();
            con.disconnect();

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArrayAdaugate = jsonObject.getJSONArray("calatorii_adaugate");
            JSONArray jsonArrayInAsteptare = jsonObject.getJSONArray("calatorii_in_asteptare");
            JSONArray jsonArrayConfirmate = jsonObject.getJSONArray("calatorii_confirmate");

            for(int i=0;i<jsonArrayAdaugate.length();i++){
                CalatorieAdaugataBuilder builder = new CalatorieAdaugataBuilder();
                JSONObject ob = jsonArrayAdaugate.getJSONObject(i);

                CalatorieAdaugata c = builder
                        .setId(Integer.valueOf(ob.getString("id")))
                        .setDataCreare(ob.getString("data_creare"))
                        .setPunctPlecare(ob.getString("punct_plecare"))
                        .setPunctSosire(ob.getString("punct_sosire"))
                        .setPret(Integer.valueOf(ob.getString("pret")))
                        .setDataPlecare(ob.getString("data_plecare"))
                        .setOraPlecare(ob.getString("ora_plecare"))
                        .setLocuriDisponibile(Integer.valueOf(ob.getString("locuri_disponibile")))
                        .setMarcaMasina(ob.getString("marca_masina"))
                        .setModelMasina(ob.getString("model_masina"))
                        .setAnFabricatie(Integer.valueOf(ob.getString("an_fabricatie")))
                        .setExperientaAuto(ob.getString("experienta_auto"))
                        .setNivelConfort(ob.getString("nivel_confort"))
                        .setMarimeBagaj(ob.getString("marime_bagaj"))
                        .setDurataCalatorie(ob.getString("durata_calatorie"))
                        .setDistantaCalatorie(ob.getString("distanta_calatorie"))
                        .setPasageriInAsteptare(ob.getString("pasageri_in_asteptare"))
                        .setPasageriConfirmati(ob.getString("pasageri_confirmati"))
                        .build();
                JSONArray jsonInAsteptare = ob.getJSONArray("in_asteptare");
                JSONArray jsonConfirmate = ob.getJSONArray("confirmate");

                for(int j=0;j<jsonInAsteptare.length();j++){
                    JSONObject obj = jsonInAsteptare.getJSONObject(j);
                    c.addPasagerConfirmat(obj.getString("id"), obj.getString("email"), obj.getString("nume"), obj.getString("data"));
                }

                for(int j=0;j<jsonArrayConfirmate.length();j++){
                    JSONObject obj = jsonConfirmate.getJSONObject(j);
                    c.addPasagerConfirmat(obj.getString("id"), obj.getString("email"), obj.getString("nume"), obj.getString("data"));
                }
                calatorii.calatoriiAdaugate.add(c);
            }

            for(int i=0;i<jsonArrayInAsteptare.length();i++) {
                CalatorieInAsteptareBuilder builder = new CalatorieInAsteptareBuilder();
                JSONObject ob = jsonArrayInAsteptare.getJSONObject(i);

                CalatorieInAsteptare c = builder
                        .setId(Integer.valueOf(ob.getString("id")))
                        .setDataCreare(ob.getString("data_creare"))
                        .setPunctPlecare(ob.getString("punct_plecare"))
                        .setPunctSosire(ob.getString("punct_sosire"))
                        .setPret(Integer.valueOf(ob.getString("pret")))
                        .setDataPlecare(ob.getString("data_plecare"))
                        .setOraPlecare(ob.getString("ora_plecare"))
                        .setLocuriDisponibile(Integer.valueOf(ob.getString("locuri_disponibile")))
                        .setMarcaMasina(ob.getString("marca_masina"))
                        .setModelMasina(ob.getString("model_masina"))
                        .setAnFabricatie(Integer.valueOf(ob.getString("an_fabricatie")))
                        .setExperientaAuto(ob.getString("experienta_auto"))
                        .setNivelConfort(ob.getString("nivel_confort"))
                        .setMarimeBagaj(ob.getString("marime_bagaj"))
                        .setDurataCalatorie(ob.getString("durata_calatorie"))
                        .setDistantaCalatorie(ob.getString("distanta_calatorie"))
                        .setDataCerere(ob.getString("data"))
                        .setNume(ob.getString("nume"))
                        .build();
                calatorii.calatoriiInAsteptare.add(c);
            }

            for(int i=0;i<jsonArrayConfirmate.length();i++){
                CalatorieConfirmataBuilder builder = new CalatorieConfirmataBuilder();
                JSONObject ob = jsonArrayConfirmate.getJSONObject(i);

                CalatorieConfirmata c = builder
                        .setId(Integer.valueOf(ob.getString("id")))
                        .setDataCreare(ob.getString("data_creare"))
                        .setPunctPlecare(ob.getString("punct_plecare"))
                        .setPunctSosire(ob.getString("punct_sosire"))
                        .setPret(Integer.valueOf(ob.getString("pret")))
                        .setDataPlecare(ob.getString("data_plecare"))
                        .setOraPlecare(ob.getString("ora_plecare"))
                        .setLocuriDisponibile(Integer.valueOf(ob.getString("locuri_disponibile")))
                        .setMarcaMasina(ob.getString("marca_masina"))
                        .setModelMasina(ob.getString("model_masina"))
                        .setAnFabricatie(Integer.valueOf(ob.getString("an_fabricatie")))
                        .setExperientaAuto(ob.getString("experienta_auto"))
                        .setNivelConfort(ob.getString("nivel_confort"))
                        .setMarimeBagaj(ob.getString("marime_bagaj"))
                        .setDurataCalatorie(ob.getString("durata_calatorie"))
                        .setDistantaCalatorie(ob.getString("distanta_calatorie"))
                        .build();
                JSONArray jsonAltiPasageri = ob.getJSONArray("alti_pasageri");

                for(int j=0;j<jsonAltiPasageri.length();j++){
                    JSONObject obj = jsonAltiPasageri.getJSONObject(j);
                    c.addPasager(obj.getString("id"), obj.getString("email"), obj.getString("nume"), obj.getString("data"));
                }
                calatorii.calatoriiConfirmate.add(c);
            }
        }catch (Exception ex){
            Log.e("CalatoriiFragmentTask", ex.getMessage());
        }
        return calatorii;
    }
}
