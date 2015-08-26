package br.com.fjn.mobileoil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener,LocationListener {
    private Button mEntrarFacebook;
    private Button mEntrarGooglePlus;
    private Button mEntrarSemCadastro;

    private String TAG = "-----------------------------------";
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEntrarFacebook = (Button) findViewById(R.id.entrar_com_facebook);
        mEntrarGooglePlus = (Button) findViewById(R.id.entrar_com_google);
        mEntrarSemCadastro = (Button) findViewById(R.id.entrar_sem_cadastro);

        mEntrarFacebook.setOnClickListener(this);
        mEntrarGooglePlus.setOnClickListener(this);
        mEntrarSemCadastro.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        Log.i(TAG, "-------------------------------------"+ provider);
        Location location = locationManager.getLastKnownLocation(provider);
        Log.i(TAG, "-------------------------------------"+ location);
        if(location !=null){
            Log.i(TAG, "------------------------------------------------- Entrou no If");
            Log.i(TAG, "Provider :" + provider + " foi selecionado ");
        }else{
            Log.i(TAG, "------------------------------------------------- ENtrou no else");
            onProviderDisabled(provider);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, PreferenciasActivity.class);
        startActivity(it);
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "Chamou o metodo onResume");
        super.onResume();
        locationManager.requestLocationUpdates(provider, 0, 0, this);
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "Chamou o metodo onPause");
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Chamou o metodo onLocationChange");
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        Log.i(TAG, "----------------------------------------------"+lat);
        Log.i(TAG, "----------------------------------------------"+lng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "Chamou o metodo onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "Chamou o metodo onProviderEnable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "Chamou o metodo onProviderDiseble");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("O GPS está desligado, deseja ativa-lo agora?").setCancelable(false).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGpsSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGpsSettingIntent);
            }
        });
        alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Para usar o APP é preciso ativar GPS!!!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}