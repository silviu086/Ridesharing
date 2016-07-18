package com.example.silviu086.licenta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ContRecenziiActivity extends AppCompatActivity {
    private Account account;
    private TextView textViewNumarRecenzii;
    private TextView textViewScorRecenzii;
    private RatingBar ratingBar;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont_recenzii);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        account = (Account)getIntent().getExtras().getSerializable("account");
        getSupportActionBar().setTitle("Recenzii");
        getSupportActionBar().setSubtitle(account.getEmail());

        textViewNumarRecenzii = (TextView) findViewById(R.id.textViewNumarRecenzii);
        textViewScorRecenzii = (TextView) findViewById(R.id.textViewScorRecenzii);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        listView = (ListView) findViewById(R.id.listView);

        float medie = 0;
        for(int i=0;i<account.getListaRecenzii().size();i++){
            medie += account.getListaRecenzii().get(i).getScor();
        }
        medie = medie/account.getListaRecenzii().size();
        DecimalFormat df = new DecimalFormat("#.0");
        if(account.getListaRecenzii().size() == 1){
            textViewNumarRecenzii.setText("1 recenzie");
        }else{
            textViewNumarRecenzii.setText(String.valueOf(account.getListaRecenzii().size()) + " recenzii");
        }
        textViewScorRecenzii.setText(String.valueOf(df.format(medie)));
        ratingBar.setRating(medie);
        ContRecenziiAdapter adapter = new ContRecenziiAdapter(this, account.getListaRecenzii());
        listView.setAdapter(adapter);
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
