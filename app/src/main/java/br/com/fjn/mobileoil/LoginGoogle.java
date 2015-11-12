package br.com.fjn.mobileoil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import br.com.fjn.mobileoil.dao.LoginWebService;

/**
 * Created by Junior Siqueira on 05/11/2015.
 */

public class LoginGoogle extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;  //inicializada com false
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Aguarde, estamos conectando você ao Google! Processando...");
        progressDialog.show();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

    }


    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop(){
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        getDataProfile();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(!mIntentInProgress && connectionResult.hasResolution()){
            try{
                mIntentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e){
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void getDataProfile(){
        new Thread(){
            public void run(){
                Person userProfile = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                if(userProfile != null){
                    String username = userProfile.getDisplayName();
                    String pass = userProfile.getId();
                    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                        if(LoginWebService.validarUserBanco(pass, email).equalsIgnoreCase("false")){
                            LoginWebService.cadastrarDadosDoLogin(username, pass, email);
                            System.out.println("Usuario cadastrado com sucesso :D!");
                        }else{
                            System.out.println("Usuario já possui registro no banco!");
                        }

                    Log.i("LOGINUSER", "" + pass + " - " + username + " - " + email);
                }
            }
        }.start();
        Intent intent = new Intent(this, PreferenciasActivity.class);
        startActivity(intent);
    }
}
