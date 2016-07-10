package com.example.silviu086.licenta;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContFragment extends Fragment {

    private View v;
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
    private RatingBar ratingBar;
    private ImageView imageViewProfil;
    private LinearLayout linearLayoutMasinaAdauga;
    private LinearLayout linearLayoutMasinaInfo;
    private LinearLayout linearLayoutProfil;
    private String imagePath;
    private Bitmap bitmapProfil = null;

    public ContFragment() {
        // Required empty public constructor
    }

    public void setAccount(Account account){
        this.account = account;
    }

    private void showImagePopup() {
        // File System.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        // Chooser of file system options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Selecteaza o imagine");
        startActivityForResult(chooserIntent, 1010);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            account.setParola(data.getExtras().getString("new_password"));
            textViewValueParola.setText(account.getParola().toString());
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
            builder.append(" Parola schimbata!");
            Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
        }
        if(resultCode == 2){
            account.setNume(data.getExtras().getString("nume"));
            account.setTelefon(data.getExtras().getString("telefon"));
            account.setVarsta(Integer.valueOf(data.getExtras().getString("varsta")));
            textViewValueNume.setText(account.getNume().toString());
            textViewValueTelefon.setText(account.getTelefon().toString());
            textViewValueVarsta.setText(String.valueOf(account.getVarsta()));
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
            builder.append(" Date personale schimbate!");
            Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
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
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
            builder.append(" Masina adaugata!");
            Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();

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
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ");
            builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
            builder.append(" Date masina schimbate!");
            Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
        }
        if (requestCode == 1010) {
            if (data == null) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(" ");
                builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                builder.append(" Operatie esuata!");
                Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                final Snackbar snackbar = Snackbar.make(v.findViewById(R.id.parent_view), "Se incarca imaginea...", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
                ContUploadPhotoTask contUploadPhotoTask = new ContUploadPhotoTask(getContext(), imagePath, new TaskCompletedUpload() {
                    @Override
                    public void onTaskCompleted(Bitmap bitmap) {
                        if(bitmap != null){
                            try{
                                Picasso.with(getContext()).load(new File(imagePath)).centerCrop().resize(100, 100)
                                        .into(imageViewProfil);
                                File mypath=new File(getContext().getFilesDir(),"photo_" + String.valueOf(account.getId()) + ".png");
                                FileOutputStream out = new FileOutputStream(mypath);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                                out.flush();
                                out.close();
                                snackbar.dismiss();
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(" ");
                                builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
                                builder.append(" Imaginea de profil a fost schimbata!");
                                Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
                            }catch (Exception ex){
                                Log.e("ContFragment", ex.getMessage());
                            }
                        }else{

                        }
                    }
                });
                contUploadPhotoTask.execute();
                cursor.close();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_cont, container, false);

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
        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        imageButtonPassword = (ImageButton) v.findViewById(R.id.imageButtonPasswordCont);
        buttonModificaParola = (Button) v.findViewById(R.id.buttonModificaParolaCont);
        buttonModificaDatePersonale = (Button) v.findViewById(R.id.buttonModificaDatePersonale);
        buttonModificaMasina = (Button) v.findViewById(R.id.buttonModificaMasina);
        buttonAdaugaMasina = (Button) v.findViewById(R.id.buttonAdaugaMasina);
        imageViewProfil = (ImageView) v.findViewById(R.id.imageViewProfil);
        linearLayoutMasinaAdauga = (LinearLayout) v.findViewById(R.id.layout_masina_adauga);
        linearLayoutMasinaInfo = (LinearLayout) v.findViewById(R.id.layout_masina_info);
        linearLayoutProfil = (LinearLayout) v.findViewById(R.id.linearLayoutProfil);

        File mypath=new File(getContext().getFilesDir(),"photo_" + String.valueOf(account.getId()) + ".png");
        if (mypath.exists()) {
            Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
            imageViewProfil.setImageDrawable(photo);
        }
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

        linearLayoutProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Internet.haveInternet(getContext())){
                    showImagePopup();
                }else{
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    builder.append(" ");
                    builder.setSpan(new ImageSpan(getContext(), R.drawable.snackbar_fail), builder.length()-1, builder.length(), 0);
                    builder.append(" Nu exista conexiune la Internet!");
                    Snackbar.make(v.findViewById(R.id.parent_view), builder, Snackbar.LENGTH_LONG).show();
                }
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
