package br.com.fjn.mobileoil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class PreferenciasActivity extends Activity implements View.OnClickListener {

    private SharedPreferences preferences;

    private Button mBotaoContinuar;

    private CheckBox mPreferenciaAlcool;
    private CheckBox mPreferenciaDiesel;
    private CheckBox mPreferenciaGasolina;

    private boolean spAlcool;
    private boolean spDiesel;
    private boolean spGasolina;

    private TextView texto;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        initComponents();
        mBotaoContinuar.setOnClickListener(this);
        preferences = getPreferences(MODE_PRIVATE);

        getPreferencias();

        texto = (TextView) findViewById(R.id.tx);
        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("NOME")){
            String nome = bundle.getString("NOME");
            texto.setText("Bem vindo "+nome);
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        googleApiClient.connect();

    }

    private void setPreferencias() {
        spAlcool = mPreferenciaAlcool.isChecked();
        spDiesel = mPreferenciaDiesel.isChecked();
        spGasolina = mPreferenciaGasolina.isChecked();
    }

    public void getPreferencias() {
        spAlcool = preferences.getBoolean("prefAlcool", false);
        spDiesel = preferences.getBoolean("prefDiesel", false);
        spGasolina = preferences.getBoolean("prefGasolina", false);
    }

    // Seleciona os check box a partir das preferências
    public void setaCheckBox() {
        mPreferenciaAlcool.setChecked(spAlcool);
        mPreferenciaDiesel.setChecked(spDiesel);
        mPreferenciaGasolina.setChecked(spGasolina);
    }

    private void salvarPreferencias() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("prefAlcool", spAlcool);
        editor.putBoolean("prefDiesel", spDiesel);
        editor.putBoolean("prefGasolina", spGasolina);
        Log.i("PREFERENCIAS", "Preferencias Salvas");
        editor.commit();
    }

    // inicializa os componentes da view
    public void initComponents() {
        mBotaoContinuar = (Button) findViewById(R.id.continuar_preferencias);
        mPreferenciaAlcool = (CheckBox) findViewById(R.id.preferenciaAlcool);
        mPreferenciaDiesel = (CheckBox) findViewById(R.id.preferenciaDiesel);
        mPreferenciaGasolina = (CheckBox) findViewById(R.id.preferenciaGasolina);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferencias, menu);
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
            Intent userProfile = new Intent(this, PerfilDoUsuario.class);
            startActivity(userProfile);
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
        setPreferencias();
        salvarPreferencias();
        getPreferencesToString();
        Intent it = new Intent(this, CombustivelActivity.class);
        startActivity(it);
    }

    private void getPreferencesToString() {
        StringBuilder builder = new StringBuilder("Prefencias do usuario");
        builder.append("spAlcool: " + spAlcool + "    ");
        builder.append("spDiesel: " + spDiesel + "    ");
        builder.append("spGasolina: " + spGasolina + "    ");
        Log.i("PREFERENCIAS", builder.toString());
    }
}