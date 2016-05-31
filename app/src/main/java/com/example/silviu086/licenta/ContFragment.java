package com.example.silviu086.licenta;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContFragment extends Fragment {

    private Account account;
    private TextView textViewValueParola;
    private TextView textViewEmailHeader;
    private TextView textViewDateCreatedHeader;
    private TextView textViewValueNume;
    private TextView textViewValueTelefon;
    private TextView textViewValueVarsta;
    private TextView textViewValueMarcaMasina;
    private TextView textViewValueModelMasina;
    private TextView textViewValueAnFabricatie;
    private TextView textViewValueExperientaAuto;
    private LinearLayout linearLayoutMasinaAdauga;
    private LinearLayout linearLayoutMasinaInfo;

    public ContFragment() {
        // Required empty public constructor
    }

    public void setAccount(Account account){
        this.account = account;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            account.setParola(data.getExtras().getString("new_password"));
            textViewValueParola.setText(account.getParola().toString());
            Toast.makeText(getContext(), "Parola schimbata!", Toast.LENGTH_SHORT).show();
        }
        if(resultCode == 2){
            account.setNume(data.getExtras().getString("nume"));
            account.setTelefon(data.getExtras().getString("telefon"));
            account.setVarsta(Integer.valueOf(data.getExtras().getString("varsta")));
            textViewValueNume.setText(account.getNume().toString());
            textViewValueTelefon.setText(account.getTelefon().toString());
            textViewValueVarsta.setText(String.valueOf(account.getVarsta()));
            Toast.makeText(getContext(), "Date personale schimbate!", Toast.LENGTH_SHORT).show();
        }
        if(resultCode == 3){
            account.setMarcaMasina(data.getExtras().getString("marca"));
            account.setModelMasina(data.getExtras().getString("model"));
            account.setAnFabricatie(Integer.valueOf(data.getExtras().getString("an")));
            account.setExperientaAuto(data.getExtras().getString("experienta"));
            textViewValueMarcaMasina.setText(data.getExtras().getString("marca"));
            textViewValueModelMasina.setText(data.getExtras().getString("model"));
            textViewValueAnFabricatie.setText(data.getExtras().getString("an"));
            textViewValueExperientaAuto.setText(data.getExtras().getString("experienta"));
            Toast.makeText(getContext(), "Masina adaugata!", Toast.LENGTH_SHORT).show();

            linearLayoutMasinaAdauga.setVisibility(View.GONE);
            linearLayoutMasinaInfo.setVisibility(View.VISIBLE);
        }
        if(resultCode == 4){
            account.setMarcaMasina(data.getExtras().getString("marca"));
            account.setModelMasina(data.getExtras().getString("model"));
            account.setAnFabricatie(Integer.valueOf(data.getExtras().getString("an")));
            account.setExperientaAuto(data.getExtras().getString("experienta"));
            textViewValueMarcaMasina.setText(data.getExtras().getString("marca"));
            textViewValueModelMasina.setText(data.getExtras().getString("model"));
            textViewValueAnFabricatie.setText(data.getExtras().getString("an"));
            textViewValueExperientaAuto.setText(data.getExtras().getString("experienta"));
            Toast.makeText(getContext(), "Date masina schimbate!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_cont, container, false);

        ImageButton imageButtonPassword;
        Button buttonModificaParola;
        Button buttonModificaDatePersonale;
        Button buttonModificaMasina;
        Button buttonAdaugaMasina;

        textViewEmailHeader = (TextView) v.findViewById(R.id.textViewEmailContHeader);
        textViewDateCreatedHeader = (TextView) v.findViewById(R.id.textViewDateCreatedContHeader);
        textViewValueParola = (TextView) v.findViewById(R.id.textViewValueParolaCont);
        textViewValueNume = (TextView) v.findViewById(R.id.textViewValueNumeCont);
        textViewValueTelefon = (TextView) v.findViewById(R.id.textViewValueTelefonCont);
        textViewValueVarsta = (TextView) v.findViewById(R.id.textViewValueVarstaCont);
        textViewValueMarcaMasina = (TextView) v.findViewById(R.id.textViewValueMarcaMasinaCont);
        textViewValueModelMasina = (TextView) v.findViewById(R.id.textViewValueModelMasinaCont);
        textViewValueAnFabricatie = (TextView) v.findViewById(R.id.textViewValueAnFabricatieCont);
        textViewValueExperientaAuto = (TextView) v.findViewById(R.id.textViewValueExperientaAutoCont);
        imageButtonPassword = (ImageButton) v.findViewById(R.id.imageButtonPasswordCont);
        buttonModificaParola = (Button) v.findViewById(R.id.buttonModificaParolaCont);
        buttonModificaDatePersonale = (Button) v.findViewById(R.id.buttonModificaDatePersonale);
        buttonModificaMasina = (Button) v.findViewById(R.id.buttonModificaMasina);
        buttonAdaugaMasina = (Button) v.findViewById(R.id.buttonAdaugaMasina);
        linearLayoutMasinaAdauga = (LinearLayout) v.findViewById(R.id.layout_masina_adauga);
        linearLayoutMasinaInfo = (LinearLayout) v.findViewById(R.id.layout_masina_info);

        textViewEmailHeader.setText(account.getEmail().toString());
        textViewDateCreatedHeader.setText("Creat la " + account.getDateCreated());
        textViewValueParola.setText(account.getParola().toString());
        textViewValueParola.setInputType(129);
        textViewValueNume.setText(account.getNume().toString());
        textViewValueTelefon.setText(account.getTelefon().toString());
        textViewValueVarsta.setText(String.valueOf(account.getVarsta()));

        if(account.getMarcaMasina().equals("")) {
            linearLayoutMasinaInfo.setVisibility(View.GONE);
        } else{
            linearLayoutMasinaAdauga.setVisibility(View.GONE);
            //setare date
            textViewValueMarcaMasina.setText(account.getMarcaMasina().toString());
            textViewValueModelMasina.setText(account.getModelMasina().toString());
            textViewValueAnFabricatie.setText(String.valueOf(account.getAnFabricatie()));
            textViewValueExperientaAuto.setText(account.getExperientaAuto().toString());
        }

        imageButtonPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        textViewValueParola.setInputType(129);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        textViewValueParola.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        buttonModificaParola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ModifyPassword.class);
                it.putExtra("email", account.getEmail().toString());
                startActivityForResult(it, 1);


            }
        });

        buttonModificaDatePersonale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ModifyDate.class);
                it.putExtra("account", account);
                startActivityForResult(it, 2);
            }
        });

        buttonAdaugaMasina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ModifyMasina.class);
                it.putExtra("button", "adauga");
                it.putExtra("account", account);
                startActivityForResult(it, 3);
            }
        });

        buttonModificaMasina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ModifyMasina.class);
                it.putExtra("button", "modifica");
                it.putExtra("account", account);
                startActivityForResult(it, 4);
            }
        });
        return v;
    }

    public static ContFragment newInstance(Account account){
        ContFragment contFragment = new ContFragment();
        contFragment.setAccount(account);
        return contFragment;
    }

}
