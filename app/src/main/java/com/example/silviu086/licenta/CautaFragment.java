package com.example.silviu086.licenta;


import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CautaFragment extends Fragment {

    private LinearLayout linearLayoutSearch;
    private LinearLayout linearLayoutSearchInfo;
    private AutoCompleteTextView autoCompleteTextViewPlecare;
    private AutoCompleteTextView autoCompleteTextViewSosire;
    private ImageView imageViewMap;
    private GoogleMap map;
    private LinearLayout linearLayoutCauta;
    private Button buttonCauta;
    private TextView textViewSearchResult;
    private ListView listViewCalatorii;
    private TextView textViewSearchInfo;
    private Button buttonAltaCautare;
    private OraseRomania oraseRomania;
    private ArrayList<Calatorie> listaCalatorii;
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
        View v = inflater.inflate(R.layout.fragment_cauta, container, false);

        linearLayoutSearch = (LinearLayout) v.findViewById(R.id.linearLayoutSearch);
        linearLayoutSearchInfo = (LinearLayout) v.findViewById(R.id.linearLayoutSearchInfo);
        autoCompleteTextViewPlecare = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewPlecare);
        autoCompleteTextViewSosire = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewSosire);
        imageViewMap = (ImageView) v.findViewById(R.id.imageViewMap);
        final LinearLayout linearLayoutLocatii = (LinearLayout) v.findViewById(R.id.linearLayoutLocatii);
        linearLayoutCauta = (LinearLayout) v.findViewById(R.id.linearLayoutCauta);
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
        autoCompleteTextViewPlecare.setThreshold(1);
        autoCompleteTextViewSosire.setAdapter(arrayAdapterOrase);
        autoCompleteTextViewSosire.setThreshold(1);


        /*
        autoCompleteTextViewPlecare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //punctSosireCheck = true;
                if (autoCompleteTextViewPlecare.getText().toString().equals("") || !oraseRomania.find(autoCompleteTextViewPlecare.getText().toString())) {
                    autoCompleteTextViewPlecare.setError("Introduceti o locatie valida!");
                }
            }
        });

        autoCompleteTextViewSosire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
        autoCompleteTextViewSosire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //punctSosireCheck = true;
                if(oraseRomania.find(s.toString())) {
                    imageViewMap.setVisibility(View.GONE);
                    DirectionsMap directionsMap = new DirectionsMap(map, getContext());
                    directionsMap.drawRoute(autoCompleteTextViewPlecare.getText().toString(), autoCompleteTextViewSosire.getText().toString());
                        //while (directionsMap.getDistanta() == null) {
                        //}
                }else{
                    autoCompleteTextViewSosire.setError("Introduceti o locatie valida!");
                }
            }
        });*/

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
                    imageViewMap.setVisibility(View.GONE);
                    DirectionsMap directionsMap = new DirectionsMap(map, getContext());
                    directionsMap.drawRoute(autoCompleteTextViewPlecare.getText().toString(), autoCompleteTextViewSosire.getText().toString());
                    //linearLayoutCauta.setVisibility(View.VISIBLE);
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
                        imageViewMap.setVisibility(View.GONE);
                        DirectionsMap directionsMap = new DirectionsMap(map, getContext());
                        directionsMap.drawRoute(autoCompleteTextViewPlecare.getText().toString(), autoCompleteTextViewSosire.getText().toString());
                        //linearLayoutCauta.setVisibility(View.VISIBLE);
                    } else {
                        autoCompleteTextViewSosire.setError("Selectati din lista o locatie!");
                    }
                }

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                }
                return false;
            }
        });

        buttonCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String punctPlecare = autoCompleteTextViewPlecare.getText().toString();
                String punctSosire = autoCompleteTextViewSosire.getText().toString();
                listViewCalatorii.setAdapter(null);
                if (!oraseRomania.find(punctPlecare)) {
                    autoCompleteTextViewPlecare.setError("Selectati o locatie valida!");
                } else if (!oraseRomania.find(punctSosire)) {
                    autoCompleteTextViewSosire.setError("Selectati o locatie valida!");
                } else {
                    //InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    //in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    listaCalatorii = new ArrayList<>();
                    CautaCalatoriiTask cautaCalatoriiTask = new CautaCalatoriiTask(punctPlecare, punctSosire, progressDialog, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            try {
                                textViewSearchResult.setVisibility(View.GONE);
                                //linearLayoutCauta.setVisibility(View.GONE);
                                linearLayoutSearch.setVisibility(View.GONE);
                                linearLayoutSearchInfo.setVisibility(View.VISIBLE);
                                JSONObject jsonObject = new JSONObject(result);
                                int success = jsonObject.getInt("success");
                                if (success == 1) {
                                    JSONArray jsonCalatorii = jsonObject.getJSONArray("calatorii");
                                    for (int i = 0; i < jsonCalatorii.length(); i++) {
                                        JSONObject jsonCalatorie = jsonCalatorii.getJSONObject(i);
                                        CalatorieBuilder builder = new CalatorieBuilder();
                                        Calatorie calatorie = builder
                                                .setId(Integer.valueOf(jsonCalatorie.getString("id")))
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
                                                .setNume(jsonCalatorie.getString("nume"))
                                                .setTelefon(jsonCalatorie.getString("telefon"))
                                                .build();
                                        listaCalatorii.add(calatorie);
                                    }
                                    CalatoriiAdapter adapter = new CalatoriiAdapter(getContext(), listaCalatorii);
                                    linearLayoutLocatii.setVisibility(View.GONE);
                                    listViewCalatorii.setAdapter(adapter);
                                    if (listaCalatorii.size() == 1) {
                                        textViewSearchInfo.setText("o calatorie gasita");
                                    } else {
                                        textViewSearchInfo.setText(listaCalatorii.size() + " calatorii gasite");
                                    }
                                } else {
                                    textViewSearchResult.setVisibility(View.VISIBLE);
                                    textViewSearchInfo.setText("nicio calatorie gasita");
                                }
                            } catch (JSONException e) {
                                Log.e("exception", e.getMessage());
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
