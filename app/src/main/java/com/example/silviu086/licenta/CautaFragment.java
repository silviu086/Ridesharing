package com.example.silviu086.licenta;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CautaFragment extends Fragment {

    private View v;
    private LinearLayout linearLayoutSearch;
    private LinearLayout linearLayoutSearchInfo;
    private AutoCompleteTextView autoCompleteTextViewPlecare;
    private AutoCompleteTextView autoCompleteTextViewSosire;
    private ImageView imageViewMap;
    private GoogleMap map;
    private LinearLayout linearLayoutCauta;
    private LinearLayout linearLayoutInfoCalatorie;
    private TextView textViewPunctPlecare;
    private TextView textViewPunctSosire;
    private TextView textViewDurataCalatorie;
    private TextView textViewDistantaCalatorie;
    private Button buttonCauta;
    private TextView textViewSearchResult;
    private ListView listViewCalatorii;
    private TextView textViewSearchInfo;
    private Button buttonAltaCautare;
    private OraseRomania oraseRomania;
    private ArrayList<Calatorie> listaCalatorii;
    private ArrayList<Account> listaConturi;
    private Account account;
    private boolean showOnMap;

    public CautaFragment() {
        // Required empty public constructor
    }

    public void setAccount(Account account){
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cauta, container, false);

        linearLayoutSearch = (LinearLayout) v.findViewById(R.id.linearLayoutSearch);
        linearLayoutSearchInfo = (LinearLayout) v.findViewById(R.id.linearLayoutSearchInfo);
        autoCompleteTextViewPlecare = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewPlecare);
        autoCompleteTextViewSosire = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewSosire);
        imageViewMap = (ImageView) v.findViewById(R.id.imageViewMap);
        final LinearLayout linearLayoutLocatii = (LinearLayout) v.findViewById(R.id.linearLayoutLocatii);
        linearLayoutCauta = (LinearLayout) v.findViewById(R.id.linearLayoutCauta);
        linearLayoutInfoCalatorie = (LinearLayout) v.findViewById(R.id.linearLayoutInfoCalatorie);
        textViewPunctPlecare = (TextView) v.findViewById(R.id.textViewPunctPlecare);
        textViewPunctSosire = (TextView) v.findViewById(R.id.textViewPunctSosire);
        textViewDurataCalatorie = (TextView) v.findViewById(R.id.textViewDurataCalatorie);
        textViewDistantaCalatorie = (TextView) v.findViewById(R.id.textViewDistantaCalatorie);
        buttonCauta = (Button) v.findViewById(R.id.buttonCauta);
        textViewSearchResult = (TextView) v.findViewById(R.id.textViewSearchResult);
        listViewCalatorii = (ListView) v.findViewById(R.id.listViewCalatorii);
        textViewSearchInfo = (TextView) v.findViewById(R.id.textViewSearchInfo);
        buttonAltaCautare = (Button) v.findViewById(R.id.buttonAltaCautare);

        //final TextView textView13 = (TextView) v.findViewById(R.id.textView13);

        showOnMap = false;
        map = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(44.42677, 26.10254), 4));

        oraseRomania = OraseRomania.getInstance();
        ArrayAdapter<String> arrayAdapterOrase = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                OraseRomania.getInstance().getListaOrase()
        );

        autoCompleteTextViewPlecare.setAdapter(arrayAdapterOrase);
        autoCompleteTextViewPlecare.setThreshold(2);
        autoCompleteTextViewSosire.setAdapter(arrayAdapterOrase);
        autoCompleteTextViewSosire.setThreshold(2);

        autoCompleteTextViewSosire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextViewPlecare.setError(null);
                autoCompleteTextViewSosire.setError(null);
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                if (autoCompleteTextViewPlecare.getText().toString().equals("") || !oraseRomania.find(autoCompleteTextViewPlecare.getText().toString())) {
                    autoCompleteTextViewPlecare.setError("Selectati o locatie valida!");
                }else if(oraseRomania.find(autoCompleteTextViewSosire.getText().toString())) {
                    if(Internet.haveInternet(getContext())){
                        imageViewMap.setVisibility(View.GONE);
                        DirectionsMap directionsMap = new DirectionsMap(
                                map, getContext(),
                                autoCompleteTextViewPlecare.getText().toString(),
                                autoCompleteTextViewSosire.getText().toString(),
                                new TaskCompleted() {@Override public void onTaskCompleted(String result) {}});
                        directionsMap.execute();
                    }else{
                        Toast.makeText(getContext(), "Nu s-a putut realiza traseul, este necesara conexiune la Internet!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    autoCompleteTextViewSosire.setError("Selectati o locatie valida!");
                }
            }
        });

        autoCompleteTextViewSosire.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!oraseRomania.find(autoCompleteTextViewPlecare.getText().toString())) {
                        autoCompleteTextViewPlecare.setError("Selectati o locatie valida!");
                    } else if (oraseRomania.find(autoCompleteTextViewSosire.getText().toString())) {
                        if(Internet.haveInternet(getContext())){
                            imageViewMap.setVisibility(View.GONE);
                            DirectionsMap directionsMap = new DirectionsMap(
                                    map, getContext(),
                                    autoCompleteTextViewPlecare.getText().toString(),
                                    autoCompleteTextViewSosire.getText().toString(),
                                    new TaskCompleted() { @Override public void onTaskCompleted(String result) {}});
                            directionsMap.execute();
                        }else{
                            Toast.makeText(getContext(), "Nu s-a putut realiza traseul, este necesara conexiune la Internet!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        autoCompleteTextViewSosire.setError("Selectati din lista o locatie!");
                    }
                }
                return false;
            }
        });

        buttonCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String punctPlecare = autoCompleteTextViewPlecare.getText().toString();
                String punctSosire = autoCompleteTextViewSosire.getText().toString();
                listViewCalatorii.setAdapter(null);
                if (!oraseRomania.find(punctPlecare)) {
                    autoCompleteTextViewPlecare.setError("Selectati o locatie valida!");
                } else if (!oraseRomania.find(punctSosire)) {
                    autoCompleteTextViewSosire.setError("Selectati o locatie valida!");
                }else if(!Internet.haveInternet(getContext())){
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    builder.append(" ");
                    builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                    builder.append(" Nu exista conexiune la Internet!");
                    Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(getContext(), "Nu s-a putut realiza cautarea, este necesara conexiune la Internet!", Toast.LENGTH_LONG).show();
                } else {
                    //InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    //in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    listaCalatorii = new ArrayList<>();
                    listaConturi = new ArrayList<Account>();
                    CautaCalatoriiTask cautaCalatoriiTask = new CautaCalatoriiTask(punctPlecare, punctSosire, progressDialog, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            try {
                                textViewSearchResult.setVisibility(View.GONE);
                                linearLayoutSearch.setVisibility(View.GONE);
                                linearLayoutSearchInfo.setVisibility(View.VISIBLE);
                                JSONObject jsonObject = new JSONObject(result);
                                int success = jsonObject.getInt("success");
                                if (success == 1) {
                                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                    progressDialog.setTitle("Loading...");
                                    progressDialog.setMessage("Se preiau datele!!");
                                    progressDialog.show();
                                    JSONArray jsonCalatorii = jsonObject.getJSONArray("calatorii");
                                    for (int i = 0; i < jsonCalatorii.length(); i++) {
                                        JSONObject jsonCalatorie = jsonCalatorii.getJSONObject(i);
                                        CalatorieBuilder builder = new CalatorieBuilder();
                                        Calatorie calatorie = builder
                                                .setId(Integer.valueOf(jsonCalatorie.getString("id")))
                                                .setDataCreare(jsonCalatorie.getString("data_creare"))
                                                .setPunctPlecare(jsonCalatorie.getString("punct_plecare"))
                                                .setPunctSosire(jsonCalatorie.getString("punct_sosire"))
                                                .setPret(Integer.valueOf(jsonCalatorie.getString("pret")))
                                                .setDataPlecare(jsonCalatorie.getString("data_plecare"))
                                                .setOraPlecare(jsonCalatorie.getString("ora_plecare"))
                                                .setLocuriDisponibile(Integer.valueOf(jsonCalatorie.getString("locuri_disponibile")))
                                                .setMarcaMasina(jsonCalatorie.getString("marca_masina"))
                                                .setModelMasina(jsonCalatorie.getString("model_masina"))
                                                .setAnFabricatie(Integer.valueOf(jsonCalatorie.getString("an_fabricatie")))
                                                .setExperientaAuto(jsonCalatorie.getString("experienta_auto"))
                                                .setNivelConfort(jsonCalatorie.getString("nivel_confort"))
                                                .setMarimeBagaj(jsonCalatorie.getString("marime_bagaj"))
                                                .setDurataCalatorie(jsonCalatorie.getString("durata_calatorie"))
                                                .setDistantaCalatorie(jsonCalatorie.getString("distanta_calatorie"))
                                                .setPasageriInAsteptare(jsonCalatorie.getString("pasageri_in_asteptare"))
                                                .setPasageriConfirmati(jsonCalatorie.getString("pasageri_confirmati"))
                                                .build();
                                        AccountBuilder accountBuilder = new AccountBuilder();
                                        Account account = accountBuilder
                                                .setId(Integer.valueOf(jsonCalatorie.getString("id_account")))
                                                .setNume(jsonCalatorie.getString("nume"))
                                                .setTelefon(jsonCalatorie.getString("telefon"))
                                                .setEmail(jsonCalatorie.getString("email"))
                                                .setVarsta(Integer.valueOf(jsonCalatorie.getString("varsta")))
                                                .build();
                                        listaCalatorii.add(calatorie);
                                        listaConturi.add(account);
                                    }
                                    for (int i = 0; i < listaConturi.size(); i++) {
                                        final File mypath = new File(getContext().getFilesDir(), "photo_" + String.valueOf(listaConturi.get(i).getId()) + ".png");
                                        final int finalI = i;
                                        ContDownloadPhotoTask pasagerDownloadPhotoTask = new ContDownloadPhotoTask(getContext(), listaConturi.get(i).getId(), new TaskCompletedUpload() {
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
                                                }
                                                if (finalI == listaConturi.size() - 1) {
                                                    progressDialog.dismiss();
                                                    textViewSearchResult.setVisibility(View.GONE);
                                                    linearLayoutSearch.setVisibility(View.GONE);
                                                    linearLayoutSearchInfo.setVisibility(View.VISIBLE);
                                                    linearLayoutInfoCalatorie.setVisibility(View.VISIBLE);
                                                    CalatoriiAdapter adapter = new CalatoriiAdapter(getContext(), listaCalatorii, listaConturi);
                                                    linearLayoutLocatii.setVisibility(View.GONE);
                                                    textViewPunctPlecare.setText(listaCalatorii.get(0).getPunctPlecare());
                                                    textViewPunctSosire.setText(listaCalatorii.get(0).getPunctSosire());
                                                    textViewDurataCalatorie.setText(listaCalatorii.get(0).getDurataCalatorie());
                                                    textViewDistantaCalatorie.setText(listaCalatorii.get(0).getDistantaCalatorie());
                                                    listViewCalatorii.setAdapter(adapter);
                                                    if (listaCalatorii.size() == 1) {
                                                        textViewSearchInfo.setText("o calatorie gasita");
                                                    } else {
                                                        textViewSearchInfo.setText(listaCalatorii.size() + " calatorii gasite");
                                                    }
                                                }
                                            }
                                        });
                                        pasagerDownloadPhotoTask.execute();
                                    }
                                } else {
                                    linearLayoutInfoCalatorie.setVisibility(View.GONE);
                                    textViewSearchResult.setVisibility(View.VISIBLE);
                                    textViewSearchInfo.setText("nicio calatorie gasita");
                                }
                            } catch (JSONException e) {
                                Log.e("CautaFragment", e.getMessage());
                            }

                        }
                    });
                    cautaCalatoriiTask.execute();
                }
            }
        });

        buttonAltaCautare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewPlecare.setText("");
                autoCompleteTextViewSosire.setText("");
                //linearLayoutCauta.setVisibility(View.GONE);
                linearLayoutSearchInfo.setVisibility(View.GONE);
                linearLayoutInfoCalatorie.setVisibility(View.GONE);
                imageViewMap.setVisibility(View.VISIBLE);
                linearLayoutLocatii.setVisibility(View.VISIBLE);
                linearLayoutSearch.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }


    public static CautaFragment newInstance(Account account){
        CautaFragment cautaFragment = new CautaFragment();
        cautaFragment.setAccount(account);
        return cautaFragment;
    }
}
