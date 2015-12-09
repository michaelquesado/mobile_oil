package br.com.fjn.mobileoil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;

import br.com.fjn.mobileoil.dao.DadosUsuario;

/**
 * Created by Junior Siqueira on 06/11/2015.
 */

public class PerfilDoUsuario extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {

    private TextView nomeUsuario;
    private TextView emailUsuario;
    private static final int PROFILE_PIC_SIZE = 400;
    private Button botaoSair;
    GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;
    private ImageView imgProfilePic;
    private SignInButton mEntrarGooglePlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_do_usuario);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        nomeUsuario = (TextView) findViewById(R.id.userName);
        emailUsuario = (TextView) findViewById(R.id.userEmail);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        botaoSair = (Button) findViewById(R.id.botaoLogout);
        botaoSair.setOnClickListener(this);
        mEntrarGooglePlus = (SignInButton) findViewById(R.id.entrar_com_google);
        mEntrarGooglePlus.setOnClickListener(this);


    }


    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.botaoLogout) {
            desconectaUsuario();
        }else{
            Intent LoginGoogleIntent = new Intent(this, LoginGoogle.class);
            startActivity(LoginGoogleIntent);
        }
    }

    public void desconectaUsuario() {
        if (mGoogleApiClient.isConnected()) {
            DadosUsuario dadosUsuario = new DadosUsuario(getApplicationContext());
            dadosUsuario.limparLogin();
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            //Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void getDataProfile() {
        Person userProfile = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (userProfile != null) {
            String name = userProfile.getDisplayName();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            String personPhotoUrl = userProfile.getImage().getUrl();
            nomeUsuario.setText(name);
            emailUsuario.setText(email);
            personPhotoUrl = personPhotoUrl.substring(0, personPhotoUrl.length() - 2) + PROFILE_PIC_SIZE;
            new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
            botaoSair.setVisibility(View.VISIBLE);
            mEntrarGooglePlus.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        getDataProfile();
        mSignInClicked = false;
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Result result) {

    }

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
