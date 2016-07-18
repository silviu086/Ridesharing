package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class UtilizatorProfilActivity extends AppCompatActivity {
    private LinearLayout parentView;
    private ImageView imageViewProfil;
    private TextView textViewEmailProfil;
    private TextView textViewtData;
    private TextView textViewNume;
    private TextView textViewTelefon;
    private TextView textViewVarsta;
    private TextView textViewFaraMasina;
    private LinearLayout linearLayoutMasina;
    private TextView textViewMarcaMasina;
    private TextView textViewModeMasina;
    private TextView textViewAnFabricatie;
    private TextView textViewExperientaAuto;
    private TextView textViewFaraRecenzii;
    private TextView textViewNumarRecenzii;
    private TextView textViewScorRecenzii;
    private RatingBar ratingBar;
    private Button buttonAfisareRecenzii;
    private LinearLayout linearLayoutRecenzii;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilizator_profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String nume = getIntent().getExtras().getString("nume");
        getSupportActionBar().setTitle("Profilul lui " + nume);
        parentView = (LinearLayout) findViewById(R.id.parent_view);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        textViewEmailProfil = (TextView) findViewById(R.id.textViewEmailProfil);
        textViewtData = (TextView) findViewById(R.id.textViewData);
        textViewNume = (TextView) findViewById(R.id.textViewNume);
        textViewTelefon = (TextView) findViewById(R.id.textViewTelefon);
        textViewVarsta = (TextView) findViewById(R.id.textViewVarsta);
        textViewFaraMasina = (TextView) findViewById(R.id.textViewFaraMasina);
        linearLayoutMasina = (LinearLayout) findViewById(R.id.linearLayoutMasina);
        textViewMarcaMasina = (TextView) findViewById(R.id.textViewMarcaMasina);
        textViewModeMasina = (TextView) findViewById(R.id.textViewModelMasina);
        textViewAnFabricatie = (TextView) findViewById(R.id.textViewAnFabricatie);
        textViewExperientaAuto = (TextView) findViewById(R.id.textViewExperientaAuto);
        textViewFaraRecenzii = (TextView) findViewById(R.id.textViewFaraRecenzii);
        textViewNumarRecenzii = (TextView) findViewById(R.id.textViewNumarRecenzii);
        textViewScorRecenzii = (TextView) findViewById(R.id.textViewScorRecenzii);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        buttonAfisareRecenzii = (Button) findViewById(R.id.buttonAfisareRecenzii);
        linearLayoutRecenzii = (LinearLayout) findViewById(R.id.linearLayoutRecenzii);
        parentView.setVisibility(View.GONE);

        int id = getIntent().getExtras().getInt("id_utilizator");
        //String email = getIntent().getExtras().getString("email");
        //getSupportActionBar().setSubtitle(email);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading...");
        progress.setMessage("Se preiau datele utilizatorului!");
        progress.show();
        UtilizatorProfilTask utilizatorProfilTask = new UtilizatorProfilTask(this, id, new TaskCompletedProfil() {
            @Override
            public void onTaskCompleted(final Account account) {
                //getSupportActionBar().setSubtitle(account.getEmail());


                /*
                if(account.isHaveProfilPhoto()) {
                    File mypath = new File(getFilesDir(), "photo_" + String.valueOf(account.getId()) + ".png");
                    if (!mypath.exists()) {
                        ContDownloadPhotoTask contDownloadPhotoTask = new ContDownloadPhotoTask(UtilizatorProfilActivity.this, account.getId(), new TaskCompletedUpload() {
                            @Override
                            public void onTaskCompleted(Bitmap bitmap) {
                                if (bitmap != null) {
                                    try {
                                        File mypath = new File(getFilesDir(), "photo_" + String.valueOf(account.getId()) + ".png");
                                        if (!mypath.exists()) {
                                            FileOutputStream out = new FileOutputStream(mypath);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                                            out.flush();
                                            out.close();
                                        }
                                    } catch (Exception ex) {
                                        Log.e("LoginTabFragment", ex.getMessage());
                                    }
                                }
                                //progress.dismiss();
                            }
                        });
                        contDownloadPhotoTask.execute();
                    }else{
                        //progress.dismiss();
                    }
                }else{
                    //progress.dismiss();
                }
                */



                File mypath=new File(getFilesDir(),"photo_" + String.valueOf(account.getId()) + ".png");
                if (mypath.exists()) {
                    Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
                    imageViewProfil.setImageDrawable(photo);
                }
                textViewEmailProfil.setText(account.getEmail());
                textViewtData.setText("Membru din " + account.getDateCreated());
                textViewNume.setText(account.getNume());
                textViewTelefon.setText(account.getTelefon());
                textViewTelefon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClipboard(account.getTelefon());
                        Toast.makeText(UtilizatorProfilActivity.this, "Telefon copiat in clipboard!", Toast.LENGTH_SHORT).show();
                    }
                });
                textViewVarsta.setText(String.valueOf(account.getVarsta()));
                if(!account.getMarcaMasina().equals("")){
                    textViewFaraMasina.setVisibility(View.GONE);
                    linearLayoutMasina.setVisibility(View.VISIBLE);
                    textViewMarcaMasina.setText(account.getMarcaMasina());
                    textViewModeMasina.setText(account.getModelMasina());
                    textViewAnFabricatie.setText(String.valueOf(account.getAnFabricatie()));
                    textViewExperientaAuto.setText(account.getExperientaAuto());
                }else{
                    textViewFaraMasina.setVisibility(View.VISIBLE);
                    linearLayoutMasina.setVisibility(View.GONE);
                }
                if(account.getListaRecenzii().size() > 0){
                    textViewFaraRecenzii.setVisibility(View.GONE);
                    linearLayoutRecenzii.setVisibility(View.VISIBLE);
                    if(account.getListaRecenzii().size() == 1){
                        textViewNumarRecenzii.setText("1 recenzie");
                    }else{
                        textViewNumarRecenzii.setText(String.valueOf(account.getListaRecenzii().size()) + " recenzii");
                    }
                    float medie = 0;
                    for(int i=0;i<account.getListaRecenzii().size();i++){
                        medie += account.getListaRecenzii().get(i).getScor();
                    }
                    medie = medie/account.getListaRecenzii().size();
                    DecimalFormat df = new DecimalFormat("#.0");
                    textViewScorRecenzii.setText(String.valueOf(df.format(medie)));
                    ratingBar.setRating(medie);
                    buttonAfisareRecenzii.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(UtilizatorProfilActivity.this, ContRecenziiActivity.class);
                            it.putExtra("account", account);
                            startActivity(it);
                        }
                    });
                }else{
                    textViewFaraRecenzii.setVisibility(View.VISIBLE);
                    linearLayoutRecenzii.setVisibility(View.GONE);
                }
                parentView.setVisibility(View.VISIBLE);
                progress.dismiss();
            }
        });
        utilizatorProfilTask.execute();
        /*
        int profilePhoto = jsonAccount.getInt("profile_photo");
                if(profilePhoto == 1){
                    account.setHaveProfilPhoto(true);
                    File mypath=new File(context.getFilesDir(),"photo_" + String.valueOf(account.getId()) + ".png");
                    if(!mypath.exists()){
                        ContDownloadPhotoTask contDownloadPhotoTask = new ContDownloadPhotoTask(getContext(), account.getId(), new TaskCompletedUpload() {
                            @Override
                            public void onTaskCompleted(Bitmap bitmap) {
                                if(bitmap != null){
                                    try {
                                        File mypath=new File(getContext().getFilesDir(),"photo_" + String.valueOf(account.getId()) + ".png");
                                        if (!mypath.exists()) {
                                            FileOutputStream out = new FileOutputStream(mypath);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                                            out.flush();
                                            out.close();
                                        }
                                    }catch (Exception ex){
                                        Log.e("LoginTabFragment", ex.getMessage());
                                    }
                                }
                            }
                        });
                        contDownloadPhotoTask.execute();
                    }
                }else{
                    account.setHaveProfilPhoto(false);
                }
         */
    }

    private void setClipboard(String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
