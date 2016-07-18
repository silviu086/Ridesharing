package com.example.silviu086.licenta;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class TrimiteMesajActivity extends AppCompatActivity {
    private LinearLayout linearLayoutParent;
    private ImageView imageViewProfil;
    private TextView textViewNume;
    private TextView textViewEmail;
    private ImageButton imageButtonTrimite;
    private TextView textViewSubiect;
    private EditText editTextMesaj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimite_mesaj);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Trimite mesaj");
        final int idCalatorie = getIntent().getExtras().getInt("id_calatorie");
        final int idExpeditor = NavigationActivity.account.getId();
        final int idDestinatar = getIntent().getExtras().getInt("id_destinatar");
        String nume = getIntent().getExtras().getString("nume");
        String email = getIntent().getExtras().getString("email");

        linearLayoutParent = (LinearLayout) findViewById(R.id.parent_view);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
        textViewNume = (TextView) findViewById(R.id.textViewNume);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        imageButtonTrimite = (ImageButton) findViewById(R.id.imageButtonTrimite);
        textViewSubiect = (TextView) findViewById(R.id.textViewSubiect);
        editTextMesaj = (EditText) findViewById(R.id.editTextMesaj);

        File mypath=new File(getFilesDir(),"photo_" + String.valueOf(idDestinatar) + ".png");
        if (mypath.exists()) {
            Drawable photo = Drawable.createFromPath(mypath.getAbsolutePath());
            imageViewProfil.setImageDrawable(photo);
        }
        textViewNume.setText(nume);
        textViewEmail.setText(email);
        textViewSubiect.setText("Subiect: Calatoria " + String.valueOf(idCalatorie));
        imageButtonTrimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextMesaj.getText().length()>=10){
                    Bundle bundle = new Bundle();
                    bundle.putString("id_calatorie", String.valueOf(idCalatorie));
                    bundle.putString("id_expeditor", String.valueOf(idExpeditor));
                    bundle.putString("id_destinatar", String.valueOf(idDestinatar));
                    bundle.putString("mesaj", editTextMesaj.getText().toString());
                    TrimiteMesajTask trimiteMesajTask = new TrimiteMesajTask(bundle, TrimiteMesajActivity.this, new TaskCompletedInteger() {
                        @Override
                        public void onTaskCompleted(Integer result) {
                            if(result == 1){
                                imageButtonTrimite.setEnabled(false);
                                editTextMesaj.setEnabled(false);
                                View view = TrimiteMesajActivity.this.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(" ");
                                builder.setSpan(new ImageSpan(TrimiteMesajActivity.this, R.drawable.snackbar_ok), builder.length() - 1, builder.length(), 0);
                                builder.append(" Mesajul a fost trimis.");
                                Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                                new Thread() {
                                    @Override
                                    public void run() {
                                        SystemClock.sleep(2500);
                                        TrimiteMesajActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        });
                                    }
                                }.start();
                            }else if(result == 2){
                                imageButtonTrimite.setEnabled(false);
                                editTextMesaj.setEnabled(false);
                                View view = TrimiteMesajActivity.this.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(" ");
                                builder.setSpan(new ImageSpan(TrimiteMesajActivity.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                                builder.append(" Ai trimis deja un mesaj, vezi conversatiile.");
                                Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                                new Thread() {
                                    @Override
                                    public void run() {
                                        SystemClock.sleep(2500);
                                        TrimiteMesajActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        });
                                    }
                                }.start();
                            }else if(result == 0){
                                View view = TrimiteMesajActivity.this.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                builder.append(" ");
                                builder.setSpan(new ImageSpan(TrimiteMesajActivity.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                                builder.append(" Nu s-a putut trimite mesajul, reincearca!");
                                Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                    trimiteMesajTask.execute();

                }else{
                    View view = TrimiteMesajActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    builder.append(" ");
                    builder.setSpan(new ImageSpan(TrimiteMesajActivity.this, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                    builder.append(" Mesajul trebuie sa aiba minim 10 caractere!");
                    Snackbar.make(linearLayoutParent, builder, Snackbar.LENGTH_SHORT).show();
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
