package br.com.fjn.mobileoil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.common.SignInButton;


import br.com.fjn.mobileoil.dao.TelaConfigDAO;

import br.com.fjn.mobileoil.utils.LatitudeLongitude;

public class MainActivity extends Activity implements View.OnClickListener, LocationListener {

    private Button mEntrarFacebook;
    private SignInButton mEntrarGooglePlus;
    private Button mEntrarSemCadastro;

    private String TAG = "MO_ACTIVITY_MAIN";
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEntrarGooglePlus = (SignInButton) findViewById(R.id.entrar_com_google);
        mEntrarSemCadastro = (Button) findViewById(R.id.entrar_sem_cadastro);
        
        mEntrarGooglePlus.setOnClickListener(this);
        mEntrarSemCadastro.setOnClickListener(this);

        TelaConfigDAO telaConfigDAO = new TelaConfigDAO(this);
        if (!telaConfigDAO.isMostrarTela("login_inicial")) {
            abrirTelaConfigPreferencias();
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

        if (v.getId() == R.id.entrar_com_google) {
            Intent LoginGoogleIntent = new Intent(this, LoginGoogle.class);
            startActivity(LoginGoogleIntent);
        } else if (v.getId() == R.id.entrar_com_facebook) {
            Intent LoginFacebookIntent = new Intent(this, PreferenciasActivity.class);
            String nome = "Ainda não implementamos";
            LoginFacebookIntent.putExtra("NOME", nome);
            startActivity(LoginFacebookIntent);
        } else {
            Intent EntrarSemCadastro = new Intent(this, CombustivelActivity.class);
            startActivity(EntrarSemCadastro);
        }

        TelaConfigDAO telaConfigDAO = new TelaConfigDAO(this);
        telaConfigDAO.ocultarTela("login_inicial", false);
        abrirTelaConfigPreferencias();
    }

    public void abrirTelaConfigPreferencias() {
        Intent it = new Intent(this, PreferenciasActivity.class);
        startActivity(it);

    }


    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            onProviderDisabled(LocationManager.GPS_PROVIDER);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChange");
        String latlog = location.getLatitude() + "," + location.getLongitude();
        LatitudeLongitude.setLatitudeLongitude(latlog);
        Log.i(TAG, "onLocationChange :: " + LatitudeLongitude.getLatitudeLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "onProviderEnable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "onProviderDisable");
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
                Toast.makeText(getApplicationContext(), "Para usar o APP é preciso ativar GPS.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}