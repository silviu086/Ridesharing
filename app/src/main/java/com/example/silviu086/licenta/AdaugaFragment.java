package com.example.silviu086.licenta;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdaugaFragment extends Fragment {

    private static final double COST_KM = 0.145187591240;
    private Account account;
    private OraseRomania oraseRomania;
    private GoogleMap map;
    private String punctPlecare;
    private String punctSosire;
    private String durata;
    private String distanta;
    private int pret;
    private int limitaPretSup;
    private int limitaPretInf;
    private boolean punctPlecareCheck = false;
    private boolean punctSosireCheck = false;
    private int dateZi;
    private int dateLuna;
    private int dateAn;
    private int timeOra;
    private int timeMinute;
    private int locuriDisponibile;

    private ScrollView scrollView;
    // CONTROALE STEP1
    private TextView textViewUnuHead;
    private TextView textViewUnuBody;
    private ImageView imageViewMap;
    private AutoCompleteTextView autoCompleteTextViewPunctPlecare;
    private AutoCompleteTextView autoCompleteTextViewPunctSosire;

    // CONTROALE STEP2
    private TextView textViewDoiHead;
    private TextView textViewDoiBody;
    private LinearLayout linearLayoutDoi;
    private TextView textViewPretTitle;
    private TextView textViewPret;
    private TextView textViewPretValuta;
    private ImageButton imageButtonPretMinus;
    private ImageButton imageButtonPretPlus;
    private TextView textViewDateTitle;
    private TextView textViewDate;
    private ImageButton imageButtonDatePicker;
    private TextView textViewTimeTitle;
    private TextView textViewTime;
    private ImageButton imageButtonTimePicker;
    private TextView textViewLocuriTitle;
    private TextView textViewLocuri;
    private ImageButton imageButtonLocuriMinus;
    private ImageButton imageButtonLocuriPlus;
    private Button buttonPasulUrmatorDoi;

    // CONTROALE STEP3
    private TextView textViewTreiHead;
    private TextView textViewTreiBody;
    private LinearLayout linearLayoutTrei;
    private TextView textViewSwitchMasina;
    private Switch aSwitchMasina;
    private TextView textViewMarcaTitle;
    private TextView textViewModelTitle;
    private TextView textViewAnTitle;
    private TextView textViewExperientaTitle;
    private TextView textViewMarcaValue;
    private TextView textViewModelValue;
    private TextView textViewAnValue;
    private TextView textViewExperientaValue;
    private AutoCompleteTextView autoCompleteTextViewMarcaValue;
    private AutoCompleteTextView autoCompleteTextViewModelValue;
    private EditText editTextAnValue;
    private Spinner spinnerExperienta;
    private Button buttonPasulUrmatorTrei;

    // CONTROALE STEP4
    private TextView textViewPatruHead;
    private TextView textViewPatruBody;
    private LinearLayout linearLayoutPatru;
    private RadioGroup radioGroupConfort;
    private RadioGroup radioGroupBagaj;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButton6;
    private TextView textViewConfort;
    private TextView textViewBagaj;
    private String radioButtonConfortValue;
    private String radioButtonBagajValue;
    private Button buttonPasulUrmatorulPatru;

    // CONTROALE STEP5
    private TextView textViewCinciHead;
    private Button buttonAdauga;
    private ProgressBar progressBar;

    public AdaugaFragment() {
        // Required empty public constructor
    }

    public void setAccount(Account account){
        this.account = account;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_adauga, container, false);

        scrollView = (ScrollView) v.findViewById(R.id.scrollViewAdauga);
        //step1
        textViewUnuHead = (TextView) v.findViewById(R.id.textViewUnuHead);
        textViewUnuBody = (TextView) v.findViewById(R.id.textViewUnuBody);
        autoCompleteTextViewPunctPlecare = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewPlecare);
        autoCompleteTextViewPunctSosire = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewSosire);
        imageViewMap = (ImageView) v.findViewById(R.id.imageViewMap);

        //step2
        textViewDoiHead = (TextView) v.findViewById(R.id.textViewDoiHead);
        textViewDoiBody = (TextView) v.findViewById(R.id.textViewDoiBody);
        linearLayoutDoi = (LinearLayout) v.findViewById(R.id.linearLayoutDoi);
        textViewPretTitle = (TextView) v.findViewById(R.id.textViewPretTitle);
        textViewPret = (TextView) v.findViewById(R.id.textViewPret);
        textViewPretValuta = (TextView) v.findViewById(R.id.textViewPretValuta);
        imageButtonPretMinus = (ImageButton) v.findViewById(R.id.imageButtonPretMinus);
        imageButtonPretPlus = (ImageButton) v.findViewById(R.id.imageButtonPretPlus);
        textViewDateTitle = (TextView) v.findViewById(R.id.textViewDateTitle);
        textViewDate = (TextView) v.findViewById(R.id.textViewDate);
        imageButtonDatePicker = (ImageButton) v.findViewById(R.id.imageButtonDatePicker);
        textViewTimeTitle = (TextView) v.findViewById(R.id.textViewTimeTitle);
        textViewTime = (TextView) v.findViewById(R.id.textViewTime);
        imageButtonTimePicker = (ImageButton) v.findViewById(R.id.imageButtonTimePicker);
        textViewLocuriTitle = (TextView) v.findViewById(R.id.textViewLocuriTitle);
        textViewLocuri = (TextView) v.findViewById(R.id.textViewLocuri);
        imageButtonLocuriMinus = (ImageButton) v.findViewById(R.id.imageButtonLocuriMinus);
        imageButtonLocuriPlus = (ImageButton) v.findViewById(R.id.imageButtonLocuriPlus);
        buttonPasulUrmatorDoi = (Button) v.findViewById(R.id.buttonPasulUrmatorDoi);
        disableStep2();

        //step3
        textViewTreiHead = (TextView) v.findViewById(R.id.textViewTreiHead);
        textViewTreiBody = (TextView) v.findViewById(R.id.textViewTreiBody);
        linearLayoutTrei = (LinearLayout) v.findViewById(R.id.linearLayoutTrei);
        textViewSwitchMasina = (TextView) v.findViewById((R.id.textViewSwitchMasina));
        aSwitchMasina = (Switch) v.findViewById(R.id.switchMasina);
        textViewMarcaTitle = (TextView) v.findViewById(R.id.textViewMarcaTitle);
        textViewModelTitle = (TextView) v.findViewById(R.id.textViewModelTitle);
        textViewAnTitle = (TextView) v.findViewById(R.id.textViewAnTitle);
        textViewExperientaTitle = (TextView) v.findViewById(R.id.textViewExperientaTitle);
        textViewMarcaValue = (TextView) v.findViewById(R.id.textViewMarcaValue);
        textViewModelValue = (TextView) v.findViewById(R.id.textViewModelValue);
        textViewAnValue = (TextView) v.findViewById(R.id.textViewAnValue);
        textViewExperientaValue = (TextView) v.findViewById(R.id.textViewExperientaValue);
        autoCompleteTextViewMarcaValue = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewMarcaValue);
        autoCompleteTextViewModelValue = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextViewModelValue);
        editTextAnValue = (EditText) v.findViewById(R.id.editTextAnValue);
        spinnerExperienta = (Spinner) v.findViewById(R.id.spinnerExperienta);
        buttonPasulUrmatorTrei = (Button) v.findViewById(R.id.buttonPasulUrmatorTrei);
        disableStep3();

        //step4
        textViewPatruHead = (TextView) v.findViewById(R.id.textViewPatruHead);
        textViewPatruBody = (TextView) v.findViewById(R.id.textViewPatruBody);
        linearLayoutPatru = (LinearLayout) v.findViewById(R.id.linearLayoutPatru);
        textViewConfort = (TextView) v.findViewById(R.id.textViewConfort);
        textViewBagaj = (TextView) v.findViewById(R.id.textViewBagaj);
        radioGroupConfort = (RadioGroup) v.findViewById(R.id.radioGroupConfort);
        radioGroupBagaj = (RadioGroup) v.findViewById(R.id.radioGroupBagaj);
        radioButton1 = (RadioButton) v.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) v.findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) v.findViewById(R.id.radioButton4);
        radioButton5 = (RadioButton) v.findViewById(R.id.radioButton5);
        radioButton6 = (RadioButton) v.findViewById(R.id.radioButton6);
        buttonPasulUrmatorulPatru = (Button) v.findViewById(R.id.buttonPasulUrmatorPatru);
        disableStep4();

        //step5
        textViewCinciHead = (TextView) v.findViewById(R.id.textViewCinciHead);
        buttonAdauga = (Button) v.findViewById(R.id.buttonAdauga);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        buttonAdauga.setBackgroundColor(getResources().getColor(R.color.colorGray));
        buttonAdauga.setTextColor(getResources().getColor(R.color.colorWhite));
        buttonAdauga.setEnabled(false);

        // STEP 1
        oraseRomania = OraseRomania.getInstance();
        ArrayAdapter<String> arrayAdapterOrase = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                OraseRomania.getInstance().getListaOrase()
                );

        map = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(44.42677, 26.10254), 4));

        autoCompleteTextViewPunctPlecare.setAdapter(arrayAdapterOrase);
        autoCompleteTextViewPunctPlecare.setThreshold(1);
        autoCompleteTextViewPunctSosire.setAdapter(arrayAdapterOrase);
        autoCompleteTextViewPunctSosire.setThreshold(1);

        autoCompleteTextViewPunctPlecare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!autoCompleteTextViewPunctSosire.getText().toString().equals("")) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
            }
        });
        autoCompleteTextViewPunctPlecare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                punctPlecareCheck = true;
                if (oraseRomania.find(s.toString())) {
                    punctPlecare = s.toString();
                    if (punctSosireCheck && punctSosireCheck) {
                        imageViewMap.setVisibility(View.GONE);
                        DirectionsMap directionsMap = new DirectionsMap(map, getContext());
                        directionsMap.drawRoute(punctPlecare, punctSosire);
                        while (directionsMap.getDistanta() == null) {
                        }
                        durata = directionsMap.getDurata();
                        distanta = directionsMap.getDistanta();
                        pret = calculeazaPret(distanta);
                        textViewPret.setText(String.valueOf(pret));
                        int numarSchimbare = numarSchimbarePret(pret);
                        limitaPretSup = pret + numarSchimbare;
                        limitaPretInf = pret - numarSchimbare;
                        enableStep2();
                        disableStep3();
                        disableStep4();
                        disableStep5();
                    }
                }
            }
        });


        autoCompleteTextViewPunctSosire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
        autoCompleteTextViewPunctSosire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                punctSosireCheck = true;
                if (oraseRomania.find(s.toString())) {
                    punctSosire = s.toString();
                    if (punctPlecareCheck && punctSosireCheck) {
                        imageViewMap.setVisibility(View.GONE);
                        DirectionsMap directionsMap = new DirectionsMap(map, getContext());
                        directionsMap.drawRoute(punctPlecare, punctSosire);
                        while (directionsMap.getDistanta() == null) {
                        }
                        durata = directionsMap.getDurata();
                        distanta = directionsMap.getDistanta();
                        pret = calculeazaPret(distanta);
                        textViewPret.setText(String.valueOf(pret));
                        int numarSchimbare = numarSchimbarePret(pret);
                        limitaPretSup = pret + numarSchimbare;
                        limitaPretInf = pret - numarSchimbare;
                        enableStep2();
                        disableStep3();
                        disableStep4();
                        disableStep5();
                    }
                }
            }
        });

        // STEP 2
        imageButtonPretMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageButtonPretPlus.isEnabled()){
                    imageButtonPretPlus.setEnabled(true);
                }
                if(pret>limitaPretInf && pret>1){
                    pret = Integer.parseInt(textViewPret.getText().toString());
                    pret--;
                    textViewPret.setText(String.valueOf(pret));
                }else{
                    imageButtonPretMinus.setEnabled(false);
                    Toast.makeText(getContext(), "Ati atins limita inferioara de pret!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButtonPretPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageButtonPretMinus.isEnabled()){
                    imageButtonPretMinus.setEnabled(true);
                }
                if(pret<limitaPretSup){
                    pret = Integer.parseInt(textViewPret.getText().toString());
                    pret++;
                    textViewPret.setText(String.valueOf(pret));
                }else{
                    imageButtonPretPlus.setEnabled(false);
                    Toast.makeText(getContext(), "Ati atins limita superioara de pret!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateZi = dayOfMonth;
                dateLuna = monthOfYear;
                dateAn = year;
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateAn, dateLuna, dateZi, 23, 59);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Calendar calendarPresent = Calendar.getInstance();
                if(calendar.after(calendarPresent) || calendar.equals(calendarPresent)){
                    textViewDate.setText(simpleDateFormat.format(calendar.getTime()).toString());
                }else{
                    Toast.makeText(getContext(), "Selectati o data din viitor!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        imageButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                dateZi = calendar.get(Calendar.DAY_OF_MONTH);
                dateLuna = calendar.get(Calendar.MONTH);
                dateAn = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateListener, dateAn, dateLuna, dateZi);
                datePickerDialog.show();
            }
        });

        final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeOra = hourOfDay;
                timeMinute = minute;
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateAn, dateLuna, dateZi, timeOra, timeMinute);
                Calendar calentdarPresent = Calendar.getInstance();
                Date time = new Date(0, 0, 0, timeOra, timeMinute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                if(calendar.after(calentdarPresent)){
                    textViewTime.setText(simpleDateFormat.format(time));
                }else{
                    Toast.makeText(getContext(), "Introduceti o ora din viitor!", Toast.LENGTH_SHORT).show();
                }

            }
        };
        imageButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewDate.getText().equals("selectati data")){
                    Toast.makeText(getContext(), "Selectati data plecarii!", Toast.LENGTH_SHORT).show();
                }else{
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeListener, timeOra, timeMinute, true);
                    timePickerDialog.show();
                }
            }
        });

        imageButtonLocuriMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageButtonLocuriPlus.isEnabled()){
                    imageButtonLocuriPlus.setEnabled(true);
                }
                if(locuriDisponibile>1){
                    locuriDisponibile = Integer.parseInt(textViewLocuri.getText().toString());
                    locuriDisponibile--;
                    textViewLocuri.setText(String.valueOf(locuriDisponibile));
                }else{
                    imageButtonLocuriMinus.setEnabled(false);
                    Toast.makeText(getContext(), "Numarul de locuri minim este 1!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButtonLocuriPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageButtonLocuriMinus.isEnabled()){
                    imageButtonLocuriMinus.setEnabled(true);
                }
                if(locuriDisponibile<4){
                    locuriDisponibile = Integer.parseInt(textViewLocuri.getText().toString());
                    locuriDisponibile++;
                    textViewLocuri.setText(String.valueOf(locuriDisponibile));
                }else{
                    imageButtonLocuriPlus.setEnabled(false);
                    Toast.makeText(getContext(), "Numarul de locuri maxim este 4!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonPasulUrmatorDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewDate.getText().equals("selectati data")){
                    Toast.makeText(getContext(), "Introduceti data plecarii!", Toast.LENGTH_SHORT).show();
                }else if(textViewTime.getText().equals("selectati ora")){
                    Toast.makeText(getContext(), "Introduceti ora de plecare!", Toast.LENGTH_SHORT).show();
                }else{
                    enableStep3();
                    buttonPasulUrmatorDoi.setEnabled(false);
                    if(account.getMarcaMasina().equals("")){
                        textViewSwitchMasina.setEnabled(false);
                        aSwitchMasina.setEnabled(false);
                    }
                }
            }
        });


        // STEP 3
        ArrayAdapter<String> arrayAdapterMarca = new ArrayAdapter<String>(
                getContext(),
                R.layout.custom_list_dropdown,
                getResources().getStringArray(R.array.lista_marci)
        );

        autoCompleteTextViewMarcaValue.setThreshold(1);
        autoCompleteTextViewMarcaValue.setAdapter(arrayAdapterMarca);

        autoCompleteTextViewModelValue.setThreshold(1);
        autoCompleteTextViewMarcaValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteTextViewModelValue.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayAdapter<String> arrayAdapterModel;
                String marca = autoCompleteTextViewMarcaValue.getText().toString();
                switch (marca.toLowerCase()) {
                    case "bmw":
                        arrayAdapterModel = new ArrayAdapter<String>(
                                getContext(),
                                R.layout.custom_list_dropdown,
                                getResources().getStringArray(R.array.lista_model_bmw)
                        );
                        autoCompleteTextViewModelValue.setAdapter(arrayAdapterModel);
                        break;
                    case "opel":
                        arrayAdapterModel = new ArrayAdapter<String>(
                                getContext(),
                                R.layout.custom_list_dropdown,
                                getResources().getStringArray(R.array.lista_model_opel)
                        );
                        autoCompleteTextViewModelValue.setAdapter(arrayAdapterModel);
                        break;
                }
            }
        });

        ArrayAdapter<String> arrayAdapterExperienta = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.lista_nivel_experienta)){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if(position == getCount()){
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        arrayAdapterExperienta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExperienta.setAdapter(arrayAdapterExperienta);
        spinnerExperienta.setSelection(arrayAdapterExperienta.getCount());

        if(account.getMarcaMasina().equals("")){
            textViewMarcaValue.setVisibility(View.GONE);
            textViewModelValue.setVisibility(View.GONE);;
            textViewAnValue.setVisibility(View.GONE);
            textViewExperientaValue.setVisibility(View.GONE);
        }else{
            aSwitchMasina.setChecked(true);
            autoCompleteTextViewMarcaValue.setVisibility(View.GONE);
            autoCompleteTextViewModelValue.setVisibility(View.GONE);
            editTextAnValue.setVisibility(View.GONE);
            spinnerExperienta.setVisibility(View.GONE);
            textViewMarcaValue.setText(account.getMarcaMasina());
            textViewModelValue.setText(account.getModelMasina());
            textViewAnValue.setText(String.valueOf(account.getAnFabricatie()));
            textViewExperientaValue.setText(account.getExperientaAuto());
        }

        aSwitchMasina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    autoCompleteTextViewMarcaValue.setVisibility(View.GONE);
                    autoCompleteTextViewModelValue.setVisibility(View.GONE);
                    editTextAnValue.setVisibility(View.GONE);
                    spinnerExperienta.setVisibility(View.GONE);
                    textViewMarcaValue.setVisibility(View.VISIBLE);
                    textViewModelValue.setVisibility(View.VISIBLE);
                    textViewAnValue.setVisibility(View.VISIBLE);
                    textViewExperientaValue.setVisibility(View.VISIBLE);
                }else{
                    textViewMarcaValue.setVisibility(View.GONE);
                    textViewModelValue.setVisibility(View.GONE);
                    textViewAnValue.setVisibility(View.GONE);
                    textViewExperientaValue.setVisibility(View.GONE);
                    autoCompleteTextViewMarcaValue.setVisibility(View.VISIBLE);
                    autoCompleteTextViewModelValue.setVisibility(View.VISIBLE);
                    editTextAnValue.setVisibility(View.VISIBLE);
                    spinnerExperienta.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonPasulUrmatorTrei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitchMasina.isChecked()) {
                    enableStep4();
                    buttonPasulUrmatorTrei.setEnabled(false);
                } else {
                    boolean isOk = true;
                    if (autoCompleteTextViewMarcaValue.getText().toString().equals("")) {
                        isOk = false;
                        autoCompleteTextViewMarcaValue.setError("Trebuie sa completati acest camp!");
                        Toast.makeText(getContext(), "Introduceti marca masinii!", Toast.LENGTH_SHORT).show();
                    } else if (autoCompleteTextViewModelValue.getText().toString().equals("")) {
                        isOk = false;
                        autoCompleteTextViewModelValue.setError("Trebuie sa completati acest camp!");
                        Toast.makeText(getContext(), "Introduceti modelul masinii!", Toast.LENGTH_SHORT).show();
                    } else if (editTextAnValue.getText().toString().equals("")) {
                        isOk = false;
                        editTextAnValue.setError("Trebuie sa completati acest camp!");
                        Toast.makeText(getContext(), "Introduceti anul de fabricatie!", Toast.LENGTH_SHORT).show();
                    } else if (spinnerExperienta.getSelectedItem().toString().equals("selecteaza din lista")) {
                        isOk = false;
                        Toast.makeText(getContext(), "Selectati nivelul de experienta auto!", Toast.LENGTH_SHORT).show();
                    }

                    if (isOk) {
                        enableStep4();
                        buttonPasulUrmatorTrei.setEnabled(false);
                    }
                }
            }
        });

        // STEP 4
        buttonPasulUrmatorulPatru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonConfortId = radioGroupConfort.getCheckedRadioButtonId();
                int radioButtonBagajId = radioGroupBagaj.getCheckedRadioButtonId();

                if(radioButtonConfortId == -1){
                    Toast.makeText(getContext(), "Selectati un nivel de confort!", Toast.LENGTH_SHORT).show();
                }else if(radioButtonBagajId == -1){
                    Toast.makeText(getContext(), "Selectati un nivel al capacitatii de bagaj!", Toast.LENGTH_SHORT).show();
                }else{
                    RadioButton radioButtonConfort = (RadioButton) radioGroupConfort.findViewById(radioButtonConfortId);
                    RadioButton radioButtonBagaj = (RadioButton) radioGroupBagaj.findViewById(radioButtonBagajId);
                    radioButtonConfortValue = radioButtonConfort.getText().toString();
                    radioButtonBagajValue = radioButtonBagaj.getText().toString();
                    buttonPasulUrmatorulPatru.setEnabled(false);
                    enableStep5();
                }

            }
        });

        // STEP 5
        buttonAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                Date date = new Date();
                String dateCreated = dateFormat.format(date);
                CalatorieBuilder builder = new CalatorieBuilder();
                Calatorie calatorie = builder.setPunctPlecare(autoCompleteTextViewPunctPlecare.getText().toString())
                        .setDataCreare(dateCreated)
                        .setPunctSosire(autoCompleteTextViewPunctSosire.getText().toString())
                        .setPret(pret)
                        .setDataPlecare(textViewDate.getText().toString())
                        .setOraPlecare(textViewTime.getText().toString())
                        .setLocuriDisponibile(locuriDisponibile)
                        .setNivelConfort(radioButtonConfortValue)
                        .setMarimeBagaj(radioButtonBagajValue)
                        .setDistantaCalatorie(distanta)
                        .setDurataCalatorie(durata)
                        .build();
                if(!aSwitchMasina.isChecked()) {
                    calatorie = builder.setMarcaMasina(autoCompleteTextViewMarcaValue.getText().toString())
                            .setModelMasina(autoCompleteTextViewModelValue.getText().toString())
                            .setAnFabricatie(Integer.valueOf(editTextAnValue.getText().toString()))
                            .setExperientaAuto(spinnerExperienta.getSelectedItem().toString())
                            .build();
                }
                AdaugaCalatorieTask adaugaCalatorieTask = new AdaugaCalatorieTask(account.getId(), calatorie, progressBar, new TaskCompleted() {
                    @Override
                    public void onTaskCompleted(String result) {
                        if(result.equals("added")){
                            Toast.makeText(getContext(), "Calatorie adaugata!", Toast.LENGTH_LONG).show();
                            NavigationActivity.setFragment(4);
                        }
                    }
                });
                adaugaCalatorieTask.execute();
            }
        });

        return v;
    }

    private int calculeazaPret(String dist){
        int pret;
        String[] elem = dist.split(" ");
        String distantaS = elem[0];
        distantaS = distantaS.replaceAll(",", ".");
        double distanta = Double.valueOf(distantaS);
        return (int)(distanta * COST_KM);
    }

    private int numarSchimbarePret(int pret){
        if(pret<10){
            return 5;
        }else if(pret<100){
            return (int)((((pret/10)%10))*6);
        }else{
            return 40;
        }
    }

    public static void scrollToView(final ScrollView scrollView, final View view) {

        // View needs a focus
        //view.requestFocus();

        // Determine if scroll needs to happen
        final Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        if (!view.getLocalVisibleRect(scrollBounds)) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, view.getBottom()+300);
                }
            });
        }
    }

    private void enableStep2() {
        scrollToView(scrollView, buttonPasulUrmatorDoi);
        textViewUnuBody.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        textViewDoiHead.setTextColor(getResources().getColor(R.color.colorBlue));
        linearLayoutDoi.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape));
        imageButtonPretMinus.setBackground(getResources().getDrawable(R.drawable.adauga_minus_color));
        imageButtonPretPlus.setBackground(getResources().getDrawable(R.drawable.adauga_plus_color));
        imageButtonDatePicker.setBackground(getResources().getDrawable(R.drawable.adauga_datepicker_color));
        imageButtonTimePicker.setBackground(getResources().getDrawable(R.drawable.adauga_timepicker_color));
        imageButtonLocuriMinus.setBackground(getResources().getDrawable(R.drawable.adauga_minus_color));
        imageButtonLocuriPlus.setBackground(getResources().getDrawable(R.drawable.adauga_plus_color));

        textViewPretTitle.setEnabled(true);
        textViewPret.setEnabled(true);
        textViewPretValuta.setEnabled(true);
        imageButtonPretMinus.setEnabled(true);
        imageButtonPretPlus.setEnabled(true);
        textViewDateTitle.setEnabled(true);
        textViewDate.setEnabled(true);
        imageButtonDatePicker.setEnabled(true);
        textViewTimeTitle.setEnabled(true);
        textViewTime.setEnabled(true);
        imageButtonTimePicker.setEnabled(true);
        textViewLocuriTitle.setEnabled(true);
        textViewLocuri.setEnabled(true);
        locuriDisponibile = 4;
        textViewLocuri.setText(String.valueOf(locuriDisponibile));
        imageButtonLocuriMinus.setEnabled(true);
        imageButtonLocuriPlus.setEnabled(true);
        buttonPasulUrmatorDoi.setEnabled(true);
    }

    private void disableStep2(){
        //textViewDoiHead.setTextColor(getResources().getColor(R.color.colorGray));
        linearLayoutDoi.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape_2));
        textViewPretTitle.setEnabled(false);
        textViewPret.setEnabled(false);
        textViewPretValuta.setEnabled(false);
        imageButtonPretMinus.setEnabled(false);
        imageButtonPretPlus.setEnabled(false);
        textViewDateTitle.setEnabled(false);
        textViewDate.setEnabled(false);
        imageButtonDatePicker.setEnabled(false);
        textViewTimeTitle.setEnabled(false);
        textViewTime.setEnabled(false);
        imageButtonTimePicker.setEnabled(false);
        textViewLocuriTitle.setEnabled(false);
        textViewLocuri.setEnabled(false);
        imageButtonLocuriMinus.setEnabled(false);
        imageButtonLocuriPlus.setEnabled(false);
        buttonPasulUrmatorDoi.setEnabled(false);
    }

    public void enableStep3() {
        scrollToView(scrollView, buttonPasulUrmatorTrei);
        textViewDoiBody.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        textViewTreiHead.setTextColor(getResources().getColor(R.color.colorBlue));
        linearLayoutTrei.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape));
        textViewSwitchMasina.setEnabled(true);
        aSwitchMasina.setEnabled(true);
        textViewMarcaTitle.setEnabled(true);
        textViewModelTitle.setEnabled(true);
        textViewAnTitle.setEnabled(true);
        textViewExperientaTitle.setEnabled(true);
        textViewMarcaValue.setEnabled(true);
        textViewModelValue.setEnabled(true);
        textViewAnValue.setEnabled(true);
        textViewExperientaValue.setEnabled(true);
        autoCompleteTextViewMarcaValue.setEnabled(true);
        autoCompleteTextViewModelValue.setEnabled(true);
        editTextAnValue.setEnabled(true);
        spinnerExperienta.setEnabled(true);
        buttonPasulUrmatorTrei.setEnabled(true);
    }

    public void disableStep3(){
        linearLayoutTrei.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape_2));
        textViewTreiHead.setTextColor(getResources().getColor(R.color.colorGray));
        textViewDoiBody.setBackgroundColor(getResources().getColor(R.color.colorGray));
        textViewSwitchMasina.setEnabled(false);
        aSwitchMasina.setEnabled(false);
        textViewMarcaTitle.setEnabled(false);
        textViewModelTitle.setEnabled(false);
        textViewAnTitle.setEnabled(false);
        textViewExperientaTitle.setEnabled(false);
        textViewMarcaValue.setEnabled(false);
        textViewModelValue.setEnabled(false);
        textViewAnValue.setEnabled(false);
        textViewExperientaValue.setEnabled(false);
        autoCompleteTextViewMarcaValue.setEnabled(false);
        autoCompleteTextViewModelValue.setEnabled(false);
        editTextAnValue.setEnabled(false);
        spinnerExperienta.setEnabled(false);
        buttonPasulUrmatorTrei.setEnabled(false);
    }

    private void enableStep4(){
        scrollView.post(new Runnable() {

            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        textViewTreiBody.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        textViewPatruHead.setTextColor(getResources().getColor(R.color.colorBlue));
        linearLayoutPatru.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape));
        radioGroupBagaj.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape));
        radioGroupConfort.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape));
        textViewConfort.setEnabled(true);
        textViewBagaj.setEnabled(true);
        radioButton1.setEnabled(true);
        radioButton2.setEnabled(true);
        radioButton3.setEnabled(true);
        radioButton4.setEnabled(true);
        radioButton5.setEnabled(true);
        radioButton6.setEnabled(true);
        radioGroupConfort.setEnabled(true);
        radioGroupBagaj.setEnabled(true);
        buttonPasulUrmatorulPatru.setEnabled(true);
    }

    private void disableStep4(){
        textViewTreiBody.setBackgroundColor(getResources().getColor(R.color.colorGray));
        textViewPatruHead.setTextColor(getResources().getColor(R.color.colorGray));
        linearLayoutPatru.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape_2));
        radioGroupBagaj.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape_2));
        radioGroupConfort.setBackground(getResources().getDrawable(R.drawable.cont_layout_shape_2));
        textViewConfort.setEnabled(false);
        textViewBagaj.setEnabled(false);
        radioGroupConfort.setEnabled(false);
        radioGroupBagaj.setEnabled(false);
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        radioButton3.setEnabled(false);
        radioButton4.setEnabled(false);
        radioButton5.setEnabled(false);
        radioButton6.setEnabled(false);
        buttonPasulUrmatorulPatru.setEnabled(false);
    }

    private void enableStep5(){
        textViewCinciHead.setTextColor(getResources().getColor(R.color.colorBlue));
        textViewPatruBody.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        buttonAdauga.setEnabled(true);
        buttonAdauga.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        buttonAdauga.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void disableStep5(){
        textViewCinciHead.setTextColor(getResources().getColor(R.color.colorGray));
        textViewPatruBody.setBackgroundColor(getResources().getColor(R.color.colorGray));
        buttonAdauga.setEnabled(false);
        buttonAdauga.setBackgroundColor(getResources().getColor(R.color.colorGray));
        buttonAdauga.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    public static AdaugaFragment newInstance(Account account){
        AdaugaFragment adaugaFragment = new AdaugaFragment();
        adaugaFragment.setAccount(account);
        return adaugaFragment;
    }

}
