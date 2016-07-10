package com.example.silviu086.licenta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private final int navigationItemNumber = 5;

    //Defining Variables
    public static Account account;
    public static CalatoriiHolder calatoriiHolder;
    public static Toolbar toolbar;
    private static NavigationView navigationView;
    private static android.support.v4.app.FragmentTransaction fragmentTransaction;
    public static FragmentManager fragmentManager;
    private static ContFragment fragmentCont;
    private static CautaFragment fragmentCauta;
    private static AdaugaFragment fragmentAdauga;
    private static CalatoriiFragment fragmentCalatorii;
    private static SetariFragment fragmentSetari;
    private TextView navigationEmail;
    private TextView navigationNume;
    private MesajeFragment fragmentMesaje;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_navigation);
        navigationEmail = (TextView) headerLayout.findViewById(R.id.emailNavigation);
        navigationNume = (TextView) headerLayout.findViewById(R.id.numeNavigation);
        account = (Account) getIntent().getExtras().getSerializable("account");
        navigationNume.setText(account.getNume());
        navigationEmail.setText(account.getEmail());
        //Initializing fragments
        fragmentCont = ContFragment.newInstance(account);
        fragmentCauta = CautaFragment.newInstance(account);
        fragmentAdauga = AdaugaFragment.newInstance(account);
        fragmentCalatorii = CalatoriiFragment.newInstance(calatoriiHolder);
        fragmentSetari = SetariFragment.newInstance(account);
        fragmentMesaje = MesajeFragment.newInstance(account);

        fragmentCalatorii.setIndexPage(0);
        //Default navigation select
        navigationView.getMenu().findItem(R.id.calatorii).setChecked(true);

        //Default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentPanel, fragmentCalatorii);
        fragmentTransaction.commit();

        //GCM

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        // Start IntentService to register this application with GCM.
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Inchidere aplicatie")
                    .setMessage("Doriti sa inchideti aplicatia?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.exit = true;
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mesaje){
            Intent it = new Intent(this, MesajeActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final ProgressDialog dialog = new ProgressDialog(NavigationActivity.this);
        dialog.setTitle("Loading...");
        dialog.setMessage("Se incarca pagina");
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (item.getItemId()) {
            case R.id.account:
                dialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(300);
                        NavigationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                drawer.closeDrawers();
                                dialog.dismiss();
                            }
                        });
                    }
                }.start();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentPanel, fragmentCont);
                fragmentTransaction.commit();
                toolbar.setTitle("Contul meu");
                break;

            case R.id.cauta:
                dialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(700);
                        NavigationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                drawer.closeDrawers();
                                dialog.dismiss();
                            }
                        });
                    }
                }.start();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentCauta = CautaFragment.newInstance(account);
                fragmentTransaction.replace(R.id.contentPanel, fragmentCauta);
                fragmentTransaction.commit();
                toolbar.setTitle("Cauta o calatorie");
                break;

            case R.id.adauga:
                dialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(700);
                        NavigationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                drawer.closeDrawers();
                                dialog.dismiss();
                            }
                        });
                    }
                }.start();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentAdauga = AdaugaFragment.newInstance(account);
                fragmentTransaction.replace(R.id.contentPanel, fragmentAdauga);
                fragmentTransaction.commit();
                toolbar.setTitle("Adauga o calatorie");
                break;

            case R.id.calatorii:
                dialog.show();
                CalatoriiFragmentTask task = new CalatoriiFragmentTask(account.getId(), new TaskCompletedCalatorii() {
                    @Override
                    public void onTaskCompleted(CalatoriiHolder result) {
                        calatoriiHolder = result;
                        fragmentCalatorii = CalatoriiFragment.newInstance(calatoriiHolder);
                        fragmentCalatorii.setIndexPage(0);
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.contentPanel, fragmentCalatorii);
                        fragmentTransaction.commit();
                        toolbar.setTitle("Calatoriile mele");
                        drawer.closeDrawers();
                        dialog.dismiss();
                    }
                });
                task.execute();
                break;

            case R.id.setari:
                dialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(300);
                        NavigationActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                drawer.closeDrawers();
                                dialog.dismiss();
                            }
                        });
                    }
                }.start();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentPanel, fragmentSetari);
                fragmentTransaction.commit();
                toolbar.setTitle("Setari");
                break;
            case R.id.logout:
                SharedPreferences.Editor edit = MainActivity.sharedPreferences.edit();
                edit.remove("username");
                edit.remove("password");
                edit.remove("id");
                edit.commit();
                finish();
                break;

            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    public static void setFragmentCalatorii(final int page){
        calatoriiHolder = null;
        CalatoriiFragmentTask task = new CalatoriiFragmentTask(account.getId(), new TaskCompletedCalatorii() {
            @Override
            public void onTaskCompleted(CalatoriiHolder result) {
                calatoriiHolder = result;
                fragmentCalatorii = CalatoriiFragment.newInstance(calatoriiHolder);
                fragmentCalatorii.setIndexPage(page);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentPanel, fragmentCalatorii);
                fragmentTransaction.commit();
                toolbar.setTitle("Calatoriile mele");
                navigationView.setCheckedItem(navigationView.getMenu().getItem(3).getItemId());;
            }
        });
        task.execute();
    }

    public static void setFragmentCalatoriiSortare(final int page){
        fragmentCalatorii = CalatoriiFragment.newInstance(calatoriiHolder);
        fragmentCalatorii.setIndexPage(page);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentPanel, fragmentCalatorii);
        fragmentTransaction.commit();
        toolbar.setTitle("Calatoriile mele");
        navigationView.setCheckedItem(navigationView.getMenu().getItem(3).getItemId());;
    }

    public static void setFragment(int index){
        final int i = index;
        if(i == 1){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentPanel, fragmentCont);
            fragmentTransaction.commit();
            toolbar.setTitle("Contul meu");
            navigationView.setCheckedItem(navigationView.getMenu().getItem(i - 1).getItemId());
        }else if(i == 2){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentPanel, fragmentCauta);
            fragmentTransaction.commit();
            toolbar.setTitle("Cauta o calatorie");
            navigationView.setCheckedItem(navigationView.getMenu().getItem(i - 1).getItemId());
        }else if(i == 3){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentPanel, fragmentAdauga);
            fragmentTransaction.commit();
            toolbar.setTitle("Adauga o calatorie");
            navigationView.setCheckedItem(navigationView.getMenu().getItem(i - 1).getItemId());
        }else if(i == 4){
            CalatoriiFragmentTask task = new CalatoriiFragmentTask(account.getId(), new TaskCompletedCalatorii() {
                @Override
                public void onTaskCompleted(CalatoriiHolder result) {
                    calatoriiHolder = result;
                    fragmentCalatorii = CalatoriiFragment.newInstance(calatoriiHolder);
                    fragmentCalatorii.setIndexPage(0);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contentPanel, new CalatoriiFragment());
                    fragmentTransaction.commit();
                    toolbar.setTitle("Calatoriile mele");
                    navigationView.setCheckedItem(navigationView.getMenu().getItem(i - 1).getItemId());
                }
            });
            task.execute();
        }else if(i == 5){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentPanel, fragmentSetari);
            fragmentTransaction.commit();
            toolbar.setTitle("Setari");
            navigationView.setCheckedItem(navigationView.getMenu().getItem(i - 1).getItemId());
        }
    }
}