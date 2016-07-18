package com.example.silviu086.licenta;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;

public class AdaugaRecenzieActivity extends AppCompatActivity {
    private LinearLayout linearLayoutParent;
    private ImageView imageViewProfil;
    private TextView textViewNume;
    private TextView textViewEmail;
    private TextView textViewNumarCalatorie;
    private Button buttonAdauga;
    private TextView textViewRating;
    private RatingBar ratingBar;
    private EditText editTextDescriere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_recenzie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Adauga recenzie");
        final int idUtilizator = NavigationActivity.account.getId();
        final int idUtilizatorVizat = getIntent().getExtras().getInt("id_utilizator_vizat");
        final int idCalatorie = getIntent().getExtras().getInt("id_calatorie");
        String nume = getIntent().getExtras().getString("nume");
        String email = getIntent().getExtras().getString("email");

        linearLayoutParent = (LinearLayout) findViewById(R.id.parent_view);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        textViewNume = (TextView) findViewById(R.id.textViewNume);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewNumarCalatorie = (TextView) findViewById(R.id.textViewNumarCalatorie);
        buttonAdauga = (Button) findViewById(R.id.buttonAdauga);
        textViewRating = (TextView) findViewById(R.id.textViewRating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        editTextDescriere = (EditText) findViewById(R.id.editTextDescriere);

        File mypath=new File(getFilesDir(),"photo_" + String.valueOf(idUtilizatorVizat) + ".png");
        if (mypath.exists()) {
            Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
            imageViewProfil.setImageDrawable(photo);
        }
        textViewNume.setText(nume);
        textViewEmail.setText("(" + email + ")");
        textViewNumarCalatorie.setText("Calatoria " + String.valueOf(idCalatorie));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating > 1) {
                    DecimalFormat df = new DecimalFormat("#.0");
                    textViewRating.setText(String.valueOf(df.format(rating)));
                } else {
                    textViewRating.setText(String.valueOf(rating));
                }
            }
        });

        buttonAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ratingBar.getRating() != 0){
                    if(editTextDescriere.getText().length() >= 10){
                        Bundle bundle = new Bundle();
                        bundle.putString("id_calatorie", String.valueOf(idCalatorie));
                        bundle.putString("id_utilizator", String.valueOf(idUtilizator));
                        bundle.putString("id_utilizator_vizat", String.valueOf(idUtilizatorVizat));
                        bundle.putString("scor", String.valueOf(ratingBar.getRating()));
                        bundle.putString("descriere", editTextDescriere.getText().toString());

                        AdaugaRecenzieTask adaugaRecenzieTask = new AdaugaRecenzieTask(bundle, AdaugaRecenzieActivity.this, new TaskCompletedInteger() {
                            @Override
                            public void onTaskCompleted(Integer result) {
                                if(result == 1){
                                    buttonAdauga.setEnabled(false);
                                    editTextDescriere.setEnabled(false);
                                    View view = AdaugaRecenzieActivity.this.getCurrentFocus();
                                    if (view != null) {
                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    }
                                    SpannableStringBuilder builder = new SpannableStringBuilder();
                                    builder.append(" ");
                                    builder.setSpan(new ImageSpan(AdaugaRecenzieActivity.this, R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
                                    builder.append(" Recenzia a fost adaugata!");
                                    Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            SystemClock.sleep(2500);
                                            AdaugaRecenzieActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    finish();
                                                }
                                            });
                                        }
                                    }.start();
                                }else if(result == 2){
                                    buttonAdauga.setEnabled(false);
                                    editTextDescriere.setEnabled(false);
                                    View view = AdaugaRecenzieActivity.this.getCurrentFocus();
                                    if (view != null) {
                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    }
                                    SpannableStringBuilder builder = new SpannableStringBuilder();
                                    builder.append(" ");
                                    builder.setSpan(new ImageSpan(AdaugaRecenzieActivity.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                                    builder.append(" Ai adaugat deja o recenzie.");
                                    Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            SystemClock.sleep(2500);
                                            AdaugaRecenzieActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    finish();
                                                }
                                            });
                                        }
                                    }.start();
                                }else if(result == 0){
                                    View view = AdaugaRecenzieActivity.this.getCurrentFocus();
                                    if (view != null) {
                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    }
                                    SpannableStringBuilder builder = new SpannableStringBuilder();
                                    builder.append(" ");
                                    builder.setSpan(new ImageSpan(AdaugaRecenzieActivity.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                                    builder.append(" Nu s-a putut adauga recenzia, reincearca!");
                                    Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
                        adaugaRecenzieTask.execute();
                    }else{
                        View view = AdaugaRecenzieActivity.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append(" ");
                        builder.setSpan(new ImageSpan(AdaugaRecenzieActivity.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                        builder.append(" Descrierea trebuie sa aiba minim 10 caractere.");
                        Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    View view = AdaugaRecenzieActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    builder.append(" ");
                    builder.setSpan(new ImageSpan(AdaugaRecenzieActivity.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                    builder.append(" Setati valoarea scorului.");
                    Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
